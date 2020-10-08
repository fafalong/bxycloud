package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.entity.SystemOrg;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Repository
public interface SystemOrgMapper extends SuperMapper<SystemOrg> {

    /**
     * 查询组织列表
     * @param params
     * @return
     */
    List<SystemOrg> selectOrgList(Map params);
    SystemOrg findSystemOrgById(Long id);
}
