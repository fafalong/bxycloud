package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.IpLimitApi;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import com.boxiaoyun.system.client.model.entity.GatewayIpLimitApi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface GatewayIpLimitApisMapper extends SuperMapper<GatewayIpLimitApi> {

    List<IpLimitApi> selectIpLimitApi(@Param("policyType") int policyType);
}
