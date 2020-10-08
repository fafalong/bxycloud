package com.boxiaoyun.system.listener;

import cn.hutool.core.bean.BeanUtil;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.system.client.model.entity.GatewayAccessLogs;
import com.boxiaoyun.system.mapper.GatewayLogsMapper;
import com.boxiaoyun.system.service.IpRegionService;
import com.boxiaoyun.system.service.SystemAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * mq消息接收者
 *
 * @author
 */
@Configuration
@Slf4j
public class LogsHandler {

    @Autowired
    private GatewayLogsMapper gatewayLogsMapper;


    @Autowired
    private IpRegionService ipRegionService;


    @Autowired
    private SystemAccountService systemAccountService;

    /**
     * 接收访问日志
     *
     * @param map
     */
    @RabbitListener(queues = CommonConstants.API_ACCESS_LOGS)
    public void accessLogsHandler(@Payload Map map) {
        try {
            if (map != null) {
                GatewayAccessLogs logs = BeanUtil.mapToBean(map, GatewayAccessLogs.class,true);
                if (logs != null) {
                    if (logs.getIp() != null) {
                        logs.setRegion(ipRegionService.getRegion(logs.getIp()));
                    }
                    logs.setUseTime(logs.getResponseTime().getTime() - logs.getRequestTime().getTime());
                    gatewayLogsMapper.insert(logs);
                }
            }
        } catch (Exception e) {
            log.error("error:", e);
        }
    }
}
