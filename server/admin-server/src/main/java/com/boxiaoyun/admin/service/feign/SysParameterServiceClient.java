package com.boxiaoyun.admin.service.feign;

import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.service.ISysParameterServiceClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author:lj
 * @date: 2020/07/15 13:26
 * @description:
 */
@Component
@FeignClient(value = SystemConstants.SYSTEM_SERVER)
public interface  SysParameterServiceClient extends ISysParameterServiceClient {

}
