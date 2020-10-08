package com.boxiaoyun.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.security.BaseClientInfo;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.system.client.model.entity.SystemApp;
import com.boxiaoyun.system.client.service.ISystemAppServiceClient;
import com.boxiaoyun.system.service.SystemAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统用户信息
 *
 * @author
 */
@Api(tags = "系统应用管理")
@RestController
public class SystemAppController extends BaseController<SystemAppService, SystemApp> implements ISystemAppServiceClient {
    @Autowired
    private RestUtil restUtil;

    @ApiOperation(value = "获取应用列表", notes = "获取应用列表")
    @GetMapping("/app/page")
    @Override
    public ResultBody<Page<SystemApp>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }


    @ApiOperation(value = "根据developerId获取应用列表", notes = "根据developerId获取应用列表")
    @GetMapping("/app/developer")
    @Override
    public  ResultBody<Page<SystemApp>> getPageByDeveloperId(@RequestParam("developerId") String developerId){
        Map<String, Object> map = Maps.newHashMap();
        map.put("developerId",developerId);
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    @ApiOperation(value = "获取应用详情", notes = "获取应用详情")
    @GetMapping("/app/info")
    @Override
    public ResultBody<SystemApp> get(@RequestParam("appId") String appId) {
        SystemApp appInfo = bizService.get(appId);
        return ResultBody.success(appInfo);
    }


    @ApiOperation(value = "根据ApiKey获取应用详情", notes = "根据ApiKey获取应用详情")
    @GetMapping("/app/info/apiKey")
    @Override
    public ResultBody<SystemApp> getByApiKey(@RequestParam("apiKey") String apiKey) {
        SystemApp appInfo = bizService.getByApiKey(apiKey);
        return ResultBody.success(appInfo);
    }

    @ApiOperation(value = "添加/修改应用信息", notes = "添加/修改应用信息")
    @PostMapping("/app/save")
    @Override
    public ResultBody<String> save(@RequestBody SystemApp systemApp) {
        if (StringUtil.isEmpty(systemApp.getAppId())) {
            bizService.add(systemApp);
        } else {
            bizService.update(systemApp);
        }
        return ResultBody.ok();
    }

    @ApiOperation(value = "删除应用信息", notes = "删除应用信息")
    @PostMapping("/app/remove")
    @Override
    public ResultBody remove(@RequestParam("appId") String appId) {
        bizService.remove(appId);
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    @ApiOperation(value = "获取应用开发配置信息", notes = "获取应用开发配置信息")
    @GetMapping("/app/client/info")
    @Override
    public ResultBody<BaseClientInfo> getByClientId(@RequestParam("clientId") String clientId) {
        BaseClientInfo clientInfo = bizService.getByClientId(clientId);
        return ResultBody.success(clientInfo);
    }

    @ApiOperation(value = "保存应用开发信息", notes = "保存应用开发信息")
    @PostMapping("/app/client/save")
    @Override
    public ResultBody<String> saveClientInfo(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "grantTypes") String grantTypes,
            @RequestParam(value = "redirectUrls") String redirectUrls,
            @RequestParam(value = "scopes") String scopes,
            @RequestParam(value = "accessTokenValidity", required = true) Integer accessTokenValidity,
            @RequestParam(value = "refreshTokenValidity", required = true) Integer refreshTokenValidity,
            @RequestParam(value = "autoApproveScopes", required = false) String autoApproveScopes
    ) {
        SystemApp app = bizService.get(appId);
        BaseClientInfo client = new BaseClientInfo(app.getApiKey(), "", scopes, grantTypes, "", redirectUrls);
        client.setAccessTokenValiditySeconds(accessTokenValidity);
        client.setRefreshTokenValiditySeconds(refreshTokenValidity);
        client.setAutoApproveScopes(autoApproveScopes != null ? StringUtils.commaDelimitedListToSet(autoApproveScopes): null);
        Map info = BeanUtil.beanToMap(app);
        client.setAdditionalInformation(info);
        bizService.updateClientInfo(client);
        return ResultBody.ok();
    }

    @ApiOperation(value = "重置应用秘钥", notes = "重置应用秘钥")
    @PostMapping("/app/reset/secret")
    @Override
    public ResultBody<String> resetSecret(@RequestParam("appId") String appId) {
        String result = bizService.restSecret(appId);
        return ResultBody.success(result);
    }

}
