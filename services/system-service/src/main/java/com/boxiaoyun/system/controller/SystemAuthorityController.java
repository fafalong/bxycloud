package com.boxiaoyun.system.controller;

import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.security.BaseAuthority;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.common.utils.TreeUtil;
import com.boxiaoyun.dozer.DozerUtils;
import com.boxiaoyun.system.client.model.AuthorityApi;
import com.boxiaoyun.system.client.model.AuthorityMenu;
import com.boxiaoyun.system.client.model.AuthorityResource;
import com.boxiaoyun.system.client.model.dto.SysAuthorityGrantSaveDTO;
import com.boxiaoyun.system.client.model.dto.VueRouter;
import com.boxiaoyun.system.client.model.entity.*;
import com.boxiaoyun.system.client.service.ISystemAuthorityServiceClient;
import com.boxiaoyun.system.service.SystemActionService;
import com.boxiaoyun.system.service.SystemAuthorityService;
import com.boxiaoyun.system.service.SystemMenuService;
import com.boxiaoyun.system.service.SystemUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author:
 * @date: 2018/11/26 18:20
 * @description:
 */
@Api(tags = "系统权限管理")
@RestController
public class SystemAuthorityController extends BaseController<SystemAuthorityService, SystemAuthority> implements ISystemAuthorityServiceClient {

    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private RestUtil restUtil;

    @Autowired
    private DozerUtils dozer;

    @Autowired
    private SystemActionService systemActionService;

/*    @Autowired
    private SystemAuthorityService systemAuthorityService;*/
    /**
     * 获取所有访问权限列表
     */
    @GetMapping("/authority/access")
    @Override
    public ResultBody<List<AuthorityResource>> findAuthorityResource() {
        List<AuthorityResource> result = bizService.findAuthorityResource();
        return ResultBody.success(result);
    }

    /**
     * 获取接口权限列表
     *
     * @param serviceId
     * @return
     */
    @GetMapping("/authority/sysApi")
    @Override
    public ResultBody<List<AuthorityApi>> findAuthorityApi(
            @RequestParam(value = "serviceId", required = false) String serviceId
    ) {
        List<AuthorityApi> result = bizService.findAuthorityApi(serviceId);
        return ResultBody.success(result);
    }

    /**
     * 获取菜单权限列表
     *
     * @return
     */
    @GetMapping("/authority/menu")
    @Override
    public ResultBody<List<VueRouter>> findAuthorityMenu() {
        List<AuthorityMenu> authorityMenus = bizService.findAuthorityMenu(1);
        List<VueRouter> treeList = dozer.mapList(authorityMenus, VueRouter.class);
        return ResultBody.success(TreeUtil.buildTree(treeList));
    }

    /**
     * 获取菜单权限列表
     *
     * @return
     */
/*    @GetMapping("/authority/menu")
    @Override
    public ResultBody<List<AuthorityMenu>> findAuthorityMenu() {
        List<AuthorityMenu> result = bizService.findAuthorityMenu(1);
        return ResultBody.ok().data(result);
    }*/

    /**
     * 获取菜单权限列表具体权限
     *
     * @return
     */
/*    @GetMapping("/authority/menu/action")
    @Override
    public ResultBody<List<SystemAction>> findAuthorityMenuAction(@RequestParam(value = "menuId") Long menuId) {
        List<SystemAuthorityAction> list = bizService.(actionId);
        return ResultBody.success(TreeUtil.buildTree(treeList));
    }*/
    /**
     * 获取功能权限列表
     *
     * @param actionId
     * @return
     */
    @GetMapping("/authority/action")
    @Override
    public ResultBody<List<SystemAuthorityAction>> findAuthorityAction(
            @RequestParam(value = "actionId") Long actionId
    ) {
        List<SystemAuthorityAction> list = bizService.findAuthorityAction(actionId);
        return ResultBody.success(list);
    }

