package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.dto.SysStationPageDTO;
import com.boxiaoyun.system.client.model.entity.SysStation;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口
 * 岗位
 * </p>
 *
 * @author bxy
 * @date 2019-07-22
 */
public interface SysStationService extends IBaseService<SysStation> {

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SysStation> findPage(PageParams pageParams, SysStationPageDTO data);
    /**
     * 根据id 查询 岗位名称
     *
     * @param ids
     * @return
     */
    Map<Serializable, Object> findStationNameByIds(Set<Long> ids);
}
