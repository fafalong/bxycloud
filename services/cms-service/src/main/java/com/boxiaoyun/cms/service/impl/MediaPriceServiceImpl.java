package com.boxiaoyun.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.*;
import com.boxiaoyun.cms.mapper.*;
import com.boxiaoyun.cms.service.MediaPriceService;
import com.opencloud.common.model.PageParams;
import com.opencloud.common.mybatis.query.CriteriaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaTypeEditor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author liujian
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MediaPriceServiceImpl implements MediaPriceService {
	

    @Autowired
    private PersonMediaMapper mediaMapper;

    @Autowired
    private MediaPriceMapper mediaPriceMapper;


    @Autowired
    private WxOfficialMapper wxOfficialMapper;

    @Autowired
    private PersonMediaCategoryMapper personMediaCategoryMapper;
    @Autowired
    private MediaLivingMapper mediaLivingMapper;


    /**
     * 获取微信公众号列表
     *
     * @param siteId
     */
    @Override
    public IPage<WxOfficial> findListPage(PageParams pageParams) {
        WxOfficial query = pageParams.mapToObject(WxOfficial.class);
        QueryWrapper<WxOfficial> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), WxOfficialLink::getJobName, query.getJobName());

        queryWrapper.orderByDesc("update_time");
        return null;//wxOfficialMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
/*
    @Override
    public IPage<WxOfficialNav> findWxOfficialNavListPage(PageParams pageParams) {
        WxOfficialNav query = pageParams.mapToObject(WxOfficialNav.class);
        QueryWrapper<WxOfficialNav> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), WxOfficialNav::getJobName, query.getJobName());
        queryWrapper.orderByDesc("update_time");
        return wxOfficialNavMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
*/
    @Override
    public WxOfficial getWxOfficial(Long wxOfficialId){
        return wxOfficialMapper.selectById(wxOfficialId);
    }
    @Override
    public void addWxOfficial(WxOfficial wxOfficial){
        wxOfficialMapper.insert(wxOfficial);
    }
    @Override
    public void modifyWxOfficial(WxOfficial wxOfficial){
        wxOfficialMapper.updateById(wxOfficial);
    }

    /**
     * 短视频价格
     *
     * @param 媒体名称：抖音、快手等
     *            pageNo 页码
     * @return
     */
    @Override
    public IPage<PersonInfo> getVideoList(int mediaCode,PageParams pageParams, Page<PersonInfo> page) {
        PersonInfo query = pageParams.mapToObject(PersonInfo.class);
        QueryWrapper<PersonInfo> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(true,PersonInfo::getMediaCode,mediaCode);
        if(query.getUid()!=null){
            queryWrapper.lambda().eq(true,PersonInfo::getUid,query.getUid());
        }
        queryWrapper.orderByDesc("a.update_time");
        //IPage<PersonInfo> personInfoList = mediaMapper.getShortVideoList(pageParams,page);
        //IPage<PersonInfo> personInfoList = mediaMapper.getShortVideoList(pageParams,queryWrapper);
        //personInfoList.setTotal(page.getTotal());
        //CriteriaQuery<PersonInfo> cq = new CriteriaQuery(pageParams);
        //cq.lambda().eq(true,PersonInfo::getMediaCode,mediaCode);
        queryWrapper.select("a.*");
        IPage<PersonInfo> personInfoList = mediaMapper.getShortVideoList(page,queryWrapper);
        return personInfoList;
    }
    /**
     * 微博价格
     *
     * @param 媒体名称：新浪微博等
     *            page 页
     * @return
     */
    @Override
    public IPage<PersonInfo> getWeiboList(PageParams pageParams, Page<PersonInfo> page) {
        PersonInfo query = pageParams.mapToObject(PersonInfo.class);
        QueryWrapper<PersonInfo> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("update_time");
        IPage<PersonInfo> personInfoList = mediaMapper.getWeiboList(page,queryWrapper);
        personInfoList.setTotal(page.getTotal());
        return personInfoList;
    }
    /**
     * 添加媒体信息
     *
     * @param personInfo
     */
    @Override
    public int addPersonInfo(PersonInfo personInfo){
        return mediaMapper.insert(personInfo);
    }
    /**
     * 添加媒体价格信息
     *
     * @param mediaPrice
     */
    @Override
    public int addMediaPrice(MediaPrice mediaPrice){
        return mediaPriceMapper.insert(mediaPrice);
    }

    /**
     * 查询媒体类型
     *
     * @param pageParams
     * @return
     */
    @Override
    public List<PersonMediaCategory> findPersonMediaCategoryListPage(){
return personMediaCategoryMapper.selectPersonMediaCategory();
    }

    /**
     * 检查personinfo是否重复
     *
     * @param personInfo
     * @return
     */
    @Override
    public int selectPersonInfo(PersonInfo personInfo){
        QueryWrapper<PersonInfo> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(true,PersonInfo::getMediaCode,personInfo.getMediaCode());
        queryWrapper.lambda().eq(true,PersonInfo::getAccountId,personInfo.getAccountId());
        return mediaMapper.selectCount(queryWrapper);
    }

    /**
     * 在线直播列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<MediaLiving> getMediaLivingList(PageParams pageParams, Page<MediaLiving> page){
        MediaLiving query = pageParams.mapToObject(MediaLiving.class);
        QueryWrapper<MediaLiving> queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("score");
        IPage<MediaLiving> mediaLivingList = mediaLivingMapper.selectPage(page,queryWrapper);
        mediaLivingList.setTotal(page.getTotal());
        return mediaLivingList;
    }
}
