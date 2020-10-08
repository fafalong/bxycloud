package com.boxiaoyun.cms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.ArticleData;
import com.boxiaoyun.cms.mapper.ArticleDataMapper;
import com.opencloud.common.model.PageParams;
import com.hysj.cms.client.model.entity.Article;
import com.boxiaoyun.cms.mapper.ArticleMapper;
import com.boxiaoyun.cms.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author liujian
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleDataMapper articleDataMapper;
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */

    @Override
    public IPage<Article> findListPage(PageParams pageParams) {
        Article query = pageParams.mapToObject(Article.class);
        QueryWrapper<Article> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), Article::getJobName, query.getJobName());

        queryWrapper.orderByDesc("update_time");
        return articleMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }

    /**
     * 自定义一对多表 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    //public IPage<Article> getArticleList(Map map) {
    public IPage<Article> getArticleList(PageParams pageParams, Page<Article> page) {
        /////
        //IPage<Article>  articleList = articleMapper.selectPage(page,new QueryWrapper<Article>());
        /////
        Article query = pageParams.mapToObject(Article.class);
        QueryWrapper<Article> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("update_time");
        //System.out.println(">>>>>>>>>>>>>59>>>>>>>>>>>>>>.>\n"+articleMapper.getArticleList(map));
        IPage<Article> articleList = articleMapper.getArticleList(pageParams,page);
        //System.out.println(">>>>>>>>>>>>>59>>>>>>>>>>>>>>.>\n"+articleMapper.getArticleList(pageParams,page));
        //return articleMapper.getArticleList(map);
        articleList.setTotal(page.getTotal());
        return articleList;
    }
    /**
     * 添加文章
     *
     * @param article
     */
    @Override
    public void addArticle(Article article) {
        articleMapper.insert(article);
        List<ArticleData> articleDataList=article.getArticleData();
        for (ArticleData articleData:articleDataList) {
            articleData.setAddTime(new java.sql.Timestamp(new Date().getTime()) );
            articleData.setUpdateTime(new java.sql.Timestamp(new Date().getTime()) );
            articleDataMapper.insert(articleData);
        }
    }

    /**
     * 更细文章
     *
     * @param article
     */
    @Override
    public void modifyArticle(Article article) {
        articleMapper.update(article);
        List<ArticleData> articleDataList=article.getArticleData();
        for (ArticleData articleData:articleDataList) {
            //articleData.setAddTime(new java.sql.Timestamp(new Date().getTime()) );
            articleData.setUpdateTime(new java.sql.Timestamp(new Date().getTime()) );
            UpdateWrapper<ArticleData> articleDataUpdateWrapper = new UpdateWrapper<>();
            articleDataUpdateWrapper.eq("article_id", articleData.getArticleId());
            int rs = articleDataMapper.update(articleData,articleDataUpdateWrapper);
        }

        //articleMapper.updateById(article);
    }

    /**
     * 根据主键获取文章
     *
     * @param articleId
     * @return
     */
    @Override
    public Article getArticle(Long articleId) {
        List<Article> articleList = articleMapper.selectById(articleId);
        if(articleList!=null && articleList.size()>0){
            return articleList.get(0);
        }else{
            return null;
        }
        //return articleMapper.selectById(articleId);

    }

    /**
     * 通过文章id移除文章
     *
     * @param articleId
     * @return
     */
    @Override
    public void removeArticle(Long articleId) {
        articleMapper.deleteById(articleId);
    }

}
