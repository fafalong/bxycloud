package com.boxiaoyun.system.configuration;

import com.boxiaoyun.common.constants.CommonConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @author LYD
 */
@Configuration
public class SystemConfiguration {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 访问日志队列
     *
     * @return
     */
    @Bean
    public Queue accessLogsQueue() {
        Queue queue = new Queue(CommonConstants.API_ACCESS_LOGS);
        return queue;
    }

    /**
     * 登陆日志
     * @return
     */
    @Bean
    public Queue accountLogsQueue() {
        Queue queue = new Queue(CommonConstants.ACCOUNT_LOGS);
        return queue;
    }

    /**
     * oauth2 令牌redis存储
     *
     * @return
     */
    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * oauth2客户端数据库存储
     *
     * @return
     */
    @Bean
    public JdbcClientDetailsService clientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }
}
