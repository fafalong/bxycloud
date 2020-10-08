package com.boxiaoyun.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hysj.cms.client.model.entity.*;
import com.boxiaoyun.cms.service.*;
import com.opencloud.common.constants.CommonConstants;
import com.opencloud.common.model.PageParams;
import com.opencloud.common.model.ResultBody;
//import com.hysj.cms.client.model.CmsInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liujian
 * @date: 2019/3/29 14:12
 * @description:
 */
@RestController
public class CmsController {
    @Autowired
    private CmsService cmsService;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private AdviceSearchService adviceSearchService;

    @Autowired
    private AdviceTabService adviceTabService;

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private AdvService advService;


    /**
     * 获取站点信息
     *
     * @param siteId
     * @return
     */
    @ApiOperation(value = "获取站点信息", notes = "获取站点信息")
    @GetMapping(value = "/website/{siteId}/info")
    public ResultBody<Website> getWebsite(@PathVariable("siteId") Long siteId) {
        return ResultBody.success(websiteService.getWebsite(siteId));
    }
    /**
     * 获取站点列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "取站点列表", notes = "取站点列表")
    @GetMapping(value = "/website/findSiteList")
    public ResultBody<IPage<Article>> findSiteList(@RequestParam(required = false) Map map) {
        IPage<Article> result = articleService.findListPage(new PageParams(map));
        return ResultBody.success(result);
    }

    /**
     * 获取频道列表
     *
     * @param siteId
     * @return
     */
    @ApiOperation(value = "获取频道列表", notes = "获取频道列表")
    @GetMapping(value = "/site/findChannelList")
    public ResultBody<IPage<Article>> findChannelList(Long siteId) {
        //IPage<Article> result = articleService.findListPage(new PageParams(map));
        return null;//ResultBody.success(result);
    }

    /**
     * 获取分类列表
     *
     * @param channelId
     * @return
     */
    @ApiOperation(value = "获取分类列表", notes = "获取分类列表")
    @GetMapping(value = "/site/findCategoryList")
    public ResultBody<IPage<Article>> findCategoryList(Long channelId) {
        //IPage<Article> result = articleService.findListPage(new PageParams(map));
        return null;//ResultBody.success(result);
    }

    /**
     * 获取搜索关键词列表
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取搜索关键词列表", notes = "获取搜索关键词列表")
    @GetMapping(value = "/common/adviceSearch")
    public ResultBody<IPage<AdviceSearch>> adviceSearch() {
        Map map =new HashMap();
        IPage<AdviceSearch> result = adviceSearchService.findListPage(new PageParams(map));
        return ResultBody.success(result);
    }

    /**
     * 获取标签列表
     *
     * @param
     * @return
     */
    @ApiOperation(value = "取标签列表", notes = "取标签列表")
    @GetMapping(value = "/common/adviceTabList")
    public ResultBody<IPage<AdviceTab>> adviceTabList() {
        Map map =new HashMap();
        IPage<AdviceTab> result = adviceTabService.findListPage(new PageParams(map));
        return ResultBody.success(result);
    }

    /**
     * 获取站点友情链接列表
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取站点友情链接列表", notes = "获取站点友情链接列表")
    @GetMapping(value = "/website/link/list")
    public ResultBody<IPage<WebsiteLink>> linkList() {
        Map map =new HashMap();
        PageParams pp = new PageParams(map);
        pp.setOrderBy(" add_time desc ");
        IPage<WebsiteLink> result = cmsService.findWebsiteLinkListPage(pp);
        return ResultBody.success(result);
    }

    /**
     * 获取站点导航列表
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取站点导航列表", notes = "获取站点导航列表")
    @GetMapping(value = "/website/nav/list")
    public ResultBody<IPage<WebsiteNav>> navList() {
        Map map =new HashMap();
        PageParams pp = new PageParams(map);
        pp.setOrderBy(" add_time desc ");
        IPage<WebsiteNav> result = cmsService.findWebsiteNavListPage(pp);
        return ResultBody.success(result);
    }

    /**
     * 获取站点广告列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取站点广告列表", notes = "获取站点广告列表")
    @GetMapping(value = "/adv/list")
    public ResultBody<IPage<Adv>> findAdvList(@RequestParam(required = false) Map map) {
        IPage<Adv> result = advService.findListPage(new PageParams(map));
        return ResultBody.success(result);
    }
    /**
     * 获取文章列表
     *
     * @param map
     * @return
     */
    /*
    @ApiOperation(value = "获取最新50篇文章", notes = "获取最新50篇文章")
    @GetMapping(value = "/cms/articlesTop")
    public ResultBody<IPage<Article>> getArticleList(@RequestParam(required = false) Map map) {
        IPage<Article> result = articleService.findListPage(new PageParams(map));
        return ResultBody.success(result);
    }
*/
    /**
     * 微信小程序首页
     *
     * @param jobName 任务名称
     * @return
     */
/*
    @ApiOperation(value = "微信小程序首页", notes = "微信小程序首页")
    @PostMapping(value = "/cms/wxindex")
    public ResultBody wxIndex(@RequestParam(name = "area",required=false) String area) {
        Map map =new HashMap();
        map.put(CommonConstants.PAGE_LIMIT_KEY,9);
        map.put(CommonConstants.PAGE_ORDER_KEY," update_time desc ");
        IPage<Article> result = articleService.findListPage(new PageParams(map));
        return ResultBody.success(result);
    }
   */
}
