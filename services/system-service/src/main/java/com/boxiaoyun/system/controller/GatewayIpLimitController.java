package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimit;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimitApi;
import com.boxiaoyun.system.client.service.IGatewayIpLimitClient;
import com.boxiaoyun.system.service.GatewayIpLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 网关IP访问控制
 *
 * @author:
 * @date: 2019/3/12 15:12
 * @description:
 */
@RestController
public class GatewayIpLimitController extends BaseController<GatewayIpLimitService, GatewayIpLimit> implements IGatewayIpLimitClient {

    @Autowired
    private RestUtil restUtil;

    /**
     * 获取IP限制策略列表
     */
    @GetMapping("/gateway/limit/ip")
    @Override
    public ResultBody<Page<GatewayIpLimit>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取IP限制策略
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/ip/info")
    @Override
    public ResultBody<GatewayIpLimit> get(@RequestParam("policyId") Long policyId) {
        return ResultBody.success(bizService.getById(policyId));
    }

    /**
     * 添加/修改IP限制策略
     *
     * @param gatewayIpLimit
     * @return
     */
    @PostMapping("/gateway/limit/ip/save")
    @Override
    public ResultBody<Long> save(@RequestBody GatewayIpLimit gatewayIpLimit) {
        bizService.saveOrUpdate(gatewayIpLimit);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }


    /**
     * 移除IP限制
     *
     * @param policyId
     * @return
     */
    @PostMapping("/gateway/limit/ip/remove")
    @Override
    public ResultBody remove(@RequestParam("policyId") Long policyId) {
        bizService.remove(policyId);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 查询IP限制策略已绑定API列表
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/ip/api/list")
    @Override
    public ResultBody<List<GatewayIpLimitApi>> getApis(@RequestParam("policyId") Long policyId) {
        return ResultBody.success(bizService.findApiList(policyId));
    }

    /**
     * IP限制策略绑定API
     *
     * @param policyId
     * @param apiIds
     * @return
     */
    @PostMapping("/gateway/limit/ip/save/api")
    @Override
    public ResultBody saveApis(
            @RequestParam("policyId") Long policyId,
            @RequestParam(value = "apiIds", required = false) String apiIds
    ) {
        bizService.saveApis(policyId, apiIds != null ? StringUtils.commaDelimitedListToStringArray(apiIds) : null);
        restUtil.refreshGateway();
        return ResultBody.ok();
    }
}
