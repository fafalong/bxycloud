package com.boxiaoyun.msg.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.msg.client.model.WebhookMessage;
import com.boxiaoyun.msg.client.model.entity.WebhookLogs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 推送通知
 *
 * @author woodev
 */
public interface IWebhookServiceClient {

    /**
     * Webhook异步通知
     *
     * @param message
     * @return
     */
    @PostMapping("/webhook/send")
    ResultBody<String> send(@RequestBody WebhookMessage message);

    /**
     * 更具消息ID重发通知
     *
     * @param msgId
     * @return
     */
    @PostMapping("/webhook/resend")
    ResultBody resend(@RequestParam("msgId") String msgId);

    /**
     * 获取Webhook异步通知日志列表
     *
     * @param map
     * @return
     */
    @GetMapping("/webhook/logs")
    ResultBody<Page<WebhookLogs>> getLogsList(@RequestParam Map<String, Object> map);
}
