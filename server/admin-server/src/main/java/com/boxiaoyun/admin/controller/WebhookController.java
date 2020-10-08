package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.WebhookServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.msg.client.model.entity.WebhookLogs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author:
 * @date: 2019/3/29 14:12
 * @description:
 */
@Api(tags = "Webhooks")
@RestController
public class WebhookController {
    @Autowired
    private WebhookServiceClient webhookServiceClient;

    /**
     * 获取任务执行日志列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取Webhook通知日志", notes = "获取Webhook通知日志")
    @GetMapping(value = "/webhook/logs")
    public ResultBody<Page<WebhookLogs>> getLogList(@RequestParam(required = false) Map map) {
        return webhookServiceClient.getLogsList(map);
    }


    @ApiOperation(value = "重新发送通知", notes = "重新发送通知")
    @PostMapping(value = "/webhook/resend")
    public ResultBody resend(@RequestParam("msgId") String msgId) {
        return webhookServiceClient.resend(msgId);
    }
}
