package com.boxiaoyun.admin.service.feign;

import com.boxiaoyun.msg.client.constatns.MsgConstants;
import com.boxiaoyun.msg.client.service.IWebhookServiceClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author:
 * @date: 2018/10/24 16:49
 * @description:
 */
@Component
@FeignClient(value = MsgConstants.MSG_SERVICE)
public interface WebhookServiceClient extends IWebhookServiceClient {


}
