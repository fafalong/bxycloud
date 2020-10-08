package com.boxiaoyun.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.utils.MapHelper;
import com.boxiaoyun.system.client.model.entity.SystemDictionaryItem;
import com.boxiaoyun.system.mapper.SystemDictionaryItemMapper;
import com.boxiaoyun.system.service.DictionaryItemService;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryItemServiceImpl extends BaseServiceImpl<SystemDictionaryItemMapper, SystemDictionaryItem> implements DictionaryItemService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public Page<SystemDictionaryItem> findPage(PageParams pageParams) {
        SystemDictionaryItem query = pageParams.mapToBean(SystemDictionaryItem.class);
        QueryWrapper<SystemDictionaryItem> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getDictId()), SystemDictionaryItem::getDictId, query.getDictId())
                .likeRight(ObjectUtils.isNotEmpty(query.getCode()), SystemDictionaryItem::getCode, query.getCode())
                .likeRight(ObjectUtils.isNotEmpty(query.getName()), SystemDictionaryItem::getName, query.getName());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(new Page(pageParams.getPage(), pageParams.getLimit()), queryWrapper);
    }
    @Override
    public Map<String, Map<String, String>> map(String[] codes) {
        QueryWrapper<SystemDictionaryItem> queryWrapper = new QueryWrapper();
        queryWrapper.eq("status",true);
        queryWrapper.in("dict_code", codes);
        queryWrapper.orderByAsc("sort_value");
        //return baseMapper.selectPage(pageParams, queryWrapper);
        List<SystemDictionaryItem> list = baseMapper.selectList(queryWrapper);
        //key 是字典编码
        Map<String, List<SystemDictionaryItem>> typeMap = list.stream().collect(groupingBy(SystemDictionaryItem::getDictCode, LinkedHashMap::new, toList()));
        //需要返回的map
        Map<String, Map<String, String>> typeCodeNameMap = new LinkedHashMap<>(typeMap.size());
        typeMap.forEach((key, items) -> {
            ImmutableMap<String, String> itemCodeMap = MapHelper.uniqueIndex(items, SystemDictionaryItem::getCode, SystemDictionaryItem::getName);
            typeCodeNameMap.put(key, itemCodeMap);
        });
        return typeCodeNameMap;
    }

    @Override
    public Map<Serializable, Object> findDictionaryItem(Set<Serializable> codes) {
        QueryWrapper<SystemDictionaryItem> queryWrapper = new QueryWrapper();
        queryWrapper.eq("status",true);
        queryWrapper.in("dict_code", codes);
        queryWrapper.orderByAsc("sort_value");
        List<SystemDictionaryItem> list = baseMapper.selectList(queryWrapper);
        //key 是字典编码
        ImmutableMap<String, String> typeMap = MapHelper.uniqueIndex(list, SystemDictionaryItem::getCode, SystemDictionaryItem::getName);
        //需要返回的map
        Map<Serializable, Object> typeCodeNameMap = new LinkedHashMap<>(typeMap.size());

        typeMap.forEach((key, value) -> {
            typeCodeNameMap.put(key, value);
        });
        return typeCodeNameMap;
    }
}
