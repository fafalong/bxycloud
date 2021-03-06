package com.boxiaoyun.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.PersonInfo;
import com.opencloud.common.model.PageParams;
import com.opencloud.common.mybatis.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PersonMediaMapper extends SuperMapper<PersonInfo> {
IPage<PersonInfo> getShortVideoList(Page<PersonInfo> page, @Param("ew") Wrapper<?> wrapper);
//IPage<PersonInfo> getShortVideoList(PageParams pageParams, QueryWrapper queryWrapper);

//IPage<PersonInfo> getWeiboList(PageParams pageParams, Page<PersonInfo> page);
    IPage<PersonInfo> getWeiboList(Page<PersonInfo> page, @Param("ew") Wrapper<?> wrapper);
}
