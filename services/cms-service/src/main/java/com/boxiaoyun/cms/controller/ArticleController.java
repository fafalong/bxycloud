package com.boxiaoyun.cms.controller;

import cn.hutool.core.codec.Base64Decoder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hysj.cms.client.model.entity.Article;
import com.hysj.cms.client.model.entity.ArticleData;
import com.opencloud.common.model.PageParams;
import com.opencloud.common.model.ResultBody;
import com.boxiaoyun.cms.service.ArticleService;
import com.boxiaoyun.cms.service.CmsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;


import java.util.*;

/**
 * @author: liujian
 * @date: 2019/3/29 14:12
 * @description:
 */
@RestController
public class ArticleController {
    @Autowired
    private CmsService cmsService;

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取文章列表", notes = "获取文章列表")
    @GetMapping(value = "/article/getNews")
    public ResultBody<IPage<Article>> getArticleList(@RequestParam(required = false) Map map) {
        PageParams pp = new PageParams(map);
        pp.setOrderBy(" add_time desc ");
        IPage<Article> result = articleService.getArticleList(pp,new Page<Article>(pp.getPage(), pp.getLimit()));
        //IPage<Article> result = articleService.findListPage(new PageParams(map));
        //return ResultBody.success(result);
        return ResultBody.success(result);
    }

    /**
     * 获取板块列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取板块列表", notes = "获取板块列表")
    @GetMapping(value = "/article/adviceTabList")
    public ResultBody<IPage<Article>> getadviceTabList(@RequestParam(required = false) Map map) {
        PageParams pp = new PageParams(map);
        IPage<Article> result = articleService.getArticleList(pp,new Page<Article>(pp.getPage(), pp.getLimit()));
        //IPage<Article> result = articleService.getArticleList(new PageParams(map));
        //IPage<Article> result = articleService.findListPage(new PageParams(map));
        return ResultBody.ok();
    }
    /**
     * 搜索建议
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "搜索建议", notes = "搜索建议")
    @GetMapping(value = "/article/adviceSearch")
    public ResultBody<IPage<Article>> getAdviceSearch(@RequestParam(required = false) Map map) {
        PageParams pp = new PageParams(map);
        IPage<Article> result = articleService.getArticleList(pp,new Page<Article>(pp.getPage(), pp.getLimit()));
        return ResultBody.ok();
    }
    /**
     * 获取文章列表(后台)
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取文章列表", notes = "获取文章列表")
    @GetMapping(value = "/article/index")
    public ResultBody<IPage<Article>> findArticleList(@RequestParam(required = false) Map map) {
        PageParams pp = new PageParams(map);
        IPage<Article> result = articleService.getArticleList(pp,new Page<Article>(pp.getPage(), pp.getLimit()));
        //IPage<Article> result = articleService.getArticleList(new PageParams(map));
        //IPage<Article> result = articleService.findListPage(new PageParams(map));
        return ResultBody.success(result);
    }

    /**
     * 保存文章
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "保存文章", notes = "保存文章")
    @PostMapping(value = "/article/save")
    public ResultBody<IPage<Article>> articleSave(@RequestParam(value = "id") String id,
                                                  @RequestParam(value = "articleTitle") String articleTitle,
                                                  @RequestParam(value = "digest") String digest,
                                                  @RequestParam(value = "keywords") String keywords,
                                                  @RequestParam(value = "orders") int orders,
                                                  @RequestParam(value = "status") String status,
                                                  @RequestParam(value = "categoryId") int categoryId,
                                                  @RequestParam(value = "templateId") int templateId,
                                                  //@RequestParam(value = "siteId") int siteId,
                                                  @RequestParam(value = "channelId") int channelId,
                                                  //@RequestParam(value = "deleted") String deleted,
                                                  @RequestParam(value = "content") String content) {
        Article article=new Article();
        if(id==null || id.isEmpty()){
            article.setId(System.currentTimeMillis());
        }else {
            article.setId(new Long(id));
        }
        article.setArticleTitle(articleTitle);
        article.setDigest(digest);
        article.setKeywords(keywords);
        article.setOrders(orders);
        article.setStatus(status);
        article.setCategoryId(categoryId);
        article.setTemplateId(templateId);
        article.setSiteId(0);
        article.setChannelId(channelId);
        article.setDeleted("0");
        article.setAddTime(new Timestamp(System.currentTimeMillis()));
        article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        article.setAllowComment("0");
        article.setFrontCover("");
        article.setHits(0);
        article.setArticleType("special");
        article.setCommentNum(0);
        article.setLikeNum(0);
        article.setImgsrc("");
        ArticleData ad=new ArticleData();
        //byte[] decoded_content = Base64.getDecoder().decode(content);
        //ad.setContent(new String(decoded_content));
        ad.setContent(content);
        ad.setArticleId(article.getId());
        List<ArticleData> alist =new ArrayList<>();
        alist.add(ad);
        article.setArticleData(alist);
        if(id!=null && !id.isEmpty()){
            articleService.modifyArticle(article);
        }else {
            articleService.addArticle(article);
        }
        return ResultBody.ok();
    }

    /**
     * 获取文章内容，必须为可显示状态
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "通过文章id获取文章内容", notes = "通过文章id获取文章内容")
    @GetMapping(value = "/article/findArticleContent")
    public ResultBody<Article> findArticleContent(@RequestParam(required = false) Map map) {
        Article article = articleService.getArticle(new Long(map.get("articleId")==null?"0":""+map.get("articleId")));
        return ResultBody.success(article);
    }

    /**
     * 删除文章(后台)，软删除，系统中还保留记录只是标记为已经删除
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "通过文章id删除文章", notes = "通过文章id删除文章")
    @PostMapping(value = "/article/removeArticle")
    public ResultBody<IPage<Article>> removeArticle(@RequestParam(required = false) Map map) {

        articleService.removeArticle(new Long(""+map.get("articleId")));
        //IPage<Article> result = articleService.getArticleList(new PageParams(map));
        //IPage<Article> result = articleService.findListPage(new PageParams(map));
        return ResultBody.ok();
    }
}
