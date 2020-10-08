package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.BeanPlusUtil;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.common.utils.TreeUtil;
import com.boxiaoyun.system.client.model.dto.MenuSaveDTO;
import com.boxiaoyun.system.client.model.dto.MenuUpdateDTO;
import com.boxiaoyun.system.client.model.dto.VueRouter;
import com.boxiaoyun.system.client.model.entity.SystemAction;
import com.boxiaoyun.system.client.model.entity.SystemMenu;
import com.boxiaoyun.system.client.service.ISystemMenuServiceClient;
import com.boxiaoyun.system.service.SystemActionService;
import com.boxiaoyun.system.service.SystemMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boxiaoyun.dozer.DozerUtils;

/**
 * @author
 */
@Api(tags = "系统菜单信息管理")
@RestController
public class SystemMenuController extends BaseController<SystemMenuService, SystemMenu> implements ISystemMenuServiceClient {

    @Autowired
    private SystemActionService systemActionService;

    @Autowired
    private RestUtil restUtil;

    @Autowired
    private DozerUtils dozer;
    /**
     * 获取菜单列表
     *
     * @param map
     * @return
     */
    @GetMapping("/menu/page")
    @Override
    public ResultBody<Page<SystemMenu>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @GetMapping("/menu/list2")
    //@Override
    public ResultBody<List<SystemMenu>> getList2() {
        return ResultBody.success(bizService.findList());
    }

    /**
     * 获取菜单列表 最新方法
     *
     * @return
     */
    @GetMapping("/menu/list")
    public ResultBody<List<VueRouter>> getList(){//(@RequestParam(value = "group", required = false) String group,
                                                //@RequestParam(value = "userId", required = false) Long userId) {
        /*if (userId == null || userId <= 0) {
            userId = getUserId();
        }*/
        //List<Menu> list = menuService.findVisibleMenu(group, userId);
        //List<VueRouter> treeList = dozer.mapList(list, VueRouter.class);
        //return success(TreeUtil.buildTree(treeList));

        List<SystemMenu> list = bizService.findList();
        List<VueRouter> treeList = dozer.mapList(list, VueRouter.class);
        //System.out.println(TreeUtil.buildTree(treeList));
        //return ResultBody.success(TreeUtil.buildTree(treeList));
        return ResultBody.success(TreeUtil.buildTree(treeList));
    }

    /**
     * 获取菜单下所有操作
     *
     * @param menuId
     * @return
     */
    @GetMapping("/menu/action")
    @Override
    public ResultBody<List<SystemAction>> getMenuAction(@RequestParam("menuId") Long menuId) {
        return ResultBody.success(systemActionService.findListByMenuId(menuId));
    }

    /**
     * 获取菜单信息详情
     *
     * @param menuId
     * @return
     */
    @GetMapping("/menu/info")
    @Override
    public ResultBody<SystemMenu> get(@RequestParam("menuId") Long menuId) {
        return ResultBody.success(bizService.getById(menuId));
    }

    /**
     * 添加菜单信息
     *
     * @param data
     * @return
     */
    @PostMapping(value = "/menu/save")
    @Override
    public ResultBody<Long> save(@RequestBody @Validated MenuSaveDTO data) {
        SystemMenu systemMenu = BeanPlusUtil.toBean(data, SystemMenu.class);
        bizService.add(systemMenu);
        return ResultBody.ok();
    }
    /**
     * 修改菜单信息
     *
     * @param data
     * @return
     */
    @PutMapping(value = "/menu/update")
    @Override
    public ResultBody<Long> update(@RequestBody @Validated MenuUpdateDTO data) {
        SystemMenu systemMenu = BeanPlusUtil.toBean(data, SystemMenu.class);
        bizService.update(systemMenu);
        return ResultBody.ok();
    }
    /**
     * 移除菜单信息
     *
     * @param menuId
     * @return
     */
    @PostMapping("/menu/remove")
    @Override
    public ResultBody<Boolean> remove(@RequestParam("menuId") Long menuId) {
        bizService.remove(menuId);
        restUtil.refreshGateway();
        return ResultBody.ok();
    }
}
