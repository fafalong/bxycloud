package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.AdviceTab;
import com.opencloud.common.model.PageParams;

import java.util.Map;

/**
 * 建议标签页
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface AdviceTabService {

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
