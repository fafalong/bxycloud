package com.boxiaoyun.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hysj.cms.client.model.entity.*;
import com.boxiaoyun.cms.service.*;
import com.opencloud.common.constants.CommonConstants;
import com.opencloud.common.model.PageParams;
import com.opencloud.common.model.ResultBody;
//import com.hysj.cms.client.model.CmsInfo;
import com.opencloud.common.security.OpenHelper;
import com.opencloud.common.security.OpenUserDetails;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liujian
 * @date: 2019/3/29 14:12
 * @description: 媒体主接广告主发的广告任务
 */
@RestController
public class MemberAdTaskController {

    @Autowired
    private MemberAdTaskService memberAdTaskService;

    /**
     * 获取用户信息
     *
     * @param map
     * @return
     */
    /**
     * 获取我任务列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取我任务列表", notes = "获取我任务列表")
    @PostMapping(value = "/mediaTask/getTask")
    public ResultBody<IPage<MemberAdTask>> getTask(@RequestParam(required = false) Map map) {
        String mediaCode = map.get("mediaCode")==null?"112":""+map.get("mediaCode");
        IPage<MemberAdTask> result=null;
        //根据媒体编码判断媒体渠道
        OpenUserDetails user = OpenHelper.getUser();
        //System.out.println("userid================"+user.getUserId());
        PageParams pp = new PageParams(map);
        pp.getRequestMap().put("uid",user.getUserId());
        pp.setOrderBy(" add_time desc ");
        pp.setLimit(10);
        result = memberAdTaskService.findListPage(pp);
        return ResultBody.success(result);
    }

}
