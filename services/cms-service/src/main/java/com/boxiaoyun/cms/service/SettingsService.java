package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hysj.cms.client.model.entity.Settings;
import com.opencloud.common.model.PageParams;

/**
 * 设置接口
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface SettingsService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<Settings> findListPage(PageParams pageParams);

    /**
     * 添加设置
     *
     * @param siteSettings
     */
    void addSettings(Settings settings);

    /**
     * 更细文章
     *
     * @param settings
     */
    void modifySiteSettings(Settings settings);


    /**
     * 根据主键获取站点信息
     *
     * @param settingsId
     * @return
     */
    Settings getSettings(String settingsId);
}
