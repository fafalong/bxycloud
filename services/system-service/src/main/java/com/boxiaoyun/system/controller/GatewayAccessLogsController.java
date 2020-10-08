package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.system.client.model.entity.GatewayAccessLogs;
import com.boxiaoyun.system.client.service.IGatewayAccessLogsClient;
import com.boxiaoyun.system.service.GatewayAccessLogsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 网关日志
 *
 * @author:
 * @date: 2019/3/12 15:12
 * @description:
 */
@RestController
public class GatewayAccessLogsController extends BaseController<GatewayAccessLogsService, GatewayAccessLogs> implements IGatewayAccessLogsClient {

    /**
     * 获取服务列表
     *
     * @return
     */
    @GetMapping("/gateway/access/logs")
    @Override
    public ResultBody<Page<GatewayAccessLogs>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

}
