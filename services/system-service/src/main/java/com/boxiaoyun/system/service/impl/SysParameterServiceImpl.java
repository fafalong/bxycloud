package com.boxiaoyun.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.mybatis.query.CriteriaQuery;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.dto.SysStationPageDTO;
import com.boxiaoyun.system.client.model.entity.SysParameter;
import com.boxiaoyun.system.mapper.SysParameterMapper;
import com.boxiaoyun.system.service.SysParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class SysParameterServiceImpl extends BaseServiceImpl<SysParameterMapper, SysParameter> implements SysParameterService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<SysParameter> findPage(PageParams pageParams) {
        SysParameter query = pageParams.mapToBean(SysParameter.class);
        QueryWrapper<SysParameter> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(ObjectUtils.isNotEmpty(query.getId()), SysParameter::getId, query.getId())
                .like(ObjectUtils.isNotEmpty(query.getName()), SysParameter::getName, query.getName())
                .eq(ObjectUtils.isNotEmpty(query.getParamKey()), SysParameter::getParamKey, query.getParamKey())
                .eq(ObjectUtils.isNotEmpty(query.getParamValue()), SysParameter::getParamValue, query.getParamValue());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

    /**
     * 检查key是否存在
     *
     * @param paramKey
     * @return
     */
    @Override
    public Boolean isExist(String paramKey){
        QueryWrapper<SysParameter> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysParameter::getParamKey, paramKey);
        int count = count(queryWrapper);
        return count > 0 ? true : false;
    }

    /**
     * 添加系统参数
     *
     * @param sysParameter
     * @return
     */
    @Override
    public void add(SysParameter sysParameter){
        if (isExist(sysParameter.getParamKey())) {
            throw new BaseException(String.format("%sKey已存在!", sysParameter.getParamKey()));
        }
        if (sysParameter.getParamStatus() == null) {
            sysParameter.setParamStatus(SystemConstants.ENABLED);
        }
        save(sysParameter);
    }

    /**
     * 修改系统参数
     *
     * @param sysParameter
     * @return
     */
    @Override
    public boolean update(SysParameter sysParameter){
        SysParameter saved = getById(sysParameter.getId());
        if (saved == null) {
            //throw new BaseException("信息不存在!");
            return false;
        }
        if(saved.getParamReadonly()==SystemConstants.DISABLED){
            return false;
        }
/*        if (!saved.getParamKey().equals(sysParameter.getParamKey())) {
            // 和原来不一致重新检查唯一性
            if (isExist(sysParameter.getParamKey())) {
                throw new BaseException(String.format("%skey已存在!", sysParameter.getParamKey()));
            }
        }*/
        sysParameter.setUpdateTime(new Date());
        updateById(sysParameter);
        return true;
    }

    /**
     * 查询key
     *
     * @param paramKey
     * @return
     */
    @Override
    public SysParameter getByParamKey(String paramKey){
        QueryWrapper<SysParameter> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysParameter::getParamKey, paramKey);
        return getOne(queryWrapper);
    }

    /**
     * 移除接口
     *
     * @param id
     * @return
     */
    @Override
    public boolean remove(Long id){
        SysParameter sysParameter = getById(id);
        if(sysParameter==null){
            return false;
        }else{
            return removeById(id);
        }

    }
}