package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hysj.cms.client.model.entity.Website;
import com.opencloud.common.model.PageParams;

/**
 * 站点接口
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface WebsiteService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<Website> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param site
     */
    void addWebsite(Website website);

    /**
     * 更细文章
     *
     * @param site
     */
    void modifyWebsite(Website website);


    /**
     * 根据主键获取站点信息
     *
     * @param siteId
     * @return
     */
    Website getWebsite(Long siteId);
}
