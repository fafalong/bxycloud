package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.entity.SystemApi;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface SystemApiMapper extends SuperMapper<SystemApi> {
    List<String> selectServiceList();
}
