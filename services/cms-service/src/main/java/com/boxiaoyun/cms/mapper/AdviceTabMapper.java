package com.boxiaoyun.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.AdviceTab;
import com.opencloud.common.model.PageParams;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdviceTabMapper extends BaseMapper<AdviceTab> {
    //List<Article> getArticleList(Map map);
    IPage<AdviceTab> getAdviceTab(PageParams pageParams, Page<AdviceTab> page);
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<AdviceTab> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param article
     */
    void addAdviceTab(AdviceTab adviceTab);

    /**
     * 更细文章
     *
     * @param article
     */
    //void modifyAdviceTab(AdviceTab adviceTab);


    /**
     * 根据主键获取文章
     *
     * @param articleId
     * @return
     */
    AdviceTab getAdviceTab(Long adviceTabId);

    /**
     * 通过文章id移除文章
     *
     * @param articleId
     * @return
     */
    public void removeAdviceTab(Long adviceTabId);
}
