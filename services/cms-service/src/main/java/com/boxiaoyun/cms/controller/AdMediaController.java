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
public class AdMediaController {

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
    @ApiOperation(value = "发布智闪投视频", notes = "发布智闪投视频")
    @GetMapping(value = "/adhome/addzhitouVideo")
    public ResultBody addzhitouVideo(@RequestParam(required = false) Map map,@RequestParam(value = "file", required = true) MultipartFile uploadFile) {
        if (file.isEmpty()) {
            System.out.println("上传失败，请选择文件");
        }
        String path = "/Users/itinypocket/workspace/temp/";
        String fileName = uploadFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //随机生成新的文件名，防止文件名冲突或者中文乱码问题
        String newFileName = uuid+suffix;
        //调用上传方法将文件上传到物理路径下
        uploadFile.getBytes
        FileUtil.uploadFile(uploadFile.getBytes(),path,newFileName);
        //可选：将图片路径存储起来为了定期清理图片（可以存储到非关系型数据库中，如mongodb）
        PicturePathDTO dto = new PicturePathDTO();
        dto.setPath(images+relativePath+newFileName);
        dto.setCreateTime(new Date());
        picturePathDao.save(dto);
        //返回虚拟路径
        //return (images+relativePath+newFileName);

        String filePath = "/Users/itinypocket/workspace/temp/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }

        MemberAdVideo advideo=new MemberAdVideo();
        //获取用户id
        OpenUserDetails user = OpenHelper.getUser();
        advideo.setUid(user.getUserId());

        return ResultBody.ok();
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
