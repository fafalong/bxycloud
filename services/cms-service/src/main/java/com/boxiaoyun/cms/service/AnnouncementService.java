package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hysj.cms.client.model.entity.Announcement;
import com.opencloud.common.model.PageParams;

/**
 * 网站公告接口
 *
 * @author: liujian
 * @date: 2019/8/12 10:39
 * @description:
 */
public interface AnnouncementService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<Announcement> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param announcement
     */
    void addAnnouncement(Announcement announcement);

    /**
     * 更细文章
     *
     * @param announcement
     */
    void modifyAnnouncement(Announcement announcement);


    /**
     * 根据主键获取文章
     *
     * @param announcementId
     * @return
     */
    Announcement getAnnouncement(String announcementId);
}
