package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.SystemApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface ISystemApiServiceClient {

    /**
     * 获取接口列表
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/api")
    ResultBody<Page<SystemApi>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取接口列表
     *
     * @param serviceId
     * @return
     */
    @GetMapping("/api/list")
    ResultBody<List<SystemApi>> getList(@RequestParam(value = "serviceId",required = false) String serviceId);

    /**
     * 获取接口信息
     *
     * @param apiId
     * @return
     */
    @GetMapping("/api/info")
    ResultBody<SystemApi> get(@RequestParam("apiId") Long apiId);

    /**
     * 添加/修改接口信息
     *
     * @param systemApi
     * @return
     */
    @PostMapping("/api/save")
    ResultBody<Long> save(@RequestBody SystemApi systemApi);

    /**
     * 移除接口信息
     *
     * @param apiId
     * @return
     */
    @PostMapping("/api/remove")
    ResultBody remove(@RequestParam("apiId") Long apiId);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return
     */
    @PostMapping("/api/batch/remove")
    ResultBody batchRemove(@RequestParam(value = "ids") String ids);

    /**
     * 批量修改状态
     *
     * @param ids
     * @param value
     * @param type  open,status,auth
     * @return
     */
    @PostMapping("/api/batch/update")
    ResultBody batchUpdate(@RequestParam(value = "ids") String ids, @RequestParam(value = "value") Integer value, @RequestParam(value = "type") String type);

    /**
     * 获取服务列表
     * @return
     */
    @GetMapping("/api/service/list")
    ResultBody getServiceList();

    /**
     * 获取批量更新的服务器列表
     * @return
     */
    @GetMapping("/api/batch/update/service/list")
    ResultBody getBatchUpdateServiceList();

    /**
     * 批量更新资源服务器
     * @param serviceIds
     * @return
     */
    @PostMapping("/api/batch/update/service")
    ResultBody batchUpdateService(@RequestParam(value = "serviceIds") String serviceIds);


}
