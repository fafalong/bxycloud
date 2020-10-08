package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.Article;
import com.opencloud.common.model.PageParams;

import java.util.Map;

/**
 * 文章内容接口
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface ArticleService {

    /**
     * 自定义一对多表 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<Article> getArticleList(PageParams pageParams, Page<Article> page);
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<Article> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param article
     */
    void addArticle(Article article);

    /**
     * 更细文章
     *
     * @param article
     */
    void modifyArticle(Article article);


    /**
     * 根据主键获取文章
     *
     * @param articleId
     * @return
     */
    Article getArticle(Long articleId);

    /**
     * 通过文章id移除文章
     *
     * @param articleId
     * @return
     */
    public void removeArticle(Long articleId);
}
