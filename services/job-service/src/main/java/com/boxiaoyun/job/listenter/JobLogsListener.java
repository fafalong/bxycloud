package com.boxiaoyun.job.listenter;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.job.client.model.entity.JobLogs;
import com.boxiaoyun.job.service.JobLogsService;
import com.boxiaoyun.job.service.feign.EmailServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 任务调度监听
 *
 * @author
 */
@Slf4j
public class JobLogsListener implements JobListener {
    private EmailServiceClient emailServiceClient;
    private JobLogsService jobLogsService;

    public JobLogsListener(EmailServiceClient emailServiceClient, JobLogsService jobLogsService) {
        this.emailServiceClient = emailServiceClient;
        this.jobLogsService = jobLogsService;
    }

    @Override
    public String getName() {
        return "JobLogsListener";
    }


    /**
     * 调度前执行
     *
     * @param job
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext job) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext job) {
    }

    /**
     * 调度完成或异常时执行
     *
     * @param job
     * @param e
     */
    @Override
    public void jobWasExecuted(JobExecutionContext job, JobExecutionException e) {
        JobDetail detail = job.getJobDetail();
        JobDataMap dataMap = detail.getJobDataMap();
        String jobName = detail.getKey().getName();
        String jobGroup = detail.getKey().getGroup();
        String alarmMail = dataMap.getString("alarmMail");
        String jobClass = detail.getJobClass().getName();
        String description = detail.getDescription();
        String exception = null;
        String cronExpression = null;
        Integer status = 1;
        Trigger trigger = job.getTrigger();
        String triggerClass = trigger.getClass().getName();
        Long repeatInterval = 0L;
        Integer repeatCount = 0;
        Date startDate = null;
        Date endDate = null;
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            cronExpression = cronTrigger.getCronExpression();
        }else if (trigger instanceof SimpleTrigger) {
            SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
            repeatInterval = simpleTrigger.getRepeatInterval();
            repeatCount = simpleTrigger.getRepeatCount();
            startDate = simpleTrigger.getStartTime();
            endDate = simpleTrigger.getEndTime();
        }
        if (e != null) {
            status = 0;
            exception = StringUtil.getExceptionToString(e);
            if (StringUtil.isNotBlank(alarmMail)) {
                String title = String.format("[%s]任务执行异常-%s", jobName, DateUtil.formatDateTime(new Date()));
                try {
                    emailServiceClient.send(alarmMail,null,title,e.getMessage(),new MultipartFile[]{});
                } catch (Exception em) {
                    log.error("==> send alarmMail error:{}", em);
                }
            }
        }
        JobLogs jobLog = new JobLogs();
        jobLog.setJobName(jobName);
        jobLog.setJobGroup(jobGroup);
        jobLog.setJobClass(jobClass);
        jobLog.setJobDescription(description);
        jobLog.setRunTime(job.getJobRunTime());
        jobLog.setCreateTime(new Date());
        jobLog.setCronExpression(cronExpression);
        jobLog.setTriggerClass(triggerClass);
        jobLog.setRunStartTime(job.getFireTime());
        jobLog.setRunEndTime(new Date(job.getFireTime().getTime() + job.getJobRunTime()));
        jobLog.setJobData(JSONObject.toJSONString(dataMap));
        jobLog.setException(exception);
        jobLog.setStatus(status);
        jobLog.setRepeatInterval(repeatInterval);
        jobLog.setRepeatCount(repeatCount);
        jobLog.setStartDate(startDate);
        jobLog.setEndDate(endDate);
        jobLogsService.save(jobLog);
    }
}
