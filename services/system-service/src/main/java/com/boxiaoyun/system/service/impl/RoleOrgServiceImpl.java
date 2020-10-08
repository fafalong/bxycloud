package com.boxiaoyun.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.github.bxy.database.mybatis.conditions.Wraps;

import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.system.client.model.entity.SystemRoleOrg;
import com.boxiaoyun.system.mapper.SystemRoleOrgMapper;
import com.boxiaoyun.system.service.RoleOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 角色组织关系
 * </p>
 *
 * @author bxy
 * @date 2019-07-03
 */
@Slf4j
@Service
public class RoleOrgServiceImpl extends BaseServiceImpl<SystemRoleOrgMapper, SystemRoleOrg> implements RoleOrgService {
    @Override
    public List<Long> listOrgByRoleId(Long roleId) {
        //List<SystemRoleOrg> list = super.list(Wraps.<SystemRoleOrg>lbQ().eq(SystemRoleOrg::getRoleId, id));
        //List<Long> orgList = list.stream().mapToLong(SystemRoleOrg::getOrgId).boxed().collect(Collectors.toList());
        //return orgList;
        return null;
    }
}