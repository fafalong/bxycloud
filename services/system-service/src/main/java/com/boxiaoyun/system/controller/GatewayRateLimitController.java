package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimit;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimitApi;
import com.boxiaoyun.system.client.service.IGatewayRateLimitClient;
import com.boxiaoyun.system.service.GatewayRateLimitService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 网关流量控制
 *
 * @author:
 * @date: 2019/3/12 15:12
 * @description:
 */
@Api(tags = "网关流量控制")
@RestController
public class GatewayRateLimitController extends BaseController<GatewayRateLimitService, GatewayRateLimit> implements IGatewayRateLimitClient {

    @Autowired
    private RestUtil restUtil;

    /**
     * 查询获取流量策略列表
     *
     * @param map
     * @return
     */
    @GetMapping("/gateway/limit/rate")
    @Override
    public ResultBody<Page<GatewayRateLimit>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取流量策略
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/rate/info")
    @Override
    public ResultBody<GatewayRateLimit> get(@RequestParam("policyId") Long policyId) {
        return ResultBody.success(bizService.getById(policyId));
    }

    /**
     * 添加/修改流量策略
     *
     * @param gatewayRateLimit
     * @return
     */
    @PostMapping("/gateway/limit/rate/save")
    @Override
    public ResultBody<Long> save(@RequestBody GatewayRateLimit gatewayRateLimit) {
        bizService.saveOrUpdate(gatewayRateLimit);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 移除流量策略
     *
     * @param policyId
     * @return
     */
    @PostMapping("/gateway/limit/rate/remove")
    @Override
    public ResultBody remove(@RequestParam("policyId") Long policyId) {
        bizService.remove(policyId);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 流量策略绑定API
     *
     * @param policyId
     * @param apiIds
     * @return
     */
    @PostMapping("/gateway/limit/rate/save/api")
    @Override
    public ResultBody saveApis(
            @RequestParam("policyId") Long policyId,
            @RequestParam(value = "apiIds", required = false) String apiIds
    ) {
        bizService.saveApis(policyId, apiIds != null ? StringUtils.commaDelimitedListToStringArray(apiIds) : new String[]{});
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 查询获取流量策略已绑定API列表
     *
     * @param policyId
     * @return
     */
    @GetMapping("/gateway/limit/rate/api/list")
    @Override
    public ResultBody<List<GatewayRateLimitApi>> getApis(@RequestParam("policyId") Long policyId) {
        return ResultBody.success(bizService.findApiList(policyId));
    }
}
