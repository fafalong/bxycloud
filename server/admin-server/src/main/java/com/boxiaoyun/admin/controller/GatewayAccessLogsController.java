package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.GatewayAccessLogsServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayAccessLogs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(tags = "网关访问日志")
@RestController
public class GatewayAccessLogsController {

    @Autowired
    private GatewayAccessLogsServiceClient gatewayAccessLogsServiceClient;

    /**
     * 获取分页列表
     *
     * @return
     */
    @ApiOperation(value = "获取分页访问日志列表", notes = "获取分页访问日志列表")
    @GetMapping("/gateway/access/logs")
    public ResultBody<Page<GatewayAccessLogs>> getAccessLogListPage(@RequestParam(required = false) Map map) {
        return gatewayAccessLogsServiceClient.getPage(map);
    }

    /**
     * 获取按天统计访问量
     * 默认最近10天
     * @return
     */
/*    @ApiOperation(value = "获取按天统计访问量", notes = "获取按天统计访问量")
    @GetMapping("/gateway/access/getAccessCountByDay")
    public ResultBody getAccessCountByDay(@RequestParam(required = false) Map map) {
        return gatewayAccessLogsServiceClient.getAccessCountByDay(map);
    }*/

}
