package com.boxiaoyun.admin.configuration;

import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.exception.BaseAccessDeniedHandler;
import com.boxiaoyun.common.exception.BaseAuthenticationEntryPoint;
import com.boxiaoyun.common.security.BaseUserDetails;
import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.common.utils.WebUtil;
import com.boxiaoyun.system.client.constants.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * oauth2资源服务器配置
 *
 * @author:
 * @date: 2018/10/23 10:31
 * @description:
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/login/**", "/oauth/**","/sigup/**","/menu/**").permitAll()
                // 监控端点内部放行
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .successHandler(new LoginSuccesssHandler())
                .and()
                .logout().permitAll()
                // /logout退出清除cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .and()
                .exceptionHandling()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .accessDeniedHandler(new BaseAccessDeniedHandler())
                .authenticationEntryPoint(new BaseAuthenticationEntryPoint())
                .and().csrf().disable()
                // 禁用httpBasic
                .httpBasic().disable();
    }

    public class LoginSuccesssHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
            HttpServletRequest request =  WebUtil.getHttpServletRequest();;
            if (request != null) {
                Map map = new HashMap();
                BaseUserDetails userDetails = SecurityHelper.getUser();
                map.put("domain", SystemConstants.ACCOUNT_DOMAIN_SITE);
                map.put("userId", userDetails.getUserId());
                map.put("account", userDetails.getUsername());
                map.put("accountId", userDetails.getAccountId());
                map.put("accountType", userDetails.getAccountType());
                map.put("loginIp", WebUtil.getClientIP(request));
                map.put("loginAgent", request.getHeader(HttpHeaders.USER_AGENT));
                amqpTemplate.convertAndSend(CommonConstants.ACCOUNT_LOGS, map);
            }
        }
    }

    public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        public LogoutSuccessHandler() {
            // 重定向到原地址
            this.setUseReferer(true);
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            try {
                // 解密请求头
                authentication = tokenExtractor.extract(request);
                if (authentication != null && authentication.getPrincipal() != null) {
                    String tokenValue = authentication.getPrincipal().toString();
                    log.debug("revokeToken tokenValue:{}", tokenValue);
                    // 移除token
                    tokenStore.removeAccessToken(tokenStore.readAccessToken(tokenValue));
                }
            } catch (Exception e) {
                log.error("revokeToken error:{}", e);
            }
            super.onLogoutSuccess(request, response, authentication);
        }
    }
}

