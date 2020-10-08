package com.boxiaoyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boxiaoyun.system.client.model.entity.SystemAuthorityApp;
import com.boxiaoyun.common.security.BaseAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public interface SystemAuthorityAppMapper extends BaseMapper<SystemAuthorityApp> {

    /**
     * 获取应用已授权权限
     *
     * @param appId
     * @return
     */
    List<BaseAuthority> selectAuthorityByApp(@Param("appId") String appId);
}
