package com.boxiaoyun.msg;

import com.boxiaoyun.msg.service.EmailConfigService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.boxiaoyun.**.mapper")
public class MsgServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MsgServiceApplication.class, args);
    }

    @Autowired
    private EmailConfigService emailConfigService;

    @Override
    public void run(String... strings) throws Exception {
        emailConfigService.loadCacheConfig();
    }
}
