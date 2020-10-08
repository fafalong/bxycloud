package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hysj.cms.client.model.entity.SiteSettings;
import com.opencloud.common.model.PageParams;

/**
 * 站点设置接口
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface SiteSettingsService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SiteSettings> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param siteSettings
     */
    void addSiteSettings(SiteSettings siteSettings);

    /**
     * 更细文章
     *
     * @param siteSettings
     */
    void modifySiteSettings(SiteSettings siteSettings);


    /**
     * 根据主键获取站点信息
     *
     * @param siteSettingsId
     * @return
     */
    SiteSettings getSiteSettings(String siteSettingsId);
}
