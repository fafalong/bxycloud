package com.boxiaoyun.msg.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.common.collect.Maps;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.msg.client.exchange.DelayExchangeBuilder;
import com.boxiaoyun.msg.client.model.WebhookMessage;
import com.boxiaoyun.msg.client.model.entity.WebhookLogs;
import com.boxiaoyun.msg.mapper.WebhookLogsMapper;
import com.boxiaoyun.msg.service.DelayMessageService;
import com.boxiaoyun.msg.service.WebhookService;
import com.boxiaoyun.msg.test.RabbitConfiguration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 异步通知日志接口
 *
 * @author:
 * @date: 2019/2/13 14:39
 * @description:
 */
@Service
public class WebhookServiceImpl extends BaseServiceImpl<WebhookLogsMapper, WebhookLogs> implements WebhookService, DelayMessageService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 最大延迟不能超过15天
     */
    private final static long MAX_DELAY = 15 * 24 * 3600 * 1000;


    /**
     * 发送延迟消息
     *
     * @param routeKey 路由KEY
     * @param msg      消息内容
     * @param times    延迟时间 毫秒
     */
    @Override
    public void delay(String routeKey, String msg, long times) throws Exception {
        delay(routeKey, null, msg, times);
    }

    /**
     * 延迟消息放入延迟队列中
     *
     * @param routeKey 路由KEY
     * @param msgId
     * @param msg      消息内容
     * @param times    延迟时间 毫秒
     */
    @Override
    public void delay(String routeKey, String msgId, String msg, long times) throws Exception {
        if (times > MAX_DELAY) {
            throw new IllegalArgumentException("延迟时间最大不能超过15天");
        }
        String delay = String.valueOf(times);
        amqpTemplate.convertAndSend(DelayExchangeBuilder.DEFAULT_DELAY_EXCHANGE, routeKey, msg, message -> {
            String messageId = msgId;
            if (StringUtils.isEmpty(messageId)) {
                messageId = IdUtil.simpleUUID();
            }
            message.getMessageProperties().setMessageId(messageId);
            message.getMessageProperties().setTimestamp(new Date());
            message.getMessageProperties().setType("x-delayed-message");
            //添加消息到队列时添加 headers={'x-delay': 8000}
            message.getMessageProperties().setDelay(Integer.parseInt(delay));
            // x-delay 这个版本请求头获取不到, 自定义了一个delay-times 来获取延迟时间
            message.getMessageProperties().setHeader("delay-times", delay);
            return message;
        });
    }

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
    @Override
    public void send(String key, String title, String url, String type, Map<String, String> content) throws Exception {
        if (StringUtils.isEmpty(key)) {
            throw new Exception("key is not empty");
        }
        if (StringUtils.isEmpty(title)) {
            throw new Exception("title is not empty");
        }
        if (StringUtils.isEmpty(type)) {
            throw new Exception("type is not empty");
        }
        if (StringUtils.isEmpty(url)) {
            throw new Exception("url is not empty");
        }
        if (content == null) {
            content = Maps.newHashMap();
        }
        WebhookMessage message = new WebhookMessage(key, title, url, type, content);
        delay(RabbitConfiguration.WEBHOOK_QUEUE_RK, JSONObject.toJSONString(message), 0);
    }

    /**
     * 发送Http通知
     *
     * @param message
     * @throws Exception
     */
    @Override
    public void send(WebhookMessage message) throws Exception {
        send(message.getKey(), message.getTitle(), message.getUrl(), message.getType(), message.getContent());
    }

    /**
     * 手动重新通知
     *
     * @param msgId
     */
    @Override
    public void send(String msgId) throws Exception {
        WebhookLogs log = getById(msgId);
        if (log == null) {
            throw new Exception("消息msgId={}不存在！");
        }
        Map<String, String> data = JSONObject.parseObject(log.getContent(), Map.class);
        WebhookMessage msg = new WebhookMessage(log.getBusinessKey(), log.getTitle(), log.getUrl(), log.getType(), data);
        delay(RabbitConfiguration.WEBHOOK_QUEUE_RK, log.getMsgId(), JSONObject.toJSONString(msg), 0);
    }

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<WebhookLogs> findPage(PageParams pageParams) {
        WebhookLogs query = pageParams.mapToBean(WebhookLogs.class);
        QueryWrapper<WebhookLogs> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getUrl()), WebhookLogs::getUrl, query.getUrl())
                .eq(ObjectUtils.isNotEmpty(query.getTitle()), WebhookLogs::getTitle, query.getTitle())
                .eq(ObjectUtils.isNotEmpty(query.getBusinessKey()), WebhookLogs::getBusinessKey, query.getBusinessKey())
                .eq(ObjectUtils.isNotEmpty(query.getType()), WebhookLogs::getType, query.getType())
                .eq(ObjectUtils.isNotEmpty(query.getResult()), WebhookLogs::getResult, query.getResult());
        return baseMapper.selectPage(pageParams, queryWrapper);
    }
}
