package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.AdviceSearch;
import com.opencloud.common.model.PageParams;

import java.util.Map;

/**
 * 文章内容接口
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface AdviceSearchService {

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<AdviceSearch> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param article
     */
    void addAdviceSearch(AdviceSearch adviceSearch);

    /**
     * 更细文章
     *
     * @param article
     */
    void modifyAdviceSearch(AdviceSearch adviceSearch);


    /**
     * 根据主键获取文章
     *
     * @param articleId
     * @return
     */
    AdviceSearch getAdviceSearch(Long adviceSearchId);

    /**
     * 通过文章id移除文章
     *
     * @param articleId
     * @return
     */
    public void removeAdviceSearch(Long adviceSearchId);
}
