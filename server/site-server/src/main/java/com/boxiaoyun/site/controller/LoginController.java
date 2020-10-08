package com.boxiaoyun.site.controller;

import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.autoconfigure.ServerConfiguration;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.common.security.oauth2.SocialClientDetails;
import com.boxiaoyun.common.security.oauth2.SocialProperties;
import com.boxiaoyun.common.utils.RestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author:
 * @date: 2018/11/9 15:43
 * @description:
 */
@Api(tags = "用户认证中心")
@RestController
public class LoginController {

    @Autowired
    private SocialProperties socialProperties;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private RestUtil restUtil;
    @Autowired
    private ServerConfiguration serverConfiguration;

    /**
     * 获取用户基础信息
     *
     * @return
     */
    @ApiOperation(value = "获取用户基础信息")
    @GetMapping("/current/user")
    public ResultBody getUserProfile() {
        return ResultBody.success(SecurityHelper.getUser());
    }

    /**
     * 获取当前登录用户信息-SSO单点登录
     *
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息-SSO单点登录", notes = "获取当前登录用户信息-SSO单点登录")
    @GetMapping("/current/user/sso")
    public Principal principal(Principal principal) {
        return principal;
    }

    /**
     * 获取用户访问令牌
     * 基于oauth2密码模式登录
     *
     * @param username
     * @param password
     * @return access_token
     */
    @ApiOperation(value = "获取用户访问令牌", notes = "基于oauth2密码模式登录,无需签名,返回access_token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "登录名", paramType = "form"),
            @ApiImplicitParam(name = "password", required = true, value = "登录密码", paramType = "form")
    })
    @PostMapping("/login/token")
    public Object getLoginToken(@RequestParam String username, @RequestParam String password, @RequestHeader HttpHeaders httpHeaders) throws Exception {
        JSONObject result = getToken(username, password, null, httpHeaders);
        if (result.containsKey("access_token")) {
            return ResultBody.success(result);
        } else {
            return result;
        }
    }


    /**
     * 退出移除令牌
     *
     * @param token
     */
    @ApiOperation(value = "退出移除令牌", notes = "退出移除令牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", required = true, value = "访问令牌", paramType = "form")
    })
    @PostMapping("/logout/token")
    public ResultBody removeToken(@RequestParam String token) {
        tokenStore.removeAccessToken(tokenStore.readAccessToken(token));
        return ResultBody.ok();
    }


    /**
     * 生成 oauth2 token
     *
     * @param userName
     * @param password
     * @param type
     * @return
     */
    public JSONObject getToken(String userName, String password, String type, HttpHeaders headers) {
        SocialClientDetails clientDetails = socialProperties.getClient().get("site");
        String url = serverConfiguration.getUrl() + "/oauth/token";
        // 使用oauth2密码模式登录.
        Map<String, String> postParameters = new LinkedHashMap<>();
        postParameters.put("username", userName);
        postParameters.put("password", password);
        postParameters.put("client_id", clientDetails.getClientId());
        postParameters.put("client_secret", clientDetails.getClientSecret());
        postParameters.put("grant_type", "password");
        // 添加参数区分,第三方登录
        postParameters.put("login_type", type);
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头,防止token失效
        headers.remove(HttpHeaders.AUTHORIZATION);
        JSONObject result = restUtil.post(url, headers.toSingleValueMap(), postParameters, JSONObject.class);
        return result;
    }

}
