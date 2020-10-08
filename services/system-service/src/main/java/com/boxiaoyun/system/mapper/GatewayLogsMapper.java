package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.entity.GatewayAccessLogs;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

/**
 * @author
 */
@Repository
public interface GatewayLogsMapper extends SuperMapper<GatewayAccessLogs> {
}
