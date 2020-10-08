package com.boxiaoyun.common.security.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author:
 * @date: 2019/2/14 14:34
 * @description:
 */
@ConfigurationProperties(prefix = "cloud.social")
public class SocialProperties {

    private Map<String, SocialClientDetails> client;

    public Map<String, SocialClientDetails> getClient() {
        return client;
    }

    public void setClient(Map<String, SocialClientDetails> client) {
        this.client = client;
    }
}
