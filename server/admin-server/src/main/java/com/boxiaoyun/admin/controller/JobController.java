package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.JobServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.job.client.model.JobInfo;
import com.boxiaoyun.job.client.model.entity.JobLogs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author:
 * @date: 2019/3/29 14:12
 * @description:
 */
@Api(tags = "定时任务管理")
@RestController
public class JobController {
    @Autowired
    private JobServiceClient jobServiceClient;

    /**
     * 获取任务执行日志列表
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获取任务执行日志列表", notes = "获取任务执行日志列表")
    @GetMapping(value = "/job/logs")
    public ResultBody<Page<JobLogs>> getLogList(@RequestParam(required = false) Map map) {
        return jobServiceClient.getLogList(map);
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    @ApiOperation(value = "获取任务列表", notes = "获取任务列表")
    @GetMapping(value = "/job")
    public ResultBody<Page<JobInfo>> getList(@RequestParam(required = false) Map map) {
        return jobServiceClient.getList(map);
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
    @ApiOperation(value = "添加远程调度任务", notes = "添加远程调度任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "jobDescription", value = "任务描述", required = true, paramType = "form"),
            @ApiImplicitParam(name = "jobType", value = "任务类型", required = true, allowableValues = "simple,cron", paramType = "form"),
            @ApiImplicitParam(name = "cron", value = "cron表达式", required = false, paramType = "form"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "form"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "form"),
            @ApiImplicitParam(name = "repeatInterval", value = "间隔时间", required = false, paramType = "form"),
            @ApiImplicitParam(name = "repeatCount", value = "重试次数", required = false, paramType = "form"),
            @ApiImplicitParam(name = "serviceId", value = "服务名", required = true, paramType = "form"),
            @ApiImplicitParam(name = "path", value = "请求路径", required = true, paramType = "form"),
            @ApiImplicitParam(name = "method", value = "请求类型", required = false, paramType = "form"),
            @ApiImplicitParam(name = "contentType", value = "响应类型", required = false, paramType = "form"),
            @ApiImplicitParam(name = "alarmMail", value = "告警邮箱", required = false, paramType = "form"),
    })
    @PostMapping("/job/save")
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
        return jobServiceClient.save(jobName, jobDescription, jobType, cron, startTime, endTime, repeatInterval, repeatCount, serviceId, path, method, contentType, alarmMail);
    }


    /**
     * 删除任务
     *
     * @param jobName 任务名称
     * @return
     */
    @ApiOperation(value = "删除任务", notes = "删除任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", required = true, paramType = "form")
    })
    @PostMapping("/job/remove")
    public ResultBody remove(@RequestParam(name = "jobName") String jobName) {
        return jobServiceClient.remove(jobName);
    }

    /**
     * 暂停任务
     *
     * @param jobName 任务名称
     * @return
     */
    @ApiOperation(value = "暂停任务", notes = "暂停任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", required = true, paramType = "form")
    })
    @PostMapping("/job/pause")
    public ResultBody pause(@RequestParam(name = "jobName") String jobName) {
        return jobServiceClient.pause(jobName);
    }


    /**
     * 恢复任务
     *
     * @param jobName 任务名称
     * @return
     */
    @ApiOperation(value = "恢复任务", notes = "恢复任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", required = true, paramType = "form")
    })
    @PostMapping("/job/resume")
    public ResultBody resume(@RequestParam(name = "jobName") String jobName) {
        return jobServiceClient.resume(jobName);
    }
}
