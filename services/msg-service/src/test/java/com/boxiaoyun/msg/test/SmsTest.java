package com.boxiaoyun.msg.test;

import com.boxiaoyun.common.test.BaseTest;
import com.boxiaoyun.msg.client.model.SmsMessage;
import com.boxiaoyun.msg.dispatcher.MessageDispatcher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author:
 * @date: 2018/11/27 14:45
 * @description:
 */
public class SmsTest extends BaseTest {
    @Autowired
    private MessageDispatcher dispatcher;

    @Test
    public void send() {
        SmsMessage smsNotify = new SmsMessage();
        smsNotify.setPhoneNum("18510152531");
        smsNotify.setSignName("测试");
        smsNotify.setTplCode("测试内容");
        this.dispatcher.dispatch(smsNotify);
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
