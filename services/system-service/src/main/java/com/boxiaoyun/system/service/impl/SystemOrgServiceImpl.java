package com.boxiaoyun.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
//import com.boxiaoyun.common.mybatis.conditions.Wraps;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.entity.SystemOrg;
//import com.boxiaoyun.system.client.model.entity.SystemOrgUser;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemUser;
//import com.boxiaoyun.system.mapper.SystemOrgMapper;
//import com.boxiaoyun.system.mapper.SystemOrgUserMapper;
import com.boxiaoyun.system.mapper.SystemOrgMapper;
import com.boxiaoyun.system.service.RoleOrgService;
import com.boxiaoyun.system.service.SystemOrgService;
import com.boxiaoyun.system.service.SystemUserService;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemOrgServiceImpl extends BaseServiceImpl<SystemOrgMapper, SystemOrg> implements SystemOrgService {

/*    @Autowired
    private SystemOrgMapper systemOrgMapper;
    @Autowired
    private SystemUserService systemUserService;*/
    @Autowired
    private RoleOrgService roleOrgService;

    @Override
    public List<SystemOrg> findChildren(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // MySQL 全文索引
        //String applySql = String.format(" MATCH(tree_path) AGAINST('%s' IN BOOLEAN MODE) ", StringUtils.join(ids, " "));
        //return super.list(Wraps.<SystemOrg>lbQ().in(SystemOrg::getId, ids).or(query -> query.apply(applySql)));
        QueryWrapper<SystemOrg> queryWrapper = new QueryWrapper();
        queryWrapper.in("id",ids);
        /*queryWrapper.lambda()
                //.likeRight(ObjectUtils.isNotEmpty(query.getCode()), SystemOrg::getCode, query.getCode())
                .likeRight(ObjectUtils.isNotEmpty(query.getLabel()), SystemOrg::getId, query.getLabel());
        queryWrapper.orderByDesc("create_time");*/
        List<SystemOrg> list = list(queryWrapper);
        return list;
    }
    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<SystemOrg> findList() {
        List<SystemOrg> list = list(new QueryWrapper<>());
        return list;
    }
    /**
     * 添加组织
     *
     * @param systemOrg
     * @return
     */
    @Override
    public void add(SystemOrg systemOrg) {
        save(systemOrg);
        // 同步权限表里的信息
    }

    /**
     * 删除组织
     *
     * @param orgId 组织ID
     * @return
     */
    @Override
    public void remove(Long orgId) {
        if (orgId == null) {
            return;
        }
        SystemOrg role = getById(orgId);
        removeById(orgId);
    }

    /**
     * 修改组织资源
     *
     * @param systemOrg
     * @return
     */
    @Override
    public SystemOrg update(SystemOrg systemOrg){
        SystemOrg saved = getById(systemOrg.getId());
        if (systemOrg == null) {
            throw new BaseException("信息不存在!");
        }
        systemOrg.setUpdateTime(new Date());
        updateById(systemOrg);
        return systemOrg;
    }
    /**
     * TODO 这里应该做缓存
     *
     * @param ids
     * @return
     */
/*    @Override
    public Map<Serializable, Object> findOrgByIds(Set<Long> ids) {
        LbqWrapper<SystemOrg> query = Wraps.<SystemOrg>lbQ()
                .in(SystemOrg::getId, ids)
                .eq(SystemOrg::getStatus, true);
        List<SystemOrg> list = super.list(query);

        //key 是 组织id， value 是org 对象
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, SystemOrg::getId, (org) -> org);
        return typeMap;
    }*/

    /**
    * 分页查询
    *
    * @param pageParams
    * @return
    */
    @Override
    public Page<SystemOrg> findPage(PageParams pageParams) {
        SystemOrg query = pageParams.mapToBean(SystemOrg.class);
        QueryWrapper<SystemOrg> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                //.likeRight(ObjectUtils.isNotEmpty(query.getCode()), SystemOrg::getCode, query.getCode())
                .likeRight(ObjectUtils.isNotEmpty(query.getLabel()), SystemOrg::getLabel, query.getLabel());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams, queryWrapper);
    }
/**
     * 查询列表
     *
     * @return
     *//*

    @Override
    public List<SystemOrg> findList() {
        List<SystemOrg> list = list(new QueryWrapper<>());
        return list;
    }

    */
/**
     * 添加角色
     *
     * @param systemOrg 角色
     * @return
     *//*

    @Override
    public SystemOrg add(SystemOrg systemOrg) {
        if (isExist(systemOrg.getLable())) {
            throw new BaseException(String.format("%s编码已存在!", systemOrg.getLable()));
        }
        if (systemOrg.getStatus() == null) {
            systemOrg.setStatus(SystemConstants.ENABLED);
        }
*/
/*        if (systemOrg.getIsPersist() == null) {
            systemOrg.setIsPersist(SystemConstants.DISABLED);
        }*//*

        systemOrg.setCreateTime(LocalDateTime.now());
        systemOrg.setUpdateTime(systemOrg.getCreateTime());
        save(systemOrg);
        return systemOrg;
    }

    */
