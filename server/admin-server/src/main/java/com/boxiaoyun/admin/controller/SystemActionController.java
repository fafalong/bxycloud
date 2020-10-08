package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemActionServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.dto.ActionSaveDTO;
import com.boxiaoyun.system.client.model.entity.SystemAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author
 */
@Api(tags = "系统功能按钮管理")
@RestController
public class SystemActionController {
    @Autowired
    private SystemActionServiceClient systemActionServiceClient;

    /**
     * 获取分页功能按钮列表
     *
     * @return
     */
    @ApiOperation(value = "获取分页功能按钮列表", notes = "获取分页功能按钮列表")
    @GetMapping("/action")
    public ResultBody<Page<SystemAction>> findPage(@RequestParam(required = false) Map map) {
        return systemActionServiceClient.getPage(map);
    }

    /**
     * 按菜单获取功能按钮列表
     *
     * @return
     */
/*    @ApiOperation(value = "按菜单获取功能按钮列表", notes = "按菜单获取功能按钮列表")
    @GetMapping("/menu/action")
    public ResultBody<Page<SystemAction>> findPage(@RequestParam("menuId") Long menuId) {
        return systemActionServiceClient.getActionByMenuId(menuId);
    }*/

    /**
     * 获取功能按钮详情
     *
     * @param actionId
     * @return
     */
    @ApiOperation(value = "获取功能按钮详情", notes = "获取功能按钮详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "功能按钮Id", paramType = "path"),
    })
    @GetMapping("/action/info")
    public ResultBody<SystemAction> get(@RequestParam("actionId") Long actionId) {
        return systemActionServiceClient.get(actionId);
    }

    /**
     * 添加/编辑功能按钮
     *
     * @param actionId   功能按钮ID
     * @param actionCode 功能按钮编码
     * @param actionName 功能按钮名称
     * @param menuId        上级菜单
     * @param status        是否启用
     * @param priority      优先级越小越靠前
     * @param actionDesc 描述
     * @return
     */
    @ApiOperation(value = "添加/编辑功能按钮", notes = "添加/编辑功能按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = false, value = "功能按钮ID", paramType = "form"),
            @ApiImplicitParam(name = "actionCode", required = true, value = "功能按钮编码", paramType = "form"),
            @ApiImplicitParam(name = "actionName", required = true, value = "功能按钮名称", paramType = "form"),
            @ApiImplicitParam(name = "menuId", required = true, value = "上级菜单", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "actionDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/action/save")
    public ResultBody save(@RequestBody @Validated ActionSaveDTO actionSaveDTO) {
        actionSaveDTO.setServiceId("system-service");
        return systemActionServiceClient.save(actionSaveDTO);
    }


    /**
     * 移除功能按钮
     *
     * @param actionId
     * @return
     */
    @ApiOperation(value = "移除功能按钮", notes = "移除功能按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "功能按钮ID", paramType = "form")
    })
    @PostMapping("/action/remove")
    public ResultBody removeAction(
            @RequestParam("actionId") Long actionId
    ) {
        return systemActionServiceClient.remove(actionId);
    }
}
