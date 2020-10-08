package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.GatewayIpLimitServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimit;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimitApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 网关IP访问控制
 *
 * @author:
 * @date: 2019/3/12 15:12
 * @description:
 */
@Api(tags = "网关IP访问控制")
@RestController
public class GatewayIpLimitController {

    @Autowired
    private GatewayIpLimitServiceClient gatewayIpLimitServiceClient;

    /**
     * 获取IP策略策略列表
     *
     * @return
     */
    @ApiOperation(value = "获取IP策略策略列表", notes = "获取IP策略策略列表")
    @GetMapping("/gateway/limit/ip")
    public ResultBody<Page<GatewayIpLimit>> getPage(@RequestParam(required = false) Map map) {
        return gatewayIpLimitServiceClient.getPage(map);
    }

    /**
     * 查询IP策略策略已绑定API列表
     *
     * @param policyId
     * @return
     */
    @ApiOperation(value = "查询IP策略策略已绑定API列表", notes = "查询IP策略策略已绑定API列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", value = "策略ID", paramType = "form"),
    })
    @GetMapping("/gateway/limit/ip/api/list")
    public ResultBody<List<GatewayIpLimitApi>> getApis(
            @RequestParam("policyId") Long policyId
    ) {
        return gatewayIpLimitServiceClient.getApis(policyId);
    }

    /**
     * IP策略策略绑定API
     *
     * @param policyId 策略ID
     * @param apiIds   API接口ID.多个以,隔开.选填
     * @return
     */
    @ApiOperation(value = "IP策略策略绑定API", notes = "一个API只能绑定一个策略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", value = "策略ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "apiIds", value = "API接口ID.多个以,隔开.选填", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/gateway/limit/ip/api/save")
    public ResultBody saveApis(
            @RequestParam("policyId") Long policyId,
            @RequestParam(value = "apiIds", required = false) String apiIds
    ) {
        return gatewayIpLimitServiceClient.saveApis(policyId, apiIds);
    }

    /**
     * 获取IP策略
     *
     * @param policyId
     * @return
     */
    @ApiOperation(value = "获取IP策略", notes = "获取IP策略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", required = true, value = "策略ID", paramType = "path"),
    })
    @GetMapping("/gateway/limit/ip/info")
    public ResultBody<GatewayIpLimit> get(@RequestParam("policyId") Long policyId) {
        return gatewayIpLimitServiceClient.get(policyId);
    }

    /**
     * 添加/编辑IP策略
     *
     * @param policyId   IP策略ID
     * @param policyName 策略名称
     * @param policyType 策略类型:0-拒绝/黑名单 1-允许/白名单
     * @param ipAddress  ip地址/IP段:多个用隔开;最多10个
     * @return
     */
    @ApiOperation(value = "添加/编辑IP策略", notes = "添加/编辑IP策略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", required = false, value = "接口Id", paramType = "form"),
            @ApiImplicitParam(name = "policyName", required = true, value = "策略名称", paramType = "form"),
            @ApiImplicitParam(name = "policyType", required = true, value = "策略类型:0-拒绝/黑名单 1-允许/白名单", allowableValues = "0,1", paramType = "form"),
            @ApiImplicitParam(name = "ipAddress", required = true, value = "ip地址/IP段:多个用隔开;最多10个", paramType = "form")
    })

    @PostMapping("/gateway/limit/ip/save")
    public ResultBody save(
            @RequestParam(value = "policyId",required = false) Long policyId,
            @RequestParam(value = "policyName") String policyName,
            @RequestParam(value = "policyType") Integer policyType,
            @RequestParam(value = "ipAddress") String ipAddress
    ) {
        GatewayIpLimit ipLimit = new GatewayIpLimit();
        ipLimit.setPolicyId(policyId);
        ipLimit.setPolicyName(policyName);
        ipLimit.setPolicyType(policyType);
        ipLimit.setIpAddress(ipAddress);
        return gatewayIpLimitServiceClient.save(ipLimit);
    }


    /**
     * 移除IP策略
     *
     * @param policyId
     * @return
     */
    @ApiOperation(value = "移除IP策略", notes = "移除IP策略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "policyId", required = true, value = "policyId", paramType = "form"),
    })
    @PostMapping("/gateway/limit/ip/remove")
    public ResultBody remove(
            @RequestParam("policyId") Long policyId
    ) {
        return  gatewayIpLimitServiceClient.remove(policyId);
    }
}
