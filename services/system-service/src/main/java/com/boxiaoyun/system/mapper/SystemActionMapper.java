package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.entity.SystemAction;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface SystemActionMapper extends SuperMapper<SystemAction> {
    List<String> findActionByRoleId(@Param("roleId") Long roleId);
}
