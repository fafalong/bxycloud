package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.GatewayRouteServiceClient;
import com.boxiaoyun.admin.service.feign.GatewayServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayRoute;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class GatewayRouteController {

    @Autowired
    private GatewayRouteServiceClient gatewayRouteServiceClient;
    @Autowired
    private GatewayServiceClient gatewayServiceClient;
    /**
     * 获取分页路由列表
     *
     * @return
     */
    @ApiOperation(value = "获取分页路由列表", notes = "获取分页路由列表")
    @GetMapping("/gateway/route/page")
    public ResultBody<Page<GatewayRoute>> getPage(@RequestParam(required = false) Map map) {
        return gatewayRouteServiceClient.getPage(map);
    }

    /**
     * 获取服务列表
     *
     * @return
     */
    @ApiOperation(value = "获取服务列表", notes = "获取服务列表")
    @GetMapping("/gateway/service/list")
    public ResultBody<GatewayRoute> getServiceList() {
        return gatewayServiceClient.getServiceList();
    }

    /**
     * 获取路由
     *
     * @param routeId
     * @return
     */
    @ApiOperation(value = "获取路由", notes = "获取路由")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", required = true, value = "路由ID", paramType = "path"),
    })
    @GetMapping("/gateway/route/info")
    public ResultBody<GatewayRoute> get(@RequestParam("routeId") Long routeId) {
        return gatewayRouteServiceClient.get(routeId);
    }

    /**
     * 添加/编辑路由
     *
     * @param routeId     路由ID
     * @param path        路径表达式
     * @param serviceId   服务名方转发
     * @param url         地址转发
     * @param stripPrefix 忽略前缀
     * @param retryable   支持重试
     * @param status      是否启用
     * @param routeName   描述
     * @return
     */
    @ApiOperation(value = "添加/编辑路由", notes = "添加/编辑路由")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", required = false, value = "路由Id", paramType = "form"),
            @ApiImplicitParam(name = "routeName", required = true, value = "路由标识", paramType = "form"),
            @ApiImplicitParam(name = "routeDesc", required = true, value = "路由名称", paramType = "form"),
            @ApiImplicitParam(name = "path", required = true, value = "路径表达式", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = false, value = "服务名方转发", paramType = "form"),
            @ApiImplicitParam(name = "url", required = false, value = "地址转发", paramType = "form"),
            @ApiImplicitParam(name = "stripPrefix", required = false, allowableValues = "0,1", defaultValue = "1", value = "忽略前缀", paramType = "form"),
            @ApiImplicitParam(name = "retryable", required = false, allowableValues = "0,1", defaultValue = "0", value = "支持重试", paramType = "form"),
            @ApiImplicitParam(name = "status", required = false, allowableValues = "0,1", defaultValue = "1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/gateway/route/save")
    public ResultBody save(
            @RequestParam(value = "routeId", required = false) Long routeId,
            @RequestParam(value = "routeName", defaultValue = "") String routeName,
            @RequestParam(value = "routeDesc", defaultValue = "") String routeDesc,
            @RequestParam(value = "path") String path,
            @RequestParam(value = "serviceId", required = false) String serviceId,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "stripPrefix", required = false, defaultValue = "1") Integer stripPrefix,
            @RequestParam(value = "retryable", required = false, defaultValue = "0") Integer retryable,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    ) {
        GatewayRoute route = new GatewayRoute();
        route.setRouteId(routeId);
        route.setPath(path);
        route.setServiceId(serviceId);
        route.setUrl(url);
        route.setRetryable(retryable);
        route.setStripPrefix(stripPrefix);
        route.setStatus(status);
        route.setRouteName(routeName);
        route.setRouteDesc(routeDesc);
        if(route.getUrl()!=null  && StringUtils.isNotEmpty(route.getUrl())){
            route.setServiceId(null);
        }
        return gatewayRouteServiceClient.save(route);
    }


    /**
     * 移除路由
     *
     * @param routeId
     * @return
     */
    @ApiOperation(value = "移除路由", notes = "移除路由")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", required = true, value = "routeId", paramType = "form"),
    })
    @PostMapping("/gateway/route/remove")
    public ResultBody remove(
            @RequestParam("routeId") Long routeId
    ) {
        return gatewayRouteServiceClient.remove(routeId);
    }
}