    /**
     * 获取角色已分配权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/authority/role")
    @Override
    public ResultBody<List<BaseAuthority>> findAuthorityRole(@RequestParam(value = "roleId") Long roleId) {
        List<BaseAuthority> result = bizService.findAuthorityByRole(roleId);
        return ResultBody.success(result);
    }

    /**
     * 获取用户菜单列表
     *
     * @param userId
     * @param root
     * @return
     */
    @GetMapping("/authority/user/menu")
    @Override
    public ResultBody<List<VueRouter>> findAuthorityMenuByUser(@RequestParam(value = "userId") Long userId, @RequestParam(value = "root") Boolean root) {
        List<AuthorityMenu> authorityMenus=bizService.findAuthorityMenuByUser(userId, root);
        List<VueRouter> treeList = dozer.mapList(authorityMenus, VueRouter.class);
        return ResultBody.success(TreeUtil.buildTree(treeList));
    }

    /**
     * 获取用户已分配权限
     *
     * @param userId
     * @return
     */
    @GetMapping("/authority/user")
    @Override
    public ResultBody<List<BaseAuthority>> findAuthorityUser(
            @RequestParam(value = "userId") Long userId
    ) {
        SystemUser user = systemUserService.getById(userId);
        List<BaseAuthority> result = bizService.findAuthorityByUser(userId, CommonConstants.ROOT.equals(user.getUserName()));
        return ResultBody.success(result);
    }

    /**
     * 获取应用已分配接口权限
     *
     * @param appId
     * @return
     */
    @GetMapping("/authority/app")
    @Override
    public ResultBody<List<BaseAuthority>> findAuthorityApp(
            @RequestParam(value = "appId") String appId
    ) {
        List<BaseAuthority> result = bizService.findAuthorityByApp(appId);
        return ResultBody.success(result);
    }

    /**
     * 分配角色权限
     *
     * @param roleId
     * @param expireTime
     * @param authorityIds
     * @return
     */
    @PostMapping("/authority/role/grant")
    @Override
    public ResultBody grantAuthorityRole(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "expireTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expireTime,
            @RequestParam(value = "authorityIds", required = false) String authorityIds
    ) {
        bizService.addAuthorityRole(roleId, expireTime, authorityIds != null ? StringUtils.commaDelimitedListToStringArray(authorityIds) : null);
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 分配用户权限
     *
     * @param userId
     * @param expireTime
     * @param authorityIds
     * @return
     */
    @PostMapping("/authority/user/grant")
    @Override
    public ResultBody grantAuthorityUser(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "expireTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expireTime,
            @RequestParam(value = "authorityIds", required = false) String authorityIds
    ) {
        bizService.addAuthorityUser(userId, expireTime, authorityIds != null ? StringUtils.commaDelimitedListToStringArray(authorityIds) : null);
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 分配应用权限
     *
     * @param appId
     * @param expireTime
     * @param authorityIds
     * @return
     */
    @PostMapping("/authority/app/grant")
    @Override
    public ResultBody grantAuthorityApp(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "expireTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expireTime,
            @RequestParam(value = "authorityIds", required = false) String authorityIds
    ) {
        bizService.addAuthorityApp(appId, expireTime, authorityIds != null ? StringUtils.commaDelimitedListToStringArray(authorityIds) : null);
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 功能按钮授权
     *
     * @param actionId
     * @param authorityIds
     * @return
     */
    @PostMapping("/authority/action/grant")
    @Override
    public ResultBody grantAuthorityAction(@RequestBody SysAuthorityGrantSaveDTO sysAuthorityGrantSaveDTO) {
        bizService.addAuthorityAction(sysAuthorityGrantSaveDTO.getActionId(), sysAuthorityGrantSaveDTO.getAuthorityIds() != null ? StringUtils.commaDelimitedListToStringArray(sysAuthorityGrantSaveDTO.getAuthorityIds()) : null);
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 获取角色的菜单和资源
     *
     * @param roleId
     * @return
     */
    @GetMapping("/authority/findAuthorityIdByRoleId")
    @Override
    public ResultBody findAuthorityIdByRoleId(@RequestParam(value = "roleId") Long roleId){
        List<String> menuIdList = new ArrayList<String>();
        List<AuthorityMenu> menuList = bizService.findAuthorityMenuByRole(roleId);
        //List<SystemMenu> menuList= systemMenuService.findMenuByRoleId(roleId);
        for (AuthorityMenu am :menuList){
            menuIdList.add(""+am.getId());
        }

        List<String> resourceIdList = systemActionService.findActionByRoleId(roleId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("menuIdList",menuIdList);
        map.put("resourceIdList",resourceIdList);
        return ResultBody.success().data(map);
    }
}
