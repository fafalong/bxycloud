package com.boxiaoyun.admin.controller;

import com.boxiaoyun.admin.service.feign.SystemAuthorityServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.security.BaseAuthority;
import com.boxiaoyun.common.utils.TreeUtil;
import com.boxiaoyun.system.client.model.AuthorityApi;
import com.boxiaoyun.system.client.model.AuthorityMenu;
import com.boxiaoyun.system.client.model.dto.SysAuthorityGrantSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysRoleGrantSaveDTO;
import com.boxiaoyun.system.client.model.dto.VueRouter;
import com.boxiaoyun.system.client.model.entity.SystemAuthorityAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author:
 * @date: 2018/11/26 18:20
 * @description:
 */
@Api(tags = "系统权限管理")
@RestController
public class SystemAuthorityController {

    @Autowired
    private SystemAuthorityServiceClient systemAuthorityServiceClient;

    /**
     * 获取权限列表
     *
     * @return
     */
    @ApiOperation(value = "获取接口权限列表", notes = "获取接口权限列表")
    @GetMapping("/authority/sysApi")
    public ResultBody<List<AuthorityApi>> findAuthorityApi(
            @RequestParam(value = "serviceId", required = false) String serviceId
    ) {
        return systemAuthorityServiceClient.findAuthorityApi(serviceId);
    }


    /**
     * 获取菜单权限列表
     *
     * @return
     */
    @ApiOperation(value = "获取菜单权限列表", notes = "获取菜单权限列表")
    @GetMapping("/authority/menu")
    //public ResultBody<List<AuthorityMenu>> findAuthorityMenu() {
    public ResultBody<List<VueRouter>> findAuthorityMenu() {
        //ResultBody<List<VueRouter>> llll=systemAuthorityServiceClient.findAuthorityMenu();
        //return llll;
        return systemAuthorityServiceClient.findAuthorityMenu();
    }

    /**
     * 获取功能权限列表
     *
     * @param actionId
     * @return
     */
    @ApiOperation(value = "获取功能权限列表", notes = "获取功能权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "功能按钮ID", paramType = "form")
    })
    @GetMapping("/authority/action")
    public ResultBody<List<SystemAuthorityAction>> findAuthorityAction(
            @RequestParam(value = "actionId") Long actionId
    ) {
        return systemAuthorityServiceClient.findAuthorityAction(actionId);
    }


    /**
     * 获取角色已分配权限
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "获取角色已分配权限", notes = "获取角色已分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form")
    })
    @GetMapping("/authority/role")
    public ResultBody<List<BaseAuthority>> findAuthorityRole(Long roleId) {
        return systemAuthorityServiceClient.findAuthorityRole(roleId);
    }


    /**
     * 获取用户已分配权限
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "获取用户已分配权限", notes = "获取用户已分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", defaultValue = "", required = true, paramType = "form")
    })
    @GetMapping("/authority/user")
    public ResultBody<List<BaseAuthority>> findAuthorityUser(
            @RequestParam(value = "ussystemApierId") Long userId
    ) {
        return systemAuthorityServiceClient.findAuthorityUser(userId);
    }


    /**
     * 获取应用已分配接口权限
     *
     * @param appId 角色ID
     * @return
     */
    @ApiOperation(value = "获取应用已分配接口权限", notes = "获取应用已分配接口权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", defaultValue = "", required = true, paramType = "form")
    })
    @GetMapping("/authority/app")
    public ResultBody<List<BaseAuthority>> findAuthorityApp(
            @RequestParam(value = "appId") String appId
    ) {
        return systemAuthorityServiceClient.findAuthorityApp(appId);
    }

    /**
     * 分配角色权限
     *
     * @param roleId       角色ID
     * @param expireTime   授权过期时间
     * @param authorityIds 权限ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "分配角色权限", notes = "分配角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "expireTime", value = "过期时间.选填", defaultValue = "", required = false, paramType = "form"),
            @ApiImplicitParam(name = "authorityIds", value = "权限ID.多个以,隔开.选填", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/authority/role/grant")
    public ResultBody grantAuthorityRole(@RequestBody SysRoleGrantSaveDTO sysRoleGrantSaveDTO
            //@RequestParam(value = "roleId") Long roleId,
            //@RequestParam(value = "expireTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expireTime,
            //@RequestParam(value = "authorityIds", required = false) String authorityIds
    ) {
        return systemAuthorityServiceClient.grantAuthorityRole(sysRoleGrantSaveDTO.getRoleId(), sysRoleGrantSaveDTO.getExpireTime(), sysRoleGrantSaveDTO.getAuthorityIds());
    }


    /**
     * 分配用户权限
     *
     * @param userId       用户ID
     * @param expireTime   授权过期时间
     * @param authorityIds 权限ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "分配用户权限", notes = "分配用户权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "expireTime", value = "过期时间.选填", defaultValue = "", required = false, paramType = "form"),
            @ApiImplicitParam(name = "authorityIds", value = "权限ID.多个以,隔开.选填", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/authority/user/grant")
    public ResultBody grantAuthorityUser(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "expireTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expireTime,
            @RequestParam(value = "authorityIds", required = false) String authorityIds
    ) {
        return systemAuthorityServiceClient.grantAuthorityUser(userId, expireTime, authorityIds);
    }


    /**
     * 分配应用权限
     *
     * @param appId        应用Id
     * @param expireTime   授权过期时间
     * @param authorityIds 权限ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "分配应用权限", notes = "分配应用权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "expireTime", value = "过期时间.选填", defaultValue = "", required = false, paramType = "form"),
            @ApiImplicitParam(name = "authorityIds", value = "权限ID.多个以,隔开.选填", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/authority/app/grant")
    public ResultBody grantAuthorityApp(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "expireTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expireTime,
            @RequestParam(value = "authorityIds", required = false) String authorityIds
    ) {
        return systemAuthorityServiceClient.grantAuthorityApp(appId, expireTime, authorityIds);
    }

    /**
     * 功能按钮绑定API
     *
     * @param actionId
     * @param authorityIds
     * @return
     */
    @ApiOperation(value = "功能按钮授权", notes = "功能按钮授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "功能按钮ID", paramType = "form"),
            @ApiImplicitParam(name = "authorityIds", required = false, value = "全新ID:多个用,号隔开", paramType = "form"),
    })
/*    @PostMapping("/authority/action/grant")
    public ResultBody grantAuthorityAction(
            @RequestParam(value = "actionId") Long actionId,
            @RequestParam(value = "authorityIds", required = false) String authorityIds
    ) {
        return systemAuthorityServiceClient.grantAuthorityAction(actionId, authorityIds);
    }*/
    @PostMapping("/authority/action/grant")
    public ResultBody grantAuthorityAction(@RequestBody SysAuthorityGrantSaveDTO sysAuthorityGrantSaveDTO) {
        return systemAuthorityServiceClient.grantAuthorityAction(sysAuthorityGrantSaveDTO);
        //return systemAuthorityServiceClient.grantAuthorityAction(actionId, authorityIds);
    }
    /**
     * 获取角色的菜单和资源
     *
     * @param roleId
     * @return
     */
    @GetMapping("/authority/findAuthorityIdByRoleId")
    public ResultBody findAuthorityIdByRoleId(@RequestParam(value = "roleId") Long roleId){
        return systemAuthorityServiceClient.findAuthorityIdByRoleId(roleId);
    }
}
