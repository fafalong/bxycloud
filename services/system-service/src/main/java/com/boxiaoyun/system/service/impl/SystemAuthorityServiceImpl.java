package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.security.BaseAuthority;
import com.boxiaoyun.common.security.BaseSecurityConstants;
import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.system.client.constants.ResourceType;
import com.boxiaoyun.system.client.model.AuthorityApi;
import com.boxiaoyun.system.client.model.AuthorityMenu;
import com.boxiaoyun.system.client.model.AuthorityResource;
import com.boxiaoyun.system.client.model.entity.*;
import com.boxiaoyun.system.mapper.*;
import com.boxiaoyun.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统权限管理
 * 对菜单、操作、API等进行权限分配操作
 *
 * @author
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemAuthorityServiceImpl extends BaseServiceImpl<SystemAuthorityMapper, SystemAuthority> implements SystemAuthorityService {

    @Autowired
    private SystemAuthorityRoleMapper systemAuthorityRoleMapper;
    @Autowired
    private SystemAuthorityUserMapper systemAuthorityUserMapper;
    @Autowired
    private SystemAuthorityAppMapper systemAuthorityAppMapper;
    @Autowired
    private SystemAuthorityActionMapper systemAuthorityActionMapper;
    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private SystemActionService systemActionService;
    @Autowired
    private SystemApiService systemApiService;
    @Autowired
    private SystemRoleService systemRoleService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemAppService systemAppService;
    @Autowired
    private RedisTokenStore redisTokenStore;

    @Value("${spring.application.name}")
    private String DEFAULT_SERVICE_ID;

    /**
     * 获取访问权限列表
     *
     * @return
     */
    @Override
    public List<AuthorityResource> findAuthorityResource() {
        List<AuthorityResource> list = Lists.newArrayList();
        // 已授权资源权限
        List<AuthorityResource> resourceList = baseMapper.selectAllAuthorityResource();
        if (resourceList != null) {
            list.addAll(resourceList);
        }
        return list;
    }

    /**
     * 获取菜单权限列表
     *
     * @return
     */
    @Override
    public List<AuthorityMenu> findAuthorityMenu(Integer status) {
        Map map = Maps.newHashMap();
        map.put("status", status);
        List<AuthorityMenu> authorities = baseMapper.selectAuthorityMenu(map);
        authorities.sort((AuthorityMenu h1, AuthorityMenu h2) -> h2.getPriority().compareTo(h1.getPriority()));
        return authorities;

    }

    @Override
    public List<AuthorityApi> findAuthorityApi(String serviceId) {
        Map map = Maps.newHashMap();
        map.put("serviceId", serviceId);
        map.put("status", 1);
        List<AuthorityApi> authorities = baseMapper.selectAuthorityApi(map);
        return authorities;

    }

    /**
     * 查询功能按钮权限列表
     *
     * @param actionId
     * @return
     */
    @Override
    public List<SystemAuthorityAction> findAuthorityAction(Long actionId) {
        QueryWrapper<SystemAuthorityAction> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemAuthorityAction::getActionId, actionId);
        return systemAuthorityActionMapper.selectList(queryWrapper);
    }


    /**
     * 保存或修改权限
     *
     * @param resourceId
     * @param resourceType
     * @return 权限Id
     */
    @Override
    public SystemAuthority saveOrUpdateAuthority(Long resourceId, ResourceType resourceType) {
        SystemAuthority systemAuthority = getAuthority(resourceId, resourceType);
        String authority = null;
        if (systemAuthority == null) {
            systemAuthority = new SystemAuthority();
        }
        //菜单资源
        if (ResourceType.menu.equals(resourceType)) {
            SystemMenu menu = systemMenuService.getById(resourceId);
            authority = BaseSecurityConstants.AUTHORITY_PREFIX_MENU + menu.getMenuCode();
            systemAuthority.setMenuId(resourceId);
            systemAuthority.setStatus(menu.getStatus());
        }
        //操作资源
        if (ResourceType.action.equals(resourceType)) {
            SystemAction operation = systemActionService.getById(resourceId);
            authority = BaseSecurityConstants.AUTHORITY_PREFIX_ACTION + operation.getActionCode();
            systemAuthority.setActionId(resourceId);
            systemAuthority.setStatus(operation.getStatus());
        }
        //api资源
        if (ResourceType.api.equals(resourceType)) {
            SystemApi api = systemApiService.getById(resourceId);
            authority = BaseSecurityConstants.AUTHORITY_PREFIX_API + api.getApiCode();
            systemAuthority.setApiId(resourceId);
            systemAuthority.setStatus(api.getStatus());
        }
        if (authority == null) {
            return null;
        }
        // 设置权限标识
        systemAuthority.setAuthority(authority);
        if (systemAuthority.getAuthorityId() == null) {
            systemAuthority.setCreateTime(new Date());
            systemAuthority.setUpdateTime(systemAuthority.getCreateTime());
            // 新增权限
            save(systemAuthority);
        } else {
            // 修改权限
            systemAuthority.setUpdateTime(new Date());
            updateById(systemAuthority);
        }
        return systemAuthority;
    }

    /**
     * 移除权限
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    @Override
    public void removeAuthority(Long resourceId, ResourceType resourceType) {
        removeGranted(resourceId, resourceType);
        QueryWrapper<SystemAuthority> queryWrapper = buildQueryWrapper(resourceId, resourceType);
        remove(queryWrapper);
    }

    /**
     * 获取权限
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    @Override
    public SystemAuthority getAuthority(Long resourceId, ResourceType resourceType) {
        if (resourceId == null || resourceType == null) {
            return null;
        }
        QueryWrapper<SystemAuthority> queryWrapper = buildQueryWrapper(resourceId, resourceType);
        return getOne(queryWrapper);
    }

    /**
     * 是否已被授权
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    @Override
    public void removeGranted(Long resourceId, ResourceType resourceType) {
        SystemAuthority authority = getAuthority(resourceId, resourceType);
        if (authority == null || authority.getAuthorityId() == null) {
            return;
        }
        QueryWrapper<SystemAuthorityRole> roleQueryWrapper = new QueryWrapper();
        roleQueryWrapper.lambda().eq(SystemAuthorityRole::getAuthorityId, authority.getAuthorityId());
        systemAuthorityRoleMapper.delete(roleQueryWrapper);
        QueryWrapper<SystemAuthorityUser> userQueryWrapper = new QueryWrapper();
        userQueryWrapper.lambda().eq(SystemAuthorityUser::getAuthorityId, authority.getAuthorityId());
        systemAuthorityUserMapper.delete(userQueryWrapper);
        QueryWrapper<SystemAuthorityApp> appQueryWrapper = new QueryWrapper();
        appQueryWrapper.lambda().eq(SystemAuthorityApp::getAuthorityId, authority.getAuthorityId());
        systemAuthorityAppMapper.delete(appQueryWrapper);
    }

    /**
     * 构建权限对象
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    private QueryWrapper<SystemAuthority> buildQueryWrapper(Long resourceId, ResourceType resourceType) {
        QueryWrapper<SystemAuthority> queryWrapper = new QueryWrapper();
        if (ResourceType.menu.equals(resourceType)) {
            queryWrapper.lambda().eq(SystemAuthority::getMenuId, resourceId);
        }
        if (ResourceType.action.equals(resourceType)) {
            queryWrapper.lambda().eq(SystemAuthority::getActionId, resourceId);
        }
        if (ResourceType.api.equals(resourceType)) {
            queryWrapper.lambda().eq(SystemAuthority::getApiId, resourceId);
        }
        return queryWrapper;
    }


    /**
     * 移除应用权限
     *
     * @param appId
     */
    @Override
    public void removeAuthorityApp(String appId) {
        QueryWrapper<SystemAuthorityApp> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemAuthorityApp::getAppId, appId);
        systemAuthorityAppMapper.delete(queryWrapper);
    }

    /**
     * 移除功能按钮权限
     *
     * @param actionId
     */
    @Override
    public void removeAuthorityAction(Long actionId) {
        QueryWrapper<SystemAuthorityAction> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemAuthorityAction::getActionId, actionId);
        systemAuthorityActionMapper.delete(queryWrapper);
    }


    /**
     * 角色授权
     *
     * @param roleId       角色ID
     * @param expireTime   过期时间,null表示长期,不限制
     * @param authorityIds 权限集合
     * @return
     */
    @Override
    public void addAuthorityRole(Long roleId, Date expireTime, String... authorityIds) {
        if (roleId == null) {
            return;
        }
        // 清空角色已有授权
        QueryWrapper<SystemAuthorityRole> roleQueryWrapper = new QueryWrapper();
        roleQueryWrapper.lambda().eq(SystemAuthorityRole::getRoleId, roleId);
        systemAuthorityRoleMapper.delete(roleQueryWrapper);
        SystemAuthorityRole authority = null;
        if (authorityIds != null && authorityIds.length > 0) {
            for (String id : authorityIds) {
                authority = new SystemAuthorityRole();
                authority.setAuthorityId(Long.parseLong(id));
                authority.setRoleId(roleId);
                authority.setExpireTime(expireTime);
                authority.setCreateTime(new Date());
                authority.setUpdateTime(authority.getCreateTime());
                // 批量添加授权
                systemAuthorityRoleMapper.insert(authority);
            }

        }
    }

    /**
     * 用户授权
     *
     * @param userId       用户ID
     * @param expireTime   过期时间,null表示长期,不限制
     * @param authorityIds 权限集合
     * @return
     */
    @Override
    public void addAuthorityUser(Long userId, Date expireTime, String... authorityIds) {
        if (userId == null) {
            return;
        }
        SystemUser user = systemUserService.getById(userId);
        if (user == null) {
            return;
        }
        if (CommonConstants.ROOT.equals(user.getUserName())) {
            throw new BaseException("默认用户无需授权!");
        }
        // 获取用户角色列表
        List<Long> roleIds = systemRoleService.findRoleIdsByUserId(userId);
        // 清空用户已有授权
        // 清空角色已有授权
        QueryWrapper<SystemAuthorityUser> userQueryWrapper = new QueryWrapper();
        userQueryWrapper.lambda().eq(SystemAuthorityUser::getUserId, userId);
        systemAuthorityUserMapper.delete(userQueryWrapper);
        SystemAuthorityUser authority = null;
        if (authorityIds != null && authorityIds.length > 0) {
            for (String id : authorityIds) {
                if (roleIds != null && roleIds.size() > 0) {
                    // 防止重复授权
                    if (isGrantedByRoleIds(id, roleIds.toArray(new Long[roleIds.size()]))) {
                        continue;
                    }
                }
                authority = new SystemAuthorityUser();
                authority.setAuthorityId(Long.parseLong(id));
                authority.setUserId(userId);
                authority.setExpireTime(expireTime);
                authority.setCreateTime(new Date());
                authority.setUpdateTime(authority.getCreateTime());
                systemAuthorityUserMapper.insert(authority);
            }
        }
    }

    /**
     * 应用授权
     *
     * @param appId        应用ID
     * @param expireTime   过期时间,null表示长期,不限制
     * @param authorityIds 权限集合
     * @return
     */
    @CacheEvict(value = {"apps"}, key = "'client:'+#appId")
    @Override
    public void addAuthorityApp(String appId, Date expireTime, String... authorityIds) {
        if (appId == null) {
            return;
        }
        SystemApp systemApp = systemAppService.get(appId);
        if (systemApp == null) {
            return;
        }
        // 清空应用已有授权
        QueryWrapper<SystemAuthorityApp> appQueryWrapper = new QueryWrapper();
        appQueryWrapper.lambda().eq(SystemAuthorityApp::getAppId, appId);
        systemAuthorityAppMapper.delete(appQueryWrapper);
        SystemAuthorityApp authority = null;
        if (authorityIds != null && authorityIds.length > 0) {
            for (String id : authorityIds) {
                authority = new SystemAuthorityApp();
                authority.setAuthorityId(Long.parseLong(id));
                authority.setAppId(appId);
                authority.setExpireTime(expireTime);
                authority.setCreateTime(new Date());
                authority.setUpdateTime(authority.getCreateTime());
                systemAuthorityAppMapper.insert(authority);

            }
        }
        // 获取应用最新的权限列表
        List<BaseAuthority> authorities = findAuthorityByApp(appId);
        // 动态更新tokenStore客户端
        SecurityHelper.updateClientAuthorities(redisTokenStore, systemApp.getApiKey(), authorities);
    }

    /**
     * 应用授权-添加单个权限
     *
     * @param appId
     * @param expireTime
     * @param authorityId
     */
    @CacheEvict(value = {"apps"}, key = "'client:'+#appId")
    @Override
    public void addAuthorityApp(String appId, Date expireTime, String authorityId) {
        SystemAuthorityApp authority = new SystemAuthorityApp();
        authority.setAppId(appId);
        authority.setAuthorityId(Long.parseLong(authorityId));
        authority.setExpireTime(expireTime);
        authority.setCreateTime(new Date());
        authority.setUpdateTime(authority.getCreateTime());
        QueryWrapper<SystemAuthorityApp> appQueryWrapper = new QueryWrapper();
        appQueryWrapper.lambda()
                .eq(SystemAuthorityApp::getAppId, appId)
                .eq(SystemAuthorityApp::getAuthorityId, authorityId);
        int count = systemAuthorityAppMapper.selectCount(appQueryWrapper);
        if (count > 0) {
            return;
        }
        authority.setCreateTime(new Date());
        systemAuthorityAppMapper.insert(authority);
    }

    /**
     * 添加功能按钮权限
     *
     * @param actionId
     * @param authorityIds
     * @return
     */
    @Override
    public void addAuthorityAction(Long actionId, String... authorityIds) {
        if (actionId == null) {
            return;
        }
        // 移除操作已绑定接口
        removeAuthorityAction(actionId);
        if (authorityIds != null && authorityIds.length > 0) {
            for (String id : authorityIds) {
                Long authorityId = Long.parseLong(id);
                SystemAuthorityAction authority = new SystemAuthorityAction();
                authority.setActionId(actionId);
                authority.setAuthorityId(authorityId);
                authority.setCreateTime(new Date());
                authority.setUpdateTime(authority.getCreateTime());
                systemAuthorityActionMapper.insert(authority);
            }
        }
    }

    /**
     * 获取应用已授权权限
     *
     * @param appId
     * @return
     */
    @Override
    public List<BaseAuthority> findAuthorityByApp(String appId) {
        List<BaseAuthority> authorities = Lists.newArrayList();
        List<BaseAuthority> list = systemAuthorityAppMapper.selectAuthorityByApp(appId);
        if (list != null) {
            authorities.addAll(list);
        }
        return authorities;
    }

    /**
     * 获取角色已授权权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<BaseAuthority> findAuthorityByRole(Long roleId) {
        return systemAuthorityRoleMapper.selectAuthorityByRole(roleId);
    }

    /**
     * 获取所有可用权限
     *
     * @param type = null 查询全部  type = 1 获取菜单和操作 type = 2 获取API
     * @return
     */
    @Override
    public List<BaseAuthority> findAuthorityByType(String type) {
        Map map = Maps.newHashMap();
        map.put("type", type);
        map.put("status", 1);
        return baseMapper.selectAuthorityAll(map);
    }

    /**
     * 获取用户已授权权限
     *
     * @param userId
     * @param root   超级管理员
     * @return
     */
    @Override
    public List<BaseAuthority> findAuthorityByUser(Long userId, Boolean root) {
        if (root) {
            // 超级管理员返回所有
            return findAuthorityByType("1");
        }
        List<BaseAuthority> authorities = Lists.newArrayList();
        List<SystemRole> rolesList = systemRoleService.findRolesByUserId(userId);
        if (rolesList != null) {
            for (SystemRole role : rolesList) {
                // 加入角色已授权
                List<BaseAuthority> roleGrantedAuthority = findAuthorityByRole(role.getRoleId());
                if (roleGrantedAuthority != null && roleGrantedAuthority.size() > 0) {
                    authorities.addAll(roleGrantedAuthority);
                }
            }
        }
        // 加入用户特殊授权
        List<BaseAuthority> userGrantedAuthority = systemAuthorityUserMapper.selectAuthorityByUser(userId);
        if (userGrantedAuthority != null && userGrantedAuthority.size() > 0) {
            authorities.addAll(userGrantedAuthority);
        }
        // 权限去重
        HashSet h = new HashSet(authorities);
        authorities.clear();
        authorities.addAll(h);
        return authorities;
    }

    /**
     * 获取用户已授权权限详情
     *
     * @param userId
     * @param root   超级管理员
     * @return
     */
    @Override
    public List<AuthorityMenu> findAuthorityMenuByUser(Long userId, Boolean root) {
        if (root) {
            // 超级管理员返回所有
            return findAuthorityMenu(null);
        }
        // 用户权限列表
        List<AuthorityMenu> authorities = Lists.newArrayList();
        List<SystemRole> rolesList = systemRoleService.findRolesByUserId(userId);
        if (rolesList != null) {
            for (SystemRole role : rolesList) {
                // 加入角色已授权
                List<AuthorityMenu> roleGrantedAuthority = systemAuthorityRoleMapper.selectAuthorityMenuByRole(role.getRoleId());
                if (roleGrantedAuthority != null && roleGrantedAuthority.size() > 0) {
                    authorities.addAll(roleGrantedAuthority);
                }
            }
        }
        // 加入用户特殊授权
        List<AuthorityMenu> userGrantedAuthority = systemAuthorityUserMapper.selectAuthorityMenuByUser(userId);
        if (userGrantedAuthority != null && userGrantedAuthority.size() > 0) {
            authorities.addAll(userGrantedAuthority);
        }
        // 权限去重
        HashSet h = new HashSet(authorities);
        authorities.clear();
        authorities.addAll(h);
        //根据优先级从大到小排序
        authorities.sort((AuthorityMenu h1, AuthorityMenu h2) -> h2.getPriority().compareTo(h1.getPriority()));
        return authorities;
    }

    /**
     * 获取用户已授权权限菜单
     *
     * @param userId
     * @param roleId   角色
     * @return
     * @author lj
     */
    @Override
    public List<AuthorityMenu> findAuthorityMenuByRole(Long roleId){
        // 用户权限列表
        List<AuthorityMenu> authorities = Lists.newArrayList();
        // 加入角色已授权
        List<AuthorityMenu> roleGrantedAuthority = systemAuthorityRoleMapper.selectAuthorityMenuByRole(roleId);
        if (roleGrantedAuthority != null && roleGrantedAuthority.size() > 0) {
            authorities.addAll(roleGrantedAuthority);
        }

        // 权限去重
        HashSet h = new HashSet(authorities);
        authorities.clear();
        authorities.addAll(h);
        //根据优先级从大到小排序
        authorities.sort((AuthorityMenu h1, AuthorityMenu h2) -> h2.getPriority().compareTo(h1.getPriority()));
        return authorities;
    }
    /**
     * 检测权限是否被多个角色授权
     *
     * @param authorityId
     * @param roleIds
     * @return
     */
    @Override
    public Boolean isGrantedByRoleIds(String authorityId, Long... roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            throw new BaseException("roleIds is empty");
        }
        QueryWrapper<SystemAuthorityRole> roleQueryWrapper = new QueryWrapper();
        roleQueryWrapper.lambda()
                .in(SystemAuthorityRole::getRoleId, roleIds)
                .eq(SystemAuthorityRole::getAuthorityId, authorityId);
        int count = systemAuthorityRoleMapper.selectCount(roleQueryWrapper);
        return count > 0;
    }

    /**
     * 清理无效数据
     *
     * @param serviceId
     * @param codes
     */
    @Override
    public void clearInvalidApi(String serviceId, Collection<String> codes) {
        if (StringUtil.isBlank(serviceId)) {
            return;
        }
        List<String> invalidApiIds = systemApiService.listObjs(new QueryWrapper<SystemApi>().select("api_id").eq("service_id", serviceId).notIn(codes != null && !codes.isEmpty(), "api_code", codes), e -> e.toString());
        if (invalidApiIds != null) {
            // 防止删除默认api
            invalidApiIds.remove("1");
            invalidApiIds.remove("2");
            // 获取无效的权限
            if (invalidApiIds.isEmpty()) {
                return;
            }
            List<String> invalidAuthorityIds = listObjs(new QueryWrapper<SystemAuthority>().select("authority_id").in("api_id", invalidApiIds), e -> e.toString());
            if (invalidAuthorityIds != null && !invalidAuthorityIds.isEmpty()) {
                // 移除关联数据
                systemAuthorityAppMapper.delete(new QueryWrapper<SystemAuthorityApp>().in("authority_id", invalidAuthorityIds));
                systemAuthorityActionMapper.delete(new QueryWrapper<SystemAuthorityAction>().in("authority_id", invalidAuthorityIds));
                systemAuthorityRoleMapper.delete(new QueryWrapper<SystemAuthorityRole>().in("authority_id", invalidAuthorityIds));
                systemAuthorityUserMapper.delete(new QueryWrapper<SystemAuthorityUser>().in("authority_id", invalidAuthorityIds));
                // 移除权限数据
                this.removeByIds(invalidAuthorityIds);
                // 移除接口资源
                systemApiService.removeByIds(invalidApiIds);
            }
        }
    }
}
