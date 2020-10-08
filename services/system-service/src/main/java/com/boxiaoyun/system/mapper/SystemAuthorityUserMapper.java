package com.boxiaoyun.system.mapper;

import com.boxiaoyun.system.client.model.AuthorityMenu;
import com.boxiaoyun.system.client.model.entity.SystemAuthorityUser;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import com.boxiaoyun.common.security.BaseAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface SystemAuthorityUserMapper extends SuperMapper<SystemAuthorityUser> {

    /**
     * 获取用户已授权权限
     *
     * @param userId
     * @return
     */
    List<BaseAuthority> selectAuthorityByUser(@Param("userId") Long userId);

    /**
     * 获取用户已授权权限完整信息
     *
     * @param userId
     * @return
     */
    List<AuthorityMenu> selectAuthorityMenuByUser(@Param("userId") Long userId);
}
