package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimit;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimitApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface IGatewayIpLimitClient {

    /**
     * 获取IP限制策略列表
     */
    @GetMapping("/gateway/limit/ip")
    ResultBody<Page<GatewayIpLimit>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取IP限制策略
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/ip/info")
    ResultBody<GatewayIpLimit> get(@RequestParam("policyId") Long policyId);

    /**
     * 添加/修改IP限制策略
     *
     * @param gatewayIpLimit
     * @return
     */
    @PostMapping("/gateway/limit/ip/save")
    ResultBody<Long> save(@RequestBody GatewayIpLimit gatewayIpLimit);

    /**
     * 移除IP限制
     *
     * @param policyId
     * @return
     */
    @PostMapping("/gateway/limit/ip/remove")
    ResultBody remove(@RequestParam("policyId") Long policyId);

    /**
     * 查询IP限制策略已绑定API列表
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/ip/api/list")
    ResultBody<List<GatewayIpLimitApi>> getApis(@RequestParam("policyId") Long policyId);

    /**
     * IP限制策略绑定API
     *
     * @param policyId
     * @param apiIds
     * @return
     */
    @PostMapping("/gateway/limit/ip/save/api")
    ResultBody saveApis(@RequestParam("policyId") Long policyId, @RequestParam(value = "apiIds", required = false) String apiIds);
}
