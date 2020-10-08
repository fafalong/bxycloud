package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.GatewayAccessLogs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author
 */
public interface ISystemDashboardServiceClient {

    /**
     * 最近10天访问记录
     *
     * @return
     */
    @GetMapping("/dashboard/visit")
    public ResultBody<Map<String, Object>> index();//(@ApiIgnore @LoginUser SysUser user) ;

}
