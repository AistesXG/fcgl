package com.fcgl.controller;

import com.fcgl.auth.UserRoleEnum;
import com.fcgl.common.entity.ParamRequest;
import com.fcgl.common.request.BatchDeleteRequest;
import com.fcgl.domain.request.CampusRequest;
import com.fcgl.domain.service.CampusService;
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

    @DeleteMapping(value = "${uri.campus.batchDelete}")
    @ApiOperation(value = "批量删除校区", notes = "批量删除校区")
    public ResponseEntity<?> batchDelete(@RequestBody @Valid BatchDeleteRequest request) {
        ApiResponse response = campusService.batchDelete(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "${uri.campus.list}")
    @ApiOperation(value = "校区分页列表", notes = "校区分页列表")
    public ResponseEntity<?> findAll(@RequestBody @Valid ParamRequest request) {
        return ResponseEntity.ok(campusService.findAll(request));
    }

    @PostMapping(value = "${uri.campus.update}")
    @ApiOperation(value = "编辑校区信息", notes = "编辑校区信息")
    public ResponseEntity<?> update(@RequestParam String cid, @RequestBody @Valid CampusRequest request) {
        return ResponseEntity.ok(campusService.update(cid, request));
    }
}
