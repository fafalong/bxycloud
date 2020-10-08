package com.boxiaoyun.msg.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.msg.client.model.entity.WebhookLogs;

/**
 * 异步通知日志接口
 *
 * @author:
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface WebhookService extends IBaseService<WebhookLogs> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<WebhookLogs> findPage(PageParams pageParams);

}
