package com.boxiaoyun.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.job.client.model.JobInfo;
import com.boxiaoyun.job.client.model.entity.JobLogs;
import com.boxiaoyun.job.client.service.IJobServiceClient;
import com.boxiaoyun.job.job.HttpJob;
import com.boxiaoyun.job.service.JobLogsService;
import com.boxiaoyun.job.service.JobService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author:
 * @date: 2019/3/29 14:12
 * @description:
 */
@RestController
public class JobController implements IJobServiceClient {
    @Autowired
    private JobService schedulerService;
    @Autowired
    private JobLogsService schedulerJobLogsService;

    /**
     * 获取任务执行日志列表
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/job/logs")
    @Override
    public ResultBody<Page<JobLogs>> getLogList(@RequestParam(required = false) Map map) {
        return ResultBody.ok().data(schedulerJobLogsService.findPage(new PageParams(map)));
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    @GetMapping(value = "/job")
    @Override
    public ResultBody<Page<JobInfo>> getList(@RequestParam(required = false) Map map) {
        List<JobInfo> list = schedulerService.getJobList();
        IPage page = new Page();
        page.setRecords(list);
        page.setTotal(list.size());
        return ResultBody.ok().data(page);
    }

    /**
     * 添加远程调度任务
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
    @Override
    public ResultBody save(
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
            @RequestParam(name = "alarmMail", required = false) String alarmMail) {
        JobInfo jobInfo = new JobInfo();
        Map data = Maps.newHashMap();
        data.put("serviceId", serviceId);
        data.put("method", method);
        data.put("path", path);
        data.put("contentType", contentType);
        data.put("alarmMail", alarmMail);
        jobInfo.setData(data);
        jobInfo.setJobName(jobName);
        jobInfo.setJobDescription(jobDescription);
        jobInfo.setJobClassName(HttpJob.class.getName());
        jobInfo.setJobGroupName(Scheduler.DEFAULT_GROUP);
        jobInfo.setStartDate(startTime);
        jobInfo.setEndDate(endTime);
        jobInfo.setRepeatInterval(repeatInterval);
        jobInfo.setRepeatCount(repeatCount);
        jobInfo.setCronExpression(cron);
        if (!schedulerService.checkExists(jobInfo.getJobName(),jobInfo.getJobGroupName())) {
            if ("simple".equals(jobType)) {
                Assert.notNull(jobInfo.getStartDate(), "startTime不能为空");
                schedulerService.addSimpleJob(jobInfo);
            } else {
                Assert.notNull(jobInfo.getCronExpression(), "cron表达式不能为空");
                schedulerService.addCronJob(jobInfo);
            }
        } else {
            if ("simple".equals(jobType)) {
                Assert.notNull(jobInfo.getStartDate(), "startTime不能为空");
                schedulerService.editSimpleJob(jobInfo);
            } else {
                Assert.notNull(jobInfo.getCronExpression(), "cron表达式不能为空");
                schedulerService.editCronJob(jobInfo);
            }
        }
        return ResultBody.ok();
    }


    /**
     * 删除任务
     *
     * @param jobName 任务名称
     * @return
     */
    @PostMapping("/job/remove")
    @Override
    public ResultBody remove(@RequestParam(name = "jobName") String jobName) {
        schedulerService.deleteJob(jobName, Scheduler.DEFAULT_GROUP);
        return ResultBody.ok();
    }

    /**
     * 暂停任务
     *
     * @param jobName 任务名称
     * @return
     */
    @PostMapping("/job/pause")
    @Override
    public ResultBody pause(@RequestParam(name = "jobName") String jobName) {
        schedulerService.pauseJob(jobName, Scheduler.DEFAULT_GROUP);
        return ResultBody.ok();
    }


    /**
     * 恢复任务
     *
     * @param jobName 任务名称
     * @return
     */
    @PostMapping("/job/resume")
    @Override
    public ResultBody resume(@RequestParam(name = "jobName") String jobName) {
        schedulerService.resumeJob(jobName, Scheduler.DEFAULT_GROUP);
        return ResultBody.ok();
    }
}
