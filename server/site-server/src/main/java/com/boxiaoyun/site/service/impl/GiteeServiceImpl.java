package com.boxiaoyun.site.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.common.security.oauth2.SocialClientDetails;
import com.boxiaoyun.common.security.oauth2.SocialProperties;
import com.boxiaoyun.common.security.oauth2.SocialService;
import com.boxiaoyun.common.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信oauth2认证实现类
 *
 * @author
 */
@Service("giteeService")
@Slf4j
public class GiteeServiceImpl implements SocialService {

    @Autowired
    private RestUtil restUtil;
    @Autowired
    private SocialProperties socialProperties;
    /**
     * 微信 登陆页面的URL
     */
    private final static String AUTHORIZATION_URL = "%s?response_type=code&client_id=%s&redirect_uri=%s&scope=%s";
    /**
     * 获取token的URL
     */
    private final static String ACCESS_TOKEN_URL = "%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    /**
     * 获取用户信息的 URL，oauth_consumer_key 为 apiKey
     */
    private static final String USER_INFO_URL = "%s?access_token=%s";


    @Override
    public String getAuthorizationUrl() {
        String url = String.format(AUTHORIZATION_URL, getClientDetails().getUserAuthorizationUri(), getClientDetails().getClientId(), getClientDetails().getRedirectUri(), getClientDetails().getScope(),System.currentTimeMillis());
        return url;
    }

    @Override
    public String getAccessToken(String code) {
        String url = String.format(ACCESS_TOKEN_URL, getClientDetails().getAccessTokenUri(), getClientDetails().getClientId(), getClientDetails().getClientSecret(), code,getClientDetails().getRedirectUri());
        JSONObject result = restUtil.get(url,null,null, JSONObject.class);
        log.debug("getAccessToken:", result);
        if (result != null && result.containsKey("access_token")) {
                return  result.getString("access_token");
        }
        return null;
    }

    @Override
    public String getOpenId(String accessToken) {
        return null;
    }

    @Override
    public JSONObject getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_URL, getClientDetails().getUserInfoUri(), accessToken);
        JSONObject result = restUtil.get(url,null,null, JSONObject.class);
        return result;
    }

    @Override
    public String refreshToken(String code) {
        return null;
    }

    /**
     * 获取登录成功地址
     *
     * @return
     */
    @Override
    public String getLoginSuccessUrl() {
        return getClientDetails().getLoginSuccessUri();
    }

    /**
     * 获取客户端配置信息
     *
     * @return
     */
    @Override
    public SocialClientDetails getClientDetails() {
        return socialProperties.getClient().get("gitee");
    }

}
