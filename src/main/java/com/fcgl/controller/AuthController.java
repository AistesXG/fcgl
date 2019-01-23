package com.fcgl.controller;

import com.fcgl.auth.LoginRequest;
import com.fcgl.auth.RegisterUserRequest;
import com.fcgl.auth.UserRoleEnum;
import com.fcgl.auth.AuthService;
import com.fcgl.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author furg@senthink.com
 * @date 2018/11/8
 */
@RestController
@Api(tags = "Authentication", description = "鉴权管理")
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private AuthService authService;

    @PostMapping(value = "${uri.auth.login}")
    @ApiOperation(value = "登录", notes = "用户登录")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        ApiResponse apiResponse = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping(value = "${uri.auth.register}")
    @ApiOperation(value = "注册", notes = "用户注册(管理员操作)")
    @Secured({UserRoleEnum.ROLE_ROOT_VALUE, UserRoleEnum.SUPER_ROLE_ROOT_VALUE})
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserRequest request) {
        ApiResponse apiResponse = authService.register(request);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(value = "${uri.auth.refresh}")
    @ApiOperation(value = "刷新token", notes = "刷新token")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        String oldToken = request.getHeader(tokenHeader);
        ApiResponse apiResponse = authService.refresh(oldToken);
        return ResponseEntity.ok(apiResponse);
    }
}
