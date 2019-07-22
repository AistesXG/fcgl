package com.fcgl.controller;

import com.fcgl.auth.UserRoleEnum;
import com.fcgl.common.entity.ParamRequest;
import com.fcgl.common.request.BatchDeleteRequest;
import com.fcgl.domain.request.DormRequest;
import com.fcgl.domain.service.DormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author furg@senthink.com
 * @date 2019/1/25
 */
@RestController
@Api(tags = "dorm", description = "宿舍楼管理")
@Secured({UserRoleEnum.ROLE_ROOT_VALUE, UserRoleEnum.ROLE_SUPER_ROOT_VALUE})
public class DormController {

    @Autowired
    private DormService dormService;

    @PostMapping(value = "${uri.dorms.add}")
    @ApiOperation(value = "添加宿舍", notes = "添加宿舍")
    public ResponseEntity<?> add(@RequestBody @Valid DormRequest request) {
        return ResponseEntity.ok(dormService.add(request));
    }

    @DeleteMapping(value = "${uri.dorms.batchDelete}")
    @ApiOperation(value = "批量删除宿舍", notes = "批量删除宿舍")
    public ResponseEntity<?> batchDelete(@RequestBody @Valid BatchDeleteRequest request) {
        return ResponseEntity.ok(dormService.batchDelete(request));
    }

    @PostMapping(value = "${uri.dorms.list}")
    @ApiOperation(value = "宿舍楼分页列表", notes = "宿舍楼分页列表")
    public ResponseEntity<?> findAll(@RequestBody @Valid ParamRequest request) {
        return ResponseEntity.ok(dormService.findAll(request));
    }

    @GetMapping(value = "${uri.dorms.detail}")
    @ApiOperation(value = "宿舍楼详情", notes = "宿舍楼详情")
    public ResponseEntity<?> detail(@RequestParam String did) {
        return ResponseEntity.ok(dormService.findOne(did));
    }

    @PostMapping(value = "${uri.dorms.update}")
    @ApiOperation(value = "编辑宿舍楼信息", notes = "编辑宿舍楼信息")
    public ResponseEntity<?> update(@RequestParam String did, @RequestBody @Valid DormRequest request) {
        return ResponseEntity.ok(dormService.update(did, request));
    }
}
