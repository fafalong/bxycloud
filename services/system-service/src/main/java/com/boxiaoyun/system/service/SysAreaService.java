package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SysArea;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysAreaService extends IBaseService<SysArea> {

    List<SysArea> findChildren(List<Long> ids);
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SysArea> findPage(PageParams pageParams);

    /**
     * 查询列表
     * @return
     */
    List<SysArea> findList();



    /**
     * 添加地区
     *
     * @param SysArea
     * @return
     */
    void add(SysArea SysArea);

    /**
     * 修改地区
     *
     * @param SysArea
     * @return
     */
    SysArea update(SysArea SysArea);

    /**
     * 移除地区
     *
     * @param orgId
     * @return
     */
    void remove(Long orgId);
}
