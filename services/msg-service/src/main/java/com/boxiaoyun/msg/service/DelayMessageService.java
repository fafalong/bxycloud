package com.boxiaoyun.msg.service;

import com.boxiaoyun.msg.client.model.WebhookMessage;

import java.util.Map;

/**
 * @author:
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface DelayMessageService {

    /**
     * 发送延迟消息
     *
     * @param routeKey 路由KEY
     * @param msg      消息内容
     * @param times    延迟时间 毫秒
     */
    void delay(String routeKey, String msg, long times) throws Exception;

    /**
     * 延迟消息放入延迟队列中
     *
     * @param routeKey 路由KEY
     * @param msgId
     * @param msg      消息内容
     * @param times    延迟时间 毫秒
     */
    void delay(String routeKey, String msgId, String msg, long times) throws Exception;

    /**
     * 发送Http通知
     * 首次是即时推送，重试通知时间间隔为 5s、10s、2min、5min、10min、30min、1h、2h、6h、15h，直到你正确回复状态 200 并且返回 success 或者超过最大重发次数
     *
     * @param key     业务逐渐
     * @param title   通知标题
     * @param url     通知地址
     * @param type    通知业务类型
     * @param content 请求数据
     */
    void send(String key, String title, String url, String type, Map<String, String> content) throws Exception;

    /**
     * 发送Http通知
     *
     * @param message
     * @throws Exception
     */
    void send(WebhookMessage message) throws Exception;


    /**
     * 手动重新通知
     *
     * @param msgId
     */
    void send(String msgId) throws Exception;
}
