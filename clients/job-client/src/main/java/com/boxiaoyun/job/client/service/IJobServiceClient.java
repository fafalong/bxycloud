package com.boxiaoyun.job.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.job.client.model.JobInfo;
import com.boxiaoyun.job.client.model.entity.JobLogs;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

/**
 * 发送邮件接口
 *
 * @author woodev
 */
public interface IJobServiceClient {

    /**
     * 获取任务执行日志列表
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/job/logs")
    ResultBody<Page<JobLogs>> getLogList(@RequestParam(required = false) Map map);

    /**
     * 获取任务列表
     *
     * @return
     */
    @GetMapping(value = "/job")
    ResultBody<Page<JobInfo>> getList(@RequestParam(required = false) Map map);

    /**
     * 添加/修改 任务
     *
     * @param jobName        任务名称
     * @param jobDescription 任务描述
     * @param jobType        任务类型
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param repeatInterval 间隔时间
     * @param repeatCount    重试次数
     * @param cron           cron表达式
     * @param serviceId      服务名
     * @param path           请求路径
     * @param method         请求类型
     * @param contentType    响应类型
     * @param alarmMail      告警邮箱
     * @return
     */
    @PostMapping("/job/save")
    ResultBody save(
            @RequestParam(name = "jobName") String jobName,
            @RequestParam(name = "jobDescription") String jobDescription,
            @RequestParam(name = "jobType") String jobType,
            @RequestParam(name = "cron", required = false) String cron,
            @RequestParam(name = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(name = "repeatInterval", required = false, defaultValue = "0") Long repeatInterval,
            @RequestParam(name = "repeatCount", required = false, defaultValue = "0") Integer repeatCount,
            @RequestParam(name = "serviceId") String serviceId,
            @RequestParam(name = "path") String path,
            @RequestParam(name = "method", required = false) String method,
            @RequestParam(name = "contentType", required = false) String contentType,
            @RequestParam(name = "alarmMail", required = false) String alarmMail);

    /**
     * 删除任务
     *
     * @param jobName 任务名称
     * @return
     */
    @PostMapping("/job/remove")
    ResultBody remove(@RequestParam(name = "jobName") String jobName);

    /**
     * 暂停任务
     *
     * @param jobName 任务名称
     * @return
     */
    @PostMapping("/job/pause")
    ResultBody pause(@RequestParam(name = "jobName") String jobName);

    /**
     * 恢复任务
     *
     * @param jobName 任务名称
     * @return
     */
    @PostMapping("/job/resume")
    ResultBody resume(@RequestParam(name = "jobName") String jobName);

}
