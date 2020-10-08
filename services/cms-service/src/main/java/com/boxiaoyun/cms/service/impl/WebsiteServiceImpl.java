package com.boxiaoyun.cms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.Channel;
import com.hysj.cms.client.model.entity.Website;
import com.hysj.cms.client.model.entity.WebsiteLink;
import com.hysj.cms.client.model.entity.WebsiteNav;
import com.boxiaoyun.cms.mapper.ChannelMapper;
import com.boxiaoyun.cms.mapper.WebsiteLinkMapper;
import com.boxiaoyun.cms.mapper.WebsiteMapper;
import com.boxiaoyun.cms.mapper.WebsiteNavMapper;
import com.boxiaoyun.cms.service.CmsService;
import com.boxiaoyun.cms.service.WebsiteService;
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
public class WebsiteServiceImpl implements WebsiteService {
	

    @Autowired
    private WebsiteMapper websiteMapper;

    /**
     * 获取网站频道列表
     *
     * @param siteId
     */
    @Override
    public IPage<Website> findListPage(PageParams pageParams) {
        WebsiteLink query = pageParams.mapToObject(WebsiteLink.class);
        QueryWrapper<Website> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), WebsiteLink::getJobName, query.getJobName());

        queryWrapper.orderByDesc("update_time");
        return websiteMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
/*
    @Override
    public IPage<WebsiteNav> findWebsiteNavListPage(PageParams pageParams) {
        WebsiteNav query = pageParams.mapToObject(WebsiteNav.class);
        QueryWrapper<WebsiteNav> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), WebsiteNav::getJobName, query.getJobName());
        queryWrapper.orderByDesc("update_time");
        return websiteNavMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
*/
    @Override
    public Website getWebsite(Long siteId){
        return websiteMapper.selectById(siteId);
    }
    @Override
    public void addWebsite(Website website){
        websiteMapper.insert(website);
    }
    @Override
    public void modifyWebsite(Website website){
        websiteMapper.updateById(website);
    }
}
