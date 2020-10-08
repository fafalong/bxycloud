package com.boxiaoyun.job.configuration;

import com.boxiaoyun.job.listenter.JobLogsListener;
import com.boxiaoyun.job.service.JobLogsService;
import com.boxiaoyun.job.service.feign.EmailServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author
 */
@Configuration
public class JobConfiguration implements SchedulerFactoryBeanCustomizer {

    @Autowired
    private JobLogsListener jobLogsListener;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        //延时5秒启动
        schedulerFactoryBean.setStartupDelay(5);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        // 任务执行日志监听
        schedulerFactoryBean.setGlobalJobListeners(jobLogsListener);
    }

    @Bean
    public JobLogsListener jobLogsListener(EmailServiceClient emailServiceClient, JobLogsService jobLogsService) {
        return new JobLogsListener(emailServiceClient,jobLogsService);
    }

}
