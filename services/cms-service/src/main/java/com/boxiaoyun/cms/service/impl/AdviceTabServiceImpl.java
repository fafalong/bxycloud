package com.boxiaoyun.cms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.AdviceTab;
import com.boxiaoyun.cms.mapper.AdviceTabMapper;
import com.boxiaoyun.cms.service.AdviceTabService;
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
public class AdviceTabServiceImpl implements AdviceTabService {
	
    @Autowired
    private AdviceTabMapper adviceTabMapper;


    @Override
    public void addAdviceTab(AdviceTab adviceTab){
        adviceTabMapper.addAdviceTab(adviceTab);
    }
    /**
     * 获取列表
     *
     * @param siteId
     */
    @Override
    public IPage<AdviceTab> findListPage(PageParams pageParams) {
        AdviceTab query = pageParams.mapToObject(AdviceTab.class);
        QueryWrapper<AdviceTab> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("update_time");
        IPage<AdviceTab> adviceTabList = adviceTabMapper.findListPage(pageParams);
        //adviceTabList.setTotal(page.getTotal());
        return adviceTabList;
    }

    /**
     * 根据主键获取文章
     *
     * @param articleId
     * @return
     */
    @Override
    public AdviceTab getAdviceTab(Long adviceTabId){
        return adviceTabMapper.getAdviceTab(adviceTabId);
    }

    /**
     * 通过文章id移除文章
     *
     * @param articleId
     * @return
     */
    @Override
    public void removeAdviceTab(Long adviceTabId){
        adviceTabMapper.removeAdviceTab(adviceTabId);
    }
}
