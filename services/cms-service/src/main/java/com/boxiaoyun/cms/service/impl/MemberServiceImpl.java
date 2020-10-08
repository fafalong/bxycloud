package com.boxiaoyun.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.Member;
import com.boxiaoyun.cms.mapper.MemberMapper;
import com.boxiaoyun.cms.service.MemberService;
import com.opencloud.common.model.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * @author liujian
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {
	

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 获取网站频道列表
     *
     * @param siteId
     */
    @Override
    public IPage<Member> findListPage(PageParams pageParams) {
        Member query = pageParams.mapToObject(Member.class);
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), MemberLink::getJobName, query.getJobName());

        queryWrapper.orderByDesc("update_time");
        return memberMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
/*
    @Override
    public IPage<MemberNav> findMemberNavListPage(PageParams pageParams) {
        MemberNav query = pageParams.mapToObject(MemberNav.class);
        QueryWrapper<MemberNav> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), MemberNav::getJobName, query.getJobName());
        queryWrapper.orderByDesc("update_time");
        return memberNavMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
*/
    @Override
    public Member getMember(Long memberId){
        return memberMapper.selectById(memberId);
    }
    @Override
    public void addMember(Member member){
        member.setPwd(passwordEncoder.encode(member.getPwd()));
        memberMapper.insert(member);
    }
    @Override
    public void modifyMember(Member member){
        memberMapper.updateById(member);
    }
}
