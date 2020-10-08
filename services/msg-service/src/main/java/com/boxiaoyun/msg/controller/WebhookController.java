package com.boxiaoyun.msg.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.msg.client.model.WebhookMessage;
import com.boxiaoyun.msg.client.model.entity.WebhookLogs;
import com.boxiaoyun.msg.client.service.IWebhookServiceClient;
import com.boxiaoyun.msg.service.DelayMessageService;
import com.boxiaoyun.msg.service.WebhookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author woodev
 */
@RestController
@Api(value = "异步通知", tags = "异步通知")
public class WebhookController implements IWebhookServiceClient {

    @Autowired
    private DelayMessageService delayMessageService;
    @Autowired
    private WebhookService webhookService;

    /**
     * Webhook异步通知
     * 即时推送，重试通知时间间隔为 5s、10s、2min、5min、10min、30min、1h、2h、6h、15h，直到你正确回复状态 200 并且返回 success 或者超过最大重发次数
     */
    @Override
    @PostMapping("/webhook/send")
   public ResultBody send(@RequestBody  WebhookMessage message)  {
        try {
            delayMessageService.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultBody.ok();
    }

    @Override
    @PostMapping("/webhook/resend")
    public ResultBody resend(@RequestParam("msgId")  String msgId)  {
        try {
            delayMessageService.send(msgId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultBody.ok();
    }

    /**
     * 获取分页异步通知列表
     *
     * @return
     */
    @GetMapping("/webhook/logs")
    @Override
    public ResultBody<Page<WebhookLogs>> getLogsList(@RequestParam(required = false) Map map) {
        return ResultBody.ok().data(webhookService.findPage(new PageParams(map)));
    }

}
