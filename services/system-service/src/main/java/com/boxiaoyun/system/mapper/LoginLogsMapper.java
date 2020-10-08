package com.boxiaoyun.system.mapper;

import com.boxiaoyun.common.mybatis.base.mapper.SuperMapper;
import com.boxiaoyun.system.client.model.entity.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Repository
public interface LoginLogsMapper extends SuperMapper<LoginLog> {
    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @param today 今天
     * @return Long
     */
    Long findTodayVisitCount(@Param("today") LocalDate today);

    /**
     * 获取系统今日访问 IP数
     *
     * @param today 今天
     * @return Long
     */
    Long findTodayIp(@Param("today") LocalDate today);

    /**
     * 获取系统近十天来的访问记录
     *
     * @param tenDays 10天前
     * @param account 用户账号
     * @return 系统近十天来的访问记录
     */
    List<Map<String, Object>> findLastTenDaysVisitCount(@Param("startDay") LocalDateTime startTime, @Param("endTime") LocalDateTime endDay, @Param("account") String account);

    /**
     * 按浏览器来统计数量
     *
     * @return
     */
    List<Map<String, Object>> findByBrowser();

    /**
     * 按造作系统内统计数量
     *
     * @return
     */
    List<Map<String, Object>> findByOperatingSystem();
}
