package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.BeanPlusUtil;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.system.client.model.dto.ActionSaveDTO;
import com.boxiaoyun.system.client.model.entity.SystemAction;
import com.boxiaoyun.system.client.service.ISystemActionServiceClient;
import com.boxiaoyun.system.service.SystemActionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author
 */
@Api(tags = "系统功能按钮")
@RestController
public class SystemActionController extends BaseController<SystemActionService, SystemAction> implements ISystemActionServiceClient {

    @Autowired
    private RestUtil restUtil;

    /**
     * 获取功能操作列表
     *
     * @param map
     * @return
     */
    @GetMapping("/action")
    @Override
    public ResultBody<Page<SystemAction>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取功能操作列表
     *
     * @param menuId
     * @return
     */
/*    @GetMapping("/menu/action")
    @Override
    public ResultBody<Page<SystemAction>> getActionByMenuId(@RequestParam("menuId") Long menuId) {
        return ResultBody.ok().data(bizService.findListByMenuId(menuId));
    }*/

    /**
     * 获取功能按钮详情
     *
     * @param actionId
     * @return
     */
    @GetMapping("/action/info")
    @Override
    public ResultBody<SystemAction> get(@RequestParam("actionId") Long actionId) {
        return ResultBody.success(bizService.getById(actionId));
    }

    /**
     * 添加/修改功能按钮
     *
     * @param systemAction
     * @return
     */
    @PostMapping("/action/save")
    @Override
    public ResultBody<Long> save(@RequestBody @Validated ActionSaveDTO actionSaveDTO) {
        SystemAction systemAction = BeanPlusUtil.toBean(actionSaveDTO, SystemAction.class);
        if (systemAction.getActionId() == null) {
            bizService.add(systemAction);
        } else {
            bizService.update(systemAction);
        }
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 移除功能按钮
     *
     * @param actionId
     * @return
     */
    @PostMapping("/action/remove")
    @Override
    public ResultBody remove(@RequestParam("actionId") Long actionId) {
        bizService.remove(actionId);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }
}
