package com.fcgl.domain.service;

import com.fcgl.auth.UserRoleEnum;
import com.fcgl.common.entity.SearchRequest;
import com.fcgl.common.exception.BusinessException;
import com.fcgl.common.exception.DataAccessException;
import com.fcgl.common.request.BatchDeleteRequest;
import com.fcgl.domain.entity.User;
import com.fcgl.domain.entity.User_;
import com.fcgl.domain.repository.UserRepository;
import com.fcgl.domain.request.UserUpdateRequest;
import com.fcgl.domain.response.UserResponse;
import com.fcgl.messages.CodeMsg;
import com.fcgl.response.ApiResponse;
import com.fcgl.response.CodeMsgDataResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author furg@senthink.com
 * @date 2018/11/29
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 查找用户信息
     *
     * @param username
     * @return
     */
    public Optional<User> loadUser(String username) {
        if (StringUtils.isBlank(username)) {
            return Optional.empty();
        }
        List<User> users;
        try {
            users = userRepository.findByAccountOrMobileOrEmail(username, username, username);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        if (users == null || users.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    /**
     * 判断用户是否存在
     *
     * @param account
     * @param mobile
     * @param email
     * @return
     */
    public boolean isUserExist(String account, String mobile, String email) {
        long result;
        try {
            result = userRepository.countByAccountOrMobileOrEmail(account, mobile, email);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }

        return result > 0;
    }


    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 根据用户启用、角色、模糊搜索获取用户的分页列表
     *
     * @param enable
     * @param role
     * @param pageRequest
     * @param searchRequest
     * @return
     */
    public Page<User> findAll(Boolean enable, UserRoleEnum role, PageRequest pageRequest, SearchRequest searchRequest) {
        List<Predicate> predicates = new LinkedList<>();
        Page<User> page = userRepository.findAll((root, query, cb) -> {
            if (role != null) {
                predicates.add(cb.equal(root.get(User_.role), role.name()));
            }
            if (enable != null) {
                predicates.add(cb.equal(root.get(User_.enable), enable));
            }
            predicates.add(searchRequest.generatePredicate(root, cb));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);
        return page;
    }

    /**
     * 更新用户的信息
     *
     * @param uid
     * @param request
     * @return
     */
    public ApiResponse updateUser(String uid, UserUpdateRequest request) {
        User user = userRepository.findTopByUid(uid).orElseThrow(() -> new BusinessException(codeMsg.failureCode(), codeMsg.failureMsg()));

        //校验手机、邮箱、账户合法性
        if (!request.getMobile().equalsIgnoreCase(user.getMobile())) {
            if (userRepository.findByMobile(request.getMobile()).size() > 0) {
                return new CodeMsgDataResponse(codeMsg.userExistCode(), codeMsg.userExistMsg());
            }
        }
        if (!request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.findByEmail(request.getEmail()).size() > 0) {
                return new CodeMsgDataResponse(codeMsg.userExistCode(), codeMsg.userExistMsg());
            }
        }
        if (!request.getAccount().equalsIgnoreCase(user.getAccount())) {
            if (userRepository.findByAccount(request.getAccount()).size() > 0) {
                return new CodeMsgDataResponse(codeMsg.userExistCode(), codeMsg.userExistMsg());
            }
        }
        //验证密码是否修改
        if (StringUtils.isNotBlank(request.getPassword())) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        } else {
            request.setPassword(user.getPassword());
        }
        BeanUtils.copyProperties(request, user);
        user = userRepository.save(user);

        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), toUserResponse(user));
    }

    /**
     * 查找用户的详情
     *
     * @param uid
     * @return
     */
    public ApiResponse findOne(String uid) {
        User user = userRepository.findTopByUid(uid)
                .orElseThrow(() -> new BusinessException(codeMsg.accountNotExistCode(), codeMsg.accountNotExistMsg()));
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), toUserResponse(user));
    }

    /**
     * 批量删除用户
     *
     * @param request
     * @return
     */
    @Transactional
    public ApiResponse batchDelete(BatchDeleteRequest request) {
        long count = userRepository.deleteAllByUidIn(request.getIds());
        if (count > 0) {
            return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), count);
        }
        throw new BusinessException(codeMsg.accountNotExistCode(), codeMsg.accountNotExistMsg());
    }

    /**
     * 启用或者禁用用户
     *
     * @param request
     * @param enable
     * @return
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public ApiResponse enabled(BatchDeleteRequest request, boolean enable) {

        List<User> users = userRepository.findAllByUidIn(request.getIds());
        users.forEach(user -> user.setEnable(enable));
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), userRepository.saveAll(users));
    }

    /**
     * 隐藏密码等隐私字段
     *
     * @param user
     * @return
     */
    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

}
