package com.boxiaoyun.admin.service.feign;

import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.service.ISysStationServiceClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author:
 * @date: 2018/10/24 16:49
 * @description:
 */
@Component
@FeignClient(value = SystemConstants.SYSTEM_SERVER)
public interface SysStationServiceClient extends ISysStationServiceClient {
}
