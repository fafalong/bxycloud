package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.dto.RoleSaveDTO;
import com.boxiaoyun.system.client.model.dto.UserRoleSaveDTO;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemRoleUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface ISystemRoleServiceClient {


    /**
     * 获取角色列表
     *
     * @param map
     * @return
     */
    @GetMapping("/role/page")
    ResultBody<Page<SystemRole>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("/role/list")
    ResultBody<List<SystemRole>> getList();

    /**
     * 获取角色详情
     */
    @GetMapping("/role/info")
    ResultBody<SystemRole> get(@RequestParam(value = "roleId") Long roleId);

    /**
     * 添加/修改角色
     *
     * @param systemRole
     * @return
     */
    @ApiOperation(value = "", notes = "")
    @PostMapping("/role/save")
    ResultBody<Long> save(@RequestBody @Validated RoleSaveDTO data);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @PostMapping("/role/remove")
    ResultBody remove(@RequestParam(value = "roleId") Long roleId);

    /**
     * 角色添加成员列表
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @PostMapping("/role/save/users")
    //ResultBody saveUsers(@RequestParam(value = "roleId", required = false) Long roleId, @RequestParam(value = "userIds", required = true) String userIds);
    ResultBody saveUsers(@RequestBody UserRoleSaveDTO userRole);

    /**
     * 获取角色成员列表
     *
     * @param roleId
     * @return
     */
    @GetMapping("/role/users")
    ResultBody<List<SystemRoleUser>> getRoleUsers(@RequestParam(value = "roleId") Long roleId);

}
