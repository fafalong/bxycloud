package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.RestUtil;
import com.boxiaoyun.system.client.model.entity.SystemApi;
import com.boxiaoyun.system.client.service.ISystemApiServiceClient;
import com.boxiaoyun.system.service.SystemApiService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Api(tags = "系统接口信息管理")
@RestController
public class SystemApiController extends BaseController<SystemApiService, SystemApi> implements ISystemApiServiceClient {
    @Autowired
    private RestUtil restUtil;

    /**
     * 获取接口列表
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/api")
    @Override
    public ResultBody<Page<SystemApi>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }


    /**
     * 获取接口列表
     *
     * @param serviceId
     * @return
     */
    @GetMapping("/api/list")
    @Override
    public ResultBody<List<SystemApi>> getList(@RequestParam(value = "serviceId",required = false) String serviceId) {
        return ResultBody.success(bizService.findList(serviceId));
    }

    /**
     * 获取接口信息
     *
     * @param apiId
     * @return
     */
    @GetMapping("/api/info")
    @Override
    public ResultBody<SystemApi> get(@RequestParam("apiId") Long apiId) {
        return ResultBody.success(bizService.getById(apiId));
    }


    /**
     * 添加/修改接口信息
     *
     * @param systemApi
     * @return
     */
    @PostMapping("/api/save")
    @Override
    public ResultBody<Long> save(@RequestBody SystemApi systemApi) {
        if (systemApi.getApiId() == null) {
            bizService.add(systemApi);
        } else {
            bizService.update(systemApi);
        }
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 移除接口信息
     *
     * @param apiId
     * @return
     */
    @PostMapping("/api/remove")
    @Override
    public ResultBody remove(@RequestParam("apiId") Long apiId) {
        bizService.remove(apiId);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 批量删除数据
     *
     * @param ids
     * @return
     */
    @PostMapping("/api/batch/remove")
    @Override
    public ResultBody batchRemove(@RequestParam(value = "ids") String ids) {
        QueryWrapper<SystemApi> wrapper = new QueryWrapper();
        wrapper.lambda().in(SystemApi::getApiId, ids.split(",")).eq(SystemApi::getIsPersist, 0);
        bizService.remove(wrapper);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 批量修改状态
     *
     * @param ids
     * @param value
     * @param type  open,status,auth
     * @return
     */
    @PostMapping("/api/batch/update")
    @Override
    public ResultBody batchUpdate(
            @RequestParam(value = "ids") String ids,
            @RequestParam(value = "value") Integer value,
            @RequestParam(value = "type") String type
    ) {
        QueryWrapper<SystemApi> wrapper = new QueryWrapper();
        wrapper.lambda().in(SystemApi::getApiId, StringUtils.commaDelimitedListToSet(ids));
        SystemApi entity = new SystemApi();
        if ("status".equals(type)) {
            entity.setStatus(value);
        } else if ("auth".equals(type)) {
            entity.setIsAuth(value);
        } else {
            return ResultBody.ok();
        }
        bizService.update(entity, wrapper);
        // 刷新网关
        restUtil.refreshGateway();
        return ResultBody.ok();
    }

    /**
     * 获取批量更新的服务器列表
     *
     * @return
     */
    @GetMapping("/api/batch/update/service/list")
    @Override
    public ResultBody getBatchUpdateServiceList() {
        return ResultBody.success(bizService.getBatchUpdateServiceList());
    }

    /**
     * 获取服务列表
     *
     * @return
     */
    @GetMapping("/api/service/list")
    @Override
    public ResultBody getServiceList() {
        return ResultBody.success(bizService.getServiceList());
    }

    /**
     * 批量更新资源服务器
     *
     * @param serviceIds
     * @return
     */
    @PostMapping("/api/batch/update/service")
    @Override
    public ResultBody batchUpdateService(@RequestParam(value = "serviceIds") String serviceIds) {
        if (bizService.batchUpdateService(StringUtils.commaDelimitedListToStringArray(serviceIds))) {
            restUtil.refreshGateway();
        }
        return ResultBody.ok();
    }

}
