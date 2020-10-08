package com.boxiaoyun.site.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.common.security.oauth2.SocialClientDetails;
import com.boxiaoyun.common.security.oauth2.SocialProperties;
import com.boxiaoyun.common.security.oauth2.SocialService;
import com.boxiaoyun.common.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信oauth2认证实现类
 *
 * @author
 */
@Service("wechatAuthService")
@Slf4j
public class WechatServiceImpl implements SocialService {
    @Autowired
    private RestUtil restUtil;
    @Autowired
    private SocialProperties SocialProperties;
    /**
     * 微信 登陆页面的URL
     */
    private final static String AUTHORIZATION_URL = "%s?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    /**
     * 获取token的URL
     */
    private final static String ACCESS_TOKEN_URL = "%s?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * 获取用户信息的 URL，oauth_consumer_key 为 apiKey
     */
    private static final String USER_INFO_URL = "%s?access_token=%s&openid=%s&lang=zh_CN";


    @Override
    public String getAuthorizationUrl() {
        String url = String.format(AUTHORIZATION_URL, getClientDetails().getUserAuthorizationUri(), getClientDetails().getClientId(), getClientDetails().getRedirectUri(), getClientDetails().getScope(),System.currentTimeMillis());
        return url;
    }

    @Override
    public String getAccessToken(String code) {
        String url = String.format(ACCESS_TOKEN_URL, getClientDetails().getAccessTokenUri(), getClientDetails().getClientId(), getClientDetails().getClientSecret(), code);
        JSONObject result = restUtil.get(url,null,null, JSONObject.class);
        log.debug("getAccessToken:", result);
        if (result != null) {
            return result.getString("access_token");
        }
        return null;
    }

    /**
     * 由于QQ的几个接口返回类型不一样，此处是获取key-value类型的参数
     *
     * @param string
     * @return
     */
    private Map<String, String> getParam(String string) {
        Map<String, String> map = new HashMap();
        String[] kvArray = string.split("&");
        for (int i = 0; i < kvArray.length; i++) {
            String[] kv = kvArray[i].split("=");
            map.put(kv[0], kv[1]);
        }
        return map;
    }

    /**
     * QQ接口返回类型是text/plain，此处将其转为json
     *
     * @param string
     * @return
     */
    private JSONObject ConvertToJson(String string) {
        string = string.substring(string.indexOf("(") + 1, string.length());
        string = string.substring(0, string.indexOf(")"));
        JSONObject jsonObject = JSONObject.parseObject(string);
        return jsonObject;
    }

    @Override
    public String getOpenId(String accessToken) {
        return null;
    }

    @Override
    public JSONObject getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_URL, getClientDetails().getUserInfoUri(), accessToken,  openId);
        JSONObject resp = restUtil.get(url, null,null,JSONObject.class);
        return resp;
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
        return SocialProperties.getClient().get("wechat");
    }

}
