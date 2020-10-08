package com.boxiaoyun.job.service.feign;

import com.boxiaoyun.msg.client.service.IEmailServiceClient;
import com.boxiaoyun.msg.client.constatns.MsgConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author:
 * @date: 2019/4/1 12:57
 * @description:
 */
@Component
@FeignClient(value = MsgConstants.MSG_SERVICE)
public interface EmailServiceClient extends IEmailServiceClient {
}
