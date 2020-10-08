package com.boxiaoyun.site.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.site.service.feign.BaseAppServiceClient;
import com.boxiaoyun.site.service.feign.SystemDeveloperServiceClient;
import com.boxiaoyun.site.service.impl.GiteeServiceImpl;
import com.boxiaoyun.site.service.impl.QQServiceImpl;
import com.boxiaoyun.site.service.impl.WechatServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author:
 * @date: 2018/10/29 15:59
 * @description:
 */
@Controller
public class IndexController {
    @Autowired
    private BaseAppServiceClient baseAppRemoteService;
    @Autowired
    private SystemDeveloperServiceClient baseDeveloperServiceClient;
    @Autowired
    private QQServiceImpl qqAuthService;
    @Autowired
    private GiteeServiceImpl giteeAuthService;
    @Autowired
    private WechatServiceImpl wechatAuthService;
    @Autowired
    private LoginController loginController;

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "login";
    }

    /**
     * 确认授权页
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/oauth/confirm_access")
    public String confirm_access(HttpServletRequest request, HttpSession session, Map model) {
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<String>();
        for (String scope : scopes.keySet()) {
            scopeList.add(scope);
        }
        model.put("scopeList", scopeList);
        Object auth = session.getAttribute("authorizationRequest");
        if (auth != null) {
            try {
                AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
                ClientDetails clientDetails = baseAppRemoteService.getByClientId(authorizationRequest.getClientId()).getData();
                model.put("app", clientDetails.getAdditionalInformation());
            } catch (Exception e) {

            }
        }
        return "confirm_access";
    }

    /**
     * 自定义oauth2错误页
     * @param request
     * @return
     */
    @RequestMapping("/oauth/error")
    @ResponseBody
    public Object handleError(HttpServletRequest request) {
        Object error = request.getAttribute("error");
        return error;
    }


    /**
     * QQ第三方登录回调
     *
     * @param code code
     * @return
     */
    @GetMapping("/oauth/qq/callback")
    public String qq(@RequestParam(value = "code") String code, @RequestHeader HttpHeaders headers) throws Exception {
        String accessToken = qqAuthService.getAccessToken(code);
        String token = "";
        if (accessToken != null) {
            String openId = qqAuthService.getOpenId(token);
            if (openId != null) {
                baseDeveloperServiceClient.addThirdParty(openId, openId, "qq","","");
                token =  loginController.getToken(openId, openId, "qq",headers).getString("access_token");
            }
        }
        return "redirect:" + qqAuthService.getLoginSuccessUrl() + "?token=" + token;
    }

    /**
     * 微信第三方登录回调
     *
     * @param code
     * @return
     */
    @GetMapping("/oauth/wechat/callback")
    public String wechat(@RequestParam(value = "code") String code, @RequestHeader HttpHeaders headers) throws Exception {
        String accessToken = wechatAuthService.getAccessToken(code);
        String token = "";
        if (accessToken != null) {
            String openId = wechatAuthService.getOpenId(token);
            if (openId != null) {
                baseDeveloperServiceClient.addThirdParty(openId, openId, "wechat","","");
                token =  loginController.getToken(openId, openId, "wechat",headers).getString("access_token");
            }
        }
        return "redirect:" + wechatAuthService.getLoginSuccessUrl() + "?token=" + token;
    }


    /**
     * 码云第三方登录回调
     *
     * @param code
     * @return
     */
    @GetMapping("/oauth/gitee/callback")
    public String gitee(@RequestParam(value = "code") String code, @RequestHeader HttpHeaders headers) throws Exception {
        String accessToken = giteeAuthService.getAccessToken(code);
        String token = "";
        if (accessToken != null) {
            JSONObject userInfo = giteeAuthService.getUserInfo(accessToken, null);
            String openId = userInfo.getString("id");
            String name = userInfo.getString("name");
            String avatar = userInfo.getString("avatar_url");
            if (openId != null) {
                baseDeveloperServiceClient.addThirdParty(openId, openId, "gitee",name,avatar);
                token = loginController.getToken(openId, openId, "gitee",headers).getString("access_token");
            }
        }
        return "redirect:" + giteeAuthService.getLoginSuccessUrl() + "?token=" + token;
    }

    /**
     * 获取第三方登录配置
     *
     * @return
     */
    @ApiOperation(value = "获取第三方登录配置", notes = "任何人都可访问")
    @GetMapping("/login/config")
    @ResponseBody
    public ResultBody getLoginConfig() {
        Map<String, String> map = Maps.newHashMap();
        map.put("qq", qqAuthService.getAuthorizationUrl());
        map.put("wechat", wechatAuthService.getAuthorizationUrl());
        map.put("gitee", giteeAuthService.getAuthorizationUrl());
        return ResultBody.success(map);
    }



}
