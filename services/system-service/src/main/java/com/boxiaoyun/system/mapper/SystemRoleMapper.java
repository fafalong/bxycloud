package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Repository
public interface SystemRoleMapper extends SuperMapper<SystemRole> {

    /**
     * 查询角色列表
     * @param params
     * @return
     */
    List<SystemRole> selectRoleList(Map params);
}
