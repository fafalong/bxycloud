package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.security.BaseClientInfo;
import com.boxiaoyun.system.client.model.entity.SystemApp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author
 */
public interface ISystemAppServiceClient {

    /**
     * 获取应用列表
     *
     * @param map
     * @return
     */
    @GetMapping("/app/page")
    ResultBody<Page<SystemApp>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 根据developerId获取应用列表
     *
     * @param developerId
     * @return
     */
    @GetMapping("/app/developer")
    ResultBody<Page<SystemApp>> getPageByDeveloperId(@RequestParam("developerId") String developerId);


    /**
     * 获取应用详情
     *
     * @param appId
     * @return
     */
    @GetMapping("/app/info")
    ResultBody<SystemApp> get(@RequestParam("appId") String appId);

    /**
     * 根据ApiKey获取应用详情
     * @param apiKey
     * @return
     */
    @GetMapping("/app/info/apiKey")
    ResultBody<SystemApp> getByApiKey(@RequestParam("apiKey") String apiKey);

    /**
     * 添加/修改应用信息
     *
     * @param systemApp
     * @return
     */
    @PostMapping("/app/save")
    ResultBody<String> save(@RequestBody SystemApp systemApp);

    /**
     * 删除应用信息
     *
     * @param appId
     * @return
     */
    @PostMapping("/app/remove")
    ResultBody remove(@RequestParam("appId") String appId);

    /**
     * 获取应用开发配置信息
     *
     * @param clientId
     * @return
     */
    @GetMapping("/app/client/info")
    ResultBody<BaseClientInfo> getByClientId(@RequestParam("clientId") String clientId);

    /**
     * 保存应用开发信息
     *
     * @param appId
     * @param grantTypes
     * @param redirectUrls
     * @param scopes
     * @param accessTokenValidity
     * @param refreshTokenValidity
     * @param autoApproveScopes
     * @return
     */
    @PostMapping("/app/client/save")
    ResultBody<String> saveClientInfo(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "grantTypes") String grantTypes,
            @RequestParam(value = "redirectUrls") String redirectUrls,
            @RequestParam(value = "scopes") String scopes,
            @RequestParam(value = "accessTokenValidity", required = true) Integer accessTokenValidity,
            @RequestParam(value = "refreshTokenValidity", required = true) Integer refreshTokenValidity,
            @RequestParam(value = "autoApproveScopes", required = false) String autoApproveScopes
    );

    /**
     * 重置应用秘钥
     *
     * @param appId 应用Id
     * @return
     */
    @PostMapping("/app/reset/secret")
    ResultBody<String> resetSecret(@RequestParam("appId") String appId);
}
