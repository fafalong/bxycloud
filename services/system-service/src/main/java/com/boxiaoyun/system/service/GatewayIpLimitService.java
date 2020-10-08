package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.IpLimitApi;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimit;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimitApi;

import java.util.List;

/**
 * 网关IP访问控制
 *
 * @author
 */
public interface GatewayIpLimitService extends IBaseService<GatewayIpLimit> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<GatewayIpLimit> findPage(PageParams pageParams);

    /**
     * 查询白名单
     *
     * @return
     */
    List<IpLimitApi> findBlackList();

    /**
     * 查询黑名单
     *
     * @return
     */
    List<IpLimitApi> findWhiteList();

    /**
     * 查询策略已绑定API列表
     *
     * @return
     */
    List<GatewayIpLimitApi> findApiList(Long policyId);

    /**
     * 删除IP限制策略
     *
     * @param policyId
     */
    void remove(Long policyId);

    /**
     * 绑定API, 一个API只能绑定一个策略
     *
     * @param policyId
     * @param apis
     */
    void saveApis(Long policyId, String... apis);

    /**
     * 清空绑定的API
     *
     * @param policyId
     */
    void clearByPolicyId(Long policyId);

    /**
     * API解除所有策略
     *
     * @param apiId
     */
    void clearByApiId(Long apiId);

}
