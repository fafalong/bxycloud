package com.boxiaoyun.msg.listener;

import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.msg.client.model.WebhookMessage;
import com.boxiaoyun.msg.client.model.entity.WebhookLogs;
import com.boxiaoyun.msg.test.RabbitConfiguration;
import com.boxiaoyun.msg.service.DelayMessageService;
import com.boxiaoyun.msg.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息接收者
 *
 * @author
 */
@Configuration
public class WebHookHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 返回结果
     */
    private static final String SUCCESS = "success";
    @Autowired
    private RestUtil restUtil;
    @Autowired
    private DelayMessageService messageSender;
    @Autowired
    private WebhookService webhookService;
    /**
     * 首次是即时推送，重试通知时间间隔为 5s、10s、2min、5min、10min、30min、1h、2h、6h、15h，直到你正确回复状态 200 并且返回 success 或者超过最大重发次数
     */
    public final static List<Integer> DELAY_TIMES = Arrays.asList(new Integer[]{
            5 * 1000,
            10 * 1000,
            2 * 60 * 1000,
            5 * 60 * 1000,
            10 * 60 * 1000,
            30 * 60 * 1000,
            60 * 60 * 1000,
            2 * 60 * 60 * 1000,
            6 * 60 * 60 * 1000,
            15 * 60 * 60 * 1000
    });

    @RabbitListener(queues = RabbitConfiguration.WEBHOOK_QUEUE)
    public void onMessage(Message message) {
        try {
            String msgId = message.getMessageProperties().getMessageId();
            String receivedMsg = new String(message.getBody(), "UTF-8");
            logger.debug("onMessage:{}", message);
            // 处理 http通知消息
            WebhookMessage notification = JSONObject.parseObject(receivedMsg, WebhookMessage.class);
            send(msgId, message, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 消息通知
     */
    protected void send(String msgId, Message message, WebhookMessage webhookMessage) throws Exception {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        // 默认延迟时间
        String originalExpiration = "";
        if (headers != null) {
            Object times = headers.get("delay-times");
            if (times != null && !"0".equals(times)) {
                originalExpiration = times.toString();
            }
        }
        String httpResult = "";
        try {
            httpResult = restUtil.post(webhookMessage.getUrl(),null, webhookMessage.getContent(),String.class);
        } catch (Exception e) {
            logger.error("http error:{}", e);
        }
        WebhookLogs log = new WebhookLogs();
        log.setTitle(webhookMessage.getTitle());
        log.setBusinessKey(webhookMessage.getKey());
        log.setMsgId(msgId);
        log.setUrl(webhookMessage.getUrl());
        log.setType(webhookMessage.getType());
        log.setContent(JSONObject.toJSONString(webhookMessage.getContent()));
        log.setRetryNums(0);
        log.setResult(new Short("0"));
        log.setDelay(0L);
        log.setTotalNums(1);
        // 添加日志
        addLog(log);
        log = webhookService.getById(msgId);
        log.setTotalNums(log.getTotalNums() + 1);
        // 通知返回结果为(success,不区分大小写). 则视为通知成功
        if (!SUCCESS.equalsIgnoreCase(httpResult)) {
            logger.info("result fail do retry:");
            Integer next = retry(msgId, webhookMessage, originalExpiration);
            // 更新日志
            if (next != null) {
                log.setRetryNums(log.getRetryNums().intValue() + 1);
                log.setDelay(Long.parseLong(String.valueOf(next)));
                modifyLog(log);
            }
        } else {
            // 更新结果
            log.setResult(new Short("1"));
            modifyLog(log);
        }
    }

    /**
     * 获取下一个通知时间
     * 返回Null表示最终通知失败
     *
     * @param originalExpiration
     * @return
     */
    protected Integer getNext(String originalExpiration) {
        if (StringUtils.isEmpty(originalExpiration)) {
            return DELAY_TIMES.get(0);
        }
        int index = DELAY_TIMES.indexOf(Integer.parseInt(originalExpiration));
        if (index >= DELAY_TIMES.size() - 1) {
            return null;
        }
        int nextInterval = DELAY_TIMES.get(index + 1);
        return nextInterval;
    }

    /**
     * 通知失败重试
     *
     * @param originalExpiration
     */
    protected Integer retry(String msgId, WebhookMessage notification, String originalExpiration) throws Exception {
        Integer next = getNext(originalExpiration);
        if (next != null) {
            // 下次延迟时间
            logger.info("current ={} next ={}", originalExpiration, next);
            messageSender.delay(RabbitConfiguration.WEBHOOK_QUEUE_RK, msgId, JSONObject.toJSONString(notification), next);
            return next;
        } else {
            // 最后一次
            logger.info("finish = {}", originalExpiration);
            return null;
        }
    }

    /**
     * 添加日志
     *
     * @param log
     */
    protected void addLog(WebhookLogs log) {
        if (log == null || StringUtils.isEmpty(log.getMsgId())) {
            return;
        }
        WebhookLogs saved = webhookService.getById(log.getMsgId());
        if (saved == null) {
            log.setCreateTime(new Date());
            log.setUpdateTime(log.getCreateTime());
            webhookService.save(log);
        }
    }

    /**
     * 更新日志
     *
     * @param log
     */
    protected void modifyLog(WebhookLogs log) {
        if (log == null || StringUtils.isEmpty(log.getMsgId())) {
            return;
        }
        log.setUpdateTime(new Date());
        webhookService.updateById(log);
    }
}
