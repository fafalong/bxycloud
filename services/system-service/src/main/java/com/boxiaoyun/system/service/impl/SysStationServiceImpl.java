package com.boxiaoyun.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.RemoteData;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.utils.MapHelper;
import com.boxiaoyun.system.client.model.dto.SysStationPageDTO;
import com.boxiaoyun.system.client.model.entity.SysStation;
import com.boxiaoyun.system.mapper.SysStationMapper;
import com.boxiaoyun.system.mapper.SystemOrgMapper;
import com.boxiaoyun.system.service.SysStationService;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysStationServiceImpl extends BaseServiceImpl<SysStationMapper, SysStation> implements SysStationService {

    @Autowired
    SysStationMapper sysStationMapper;

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<SysStation> findPage(PageParams pageParams, SysStationPageDTO data) {
        SysStation query = pageParams.mapToBean(SysStation.class);
        query.setStatus(true);
        if (query != null && data.getOrgId() != null) {
            query.setOrgId((data.getOrgId()));
        }
        QueryWrapper<SysStation> queryWrapper = new QueryWrapper();
        if(query.getName()!=null){
            queryWrapper.like("name", query.getName());
        }
        if(query.getStationDesc()!=null){
            queryWrapper.like("station_desc", query.getStationDesc());
        }
        queryWrapper.eq(query.getOrgId() != null && ObjectUtil.isNotEmpty(query.getOrgId()), "org_id", query.getOrgId())
                .eq("status", query.getStatus())
                //.geHeader(SysStation::getCreateTime, data.getStartCreateTime())
                //.leFooter(SysStation::getCreateTime, data.getEndCreateTime())
                .orderByDesc("create_time");


/*        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getActionCode()), SysStation::getActionCode, query.getActionCode())
                .likeRight(ObjectUtils.isNotEmpty(query.getActionName()), SysStation::getActionName, query.getActionName());
        queryWrapper.orderByDesc("create_time");*/
        //return baseMapper.selectPage(new Page(pageParams.getPage(), pageParams.getLimit()), queryWrapper);
        //List<SysStation> xxx = sysStationMapper.findSysStationList(null);
        //return null;
        Page<SysStation> page = new Page<>(pageParams.getPage(),pageParams.getLimit());
        IPage<SysStation> list = sysStationMapper.getSysStationList(page,queryWrapper);
        return list;
        //return null;
    }
    /**
     * TODO 这里应该做缓存
     *
     * @param ids
     * @return
     */
    @Override
    public Map<Serializable, Object> findStationNameByIds(Set<Long> ids) {
        QueryWrapper<SysStation> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .in(SysStation::getId, ids)
                .eq(SysStation::getStatus, true);
        List<SysStation> list = super.list(queryWrapper);
        //key 是 组织id， value 是org 对象
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, SysStation::getId, SysStation::getName);
        return typeMap;
    }
}
