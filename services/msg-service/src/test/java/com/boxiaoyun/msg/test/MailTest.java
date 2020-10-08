package com.boxiaoyun.msg.test;

import com.google.common.collect.Maps;
import com.boxiaoyun.common.test.BaseTest;
import com.boxiaoyun.msg.client.model.EmailMessage;
import com.boxiaoyun.msg.client.model.EmailTplMessage;
import com.boxiaoyun.msg.dispatcher.MessageDispatcher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author:
 * @date: 2018/11/27 14:45
 * @description:
 */
public class MailTest extends BaseTest {
    @Autowired
    private MessageDispatcher dispatcher;

    @Test
    public void send() {
        EmailMessage message = new EmailMessage();
        message.setTo(new String[]{"515608851@qq.com"});
        message.setSubject("测试");
        message.setContent("测试内容");
        this.dispatcher.dispatch(message);
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendByTpl() {
        Map tplParams = Maps.newHashMap();
        tplParams.put("a","输入字符");
        EmailTplMessage message = new EmailTplMessage();
        message.setTo(new String[]{"515608851@qq.com"});
        message.setSubject("测试");
        message.setContent("测试内容");
        message.setTplCode("test");
        message.setTplParams(tplParams);
        this.dispatcher.dispatch(message);
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
