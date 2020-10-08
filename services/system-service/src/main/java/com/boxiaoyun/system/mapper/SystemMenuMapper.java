package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.entity.SystemMenu;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lj
 */
@Repository
public interface SystemMenuMapper extends SuperMapper<SystemMenu> {

    List<SystemMenu> findMenuByRoleId(Long roleId);
}
