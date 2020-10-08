package com.boxiaoyun.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.system.client.model.entity.SysStation;
import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Repository
public interface SysStationMapper extends SuperMapper<SysStation> {

    /**
     * 查询组织列表
     * @param params
     * @return
     */
    List<SysStation> selectSysStationList(Map params);
    List<SysStation> findSysStationList(Map params);
    IPage<SysStation> getSysStationList(Page<SysStation> page,@Param("ew")QueryWrapper query);
}
