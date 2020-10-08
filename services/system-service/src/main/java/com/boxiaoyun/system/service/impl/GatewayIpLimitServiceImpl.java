package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.system.client.model.IpLimitApi;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimit;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimitApi;
import com.boxiaoyun.system.mapper.GatewayIpLimitApisMapper;
import com.boxiaoyun.system.mapper.GatewayIpLimitMapper;
import com.boxiaoyun.system.service.GatewayIpLimitService;
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
public class GatewayIpLimitServiceImpl extends BaseServiceImpl<GatewayIpLimitMapper, GatewayIpLimit> implements GatewayIpLimitService {

    @Autowired
    private GatewayIpLimitApisMapper gatewayIpLimitApisMapper;


    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<GatewayIpLimit> findPage(PageParams pageParams) {
        GatewayIpLimit query = pageParams.mapToBean(GatewayIpLimit.class);
        QueryWrapper<GatewayIpLimit> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getPolicyName()),GatewayIpLimit::getPolicyName, query.getPolicyName())
                .eq(ObjectUtils.isNotEmpty(query.getPolicyType()),GatewayIpLimit::getPolicyType, query.getPolicyType());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams,queryWrapper);
    }

    /**
     * 查询白名单
     *
     * @return
     */
    @Override
    public List<IpLimitApi> findBlackList() {
        List<IpLimitApi> list = gatewayIpLimitApisMapper.selectIpLimitApi(0);
        return list;
    }

    /**
     * 查询黑名单
     *
     * @return
     */
    @Override
    public List<IpLimitApi> findWhiteList() {
        List<IpLimitApi> list = gatewayIpLimitApisMapper.selectIpLimitApi(1);
        return list;
    }

    /**
     * 查询策略已绑定API列表
     *
     * @return
     */
    @Override
    public List<GatewayIpLimitApi> findApiList(Long policyId) {
        QueryWrapper<GatewayIpLimitApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(GatewayIpLimitApi::getPolicyId, policyId);
        List<GatewayIpLimitApi> list = gatewayIpLimitApisMapper.selectList(queryWrapper);
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
                // 先api解除所有绑定, 一个API只能绑定一个策略
                Long apiId = Long.parseLong(api);
                clearByApiId(apiId);
                GatewayIpLimitApi item = new GatewayIpLimitApi();
                item.setApiId(apiId);
                item.setPolicyId(policyId);
                // 重新绑定策略
                gatewayIpLimitApisMapper.insert(item);

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
        QueryWrapper<GatewayIpLimitApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(GatewayIpLimitApi::getPolicyId, policyId);
        gatewayIpLimitApisMapper.delete(queryWrapper);
    }

    /**
     * API解除所有策略
     *
     * @param apiId
     */
    @Override
    public void clearByApiId(Long apiId) {
        QueryWrapper<GatewayIpLimitApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(GatewayIpLimitApi::getApiId, apiId);
        gatewayIpLimitApisMapper.delete(queryWrapper);
    }
}
