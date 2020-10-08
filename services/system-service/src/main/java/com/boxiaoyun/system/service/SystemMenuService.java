package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemMenu;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 菜单资源管理
 * @author
 */
public interface SystemMenuService extends IBaseService<SystemMenu> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SystemMenu> findPage(PageParams pageParams);

    /**
     * 查询列表
     * @return
     */
    List<SystemMenu> findList();

    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode
     * @return
     */
    Boolean isExist(String menuCode);


    /**
     * 添加菜单资源
     *
     * @param menu
     * @return
     */
    SystemMenu add(SystemMenu menu);

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    SystemMenu update(SystemMenu menu);

    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    void remove(Long menuId);

    /**
     * 获取角色包含的菜单
     *
     * @param roleId
     * @return
     * @author lj
     */
    List<SystemMenu> findMenuByRoleId(Long roleId);
}
