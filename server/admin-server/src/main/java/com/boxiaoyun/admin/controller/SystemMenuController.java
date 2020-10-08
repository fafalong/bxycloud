package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemMenuServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.TreeUtil;
import com.boxiaoyun.system.client.model.dto.MenuSaveDTO;
import com.boxiaoyun.system.client.model.dto.MenuUpdateDTO;
import com.boxiaoyun.system.client.model.dto.VueRouter;
import com.boxiaoyun.system.client.model.entity.SystemAction;
import com.boxiaoyun.system.client.model.entity.SystemMenu;
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
@Api(tags = "系统菜单资源管理")
@RestController
public class SystemMenuController {

    @Autowired
    private SystemMenuServiceClient systemMenuServiceClient;

    /**
     * 获取分页菜单资源列表
     *
     * @return
     */
    @ApiOperation(value = "获取分页菜单资源列表", notes = "获取分页菜单资源列表")
    @GetMapping("/menu/page")
    public ResultBody<Page<SystemMenu>> getPage(@RequestParam(required = false) Map map) {
        return systemMenuServiceClient.getPage(map);
    }

    /**
     * 菜单所有资源列表
     *
     * @return
     */
/*
    @ApiOperation(value = "菜单所有资源列表", notes = "菜单所有资源列表")
    @GetMapping("/menu/list")
    public ResultBody<List<SystemMenu>> getList() {
        return systemMenuServiceClient.getList();
    }
*/

    @ApiOperation(value = "菜单所有资源列表", notes = "菜单所有资源列表")
    @GetMapping("/menu/list")
    public ResultBody<List<VueRouter>> getList() {
        return systemMenuServiceClient.getList();
    }

    /**
     * 获取菜单下所有操作
     *
     * @param menuId
     * @return
     */
    @ApiOperation(value = "获取菜单下所有操作", notes = "获取菜单下所有操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "menuId", paramType = "form"),
    })
    @GetMapping("/menu/action")
    public ResultBody<List<SystemAction>> getMenuAction(Long menuId) {
        return systemMenuServiceClient.getMenuAction(menuId);
    }

    /**
     * 获取菜单资源详情
     *
     * @param menuId
     * @return 应用信息
     */
    @ApiOperation(value = "获取菜单资源详情", notes = "获取菜单资源详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId"),
    })
    @GetMapping("/menu/info")
    public ResultBody<SystemMenu> get(@RequestParam("menuId") Long menuId) {
        return systemMenuServiceClient.get(menuId);
    }

    /**
     * 添加/编辑菜单资源
     *
     * @param menuCode 菜单编码
     * @param label    菜单名称
     * @param icon     图标
     * @param scheme   请求前缀
     * @param path     请求路径
     * @param target   打开方式
     * @param status   是否启用
     * @param parentId 父节点ID
     * @param priority 优先级越小越靠前
     * @param menuDesc 描述
     * @return
     */
    @ApiOperation(value = "添加/编辑菜单资源", notes = "添加/编辑菜单资源")
    @PostMapping("/menu/save")
    public ResultBody<Long> save(@RequestBody @Validated MenuSaveDTO data) {
        return systemMenuServiceClient.save(data);
    }

    @PutMapping(value = "/menu/update")
    public ResultBody<Long> update(@RequestBody @Validated MenuUpdateDTO data){
        return systemMenuServiceClient.update(data);
    }
    /**
     * 移除菜单资源
     *
     * @param menuId
     * @return
     */
    @ApiOperation(value = "移除菜单资源", notes = "移除菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId", paramType = "form"),
    })
    @PostMapping("/menu/remove")
    public ResultBody<Boolean> remove(
            @RequestParam("menuId") Long menuId
    ) {
        return systemMenuServiceClient.remove(menuId);
    }
}
