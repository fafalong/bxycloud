package com.boxiaoyun.cms.configuration;

//import com.boxiaoyun.cms.service.feign.EmailServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyadu
 */
@Configuration
public class CmsConfiguration {

    //@Autowired
    //private JobLogsListener jobLogsListener;

    //@Override
    public void customize( ) {
        //延时5秒启动
        //schedulerFactoryBean.setStartupDelay(5);
        //schedulerFactoryBean.setAutoStartup(true);
        //schedulerFactoryBean.setOverwriteExistingJobs(true);
        // 任务执行日志监听
        //schedulerFactoryBean.setGlobalJobListeners(jobLogsListener);
    }

    //@Bean
    //public JobLogsListener jobLogsListener(EmailServiceClient emailServiceClient, TaskJobLogsService schedulerJobLogsService) {
    //    return new JobLogsListener(emailServiceClient,schedulerJobLogsService);
    //}

}
