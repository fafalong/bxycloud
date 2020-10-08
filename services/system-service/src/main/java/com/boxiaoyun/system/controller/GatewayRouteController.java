package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.system.client.model.entity.GatewayRoute;
import com.boxiaoyun.system.client.service.IGatewayRouteClient;
import com.boxiaoyun.system.service.GatewayRouteService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 网关智能路由
 *
 * @author:
 * @date: 2019/3/12 15:12
 * @description:
 */
@Api(tags = "网关智能路由")
@RestController
public class GatewayRouteController extends BaseController<GatewayRouteService, GatewayRoute> implements IGatewayRouteClient {

    @Autowired
    private RestUtil restUtil;


    /**
     * 获取路由列表
     */
    @GetMapping("/gateway/route/page")
    @Override
    public ResultBody<Page<GatewayRoute>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取路由详情
     *
     * @param routeId
     * @return
     */
    @GetMapping("/gateway/route/info")
    @Override
    public ResultBody<GatewayRoute> get(@RequestParam("routeId") Long routeId) {
        return ResultBody.success(bizService.getById(routeId));
    }

    /**
     * 添加/修改路由
     *
     * @param gatewayRoute
     * @return
     */
    @PostMapping("/gateway/route/save")
    @Override
    public ResultBody<Long> save(@RequestBody GatewayRoute gatewayRoute) {
        if (gatewayRoute.getRouteId() == null) {
            bizService.add(gatewayRoute);
        } else {
            bizService.update(gatewayRoute);
        }
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 移除路由
     *
     * @param routeId
     * @return
     */
    @PostMapping("/gateway/route/remove")
    @Override
    public ResultBody remove(@RequestParam("routeId") Long routeId) {
        bizService.remove(routeId);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }
}
