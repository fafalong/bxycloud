package com.boxiaoyun.job.service;

import com.google.common.collect.Maps;
import com.boxiaoyun.common.test.BaseTest;
import com.boxiaoyun.job.client.model.JobInfo;
import com.boxiaoyun.job.job.HttpJob;
import com.boxiaoyun.job.service.feign.EmailServiceClient;
import org.junit.Test;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author:
 * @date: 2019/3/29 14:59
 * @description:
 */
public class SchedulerServiceTest extends BaseTest {

    @Autowired
    private JobService jobService;
    @Autowired
    private EmailServiceClient emailServiceClient;
    @Test
    public void getJobList() throws Exception {
        List<JobInfo> list= jobService.getJobList();
    }

    @Test
    public void addSimpleJob() throws Exception {
    }

    @Test
    public void addCronJob() throws Exception {
        String cron = "3 * * * * ? *";
        JobInfo jobInfo = new JobInfo();
        Map data= Maps.newHashMap();
        String serviceId = "upm-server";
        data.put("serviceId",serviceId);
        data.put("method","get");
        data.put("path","/test");
        data.put("contentType","application/x-www-form-urlencoded");
        data.put("alarmMail","515608851@qq.com");
        jobInfo.setData(data);
        jobInfo.setJobName("定时任务测试");
        jobInfo.setJobDescription("定时任务描述3秒执行一次");
        jobInfo.setJobClassName(HttpJob.class.getName());
        jobInfo.setJobGroupName(Scheduler.DEFAULT_GROUP);
        jobInfo.setCronExpression(cron);
        jobService.addCronJob(jobInfo);
    }

    @Test
    public void editJob() throws Exception {
        String cron = "3 * * * * ? *";
        JobInfo jobInfo = new JobInfo();
        Map data= Maps.newHashMap();
        String serviceId = "upm-server";
        data.put("serviceId",serviceId);
        data.put("method","get");
        data.put("path","/test");
        data.put("contentType","application/x-www-form-urlencoded");
        data.put("alarmMail","515608851@qq.com");
        jobInfo.setData(data);
        jobInfo.setJobName("定时任务测试");
        jobInfo.setJobDescription("定时任务描述3秒2222222执行一次");
        jobInfo.setJobClassName(HttpJob.class.getName());
        jobInfo.setJobGroupName(Scheduler.DEFAULT_GROUP);
        jobInfo.setCronExpression(cron);
        jobService.editCronJob(jobInfo);
    }

    @Test
    public void deleteJob() throws Exception {
        jobService.deleteJob("定时任务测试",Scheduler.DEFAULT_GROUP);
    }

    @Test
    public void pauseJob() throws Exception {
        jobService.pauseJob("定时任务测试",Scheduler.DEFAULT_GROUP);
    }

    @Test
    public void resumeJob() throws Exception {
        jobService.resumeJob("定时任务测试",Scheduler.DEFAULT_GROUP);
    }

    @Test
    public void sendEmail() throws IOException {
        MockMultipartFile file1 = new MockMultipartFile("attachments","diagram.png",null,new FileInputStream("D:\\diagram.png"));
        emailServiceClient.send("515608851@qq.com",null,"测试","测试内容",new MultipartFile[]{file1});
    }


    @Test
    public void sendEmail2() throws IOException {
        MockMultipartFile file1 = new MockMultipartFile("file","diagram.png",null,new FileInputStream("D:\\diagram.png"));
        emailServiceClient.send2(file1);
    }
}
