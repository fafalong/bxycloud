package com.boxiaoyun.cms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.MemberAdTask;
import com.boxiaoyun.cms.mapper.MemberAdTaskMapper;
import com.boxiaoyun.cms.service.MemberAdTaskService;
import com.opencloud.common.model.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MemberAdTaskServiceImpl implements MemberAdTaskService {
	

    @Autowired
    private MemberAdTaskMapper memberAdTaskMapper;

    /**
     * 获取网站频道列表
     *
     * @param siteId
     */
    @Override
    public IPage<MemberAdTask> findListPage(PageParams pageParams) {
        MemberAdTask query = pageParams.mapToObject(MemberAdTask.class);
        QueryWrapper<MemberAdTask> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), AdvLink::getJobName, query.getJobName());

        queryWrapper.orderByDesc("update_time");
        return memberAdTaskMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
/*
    @Override
    public IPage<AdvNav> findAdvNavListPage(PageParams pageParams) {
        AdvNav query = pageParams.mapToObject(AdvNav.class);
        QueryWrapper<AdvNav> queryWrapper = new QueryWrapper();
        //queryWrapper.lambda()
        //        .likeRight(ObjectUtils.isNotEmpty(query.getTitle()), AdvNav::getJobName, query.getJobName());
        queryWrapper.orderByDesc("update_time");
        return advNavMapper.selectPage(new Page(pageParams.getPage(),pageParams.getLimit()),queryWrapper);
    }
*/
    @Override
    public MemberAdTask getMemberAdTask(Long taskId){
        return memberAdTaskMapper.selectById(taskId);
    }
    @Override
    public void addMemberAdTask(MemberAdTask memberAdTask){
        memberAdTaskMapper.insert(memberAdTask);
    }
    @Override
    public void modifyMemberAdTask(MemberAdTask memberAdTask){
        memberAdTaskMapper.updateById(memberAdTask);
    }
}
