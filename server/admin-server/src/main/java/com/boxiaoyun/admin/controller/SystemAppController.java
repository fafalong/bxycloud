package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemAppServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.security.BaseClientInfo;
import com.boxiaoyun.system.client.model.entity.SystemApp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 系统用户信息
 *
 * @author
 */
@Api(tags = "系统应用管理")
@RestController
public class SystemAppController {
    @Autowired
    private SystemAppServiceClient systemAppServiceClient;

    /**
     * 获取分页应用列表
     *
     * @return
     */
    @ApiOperation(value = "获取分页应用列表", notes = "获取分页应用列表")
    @GetMapping("/app/page")
    public ResultBody<Page<SystemApp>> getPage(@RequestParam(required = false) Map map) {
        return systemAppServiceClient.getPage(map);
    }

    /**
     * 获取应用详情
     *
     * @param appId
     * @return
     */
    @ApiOperation(value = "获取应用详情", notes = "获取应用详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", defaultValue = "1", required = true, paramType = "path"),
    })
    @GetMapping("/app/info")
    public ResultBody<SystemApp> get(
            @RequestParam("appId") String appId
    ) {
        return systemAppServiceClient.get(appId);
    }

    /**
     * 获取应用开发配置信息
     *
     * @param clientId
     * @return
     */
    @ApiOperation(value = "获取应用开发配置信息", notes = "获取应用开发配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "应用ID", defaultValue = "1", required = true, paramType = "path"),
    })
    @GetMapping("/app/client/info")
    public ResultBody<BaseClientInfo> getClientInfo(
            @RequestParam("clientId") String clientId
    ) {
        return systemAppServiceClient.getByClientId(clientId);
    }

    /**
     * 添加/编辑应用信息
     *
     * @param appId
     * @param appName   应用名称
     * @param appNameEn 应用英文名称
     * @param appOs     手机应用操作系统:ios-苹果 android-安卓
     * @param appType   应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appIcon   应用图标
     * @param appDesc   应用说明
     * @param status    状态
     * @param website   官网地址
     * @param developerId    开发者
     * @return
     * @
     */
    @ApiOperation(value = "添加/编辑应用信息", notes = "添加/编辑应用信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = false, paramType = "form"),
            @ApiImplicitParam(name = "appName", value = "应用名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appNameEn", value = "应用英文名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appType", value = "应用类型(server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用)", allowableValues = "server,app,pc,wap", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appIcon", value = "应用图标",required = false, paramType = "form"),
            @ApiImplicitParam(name = "appOs", value = "手机应用操作系统", allowableValues = "android,ios", required = false, paramType = "form"),
            @ApiImplicitParam(name = "appDesc", value = "应用说明", required = false, paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "website", value = "官网地址", required = false, paramType = "form"),
            @ApiImplicitParam(name = "developerId", value = "开发者", required = false, paramType = "form")
    })
    @PostMapping("/app/save")
    public ResultBody save(
            @RequestParam(value = "appId",required = false) String appId,
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "appNameEn") String appNameEn,
            @RequestParam(value = "appType") String appType,
            @RequestParam(value = "appIcon", required = false) String appIcon,
            @RequestParam(value = "appOs", required = false) String appOs,
            @RequestParam(value = "appDesc", required = false) String appDesc,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "website",required = false) String website,
            @RequestParam(value = "developerId", required = false) Long developerId
    ) {
        SystemApp app = new SystemApp();
        app.setAppId(appId);
        app.setAppName(appName);
        app.setAppNameEn(appNameEn);
        app.setAppType(appType);
        app.setAppOs(appOs);
        app.setAppIcon(appIcon);
        app.setAppDesc(appDesc);
        app.setStatus(status);
        app.setWebsite(website);
        app.setDeveloperId(developerId);
        return systemAppServiceClient.save(app);
    }

    /**
     * 保存应用开发信息
     *
     * @param appId                应用名称
     * @param grantTypes           授权类型(多个使用,号隔开)
     * @param redirectUrls         第三方应用授权回调地址(多个使用,号隔开)
     * @param scopes               用户授权范围(多个使用,号隔开)
     * @param autoApproveScopes    用户自动授权范围(多个使用,号隔开)
     * @param accessTokenValidity  令牌有效期(秒)
     * @param refreshTokenValidity 刷新令牌有效期(秒)
     * @return
     */
    @ApiOperation(value = "保存应用开发信息", notes = "保存应用开发信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "grantTypes", value = "授权类型(多个使用,号隔开)", required = true, paramType = "form"),
            @ApiImplicitParam(name = "redirectUrls", value = "第三方应用授权回调地址", required = true, paramType = "form"),
            @ApiImplicitParam(name = "scopes", value = "用户授权范围(多个使用,号隔开)", required = true, paramType = "form"),
            @ApiImplicitParam(name = "autoApproveScopes", value = "用户自动授权范围(多个使用,号隔开)", required = false, paramType = "form"),
            @ApiImplicitParam(name = "accessTokenValidity", value = "令牌有效期(秒)", required = true, paramType = "form"),
            @ApiImplicitParam(name = "refreshTokenValidity", value = "刷新令牌有效期(秒)", required = true, paramType = "form")
    })
    @PostMapping("/app/client/save")
    public ResultBody<String> saveClientInfo(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "grantTypes") String grantTypes,
            @RequestParam(value = "redirectUrls") String redirectUrls,
            @RequestParam(value = "scopes") String scopes,
            @RequestParam(value = "accessTokenValidity", required = true) Integer accessTokenValidity,
            @RequestParam(value = "refreshTokenValidity", required = true) Integer refreshTokenValidity,
            @RequestParam(value = "autoApproveScopes", required = false) String autoApproveScopes
    ) {
        return  systemAppServiceClient.saveClientInfo(appId,grantTypes,redirectUrls,scopes,accessTokenValidity,refreshTokenValidity,autoApproveScopes);
    }


    /**
     * 重置应用秘钥
     *
     * @param appId 应用Id
     * @return
     */
    @ApiOperation(value = "重置应用秘钥", notes = "重置应用秘钥")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, paramType = "form"),
    })
    @PostMapping("/app/reset")
    public ResultBody<String> resetSecret(
            @RequestParam("appId") String appId
    ) {
        return systemAppServiceClient.resetSecret(appId);
    }

    /**
     * 删除应用信息
     *
     * @param appId
     * @return
     */
    @ApiOperation(value = "删除应用信息", notes = "删除应用信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, paramType = "form"),
    })
    @PostMapping("/app/remove")
    public ResultBody remove(
            @RequestParam("appId") String appId
    ) {
        return systemAppServiceClient.remove(appId);
    }
}
