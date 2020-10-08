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
 * @description:
 */
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 获取用户信息
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping(value = "/member/getMemberInfo")
    public ResultBody<Member> getMemberInfo(@RequestParam(required = false) Map map) {
        String memnberId= (String)map.get("memberId");
        Member member = memberService.getMember(new Long(memnberId));
        return ResultBody.success(member);
    }

}
