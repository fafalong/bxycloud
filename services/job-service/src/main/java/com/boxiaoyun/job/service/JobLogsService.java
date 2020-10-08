package com.boxiaoyun.job.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.job.client.model.entity.JobLogs;

/**
 * 异步通知日志接口
 *
 * @author:
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface JobLogsService   extends IBaseService<JobLogs> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<JobLogs> findPage(PageParams pageParams);
}
