package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.utils.BeanPlusUtil;
import com.boxiaoyun.common.utils.SnowFlake;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.dto.RoleSaveDTO;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemRoleOrg;
import com.boxiaoyun.system.client.model.entity.SystemRoleUser;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import com.boxiaoyun.system.mapper.SystemRoleMapper;
import com.boxiaoyun.system.mapper.SystemRoleUserMapper;
import com.boxiaoyun.system.service.RoleOrgService;
import com.boxiaoyun.system.service.SystemRoleService;
import com.boxiaoyun.system.service.SystemUserService;
import com.boxiaoyun.system.strategy.DataScopeContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {
    @Autowired
    private SystemRoleUserMapper systemRoleUserMapper;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private DataScopeContext dataScopeContext;
    @Autowired
    private RoleOrgService roleOrgService;
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public Page<SystemRole> findPage(PageParams pageParams) {
        SystemRole query = pageParams.mapToBean(SystemRole.class);
        QueryWrapper<SystemRole> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getCode()), SystemRole::getCode, query.getCode())
                .likeRight(ObjectUtils.isNotEmpty(query.getName()), SystemRole::getName, query.getName());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<SystemRole> findList() {
        List<SystemRole> list = list(new QueryWrapper<>());
        return list;
    }

    /**
     * 添加角色
     *
     * @param role 角色
     * @return
     */
    @Override
    public SystemRole add(Long userId,RoleSaveDTO data) {
        SystemRole role = BeanPlusUtil.toBean(data, SystemRole.class);
        if (isExist(role.getCode())) {
            throw new BaseException(String.format("%s编码已存在!", role.getCode()));
        }
        if (role.getStatus() == null) {
            role.setStatus(SystemConstants.ENABLED);
        }
        if (role.getIsPersist() == null) {
            role.setIsPersist(SystemConstants.DISABLED);
        }
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        save(role);
        saveRoleOrg(userId, role, data.getOrgList());
        return role;
    }

    /**
     * 更新角色
     *
     * @param role 角色
     * @return
     */
    @Override
    public SystemRole update(SystemRole role) {
        SystemRole saved = getById(role.getRoleId());
        if (role == null) {
            throw new BaseException("信息不存在!");
        }
        if (!saved.getCode().equals(role.getCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(role.getCode())) {
                throw new BaseException(String.format("%s编码已存在!", role.getCode()));
            }
        }
        role.setUpdateTime(new Date());
        updateById(role);
        return role;
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public void remove(Long roleId) {
        if (roleId == null) {
            return;
        }
        SystemRole role = getById(roleId);
        if (role != null && role.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止删除"));
        }
        int count = getCountByRoleId(roleId);
        if (count > 0) {
            throw new BaseException("该角色下存在授权人员,禁止删除!");
        }
        removeById(roleId);
    }

    /**
     * 检测角色编码是否存在
     *
     * @param code
     * @return
     */
    @Override
    public Boolean isExist(String code) {
        if (StringUtil.isBlank(code)) {
            throw new BaseException("code不能为空!");
        }
        QueryWrapper<SystemRole> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemRole::getCode, code);
        return count(queryWrapper) > 0;
    }

    /**
     * 用户添加角色
     *
     * @param userId
     * @param roles
     * @return
     */
    @Override
    public void saveRoles(Long userId, String... roles) {
        if (userId == null || roles == null) {
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
        if (roles != null && roles.length > 0) {
            for (String roleId : roles) {
                SystemRoleUser roleUser = new SystemRoleUser();
                roleUser.setUserId(userId);
                roleUser.setRoleId(Long.parseLong(roleId));
                systemRoleUserMapper.insert(roleUser);
            }
            // 批量保存
        }
    }

    /**
     * 角色添加成员
     *
     * @param roleId
     * @param userIds
     */
    @Override
    public void saveUsers(Long roleId, List<Long> userIds) {
        if (roleId == null || userIds == null) {
            return;
        }
        // 先清空,在添加
        removeUsersByRoleId(roleId);
        if (userIds != null && userIds.size() > 0) {
            for (Long userId : userIds) {
                SystemRoleUser roleUser = new SystemRoleUser();
                roleUser.setUserId(userId);
                roleUser.setRoleId(roleId);
                systemRoleUserMapper.insert(roleUser);
            }
            // 批量保存
        }
    }

    /**
     * 查询角色成员
     *
     * @return
     */
    @Override
    public List<SystemRoleUser> findUsersByRoleId(Long roleId) {
        QueryWrapper<SystemRoleUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemRoleUser::getRoleId, roleId);
        return systemRoleUserMapper.selectList(queryWrapper);
    }

    /**
     * 获取角色所有授权组员数量
     *
     * @param roleId
     * @return
     */
    @Override
    public int getCountByRoleId(Long roleId) {
        QueryWrapper<SystemRoleUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemRoleUser::getRoleId, roleId);
        int result = systemRoleUserMapper.selectCount(queryWrapper);
        return result;
    }

    /**
     * 获取组员角色数量
     *
     * @param userId
     * @return
     */
    @Override
    public int getCountByUserId(Long userId) {
        QueryWrapper<SystemRoleUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemRoleUser::getUserId, userId);
        int result = systemRoleUserMapper.selectCount(queryWrapper);
        return result;
    }

    /**
     * 移除角色所有组员
     *
     * @param roleId
     * @return
     */
    @Override
    public void removeUsersByRoleId(Long roleId) {
        QueryWrapper<SystemRoleUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemRoleUser::getRoleId, roleId);
        systemRoleUserMapper.delete(queryWrapper);
    }

    /**
     * 移除组员的所有角色
     *
     * @param userId
     * @return
     */
    @Override
    public void removeRolesByUserId(Long userId) {
        QueryWrapper<SystemRoleUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemRoleUser::getUserId, userId);
        systemRoleUserMapper.delete(queryWrapper);
    }

    /**
     * 检测是否存在
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public Boolean isExist(Long userId, Long roleId) {
        QueryWrapper<SystemRoleUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemRoleUser::getRoleId, roleId);
        queryWrapper.lambda().eq(SystemRoleUser::getUserId, userId);
        systemRoleUserMapper.delete(queryWrapper);
        int result = systemRoleUserMapper.selectCount(queryWrapper);
        return result > 0;
    }


    /**
     * 获取组员角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<SystemRole> findRolesByUserId(Long userId) {
        List<SystemRole> roles = systemRoleUserMapper.selectUsersByRoleId(userId);
        return roles;
    }

    /**
     * 获取用户角色ID列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        return systemRoleUserMapper.selectUsersIdByRoleId(userId);
    }

    private void saveRoleOrg(Long userId, SystemRole role, List<Long> orgList) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        // 根据 数据范围类型 和 勾选的组织ID， 重新计算全量的组织ID
        List<Long> orgIds = dataScopeContext.getOrgIdsForDataScope(orgList, role.getDsType(), userId);
        if (orgIds != null && !orgIds.isEmpty()) {
            List<SystemRoleOrg> list = orgIds.stream().map((orgId) ->
                    SystemRoleOrg.builder().id(snowFlake.nextId())
                            .orgId(orgId).roleId(role.getRoleId())
                            .build()
            ).collect(Collectors.toList());
            roleOrgService.saveBatch(list);
        }
    }
}
