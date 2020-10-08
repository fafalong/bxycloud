package com.boxiaoyun.job.job;

import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微服务远程调用任务
 *
 * @author
 */
@Slf4j
public class HttpJob implements Job {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 负载均衡
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws RuntimeException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String serviceId = dataMap.getString("serviceId");
        String method = dataMap.getString("method");
        method = StringUtil.isBlank(method) ? "POST" : method;
        String path = dataMap.getString("path");
        String contentType = dataMap.getString("contentType");
        contentType = StringUtil.isBlank(contentType) ? MediaType.APPLICATION_FORM_URLENCODED_VALUE : contentType;
        String body = dataMap.getString("body");
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        // 获取服务实例
        if (serviceInstance == null) {
            throw new RuntimeException(String.format("%s服务暂不可用", serviceId));
        }
        String url = String.format("%s%s", serviceInstance.getUri(), path);
        HttpHeaders headers = new HttpHeaders();
        HttpMethod httpMethod = HttpMethod.resolve(method.toUpperCase());
        HttpEntity requestEntity = null;
        headers.setContentType(MediaType.parseMediaType(contentType));
        if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            // json格式
            requestEntity = new HttpEntity(body, headers);
        } else {
            // 表单形式
            // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, String> params = new LinkedMultiValueMap();
            if (StringUtil.isNotBlank(body)) {
                Map data = JSONObject.parseObject(body, Map.class);
                params.putAll(data);
                requestEntity = new HttpEntity(params, headers);
            }
        }
        ResponseEntity<String> result = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
        log.debug("==> url[{}] method[{}] data=[{}] result=[{}]", url, httpMethod, requestEntity, result);
    }
}
