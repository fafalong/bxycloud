package com.boxiaoyun.cms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.AdviceSearch;
import com.boxiaoyun.cms.mapper.AdviceSearchMapper;
import com.boxiaoyun.cms.service.AdviceSearchService;
import com.opencloud.common.model.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * @author liujian
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AdviceSearchServiceImpl implements AdviceSearchService {
	
    @Autowired
    private AdviceSearchMapper adviceSearchMapper;


    public void addAdviceSearch(AdviceSearch adviceSearch){
        adviceSearchMapper.addAdviceSearch(adviceSearch);
    }

    public void modifyAdviceSearch(AdviceSearch adviceSearch){
        adviceSearchMapper.modifyAdviceSearch(adviceSearch);
    }

    public AdviceSearch getAdviceSearch(Long adviceSearchId){
        return adviceSearchMapper.getAdviceSearch(adviceSearchId);
    }

    public void removeAdviceSearch(Long adviceSearchId){
        adviceSearchMapper.removeAdviceSearch(adviceSearchId);
    }

    @Override
    public IPage<AdviceSearch> findListPage(PageParams pageParams) {
        AdviceSearch query = pageParams.mapToObject(AdviceSearch.class);
        QueryWrapper<AdviceSearch> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("update_time");
        return adviceSearchMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
}
