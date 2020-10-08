package com.boxiaoyun.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemRoleOrg;

/**
 * <p>
 * 业务接口
 * 角色组织关系
 * </p>
 *
 * @author bxy
 * @date 2019-07-03
 */
public interface RoleOrgService extends IBaseService<SystemRoleOrg> {

    /**
     * 根据角色id查询
     *
     * @param id
     * @return
     */
    List<Long> listOrgByRoleId(Long id);
}

