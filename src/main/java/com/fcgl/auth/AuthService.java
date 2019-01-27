package com.fcgl.auth;


import com.fcgl.common.entity.ParamRequest;
import com.fcgl.common.exception.BusinessException;
import com.fcgl.common.exception.DataAccessException;
import com.fcgl.common.util.RandomUtils;
import com.fcgl.domain.entity.User;
import com.fcgl.domain.service.UserService;
import com.fcgl.messages.CodeMsg;
import com.fcgl.response.ApiResponse;
import com.fcgl.response.CodeMsgDataResponse;
import com.fcgl.security.CurrentUser;
import com.fcgl.security.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author furg@senthink.com
 * @date 2018/11/8
 */
@Service
public class AuthService {

    @Value("${jwt.prefix}")
    private String tokenPrefix;
    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Transactional(rollbackFor = {DataAccessException.class, BusinessException.class})
    public ApiResponse register(RegisterUserRequest request) {

        if (userService.isUserExist(request.getAccount(), request.getMobile(), request.getEmail())) {
            throw new BusinessException(codeMsg.userExistCode(), codeMsg.userExistMsg());
        }
        final String password = request.getPassword();
        request.setPassword(passwordEncoder.encode(password));
        User user = RegisterUserRequest.convertTo(request);
        user.setLastPwdRestDate(new Date());
        user.setUid(RandomUtils.randomString(30));
        user.setEnable(true);
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), userService.saveUser(user));
    }


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public ApiResponse login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(upToken);
        } catch (DisabledException e) {
            throw new BusinessException(codeMsg.userDisabledCode(), codeMsg.userDisabledMsg());
        } catch (Exception e) {
            throw new BusinessException(codeMsg.accountErrorCode(), codeMsg.accountErrorMsg());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        String token = jwtTokenUtils.generateToken(currentUser);
        long expireAt = jwtTokenUtils.getExpirationDateFromToken(token).getTime();
        TokenResponse tokenResponse = new TokenResponse(token, expireAt, currentUser.getRole());
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), tokenResponse);
    }

    /**
     * 刷新Token操作
     *
     * @param oldToken 旧的Token
     * @return an instance of {@link ApiResponse}
     */
    public ApiResponse refresh(String oldToken) {
        if (StringUtils.isBlank(oldToken)) {
            return new CodeMsgDataResponse(codeMsg.tokenErrorCode(), codeMsg.tokenErrorMsg());
        }
        String token = oldToken.substring(tokenPrefix.length());
        String username = jwtTokenUtils.getUsernameFromToken(token);
        CurrentUser currentUser = (CurrentUser) userDetailsService.loadUserByUsername(username);
        boolean canRefresh = jwtTokenUtils.canTokenBeRefreshed(token, currentUser.getLastPwdResetDate());
        if (canRefresh) {
            String newToken = jwtTokenUtils.refreshToken(token);
            long expireAt = jwtTokenUtils.getExpirationDateFromToken(newToken).getTime();
            TokenResponse tokenResponse = new TokenResponse(token, expireAt, currentUser.getRole());
            return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), tokenResponse);
        }
        return new CodeMsgDataResponse(codeMsg.failureCode(), codeMsg.failureMsg());
    }

    /**
     * 获取用户分页列表
     *
     * @param enable
     * @param role
     * @param request
     * @return
     */
    public ApiResponse findAll(Boolean enable, String role, ParamRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest page = request.getPageDto().convertToPageRequest(sort);
        Page<User> list = userService.findAll(enable, role, page, request.getSearchRequest());
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), list);
    }
}
