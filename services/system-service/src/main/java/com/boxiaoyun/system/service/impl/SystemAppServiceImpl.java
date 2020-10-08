package com.boxiaoyun.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.mybatis.query.CriteriaQuery;
import com.boxiaoyun.common.security.BaseClientInfo;
import com.boxiaoyun.common.utils.RedisUtil;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.entity.SystemApp;
import com.boxiaoyun.system.client.model.entity.SystemDeveloper;
import com.boxiaoyun.system.mapper.SystemAppMapper;
import com.boxiaoyun.system.service.SystemAppService;
import com.boxiaoyun.system.service.SystemAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author:
 * @date: 2018/11/12 16:26
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemAppServiceImpl extends BaseServiceImpl<SystemAppMapper, SystemApp> implements SystemAppService {

    @Autowired
    private SystemAuthorityService systemAuthorityService;
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;
    @Autowired
    private RedisUtil redisUtil;

    private final static String APP_PREFIX = "app:";
    private final static String APP_API_KEY_PREFIX = "app_api_key:";
    private final static String APP_CLIENT_PREFIX = "app_client:";

    /**
     * token有效期，默认12小时
     */
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;
    /**
     * token有效期，默认7天
     */
    public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7;

    /**
     * 查询应用列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<SystemApp> findPage(PageParams pageParams) {
        SystemApp query = pageParams.mapToBean(SystemApp.class);
        CriteriaQuery<SystemApp> cq = new CriteriaQuery(pageParams);
        cq.lambda()
                .eq(ObjectUtils.isNotEmpty(query.getDeveloperId()), SystemApp::getDeveloperId, query.getDeveloperId())
                .eq(ObjectUtils.isNotEmpty(query.getAppType()), SystemApp::getAppType, query.getAppType())
                .eq(ObjectUtils.isNotEmpty(pageParams.getRequestMap().get("aid")), SystemApp::getAppId, pageParams.getRequestMap().get("aid"))
                .likeRight(ObjectUtils.isNotEmpty(query.getAppName()), SystemApp::getAppName, query.getAppName())
                .likeRight(ObjectUtils.isNotEmpty(query.getAppNameEn()), SystemApp::getAppNameEn, query.getAppNameEn());
        cq.select("app.*,developer.user_name");
        //关联BaseDeveloper表
        cq.createAlias(SystemDeveloper.class);
        cq.orderByDesc("create_time");
        return pageList(cq);
    }

    /**
     * 获取app详情
     *
     * @param appId
     * @return
     */
    @Override
    public SystemApp get(String appId) {
        String key = APP_PREFIX + appId;
        if (redisUtil.hasKey(key)) {
            return (SystemApp) redisUtil.get(key);
        }
        SystemApp app = getById(appId);
        if (app != null) {
            redisUtil.set(key, app, 2, TimeUnit.HOURS);
        }
        return app;
    }

    /**
     * 根据ApiKey获取app信息
     *
     * @param apiKey
     * @return
     */
    @Override
    public SystemApp getByApiKey(String apiKey) {
        String key = APP_API_KEY_PREFIX + apiKey;
        if (redisUtil.hasKey(key)) {
            return (SystemApp) redisUtil.get(key);
        }
        QueryWrapper<SystemApp> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemApp::getApiKey, apiKey);
        SystemApp app = getOne(queryWrapper);
        if (app != null) {
            redisUtil.set(key, app, 2, TimeUnit.HOURS);
        }
        return app;
    }

    /**
     * 获取app和应用信息
     *
     * @return
     */
    @Override
    public BaseClientInfo getByClientId(String clientId) {
        BaseClientDetails baseClientDetails = null;
        try {
            baseClientDetails = (BaseClientDetails) jdbcClientDetailsService.loadClientByClientId(clientId);
        } catch (Exception e) {
            return null;
        }
        String appId = baseClientDetails.getAdditionalInformation().get("appId").toString();
        String key = APP_CLIENT_PREFIX + appId + ":" + baseClientDetails.getClientId();
        if (redisUtil.hasKey(key)) {
            return (BaseClientInfo) redisUtil.get(key);
        }
        BaseClientInfo baseClientInfo = new BaseClientInfo();
        BeanUtils.copyProperties(baseClientDetails, baseClientInfo);
        baseClientInfo.setAuthorities(systemAuthorityService.findAuthorityByApp(appId));
        redisUtil.set(key, baseClientInfo, 2, TimeUnit.HOURS);
        return baseClientInfo;
    }

    /**
     * 更新应用开发新型
     *
     * @param client
     */
    @Override
    public void updateClientInfo(BaseClientInfo client) {
        BaseClientInfo baseClientInfo = getByClientId(client.getClientId());
        if (baseClientInfo == null) {
            return;
        }
        String appId = baseClientInfo.getAdditionalInformation().get("appId").toString();
        String key = APP_CLIENT_PREFIX + appId + ":" + baseClientInfo.getClientId();
        jdbcClientDetailsService.updateClientDetails(client);
        redisUtil.del(key);
    }

    /**
     * 添加应用
     *
     * @param app
     * @return 应用信息
     */
    @Override
    public SystemApp add(SystemApp app) {
        String appId = String.valueOf(System.currentTimeMillis());
        String apiKey = RandomUtil.randomString(24);
        String secretKey = RandomUtil.randomString(32);
        app.setAppId(appId);
        app.setApiKey(apiKey);
        app.setSecretKey(secretKey);
        app.setCreateTime(new Date());
        app.setUpdateTime(app.getCreateTime());
        if (app.getIsPersist() == null) {
            app.setIsPersist(0);
        }
        save(app);
        Map info = BeanUtil.beanToMap(app);
        // 功能授权
        BaseClientInfo client = new BaseClientInfo();
        client.setClientId(app.getApiKey());
        client.setClientSecret(app.getSecretKey());
        client.setAdditionalInformation(info);
        Set<String> resourceIds = new HashSet();
        resourceIds.add("default-oauth2-resource");
        // 设置默认可访问资源服务器Id,如果没设置，就是对所有的resource都有访问权限
        client.setResourceIds(resourceIds);
        client.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "client_credentials", "implicit", "refresh_token"));
        client.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
        client.setRefreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
        jdbcClientDetailsService.addClientDetails(client);
        return app;
    }

    /**
     * 修改应用
     *
     * @param systemApp 应用
     * @return 应用信息
     */
    @Override
    public SystemApp update(SystemApp systemApp) {
        // 修改客户端附加信息
        SystemApp app = get(systemApp.getAppId());
        String apiKey = app.getApiKey();
        String secretKey = app.getSecretKey();
        BeanUtils.copyProperties(systemApp, app);
        app.setApiKey(apiKey);
        app.setSecretKey(secretKey);
        updateById(app);
        BaseClientDetails client = (BaseClientDetails) jdbcClientDetailsService.loadClientByClientId(app.getApiKey());
        client.setAdditionalInformation(BeanUtil.beanToMap(app));
        jdbcClientDetailsService.updateClientDetails(client);
        redisUtil.del(APP_PREFIX + app.getAppId());
        redisUtil.del(APP_API_KEY_PREFIX + app.getApiKey());
        redisUtil.del(APP_CLIENT_PREFIX + app.getAppId() + ":" + app.getApiKey());
        return app;
    }

    /**
     * 重置秘钥
     *
     * @param appId
     * @return
     */
    @Override
    public String restSecret(String appId) {
        SystemApp app = get(appId);
        if (app == null) {
            throw new BaseException(appId + "应用不存在!");
        }
        if (app.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止修改"));
        }
        // 生成新的密钥
        String secretKey = RandomUtil.randomString(32);
        app.setSecretKey(secretKey);
        updateById(app);
        jdbcClientDetailsService.updateClientSecret(app.getApiKey(), secretKey);
        redisUtil.del(APP_PREFIX + app.getAppId());
        redisUtil.del(APP_API_KEY_PREFIX + app.getApiKey());
        redisUtil.del(APP_CLIENT_PREFIX + app.getAppId() + ":" + app.getApiKey());
        return secretKey;
    }

    @Override
    public void remove(String appId) {
        SystemApp app = get(appId);
        if (app == null) {
            throw new BaseException(appId + "应用不存在!");
        }
        if (app.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止删除"));
        }
        // 移除应用权限
        systemAuthorityService.removeAuthorityApp(appId);
        removeById(app.getAppId());
        jdbcClientDetailsService.removeClientDetails(app.getApiKey());
        redisUtil.del(APP_PREFIX + app.getAppId());
        redisUtil.del(APP_API_KEY_PREFIX + app.getApiKey());
        redisUtil.del(APP_CLIENT_PREFIX + app.getAppId() + ":" + app.getApiKey());
    }


}
