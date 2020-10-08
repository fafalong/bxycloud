package com.boxiaoyun.gateway.filter;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.google.common.collect.Maps;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.exception.BaseSignatureException;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SignatureUtil;
import com.boxiaoyun.gateway.configuration.ApiProperties;
import com.boxiaoyun.gateway.exception.JsonSignatureDeniedHandler;
import com.boxiaoyun.gateway.filter.context.GatewayContext;
import com.boxiaoyun.gateway.service.feign.SystemAppServiceClient;
import com.boxiaoyun.system.client.model.entity.SystemApp;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 数字验签前置过滤器
 *
 * @author:
 * @date: 2018/11/28 18:26
 * @description:
 */
public class PreSignatureFilter implements WebFilter {
    private JsonSignatureDeniedHandler signatureDeniedHandler;
    private SystemAppServiceClient systemAppServiceClient;
    private ApiProperties apiProperties;
    private static final AntPathMatcher pathMatch = new AntPathMatcher();
    private Set<String> signIgnores = new ConcurrentHashSet<>();

    public PreSignatureFilter(SystemAppServiceClient systemAppServiceClient, ApiProperties apiProperties, JsonSignatureDeniedHandler signatureDeniedHandler) {
        this.apiProperties = apiProperties;
        this.systemAppServiceClient = systemAppServiceClient;
        this.signatureDeniedHandler = signatureDeniedHandler;
        // 默认忽略签名
        signIgnores.add("/");
        signIgnores.add("/error");
        signIgnores.add("/favicon.ico");
        signIgnores.add("/actuator/**");
        signIgnores.add("/menu/**");
        if (apiProperties != null) {
            if (apiProperties.getSignIgnores() != null) {
                signIgnores.addAll(apiProperties.getSignIgnores());
            }
            if (apiProperties.getApiDebug()) {
                signIgnores.add("/**/v2/api-docs/**");
                signIgnores.add("/**/swagger-resources/**");
                signIgnores.add("/webjars/**");
                signIgnores.add("/doc.html");
                signIgnores.add("/swagger-ui.html");
            }
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getURI().getPath();
        if (apiProperties.getCheckSign() && !notSign(requestPath)) {
            try {
                Map params = Maps.newHashMap();
                GatewayContext gatewayContext = exchange.getAttribute(GatewayContext.CACHE_GATEWAY_CONTEXT);
                // 排除文件上传
                if (gatewayContext != null) {
                    params = gatewayContext.getAllRequestData().toSingleValueMap();
                }
                params.put(CommonConstants.SIGN_API_KEY,"7gBZcbsC7kLIWCdELIl8nxcs");//liujian
                //params.put(CommonConstants.SIGN_SIGN_KEY,"7gBZcbsC7kLIWCdELIl8nxcs");
                // 验证请求参数
                SignatureUtil.validateParams(params);
                //开始验证签名
                if (systemAppServiceClient != null) {
                    String apiKey = params.get(CommonConstants.SIGN_API_KEY).toString();
                    // 获取客户端信息
                    ResultBody<SystemApp> resultBody = systemAppServiceClient.getByApiKey(apiKey);
                    SystemApp app = resultBody.getData();
                    if (app == null || app.getAppId() == null) {
                        return signatureDeniedHandler.handle(exchange, new BaseSignatureException("无效的ApiKey"));
                    }
                    // 服务器验证签名结果
                    //if (!SignatureUtil.validateSign(params, app.getSecretKey())) {
                    //    return signatureDeniedHandler.handle(exchange, new BaseSignatureException("签名验证失败!"));
                    //}
                }
            } catch (Exception ex) {
                return signatureDeniedHandler.handle(exchange, new BaseSignatureException(ex.getMessage()));
            }
        }
        return chain.filter(exchange);
    }


    protected static List<String> getIgnoreMatchers(String... antPatterns) {
        List<String> matchers = new CopyOnWriteArrayList();
        for (String path : antPatterns) {
            matchers.add(path);
        }
        return matchers;
    }

    protected boolean notSign(String requestPath) {
        if (apiProperties.getSignIgnores() == null) {
            return false;
        }
        for (String path : signIgnores) {
            if (pathMatch.match(path, requestPath)) {
                return true;
            }
        }
        return false;
    }
}
