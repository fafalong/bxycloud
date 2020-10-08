package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemApi;

import java.util.List;
import java.util.Map;

/**
 * 接口资源管理
 *
 * @author
 */
public interface SystemApiService extends IBaseService<SystemApi> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SystemApi> findPage(PageParams pageParams);

    /**
     * 查询列表
     *
     * @return
     */
    List<SystemApi> findList(String serviceId);

    /**
     * 检查接口编码是否存在
     *
     * @param apiCode
     * @return
     */
    Boolean isExist(String apiCode);

    /**
     * 添加接口
     *
     * @param api
     * @return
     */
    void add(SystemApi api);

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    void update(SystemApi api);

    /**
     * 查询接口
     *
     * @param apiCode
     * @return
     */
    SystemApi getByCode(String apiCode);

    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    void remove(Long apiId);

    /**
     * 更新资源服务器
     *
     * @param serviceIds
     * @return
     */
    boolean batchUpdateService(String ...serviceIds);

    /**
     * 获取等待更新的服务器列表
     * @return
     */
    List<Map> getBatchUpdateServiceList();

    /**
     * 获取服务列表
     * @return
     */
    List<String> getServiceList();
}
