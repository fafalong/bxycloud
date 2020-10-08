package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.dto.MenuSaveDTO;
import com.boxiaoyun.system.client.model.dto.MenuUpdateDTO;
import com.boxiaoyun.system.client.model.dto.VueRouter;
import com.boxiaoyun.system.client.model.entity.SystemAction;
import com.boxiaoyun.system.client.model.entity.SystemMenu;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
 public interface ISystemMenuServiceClient {
    /**
     * 获取菜单列表
     * @param map
     * @return
     */
    @GetMapping("/menu/page")
    ResultBody<Page<SystemMenu>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取菜单列表
     * @return
     */
/*    @GetMapping("/menu/list")
    ResultBody<List<SystemMenu>> getList();*/

    @GetMapping("/menu/list")
    ResultBody<List<VueRouter>> getList();
    /**
     * 获取菜单下所有操作
     * @param menuId
     * @return
     */
    @GetMapping("/menu/action")
    ResultBody<List<SystemAction>> getMenuAction(@RequestParam("menuId") Long menuId);

    /**
     * 获取菜单信息详情
     * @param menuId
     * @return
     */
    @GetMapping("/menu/info")
    ResultBody<SystemMenu> get(@RequestParam("menuId") Long menuId);

    /**
     * 添加菜单信息
     * @param data
     * @return
     */
    @PostMapping(value = "/menu/save")
    ResultBody<Long> save(@RequestBody @Validated MenuSaveDTO data);

   /**
    * 修改菜单信息
    * @param data
    * @return
    */
    @PutMapping(value = "/menu/update")
    public ResultBody<Long> update(@RequestBody @Validated MenuUpdateDTO data);

   /**
     * 移除菜单信息
     * @param menuId
     * @return
     */
    @PostMapping("/menu/remove")
    ResultBody<Boolean> remove(@RequestParam("menuId") Long menuId);
}
