package com.boxiaoyun.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hysj.cms.client.model.entity.*;
import com.boxiaoyun.cms.service.*;
import com.opencloud.common.constants.CommonConstants;
import com.opencloud.common.model.PageParams;
import com.opencloud.common.model.ResultBody;
import com.opencloud.common.security.OpenHelper;
import com.opencloud.common.security.OpenUserDetails;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author: liujian
 * @date: 2019/10/29 14:12
 * @description:
 */
@RestController
public class MediaPriceController {

    @Autowired
    private MediaPriceService mediaPriceService;

    //@Autowired
    //private PersonMediaService personMediaService;

    /**
     * 获取用户信息
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping(value = "/media/getWxOfficialInfo")
    public ResultBody<WxOfficial> getWxOfficialInfo(@RequestParam(required = false) Map map) {
        String memnberId= (String)map.get("wxOfficialId");
        WxOfficial wxOfficial = mediaPriceService.getWxOfficial(new Long(memnberId));
        return ResultBody.success(wxOfficial);
    }

    /**
     * 获取媒体价格列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取媒体价格列表", notes = "获取媒体价格列表")
    @PostMapping(value = "/media/mediaList")
    public ResultBody<IPage<PersonInfo>> findMediaList(@RequestParam(required = false) Map map) {
        String mediaCode = map.get("mediaCode")==null?"112":""+map.get("mediaCode");
        String mediaType = map.get("mediaType")==null?"shortVideo":""+map.get("mediaType");
        PageParams pp = new PageParams(map);
        pp.getRequestMap().put("mediaCode",mediaCode);
        pp.setOrderBy(" add_time desc ");
        IPage<PersonInfo> result=null;
        //根据媒体编码判断媒体渠道
        if(mediaType.equalsIgnoreCase("weibo")){//微博
            result = mediaPriceService.getWeiboList(pp, new Page<PersonInfo>(pp.getPage(), pp.getLimit()));
        } else if(mediaType.equalsIgnoreCase("shortvideo")) { //短视频
            result = mediaPriceService.getVideoList(Integer.parseInt(mediaCode),pp, new Page<PersonInfo>(pp.getPage(), pp.getLimit()));
        }
        return ResultBody.success(result);
    }

    /**
     * 用户添加媒体信息
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "用户添加媒体信息", notes = "用户添加媒体信息")
    @PostMapping(value = "/media/addmedia")
    public ResultBody addMedia(@RequestParam(required = false) Map map) {
        PersonInfo personInfo=new PersonInfo();
        //获取用户id
        OpenUserDetails user = OpenHelper.getUser();
        personInfo.setUid(user.getUserId());

        String mediaCode = map.get("mediaCode")==null?"112":""+map.get("mediaCode");
        String accountId = map.get("accountId")==null?"":""+map.get("accountId");//如快手id号，或抖音id号
        String accountName = map.get("accountName")==null?"":""+map.get("accountName");//如快手账号名称，或抖音账号名称
        String homePage = map.get("homePage")==null?"":""+map.get("homePage");//主页
        String avatar = map.get("personLogo")==null?"":""+map.get("personLogo");//头像
        String fansNum = map.get("fansNum")==null?"0":""+map.get("fansNum");//粉丝数
        String accountDesc = map.get("accountDesc")==null?"":""+map.get("accountDesc");//账号描述
        String live_price = map.get("live_price")==null?"0":""+map.get("live_price");//活动现场直播
        String ad_in_live = map.get("ad_in_live")==null?"0":""+map.get("ad_in_live");//直播广告植入
        Random ran=new Random();
        Long person_id = System.currentTimeMillis()+ran.nextInt(800);
        personInfo.setId(person_id);
        personInfo.setAccountId(accountId);
        personInfo.setAccountName(accountName);
        personInfo.setHomePage(homePage);
        personInfo.setPersonLogo(avatar);
        personInfo.setFansNum(Integer.parseInt(fansNum));
        personInfo.setPersonDesc(accountDesc);
        personInfo.setMediaCode(Integer.parseInt(mediaCode));
        personInfo.setAddTime(new Timestamp(System.currentTimeMillis()));
        personInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        personInfo.setPublishTime(null);
        personInfo.setApproveStatus("check");

        int p = mediaPriceService.selectPersonInfo(personInfo);
        if(p>0){
            return ResultBody.failed();
        }

        int id = mediaPriceService.addPersonInfo(personInfo);

        MediaPrice mediaPrice=new MediaPrice();
        //活动现场直播
        mediaPrice.setPersonId(person_id);
        mediaPrice.setId(System.currentTimeMillis()+ran.nextInt(800));
        mediaPrice.setTypeName("活动现场直播");
        mediaPrice.setPubPrice(Integer.parseInt(live_price));
        mediaPrice.setStatus("A");
        mediaPriceService.addMediaPrice(mediaPrice);
        //直播广告植入
        //mediaPrice=new MediaPrice();
        //mediaPrice.setPersonId(id);
        mediaPrice.setId(System.currentTimeMillis()+ran.nextInt(800));
        mediaPrice.setTypeName("直播广告植入");
        mediaPrice.setPubPrice(Integer.parseInt(ad_in_live));
        mediaPriceService.addMediaPrice(mediaPrice);
        //prsonInfo.setAccountPrice(Integer.parseInt(accountPrice));
        //personMedia.setMediaNameId(Integer.parseInt(mediaCode));
        //personMediaService.addPersonMedia(personMedia);
        return ResultBody.ok();
    }

    /**
     * 获取我已发布媒体列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取我已发布媒体列表", notes = "获取我已发布媒体列表")
    @PostMapping(value = "/media/myMediaList")
    public ResultBody<IPage<PersonInfo>> findMyMediaList(@RequestParam(required = false) Map map) {
        String mediaCode = map.get("mediaCode")==null?"112":""+map.get("mediaCode");
        IPage<PersonInfo> result=null;
        //根据媒体编码判断媒体渠道
        OpenUserDetails user = OpenHelper.getUser();
        System.out.println("userid================"+user.getUserId());
        PageParams pp = new PageParams(map);
        pp.getRequestMap().put("mediaCategory_id",mediaCode);
        pp.getRequestMap().put("uid",user.getUserId());
        pp.setOrderBy(" add_time desc ");
        pp.setLimit(100);
        result = mediaPriceService.getVideoList(Integer.parseInt(mediaCode),pp, new Page<PersonInfo>(pp.getPage(), 100));
        return ResultBody.success(result);
    }
    /**
     * 获取媒体类型
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取媒体类型", notes = "获取媒体类型")
    @PostMapping(value = "/media/getMediaCategory")
    public ResultBody getMediaCategory(@RequestParam(required = false) Map map) {


        List<PersonMediaCategory> result = mediaPriceService.findPersonMediaCategoryListPage();
        return ResultBody.success(result);
    }


    /**
     * 获取直播列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取直播列表", notes = "获取直播列表")
    @PostMapping(value = "/media/getMediaLiving")
    public ResultBody<IPage<MediaLiving>> getMediaLiving(@RequestParam(required = false) Map map) {
        PageParams pp = new PageParams(map);
        IPage<MediaLiving> result = mediaPriceService.getMediaLivingList(pp,new Page<MediaLiving>(pp.getPage(), 100));
        return ResultBody.success(result);
    }
}
