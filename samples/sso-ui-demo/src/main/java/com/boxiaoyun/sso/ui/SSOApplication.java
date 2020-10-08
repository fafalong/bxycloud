package com.boxiaoyun.sso.ui;

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
public class SSOApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class, args);
    }

}
