package com.boxiaoyun.admin.service.feign;

import com.boxiaoyun.job.client.constans.JobConstants;
import com.boxiaoyun.job.client.service.IJobServiceClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author:
 * @date: 2018/10/24 16:49
 * @description:
 */
@Component
@FeignClient(value = JobConstants.JOB_SERVICE)
public interface JobServiceClient extends IJobServiceClient {


}
