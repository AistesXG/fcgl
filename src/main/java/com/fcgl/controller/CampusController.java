package com.fcgl.controller;

import com.fcgl.auth.UserRoleEnum;
import com.fcgl.domain.request.CampusRequest;
import com.fcgl.domain.service.CampusService;
import com.fcgl.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@RestController
@Api(tags = "Campus", description = "校区管理")
@Secured({UserRoleEnum.ROLE_ROOT_VALUE, UserRoleEnum.ROLE_SUPER_ROOT_VALUE})
public class CampusController {

    @Autowired
    private CampusService campusService;

    @PostMapping(value = "${uri.campus.add}")
    @ApiOperation(value = "添加校区", notes = "添加校区")
    public ResponseEntity<?> addCampus(@RequestBody @Valid CampusRequest request) {
        ApiResponse response = campusService.addCampus(request);
        return ResponseEntity.ok(response);
    }
}
