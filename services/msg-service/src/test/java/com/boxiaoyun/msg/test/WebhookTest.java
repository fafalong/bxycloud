package com.boxiaoyun.msg.test;

import com.boxiaoyun.common.test.BaseTest;
import com.boxiaoyun.msg.service.DelayMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @author:
 * @date: 2019/2/19 15:23
 * @description:
 */
public class WebhookTest extends BaseTest {
    @Autowired
    private DelayMessageService messageSender;

    @Test
    public void send() throws Exception {
        messageSender.send("123456","测试","http://www.baidu.com/notity/callback", "order_pay", new HashMap<>());
        System.out.println("发送成功");
        Thread.sleep(500000);
    }
}
