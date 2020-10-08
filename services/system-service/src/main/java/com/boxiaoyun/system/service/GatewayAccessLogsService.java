package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.GatewayAccessLogs;

import java.util.List;
import java.util.Map;

/**
 * 网关访问日志
 * @author
 */
public interface GatewayAccessLogsService extends IBaseService<GatewayAccessLogs> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<GatewayAccessLogs> findPage(PageParams pageParams);



}
