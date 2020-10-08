package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.common.security.BaseClientInfo;
import com.boxiaoyun.system.client.model.entity.SystemApp;

/**
 * 应用信息管理
 *
 * @author
 */
public interface SystemAppService extends IBaseService<SystemApp> {

    /**
     * 查询应用列表
     *
     * @param pageParams
     * @return
     */
    IPage<SystemApp> findPage(PageParams pageParams);

    /**
     * 获取app信息
     *
     * @param appId
     * @return
     */
    SystemApp get(String appId);

    /**
     * 根据ApiKey获取app信息
     * @param apiKey
     * @return
     */
    SystemApp getByApiKey(String apiKey);

    /**
     * 获取app和应用信息
     *
     * @param clientId
     * @return
     */
    BaseClientInfo getByClientId(String clientId);


    /**
     * 更新应用开发新型
     *
     * @param client
     */
    void updateClientInfo(BaseClientInfo client);

    /**
     * 添加应用
     *
     * @param systemApp 应用
     * @return 应用信息
     */
    SystemApp add(SystemApp systemApp);

    /**
     * 修改应用
     *
     * @param systemApp 应用
     * @return 应用信息
     */
    SystemApp update(SystemApp systemApp);


    /**
     * 重置秘钥
     *
     * @param appId
     * @return
     */
    String restSecret(String appId);

    /**
     * 删除应用
     *
     * @param appId
     * @return
     */
    void remove(String appId);
}
