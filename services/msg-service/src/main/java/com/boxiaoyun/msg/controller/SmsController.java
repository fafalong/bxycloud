package com.boxiaoyun.msg.controller;

import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.msg.client.model.SmsMessage;
import com.boxiaoyun.msg.client.service.ISmsServiceClient;
import com.boxiaoyun.msg.dispatcher.MessageDispatcher;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 推送通知
 *
 * @author woodev
 */
@RestController
@Api(value = "短信", tags = "短信")
public class SmsController implements ISmsServiceClient {


    @Autowired
    private MessageDispatcher dispatcher;

    /**
     * 发送短信
     *smsMessage
     * @return
     */
    @PostMapping("/sms/send")
    @Override
    public ResultBody<String> send(SmsMessage smsMessage) {
        this.dispatcher.dispatch(smsMessage);
        return ResultBody.ok();
    }
}
