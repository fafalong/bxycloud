package com.boxiaoyun.system.service.impl;

import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import com.boxiaoyun.system.mapper.LoginLogsMapper;
import com.boxiaoyun.system.mapper.SystemUserMapper;
import com.boxiaoyun.system.service.LoginLogService;
import com.boxiaoyun.system.service.SystemAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginLogServiceImpl extends BaseServiceImpl<SystemUserMapper, SystemUser> implements LoginLogService {

    @Autowired
    LoginLogsMapper loginLogsMapper;
    /**
     * 用户登录日志信息
     * @param systemUser
     * @return
     */
    public void loginLog(SystemUser systemUser){

    }
    
    /**
     * 获取按天统计访问量
     * 默认最近10天
     * @return
     */
    @Override
    public List<Map<String, Object>> getAccessCountByDay(LocalDateTime startDay, LocalDateTime endDay, String account){
        return loginLogsMapper.findLastTenDaysVisitCount(startDay,endDay,account);
    }
}
