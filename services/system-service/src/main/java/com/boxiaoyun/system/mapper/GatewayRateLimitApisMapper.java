package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.RateLimitApi;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import com.boxiaoyun.system.client.model.entity.GatewayRateLimitApi;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface GatewayRateLimitApisMapper extends SuperMapper<GatewayRateLimitApi> {

    List<RateLimitApi> selectRateLimitApi();

}
