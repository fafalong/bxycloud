package com.boxiaoyun.autoconfigure;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.boxiaoyun.common.annotation.RequestMappingScan;
import com.boxiaoyun.common.configuration.CommonProperties;
import com.boxiaoyun.common.exception.BaseRestResponseErrorHandler;
import com.boxiaoyun.common.exception.GlobalExceptionHandler;
import com.boxiaoyun.common.filter.BaseFilter;
import com.boxiaoyun.common.health.DbHealthIndicator;
import com.boxiaoyun.common.mybatis.ModelMetaObjectHandler;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.common.security.oauth2.SocialProperties;
import com.boxiaoyun.common.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * 默认配置类
 *
 * @author
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CommonProperties.class,  SocialProperties.class})
public class AutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ServerConfiguration.class)
    public ServerConfiguration serverConfiguration(){
        return new ServerConfiguration();
    }

    /**
     * xss过滤
     * body缓存
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean baseFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new BaseFilter());
        log.info("BaseFilter [{}]", filterRegistrationBean);
        return filterRegistrationBean;
    }


    /**
     * 默认加密配置
     *
     * @return
     */
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("默认密码加密类 [{}]", encoder);
        return encoder;
    }


    /**
     * Spring上下文工具配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SpringContextHolder.class)
    public SpringContextHolder springContextHolder() {
        SpringContextHolder holder = new SpringContextHolder();
        log.info("Spring上下文静态工具类 [{}]", holder);
        return holder;
    }

    /**
     * 统一异常处理配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        log.info("全局异常处理器 [{}]", exceptionHandler);
        return exceptionHandler;
    }

    /**
     * 资源扫描类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScan.class)
    public RequestMappingScan resourceAnnotationScan(RedisTemplate redisTemplate) {
        RequestMappingScan scan = new RequestMappingScan(redisTemplate);
        log.info("资源扫描类.[{}]", scan);
        return scan;
    }

    /**
     * 自定义Oauth2请求类
     *
     * @param commonProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RestUtil.class)
    public RestUtil baseRestTemplate(RestTemplate restTemplate,CommonProperties commonProperties, BusProperties busProperties, ApplicationEventPublisher publisher) {
        restTemplate.setErrorHandler(new BaseRestResponseErrorHandler());
        RestUtil restUtil = new RestUtil(restTemplate,commonProperties, busProperties, publisher);
        //设置自定义ErrorHandler
        log.info("RestUtil [{}]", restUtil);
        return restUtil;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //设置自定义ErrorHandler
        restTemplate.setErrorHandler(new BaseRestResponseErrorHandler());
        log.info("RestTemplate [{}]", restTemplate);
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(DbHealthIndicator.class)
    public DbHealthIndicator dbHealthIndicator() {
        DbHealthIndicator dbHealthIndicator = new DbHealthIndicator();
        log.info("DbHealthIndicator [{}]", dbHealthIndicator);
        return dbHealthIndicator;
    }


    /**
     * 分页插件
     */
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        log.info("分页插件 [{}]", paginationInterceptor);
        return paginationInterceptor;
    }

    /**
     * 自动填充模型数据
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ModelMetaObjectHandler.class)
    public ModelMetaObjectHandler modelMetaObjectHandler() {
        ModelMetaObjectHandler metaObjectHandler = new ModelMetaObjectHandler();
        log.info("自动填充模型数据处理器 [{}]", metaObjectHandler);
        return metaObjectHandler;
    }
}
