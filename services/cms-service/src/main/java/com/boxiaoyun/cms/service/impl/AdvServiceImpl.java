package com.boxiaoyun.cms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.Adv;
import com.boxiaoyun.cms.mapper.AdvMapper;
import com.boxiaoyun.cms.service.CmsService;
import com.boxiaoyun.cms.service.AdvService;
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
public class AdvServiceImpl implements AdvService {
	

    @Autowired
    private AdvMapper advMapper;

    /**
     * 获取网站频道列表
     *
     * @param siteId
     */
    @Override
    public IPage<Adv> findListPage(PageParams pageParams) {
        Adv query = pageParams.mapToObject(Adv.class);
        QueryWrapper<Adv> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), AdvLink::getJobName, query.getJobName());

        queryWrapper.orderByDesc("update_time");
        return advMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
/*
    @Override
    public IPage<AdvNav> findAdvNavListPage(PageParams pageParams) {
        AdvNav query = pageParams.mapToObject(AdvNav.class);
        QueryWrapper<AdvNav> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), AdvNav::getJobName, query.getJobName());
        queryWrapper.orderByDesc("update_time");
        return advNavMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
*/
    @Override
    public Adv getAdv(Long siteId){
        return advMapper.selectById(siteId);
    }
    @Override
    public void addAdv(Adv adv){
        advMapper.insert(adv);
    }
    @Override
    public void modifyAdv(Adv adv){
        advMapper.updateById(adv);
    }
}
