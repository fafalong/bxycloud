package com.boxiaoyun.site.service.feign;

import com.boxiaoyun.system.client.service.ISystemAppServiceClient;
import com.boxiaoyun.system.client.constants.SystemConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author:
 * @date: 2018/10/24 16:49
 * @description:
 */
@Component
@FeignClient(value = SystemConstants.SYSTEM_SERVER)
public interface BaseAppServiceClient extends ISystemAppServiceClient {


}
