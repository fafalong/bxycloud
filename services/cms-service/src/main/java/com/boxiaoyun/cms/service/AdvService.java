package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hysj.cms.client.model.entity.Adv;
import com.opencloud.common.model.PageParams;

/**
 * 站点接口
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface AdvService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<Adv> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param site
     */
    void addAdv(Adv adv);

    /**
     * 更细文章
     *
     * @param site
     */
    void modifyAdv(Adv adv);


    /**
     * 根据主键获取站点信息
     *
     * @param siteId
     * @return
     */
    Adv getAdv(Long siteId);
}
