package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.AuthorityMenu;
import com.boxiaoyun.system.client.model.entity.SystemAuthorityRole;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import com.boxiaoyun.common.security.BaseAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface SystemAuthorityRoleMapper extends SuperMapper<SystemAuthorityRole> {

    /**
     * 获取角色已授权权限
     *
     * @param roleId
     * @return
     */
    List<BaseAuthority> selectAuthorityByRole(@Param("roleId") Long roleId);

    /**
     * 获取角色菜单权限
     *
     * @param roleId
     * @return
     */
    List<AuthorityMenu> selectAuthorityMenuByRole(@Param("roleId") Long roleId);
}