/**
     * 更新角色
     *
     * @param systemOrg 角色
     * @return
     *//*

    @Override
    public SystemOrg update(SystemOrg systemOrg) {
        SystemOrg saved = getById(systemOrg.getId());
        if (systemOrg == null) {
            throw new BaseException("信息不存在!");
        }
        if (!saved.getLabel().equals(systemOrg.getLabel())) {
            // 和原来不一致重新检查唯一性
            if (isExist(systemOrg.getLabel())) {
                throw new BaseException(String.format("%s编码已存在!", systemOrg.getCode()));
            }
        }
        systemOrg.setUpdateTime(LocalDateTime.now());
        updateById(systemOrg);
        return systemOrg;
    }

    */
/**
     * 删除组织
     *
     * @param orgId 组织ID
     * @return
     *//*

    @Override
    public void remove(Long orgId) {
        if (orgId == null) {
            return;
        }
        SystemOrg role = getById(orgId);
*/
/*        if (role != null && role.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止删除"));
        }*//*

        int count = getCountByRoleId(orgId);
        if (count > 0) {
            throw new BaseException("该组织下存在授权人员,禁止删除!");
        }
        removeById(orgId);
    }

    */
/**
     * 检测组织编码是否存在
     *
     * @param code
     * @return
     *//*

    @Override
    public Boolean isExist(String code) {
        if (StringUtil.isBlank(code)) {
            throw new BaseException("code不能为空!");
        }
        QueryWrapper<SystemOrg> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemOrg::getLabel, code);
        return count(queryWrapper) > 0;
    }

    */
/**
     * 用户添加角色
     *
     * @param userId
     * @param systemOrg
     * @return
     *//*

    @Override
    public void saveOrg(Long userId, String... systemOrg) {
*/
/*        if (userId == null || systemOrg == null) {
            return;
        }
        SystemUser user = systemUserService.getById(userId);
        if (user == null) {
            return;
        }
        if (CommonConstants.ROOT.equals(user.getUserName())) {
            throw new BaseException("默认用户无需分配!");
        }
        // 先清空,在添加
        removeRolesByUserId(userId);
        if (systemOrg != null && systemOrg.length > 0) {
            for (String roleId : systemOrg) {
                SystemOrgUser roleUser = new SystemOrgUser();
                roleUser.setUserId(userId);
                roleUser.setRoleId(Long.parseLong(roleId));
                systemRoleUserMapper.insert(roleUser);
            }
            // 批量保存
        }*//*

    }

    */
/**
     * 添加成员
     *
     * @param roleId
     * @param userIds
     *//*

    @Override
    public void saveUsers(Long roleId, String... userIds) {
*/
/*        if (roleId == null || userIds == null) {
            return;
        }
        // 先清空,在添加
        removeUsersByRoleId(roleId);
        if (userIds != null && userIds.length > 0) {
            for (String userId : userIds) {
                SystemOrgUser roleUser = new SystemOrgUser();
                roleUser.setUserId(Long.parseLong(userId));
                roleUser.setRoleId(roleId);
                systemRoleUserMapper.insert(roleUser);
            }
            // 批量保存
        }*//*

    }

    */
/**
     * 查询角色成员
     *
     * @return
     *//*

    @Override
    public List<SystemOrgUser> findUsersByRoleId(Long roleId) {
        QueryWrapper<SystemOrgUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemOrgUser::getRoleId, roleId);
        return systemRoleUserMapper.selectList(queryWrapper);
    }

    */
/**
     * 获取角色所有授权组员数量
     *
     * @param roleId
     * @return
     *//*

    @Override
    public int getCountByOrgId(Long orgId) {
        QueryWrapper<SystemOrgUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemOrgUser::getRoleId, roleId);
        int result = systemRoleUserMapper.selectCount(queryWrapper);
        return result;
    }

    */
/**
     * 获取组员角色数量
     *
     * @param userId
     * @return
     *//*

    @Override
    public int getCountByUserId(Long userId) {
        QueryWrapper<SystemOrgUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemOrgUser::getUserId, userId);
        int result = systemRoleUserMapper.selectCount(queryWrapper);
        return result;
    }

    */
/**
     * 移除角色所有组员
     *
     * @param roleId
     * @return
     *//*

    @Override
    public void removeUsersByRoleId(Long roleId) {
        QueryWrapper<SystemOrgUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemOrgUser::getRoleId, roleId);
        systemRoleUserMapper.delete(queryWrapper);
    }

    */
/**
     * 移除组员的所有角色
     *
     * @param userId
     * @return
     *//*

    @Override
    public void removeRolesByUserId(Long userId) {
        QueryWrapper<SystemOrgUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemOrgUser::getUserId, userId);
        systemRoleUserMapper.delete(queryWrapper);
    }

    */
/**
     * 检测是否存在
     *
     * @param userId
     * @param roleId
     * @return
     *//*

    @Override
    public Boolean isExist(Long userId, Long roleId) {
        QueryWrapper<SystemOrgUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemOrgUser::getRoleId, roleId);
        queryWrapper.lambda().eq(SystemOrgUser::getUserId, userId);
        systemRoleUserMapper.delete(queryWrapper);
        int result = systemRoleUserMapper.selectCount(queryWrapper);
        return result > 0;
    }


    */
/**
     * 获取组员角色
     *
     * @param userId
     * @return
     *//*

    @Override
    public List<SystemOrg> findRolesByUserId(Long userId) {
        List<SystemOrg> roles = systemRoleUserMapper.selectUsersByRoleId(userId);
        return roles;
    }

    */
/**
     * 获取用户角色ID列表
     *
     * @param userId
     * @return
     *//*

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        return systemRoleUserMapper.selectUsersIdByRoleId(userId);
    }

*/

}
