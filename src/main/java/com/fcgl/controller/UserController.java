package com.fcgl.controller;

import com.fcgl.auth.AuthService;
import com.fcgl.auth.UserRoleEnum;
import com.fcgl.common.entity.ParamRequest;
import com.fcgl.common.request.BatchDeleteRequest;
import com.fcgl.domain.request.UserUpdateRequest;
import com.fcgl.domain.service.UserService;
import com.fcgl.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@RestController
@Api(tags = "USER", description = "用户管理")
@Secured({UserRoleEnum.ROLE_ROOT_VALUE, UserRoleEnum.ROLE_SUPER_ROOT_VALUE})
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "${uri.users.list}")
    @ApiOperation(value = "用户分页列表", notes = "用户分页列表")
    public ResponseEntity<?> findAll(@RequestParam(required = false) Boolean enable,
                                     @RequestParam(required = false) String role,
                                     @RequestBody @Valid ParamRequest request) {
        ApiResponse response = authService.findAll(enable, role, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "${uri.users.update}")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public ResponseEntity<?> updateUser(@RequestParam String uid,
                                        @RequestBody @Valid UserUpdateRequest request) {
        ApiResponse response = userService.updateUser(uid, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "${uri.users.detail}")
    @ApiOperation(value = "用户信息详情", notes = "用户信息详情")
    public ResponseEntity<?> findOne(@RequestParam String uid) {
        ApiResponse response = userService.findOne(uid);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "${uri.users.batchDelete}")
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    public ResponseEntity<?> batchDelete(@RequestBody @Valid BatchDeleteRequest request) {
        ApiResponse response = userService.batchDelete(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "${uri.users.enable}")
    @ApiOperation(value = "启用/禁用用户", notes = "启用/禁用用户")
    public ResponseEntity<?> enabled(@RequestBody @Valid BatchDeleteRequest request, @RequestParam boolean enable) {
        ApiResponse response = userService.enabled(request, enable);
        return ResponseEntity.ok(response);
    }
}
