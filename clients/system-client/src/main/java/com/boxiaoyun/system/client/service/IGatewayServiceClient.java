package com.boxiaoyun.system.client.service;

import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.IpLimitApi;
import com.boxiaoyun.system.client.model.RateLimitApi;
import com.boxiaoyun.system.client.model.entity.GatewayRoute;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author
 */
public interface IGatewayServiceClient {

    /**
     * 获取服务列表
     *
     * @return
     */
    @GetMapping("/gateway/service/list")
    ResultBody getServiceList() ;

    /**
     * 获取黑名单接口列表
     *
     * @return
     */
    @GetMapping("/gateway/api/blackList")
    ResultBody<List<IpLimitApi>> getBlackApiList() ;

    /**
     * 获取白名单接口列表
     * @return
     */
    @GetMapping("/gateway/api/whiteList")
    ResultBody<List<IpLimitApi> > getWhiteApiList();

    /**
     * 获取限流接口列表
     * @return
     */
    @GetMapping("/gateway/api/rateLimit")
    ResultBody<List<RateLimitApi> > getRateLimitApiList();

    /**
     * 获取路由列表
     * @return
     */
    @GetMapping("/gateway/api/route")
    ResultBody<List<GatewayRoute> > getRouteList();
}
