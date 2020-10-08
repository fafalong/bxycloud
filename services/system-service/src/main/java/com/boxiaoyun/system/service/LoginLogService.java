package com.boxiaoyun.system.service;

import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户登录日志
 *
 * @author:
 * @date: 2018/10/24 16:38
 * @description:
 */
public interface LoginLogService extends IBaseService<SystemUser> {
    /**
     * 用户登录日志信息
     * @param systemUser
     * @return
     */
    void loginLog(SystemUser systemUser);
    
    /**
     * 获取按天统计访问量
     * 默认最近10天
     * @return
     */
    List<Map<String, Object>> getAccessCountByDay(LocalDateTime startDay, LocalDateTime endDay, String account);
}
