package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.system.client.model.RateLimitApi;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimit;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimitApi;
import com.boxiaoyun.system.mapper.GatewayRateLimitApisMapper;
import com.boxiaoyun.system.mapper.GatewayRateLimitMapper;
import com.boxiaoyun.system.service.GatewayRateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRateLimitServiceImpl extends BaseServiceImpl<GatewayRateLimitMapper, GatewayRateLimit> implements GatewayRateLimitService {
    @Autowired
    private GatewayRateLimitApisMapper gatewayRateLimitApisMapper;

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<GatewayRateLimit> findPage(PageParams pageParams) {
        GatewayRateLimit query = pageParams.mapToBean(GatewayRateLimit.class);
        QueryWrapper<GatewayRateLimit> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getPolicyName()),GatewayRateLimit::getPolicyName, query.getPolicyName())
                .eq(ObjectUtils.isNotEmpty(query.getPolicyType()),GatewayRateLimit::getPolicyType, query.getPolicyType());
        queryWrapper.orderByDesc("create_time");
        return this.baseMapper.selectPage(pageParams,queryWrapper);
    }

    /**
     * 查询接口流量限制
     *
     * @return
     */
    @Override
    public List<RateLimitApi> findApiList() {
        List<RateLimitApi> list = gatewayRateLimitApisMapper.selectRateLimitApi();
        return list;
    }

    /**
     * 查询策略已绑定API列表
     *
     * @param policyId
     * @return
     */
    @Override
    public List<GatewayRateLimitApi> findApiList(Long policyId) {
        QueryWrapper<GatewayRateLimitApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(GatewayRateLimitApi::getPolicyId, policyId);
        List<GatewayRateLimitApi> list = gatewayRateLimitApisMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 删除IP限制策略
     *
     * @param policyId
     */
    @Override
    public void remove(Long policyId) {
        clearByPolicyId(policyId);
        removeById(policyId);
    }

    /**
     * 绑定API, 一个API只能绑定一个策略
     *
     * @param policyId
     * @param apis
     */
    @Override
    public void saveApis(Long policyId, String... apis) {
        // 先清空策略已有绑定
        clearByPolicyId(policyId);
        if (apis != null && apis.length > 0) {
            for (String api : apis) {
                Long apiId = Long.parseLong(api);
                // 先api解除所有绑定, 一个API只能绑定一个策略
                clearByApiId(apiId);
                GatewayRateLimitApi item = new GatewayRateLimitApi();
                item.setApiId(apiId);
                item.setPolicyId(policyId);
                // 重新绑定策略
                gatewayRateLimitApisMapper.insert(item);
            }
        }
    }

    /**
     * 清空绑定的API
     *
     * @param policyId
     */
    @Override
    public void clearByPolicyId(Long policyId) {
        QueryWrapper<GatewayRateLimitApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(GatewayRateLimitApi::getPolicyId, policyId);
        gatewayRateLimitApisMapper.delete(queryWrapper);
    }

    /**
     * API解除所有策略
     *
     * @param apiId
     */
    @Override
    public void clearByApiId(Long apiId) {
        QueryWrapper<GatewayRateLimitApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(GatewayRateLimitApi::getApiId, apiId);
        gatewayRateLimitApisMapper.delete(queryWrapper);
    }
}
