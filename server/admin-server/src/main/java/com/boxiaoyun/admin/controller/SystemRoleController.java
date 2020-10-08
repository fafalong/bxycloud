package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemRoleServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.system.client.model.dto.RoleSaveDTO;
import com.boxiaoyun.system.client.model.dto.UserRoleSaveDTO;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemRoleUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Api(tags = "系统角色管理")
@RestController
public class SystemRoleController {
    @Autowired
    private SystemRoleServiceClient systemRoleServiceClient;

    /**
     * 获取分页角色列表
     *
     * @return
     */
    @ApiOperation(value = "获取分页角色列表", notes = "获取分页角色列表")
    @GetMapping("/role/page")
    public ResultBody<Page<SystemRole>> getPage(@RequestParam(required = false) Map map) {
        return systemRoleServiceClient.getPage(map);
    }

    /**
     * 获取所有角色列表
     *
     * @return
     */
    @ApiOperation(value = "获取所有角色列表", notes = "获取所有角色列表")
    @GetMapping("/role/list")
    public ResultBody<List<SystemRole>> getList() {
        return systemRoleServiceClient.getList();
    }

    /**
     * 获取角色详情
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "获取角色详情", notes = "获取角色详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "path")
    })
    @GetMapping("/role/info")
    public ResultBody<SystemRole> get(@RequestParam(value = "roleId") Long roleId) {
        return systemRoleServiceClient.get(roleId);
    }

    /**
     * 添加/编辑角色
     *
     * @param roleId   角色ID
     * @param code 角色编码
     * @param name 角色显示名称
     * @param describe 描述
     * @param status   启用禁用
     * @return
     */
    @ApiOperation(value = "添加/编辑角色", notes = "添加/编辑角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = false, paramType = "form"),
            @ApiImplicitParam(name = "code", value = "角色编码", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "角色显示名称", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "roleDesc", value = "描述", defaultValue = "", required = false, paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/role/save")
/*    public ResultBody save(
            @RequestParam(value = "roleId", required = false) Long roleId,
            @RequestParam(value = "code") String code,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "roleDesc", required = false) String roleDesc,
            @RequestParam(value = "status", defaultValue = "1", required = false) Integer status
    ) {*/
    public ResultBody save(@RequestBody RoleSaveDTO data) {
        data.setUserId(SecurityHelper.getUser().getUserId());
        return systemRoleServiceClient.save(data);
    }


    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/role/remove")
    public ResultBody remove(
            @RequestParam(value = "roleId") Long roleId
    ) {
        return systemRoleServiceClient.remove(roleId);
    }

    /**
     * 角色添加成员
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @ApiOperation(value = "角色添加成员", notes = "角色添加成员")
    @PostMapping("/role/users/save")
/*    public ResultBody saveUsers(
            @RequestParam(value = "roleId",required = false) Long roleId,
            @RequestParam(value = "userIds", required = true) String userIds
    ) {*/
    public ResultBody saveUsers(@RequestBody UserRoleSaveDTO userRole){
        //return systemRoleServiceClient.saveUsers(roleId, userIds);
        return systemRoleServiceClient.saveUsers(userRole);
    }

    /**
     * 查询角色成员
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "查询角色成员", notes = "查询角色成员")
    @GetMapping("/role/users")
    public ResultBody<List<SystemRoleUser>> getRoleUsers(
            @RequestParam(value = "roleId") Long roleId
    ) {
        return systemRoleServiceClient.getRoleUsers(roleId);
    }

}
