package com.boxiaoyun.system.service;

import com.boxiaoyun.system.client.constants.ResourceType;
import com.boxiaoyun.system.client.model.AuthorityResource;
import com.boxiaoyun.system.client.model.AuthorityApi;
import com.boxiaoyun.system.client.model.AuthorityMenu;
import com.boxiaoyun.system.client.model.entity.SystemAuthority;
import com.boxiaoyun.system.client.model.entity.SystemAuthorityAction;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.common.security.BaseAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 系统权限管理
 *
 * @author
 */
public interface SystemAuthorityService extends IBaseService<SystemAuthority> {

    /**
     * 获取访问权限列表
     *
     * @return
     */
    List<AuthorityResource> findAuthorityResource();

    /**
     * 获取菜单权限列表
     *
     * @param status
     * @return
     */
    List<AuthorityMenu> findAuthorityMenu(Integer status);


    /**
     * 获取API权限列表
     *
     * @param serviceId
     * @return
     */
    List<AuthorityApi> findAuthorityApi(String serviceId);


    /**
     * 查询功能按钮权限列表
     *
     * @param actionId
     * @return
     */
    List<SystemAuthorityAction> findAuthorityAction(Long actionId);


    /**
     * 保存或修改权限
     *
     * @param resourceId
     * @param resourceType
     * @return 权限Id
     */
    SystemAuthority saveOrUpdateAuthority(Long resourceId, ResourceType resourceType);


    /**
     * 获取权限
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    SystemAuthority getAuthority(Long resourceId, ResourceType resourceType);

    /**
     * 移除权限
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    void removeAuthority(Long resourceId, ResourceType resourceType);

    /**
     * 移除应用权限
     *
     * @param appId
     */
    void removeAuthorityApp(String appId);


    /**
     * 移除功能按钮权限
     * @param actionId
     */
    void  removeAuthorityAction(Long actionId);

    /**
     * 是否已被授权
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    void removeGranted(Long resourceId, ResourceType resourceType);

    /**
     * 角色授权
     *
     * @param
     * @param roleId       角色ID
     * @param expireTime   过期时间,null表示长期,不限制
     * @param authorityIds 权限集合
     * @return 权限标识
     */
    void addAuthorityRole(Long roleId, Date expireTime, String... authorityIds);


    /**
     * 用户授权
     *
     * @param
     * @param userId       用户ID
     * @param expireTime   过期时间,null表示长期,不限制
     * @param authorityIds 权限集合
     * @return 权限标识
     */
    void addAuthorityUser(Long userId, Date expireTime, String... authorityIds);


    /**
     * 应用授权
     *
     * @param
     * @param appId        应用ID
     * @param expireTime   过期时间,null表示长期,不限制
     * @param authorityIds 权限集合
     * @return
     */
    void addAuthorityApp(String appId, Date expireTime, String... authorityIds);

    /**
     * 应用授权-添加单个权限
     *
     * @param appId
     * @param expireTime
     * @param authorityId
     */
    void addAuthorityApp(String appId, Date expireTime, String authorityId);

    /**
     * 添加功能按钮权限
     *
     * @param actionId
     * @param authorityIds
     * @return
     */
    void addAuthorityAction(Long actionId, String... authorityIds);

    /**
     * 获取应用已授权权限
     *
     * @param appId
     * @return
     */
    List<BaseAuthority> findAuthorityByApp(String appId);

    /**
     * 获取角色已授权权限
     *
     * @param roleId
     * @return
     */
    List<BaseAuthority> findAuthorityByRole(Long roleId);

    /**
     * 获取用户已授权权限
     *
     * @param userId
     * @param root
     * @return
     */
    List<BaseAuthority> findAuthorityByUser(Long userId, Boolean root);

    /**
     * 获取开放对象权限
     *
     * @param type = null 查询全部  type = 1 获取菜单和操作 type = 2 获取API
     * @return
     */
    List<BaseAuthority> findAuthorityByType(String type);

    /**
     * 获取用户已授权权限详情
     *
     * @param userId
     * @param root
     * @return
     */
    List<AuthorityMenu> findAuthorityMenuByUser(Long userId, Boolean root);

    /**
     * 检测全是是否被多个角色授权
     *
     * @param authorityId
     * @param roleIds
     * @return
     */
    Boolean isGrantedByRoleIds(String authorityId, Long... roleIds);


    /**
     * 清理无效权限
     * @param serviceId
     * @param codes
     */
    void clearInvalidApi(String serviceId,Collection<String> codes);

    /**
     * 获取用户已授权权限菜单和资源列表
     *
     * @param userId
     * @param roleId   角色
     * @return
     * @author lj
     * @date 2020-07-16 20:02
     */
    List<AuthorityMenu> findAuthorityMenuByRole(Long roleId);

    /**
     * 获取用户已授权权限资源列表
     *
     * @param roleId   角色
     * @return
     * @author lj
     * @date 2020-07-16 20:02
     */
    //List<AuthorityMenu> findAuthorityActionByRole(Long roleId);
}
