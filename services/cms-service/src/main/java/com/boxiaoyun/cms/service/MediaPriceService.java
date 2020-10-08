package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.*;
import com.opencloud.common.model.PageParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员接口
 *
 * @author: liujian
 * @date: 2019/10/13 14:39
 * @description:
 */
public interface MediaPriceService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<WxOfficial> findListPage(PageParams pageParams);

    /**
     * 添加媒体信息
     *
     * @param personInfo
     */
    int addPersonInfo(PersonInfo personInfo);

    /**
     * 添加微信公众号
     *
     * @param wxOfficial
     */
    void addWxOfficial(WxOfficial wxOfficial);

    /**
     * 更新微信公众号
     *
     * @param wxOfficial
     */
    void modifyWxOfficial(WxOfficial wxOfficial);


    /**
     * 根据主键获取微信公众号信息
     *
     * @param wxOfficialId
     * @return
     */
    WxOfficial getWxOfficial(Long wxOfficialId);

    /**
     * 短视频价格
     *
     * @param 媒体名称：抖音、快手等
     *            pageNo 页码
     * @return
     */
    public IPage<PersonInfo> getVideoList(int mediaCode,PageParams pageParams, Page<PersonInfo> page) ;

    /**
     * 微博价格
     *
     * @param 媒体名称：新浪微博等
     *            page 页
     * @return
     */
    public IPage<PersonInfo> getWeiboList(PageParams pageParams, Page<PersonInfo> page);

    /**
     * 查询媒体类型
     *
     * @param pageParams
     * @return
     */
    public List<PersonMediaCategory> findPersonMediaCategoryListPage();

    /**
     * 添加媒体价格
     *
     * @param mediaPrice
     * @return
     */
    public int addMediaPrice(MediaPrice mediaPrice);

    /**
     * 检查personinfo是否重复
     *
     * @param personInfo
     * @return
     */
    public int selectPersonInfo(PersonInfo personInfo);

    /**
     * 在线直播列表
     *
     * @param MediaLiving
     * @return
     */
    public IPage<MediaLiving> getMediaLivingList(PageParams pageParams, Page<MediaLiving> page);
}
