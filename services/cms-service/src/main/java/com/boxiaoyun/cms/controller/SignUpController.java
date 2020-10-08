package com.boxiaoyun.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hysj.cms.client.model.entity.Member;
import com.boxiaoyun.cms.service.*;
import com.opencloud.common.constants.CommonConstants;
import com.opencloud.common.model.PageParams;
import com.opencloud.common.model.ResultBody;
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

import java.sql.Timestamp;
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
public class SignUpController {

    @Autowired
    private MemberService memberService;

    /**
     * 注册，直接注册本站
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "前台用户注册", notes = "前台用户注册")
    @PostMapping(value = "/signup")
    public ResultBody<Member> signup(@RequestParam(required = false) Map map) {
        String user_name= (String)map.get("userName");
        Member member = new Member();
        member.setUid(System.nanoTime());
        member.setMemberType(""+map.get("memberType"));
        member.setUserName(""+map.get("userName"));
        member.setMobile(""+map.get("mobile"));
        member.setEmail(""+map.get("email"));
        member.setPwd(""+map.get("pwd"));//需要加密
        member.setGender("0");
        member.setAvatar(""+map.get("avatar"));
        //member.setBirthday(new Date(""+map.get("birthday")));
        member.setSummary(""+map.get("summary"));
        member.setStatus(1);
        member.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        member.setAddTime(new Timestamp(System.currentTimeMillis()));
        memberService.addMember(member);
        if(false) {//注册成功记录系统日志
            //memberService.addMemberLog();//用户日志
        }
        return ResultBody.ok();

    }

    /**
     * 检查mobile是否重复
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "验证手机号是否重复", notes = "验证手机号是否重复")
    @GetMapping(value = "/checkMobile")
    public ResultBody<Member> checkMobile(String mobile) {

        Member member= memberService.getMember(new Long("0"));
        return ResultBody.success(member);
    }

    /**
     * 检查email是否重复
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "验证手机号是否重复", notes = "验证手机号是否重复")
    @GetMapping(value = "/checkEmail")
    public ResultBody<Member> checkEmail(String email) {

        Member member= memberService.getMember(new Long(""));
        return ResultBody.success(member);
    }
}
