package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimit;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimitApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface IGatewayRateLimitClient {

    /**
     * 查询获取流量策略列表
     *
     * @param map
     * @return
     */
    @GetMapping("/gateway/limit/rate")
    ResultBody<Page<GatewayRateLimit>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取流量策略
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/rate/info")
    ResultBody<GatewayRateLimit> get(@RequestParam("policyId") Long policyId);

    /**
     * 添加/修改流量策略
     *
     * @param gatewayRateLimit
     * @return
     */
    @PostMapping("/gateway/limit/rate/save")
    ResultBody<Long> save(@RequestBody GatewayRateLimit gatewayRateLimit);

    /**
     * 移除流量策略
     *
     * @param policyId
     * @return
     */
    @PostMapping("/gateway/limit/rate/remove")
    ResultBody remove(@RequestParam("policyId") Long policyId);

    /**
     * 流量策略绑定API
     *
     * @param policyId
     * @param apiIds
     * @return
     */
    @PostMapping("/gateway/limit/rate/save/api")
    ResultBody saveApis(@RequestParam("policyId") Long policyId, @RequestParam(value = "apiIds", required = false) String apiIds);

    /**
     * 查询获取流量策略已绑定API列表
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/rate/api/list")
    ResultBody<List<GatewayRateLimitApi>> getApis(@RequestParam("policyId") Long policyId);
}
