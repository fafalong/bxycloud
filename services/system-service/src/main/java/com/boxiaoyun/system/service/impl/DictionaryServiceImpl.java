package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.system.client.model.entity.SystemDictionary;
import com.boxiaoyun.system.mapper.DictionaryMapper;
import com.boxiaoyun.system.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, SystemDictionary> implements DictionaryService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<SystemDictionary> findPage(PageParams pageParams) {
        SystemDictionary query = pageParams.mapToBean(SystemDictionary.class);
        QueryWrapper<SystemDictionary> queryWrapper = new QueryWrapper();
        /*queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getPolicyName()),Dictionary::getPolicyName, query.getPolicyName())
                .eq(ObjectUtils.isNotEmpty(query.getPolicyType()),Dictionary::getPolicyType, query.getPolicyType());*/
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams,queryWrapper);
    }
    /**
     * 检查dictCode编码是否存在
     *
     * @param dictCode
     * @return
     */
    @Override
    public Boolean isExist(String dictCode) {
        QueryWrapper<SystemDictionary> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemDictionary::getCode, dictCode);
        int count = count(queryWrapper);
        return count > 0 ? true : false;
    }

    /**
     * 添加dictionary操作
     *
     * @param dictionary
     * @return
     */
    @Override
    public SystemDictionary add(SystemDictionary dictionary) {
        if (isExist(dictionary.getCode())) {
            throw new BaseException(String.format("%s编码已存在!", dictionary.getCode()));
        }
        dictionary.setCreateTime(new Date());
        dictionary.setUpdateTime(dictionary.getCreateTime());
        save(dictionary);
        // 同步权限表里的信息
        return dictionary;
    }

    /**
     * 修改dictionary操作
     *
     * @param dictionary
     * @return
     */
    @Override
    public SystemDictionary update(SystemDictionary dictionary) {
        SystemDictionary saved = getById(dictionary.getId());
        if (saved == null) {
            throw new BaseException(String.format("%s信息不存在", dictionary.getId()));
        }
        if (!saved.getCode().equals(dictionary.getCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(dictionary.getCode())) {
                throw new BaseException(String.format("%s编码已存在!", dictionary.getCode()));
            }
        }
        dictionary.setUpdateTime(new Date());
        updateById(dictionary);
        return dictionary;
    }

    /**
     * 移除字典
     *
     * @param id
     * @return
     */
    @Override
    public void remove(Long id) {
        SystemDictionary dict = getById(id);
        removeById(id);
    }
}
