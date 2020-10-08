package com.boxiaoyun.gateway.filter;

import com.boxiaoyun.gateway.exception.JsonAccessDeniedHandler;
import com.boxiaoyun.gateway.util.ReactiveWebUtils;
import com.boxiaoyun.system.client.model.AuthorityResource;
import com.boxiaoyun.common.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 访问验证前置过滤器
 *
 * @author
 */
@Slf4j
public class PreCheckFilter implements WebFilter {

    private JsonAccessDeniedHandler accessDeniedHandler;

    private AccessManager accessManager;

    private static final int DISABLED = 0;
    private static final int UPDATING = 2;

    public PreCheckFilter(AccessManager accessManager, JsonAccessDeniedHandler accessDeniedHandler) {
        this.accessManager = accessManager;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String requestPath = request.getURI().getPath();
        String remoteIpAddress = ReactiveWebUtils.getRemoteAddress(exchange);
        String origin = request.getHeaders().getOrigin();
        AuthorityResource resource = accessManager.getResource(requestPath);
        if (resource != null) {
            if (DISABLED == resource.getStatus().intValue()) {
                // 禁用
                return accessDeniedHandler.handle(exchange, new AccessDeniedException(ErrorCode.ACCESS_DENIED_DISABLED.getMessage()));
            } else if (UPDATING == resource.getStatus().intValue()) {
                // 维护中
                return accessDeniedHandler.handle(exchange, new AccessDeniedException(ErrorCode.ACCESS_DENIED_UPDATING.getMessage()));
            }
        }
        // 1.ip黑名单检测
        boolean deny = accessManager.matchIpOrOriginBlacklist(requestPath, remoteIpAddress, origin);
        if (deny) {
            // 拒绝
            return accessDeniedHandler.handle(exchange, new AccessDeniedException(ErrorCode.ACCESS_DENIED_BLACK_LIMITED.getMessage()));
        }

        // 3.ip白名单检测
        Boolean[] matchIpWhiteListResult = accessManager.matchIpOrOriginWhiteList(requestPath, remoteIpAddress, origin);
        boolean hasWhiteList = matchIpWhiteListResult[0];
        boolean allow = matchIpWhiteListResult[1];
        if (hasWhiteList) {
            // 接口存在白名单限制
            if (!allow) {
                // IP白名单检测通过,拒绝
                return accessDeniedHandler.handle(exchange, new AccessDeniedException(ErrorCode.ACCESS_DENIED_WHITE_LIMITED.getMessage()));
            }
        }
        return chain.filter(exchange);
    }
}
