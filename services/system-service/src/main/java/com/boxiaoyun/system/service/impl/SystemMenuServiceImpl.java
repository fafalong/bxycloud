package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.constants.ResourceType;
import com.boxiaoyun.system.client.model.entity.SystemMenu;
import com.boxiaoyun.system.mapper.SystemMenuMapper;
import com.boxiaoyun.system.service.SystemActionService;
import com.boxiaoyun.system.service.SystemAuthorityService;
import com.boxiaoyun.system.service.SystemMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {
    @Autowired
    private SystemAuthorityService systemAuthorityService;

    @Autowired
    private SystemActionService systemActionService;

    @Autowired
    SystemMenuMapper systemMenuMapper;

    @Value("${spring.application.name}")
    private String DEFAULT_SERVICE_ID;


    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<SystemMenu> findPage(PageParams pageParams) {
        SystemMenu query = pageParams.mapToBean(SystemMenu.class);
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getMenuCode()), SystemMenu::getMenuCode, query.getMenuCode())
                .likeRight(ObjectUtils.isNotEmpty(query.getLabel()), SystemMenu::getLabel, query.getLabel());
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<SystemMenu> findList() {
        List<SystemMenu> list = list(new QueryWrapper<>());
        //根据优先级从大到小排序
        list.sort((SystemMenu h1, SystemMenu h2) -> h2.getPriority().compareTo(h1.getPriority()));
        //return list;
        return findVisibleMenu(null,null);
    }

    /**
     * 查询用户可用菜单
     * 1，查询当前用户拥有的所有菜单列表 [menuId,menuId...]
     * 2，缓存&DB为空则返回
     * 3，缓存总用户菜单列表 为空，但db存在，则将list便利set到缓存，并直接返回
     * 4，缓存存在用户菜单列表，则根据菜单id遍历去缓存查询菜单。
     * 5，过滤group后，返回
     *liujian
     * <p>
     * 注意：什么地方需要清除 USER_MENU 缓存
     * 给用户重新分配角色时， 角色重新分配资源/菜单时
     *
     * @param group
     * @param userId
     * @return
     */
    //@Override
    public List<SystemMenu> findVisibleMenu(String group, Long userId) {
        //String key = CacheKey.buildKey(userId);
        //List<SystemMenu> visibleMenu = new ArrayList<>();
        //List<Long> list = new ArrayList<>();
        //使用 this::getByIdWithCache 会导致无法读取缓存
//        List<Menu> menuList = list.stream().map(this::getByIdWithCache).collect(Collectors.toList());
        List<SystemMenu> visibleMenu = list(new QueryWrapper<>());
        //根据优先级从大到小排序
        visibleMenu.sort((SystemMenu h1, SystemMenu h2) -> h2.getPriority().compareTo(h1.getPriority()));

        return menuListFilterGroup(group, visibleMenu);
    }

    private List<SystemMenu> menuListFilterGroup(String group, List<SystemMenu> visibleMenu) {
        if (group==null || group.isEmpty()) {
            return visibleMenu;
        }
        return visibleMenu.stream().filter((menu) -> group.equals(menu.getGroup())).collect(Collectors.toList());
    }


    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode
     * @return
     */
    @Override
    public Boolean isExist(String menuCode) {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemMenu::getMenuCode, menuCode);
        int count = count(queryWrapper);
        return count > 0 ? true : false;
    }

    /**
     * 添加菜单资源
     *
     * @param menu
     * @return
     */
    @Override
    public SystemMenu add(SystemMenu menu) {
        if (isExist(menu.getMenuCode())) {
            throw new BaseException(String.format("%s编码已存在!", menu.getMenuCode()));
        }
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getPriority() == null) {
            menu.setPriority(0);
        }
        if (menu.getStatus() == null) {
            menu.setStatus(1);
        }
        if (menu.getIsPersist() == null) {
            menu.setIsPersist(0);
        }
        menu.setServiceId(DEFAULT_SERVICE_ID);
        menu.setCreateTime(new Date());
        menu.setUpdateTime(menu.getCreateTime());
        save(menu);
        // 同步权限表里的信息
        systemAuthorityService.saveOrUpdateAuthority(menu.getId(), ResourceType.menu);
        return menu;
    }

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    @Override
    public SystemMenu update(SystemMenu menu) {
        SystemMenu saved = getById(menu.getId());
        if (saved == null) {
            throw new BaseException(String.format("%s信息不存在!", menu.getId()));
        }
        if (!saved.getMenuCode().equals(menu.getMenuCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(menu.getMenuCode())) {
                throw new BaseException(String.format("%s编码已存在!", menu.getMenuCode()));
            }
        }
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getPriority() == null) {
            menu.setPriority(0);
        }
        menu.setUpdateTime(new Date());
        updateById(menu);
        // 同步权限表里的信息
        systemAuthorityService.saveOrUpdateAuthority(menu.getId(), ResourceType.menu);
        return menu;
    }


    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public void remove(Long menuId) {
        SystemMenu menu = getById(menuId);
        if (menu != null && menu.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止删除!"));
        }
        // 移除菜单权限
        systemAuthorityService.removeAuthority(menuId, ResourceType.menu);
        // 移除功能按钮和相关权限
        systemActionService.removeByMenuId(menuId);
        // 移除菜单信息
        removeById(menuId);
    }

    /**
     * 获取角色包含的菜单
     *
     * @param roleId
     * @return
     * @author lj
     */
    @Override
    public List<SystemMenu> findMenuByRoleId(Long roleId) {
        return systemMenuMapper.findMenuByRoleId(roleId);
    }
}
