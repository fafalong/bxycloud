package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.GatewayRateLimitServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimit;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimitApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 网关流量控制
 *
 * @author:
 * @date: 2019/3/12 15:12
 * @description:
 */
@Api(tags = "网关流量控制")
@RestController
public class GatewayRateLimitController {

    @Autowired
    private GatewayRateLimitServiceClient gatewayRateLimitServiceClient;

    /**
     * 查询获取流量策略列表
     *
     * @return
     */
    @ApiOperation(value = "查询获取流量策略列表", notes = "查询获取流量策略列表")
    @GetMapping("/gateway/limit/rate")
    public ResultBody<Page<GatewayRateLimit>> getPage(@RequestParam(required = false) Map map) {
        return gatewayRateLimitServiceClient.getPage(map);
    }

    /**
     * 查询获取流量策略已绑定API列表
     *
     * @param policyId
     * @return
     */
    @ApiOperation(value = "查询获取流量策略已绑定API列表", notes = "查询获取流量策略已绑定API列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", value = "策略ID", paramType = "form"),
    })
    @GetMapping("/gateway/limit/rate/api/list")
    public ResultBody<List<GatewayRateLimitApi>> getApis(
            @RequestParam("policyId") Long policyId
    ) {
        return gatewayRateLimitServiceClient.getApis(policyId);
    }

    /**
     * 流量策略绑定API
     *
     * @param policyId 策略ID
     * @param apiIds   API接口ID.多个以,隔开.选填
     * @return
     */
    @ApiOperation(value = "流量策略绑定API", notes = "一个API只能绑定一个策略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", value = "策略ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "apiIds", value = "API接口ID.多个以,隔开.选填", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/gateway/limit/rate/api/save")
    public ResultBody saveApis(
            @RequestParam("policyId") Long policyId,
            @RequestParam(value = "apiIds", required = false) String apiIds
    ) {
        return  gatewayRateLimitServiceClient.saveApis(policyId, apiIds);
    }

    /**
     * 获取流量控制
     *
     * @param policyId
     * @return
     */
    @ApiOperation(value = "获取流量控制", notes = "获取流量控制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", required = true, value = "策略ID", paramType = "path"),
    })
    @GetMapping("/gateway/limit/rate/info")
    public ResultBody<GatewayRateLimit> get(@RequestParam("policyId") Long policyId) {
        return gatewayRateLimitServiceClient.get(policyId);
    }

    /**
     * 编辑流量控制
     *
     * @param policyId     流量控制ID
     * @param policyName   策略名称
     * @param limitQuota   限制数
     * @param intervalUnit 单位时间
     * @param policyType   限流规则类型
     * @return
     */
    @ApiOperation(value = "编辑流量控制", notes = "编辑流量控制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", required = false, value = "接口Id", paramType = "form"),
            @ApiImplicitParam(name = "policyName", required = true, value = "策略名称", paramType = "form"),
            @ApiImplicitParam(name = "policyType", required = true, value = "限流规则类型:url,origin,user", allowableValues = "url,origin,user", paramType = "form"),
            @ApiImplicitParam(name = "limitQuota", required = true, value = "限制数", paramType = "form"),
            @ApiImplicitParam(name = "intervalUnit", required = true, value = "单位时间:seconds-秒,minutes-分钟,hours-小时,days-天", allowableValues = "seconds,minutes,hours,days", paramType = "form"),
    })

    @PostMapping("/gateway/limit/rate/save")
    public ResultBody save(
            @RequestParam(value = "policyId",required = false) Long policyId,
            @RequestParam(value = "policyName") String policyName,
            @RequestParam(value = "policyType") String policyType,
            @RequestParam(value = "limitQuota") Long limitQuota,
            @RequestParam(value = "intervalUnit") String intervalUnit
    ) {
        GatewayRateLimit rateLimit = new GatewayRateLimit();
        rateLimit.setPolicyId(policyId);
        rateLimit.setPolicyName(policyName);
        rateLimit.setLimitQuota(limitQuota);
        rateLimit.setIntervalUnit(intervalUnit);
        rateLimit.setPolicyType(policyType);
        return gatewayRateLimitServiceClient.save(rateLimit);
    }

    /**
     * 移除流量策略
     *
     * @param policyId
     * @return
     */
    @ApiOperation(value = "移除流量策略", notes = "移除流量策略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", required = true, value = "policyId", paramType = "form"),
    })
    @PostMapping("/gateway/limit/rate/remove")
    public ResultBody remove(
            @RequestParam("policyId") Long policyId
    ) {
        return gatewayRateLimitServiceClient.remove(policyId);
    }
}
