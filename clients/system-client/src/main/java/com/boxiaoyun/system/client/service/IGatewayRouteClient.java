package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayRoute;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author
 */
public interface IGatewayRouteClient {

    /**
     * 获取路由列表
     */
    @GetMapping("/gateway/route/page")
    ResultBody<Page<GatewayRoute>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取路由详情
     *
     * @param routeId
     * @return
     */
    @GetMapping("/gateway/route/info")
    ResultBody<GatewayRoute> get(@RequestParam("routeId") Long routeId);

    /**
     * 添加/修改路由
     *
     * @param gatewayRoute
     * @return
     */
    @ApiOperation(value = "添加/修改路由", notes = "添加/修改路由")
    @PostMapping("/gateway/route/save")
    ResultBody<Long> save(@RequestBody GatewayRoute gatewayRoute);

    /**
     * 移除路由
     *
     * @param routeId
     * @return
     */
    @ApiOperation(value = "移除路由", notes = "移除路由")
    @PostMapping("/gateway/route/remove")
    ResultBody remove(@RequestParam("routeId") Long routeId);
}
