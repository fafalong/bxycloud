package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemOrg;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SystemOrgService extends IBaseService<SystemOrg> {

    List<SystemOrg> findChildren(List<Long> ids);
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SystemOrg> findPage(PageParams pageParams);

    /**
     * 查询列表
     * @return
     */
    List<SystemOrg> findList();



    /**
     * 添加组织资源
     *
     * @param systemOrg
     * @return
     */
    void add(SystemOrg systemOrg);

    /**
     * 修改组织资源
     *
     * @param systemOrg
     * @return
     */
    SystemOrg update(SystemOrg systemOrg);

    /**
     * 移除组织
     *
     * @param orgId
     * @return
     */
    void remove(Long orgId);
}
