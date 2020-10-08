package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemApiServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.SystemApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Api(tags = "系统接口资源管理")
@RestController
public class SystemApiController {
    @Autowired
    private SystemApiServiceClient systemApiServiceClient;

    /**
     * 获取分页接口列表
     *
     * @return
     */
    @ApiOperation(value = "获取分页接口列表", notes = "获取分页接口列表")
    @GetMapping(value = "/api")
    public ResultBody<Page<SystemApi>> getPage(@RequestParam(required = false) Map map) {
        return systemApiServiceClient.getPage(map);
    }


    /**
     * 获取所有接口列表
     *
     * @return
     */
    @ApiOperation(value = "获取所有接口列表", notes = "获取所有接口列表")
    @GetMapping("/api/list")
    public ResultBody<List<SystemApi>> getList(@RequestParam(value = "serviceId", required = false) String serviceId) {
        return systemApiServiceClient.getList(serviceId);
    }

    /**
     * 获取接口资源
     *
     * @param apiId
     * @return
     */
    @ApiOperation(value = "获取接口资源", notes = "获取接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "path"),
    })
    @GetMapping("/api/info")
    public ResultBody<SystemApi> getApi(@RequestParam("apiId") Long apiId) {
        return systemApiServiceClient.get(apiId);
    }

    /**
     * 编辑接口资源
     *
     * @param apiId     接口ID
     * @param apiCode   接口编码
     * @param apiName   接口名称
     * @param serviceId 服务ID
     * @param path      请求路径
     * @param status    是否启用
     * @param priority  优先级越小越靠前
     * @param apiDesc   描述
     * @return
     */
    @ApiOperation(value = "编辑接口资源", notes = "编辑接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = false, value = "接口Id", paramType = "form"),
            @ApiImplicitParam(name = "apiCode", required = true, value = "接口编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "接口名称", paramType = "form"),
            @ApiImplicitParam(name = "apiCategory", required = true, value = "接口分类", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = true, value = "服务ID", paramType = "form"),
            @ApiImplicitParam(name = "path", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "apiDesc", required = false, value = "描述", paramType = "form"),
            @ApiImplicitParam(name = "isAuth", required = false, defaultValue = "0", allowableValues = "0,1", value = "是否身份认证", paramType = "form"),
    })
    @PostMapping("/api/save")
    public ResultBody save(
            @RequestParam(value = "apiId", required = false) Long apiId,
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "apiCategory") String apiCategory,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc,
            @RequestParam(value = "isAuth", required = false, defaultValue = "1") Integer isAuth
    ) {
        SystemApi api = new SystemApi();
        api.setApiId(apiId);
        api.setApiCode(apiCode);
        api.setApiName(apiName);
        api.setApiCategory(apiCategory);
        api.setServiceId(serviceId);
        api.setPath(path);
        api.setStatus(status);
        api.setPriority(priority);
        api.setApiDesc(apiDesc);
        api.setIsAuth(isAuth);
        return systemApiServiceClient.save(api);
    }


    /**
     * 移除接口资源
     *
     * @param apiId
     * @return
     */
    @ApiOperation(value = "移除接口资源", notes = "移除接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
    })
    @PostMapping("/api/remove")
    public ResultBody remove(
            @RequestParam("apiId") Long apiId
    ) {
        return systemApiServiceClient.remove(apiId);
    }


    /**
     * 批量删除数据
     *
     * @return
     */
    @ApiOperation(value = "批量删除数据", notes = "批量删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
    })
    @PostMapping("/api/batch/remove")
    public ResultBody batchRemove(
            @RequestParam(value = "ids") String ids
    ) {
        return systemApiServiceClient.batchRemove(ids);
    }


    /**
     * 批量修改公开状态
     *
     * @return
     */
    @ApiOperation(value = "批量修改", notes = "批量修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form"),
            @ApiImplicitParam(name = "value", required = true, value = "值", paramType = "form"),
            @ApiImplicitParam(name = "type", required = true, value = "open,status,auth", paramType = "form")
    })
    @PostMapping("/api/batch/update")
    public ResultBody batchUpdate(
            @RequestParam(value = "ids") String ids,
            @RequestParam(value = "value") Integer value,
            @RequestParam(value = "type") String type
    ) {
        return systemApiServiceClient.batchUpdate(ids, value, type);
    }

    @ApiOperation(value = "获取服务列表", notes = "获取服务列表")
    @GetMapping("/api/service/list")
    public ResultBody getServiceList() {
        return systemApiServiceClient.getServiceList();
    }

    @ApiOperation(value = "获取批量更新的服务器列表", notes = "获取批量更新的服务器列表")
    @GetMapping("/api/batch/update/service/list")
    public ResultBody batchUpdate() {
        return systemApiServiceClient.getBatchUpdateServiceList();
    }


    @ApiOperation(value = "批量更新资源服务器", notes = "批量更新资源服务器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceIds", required = true, value = "多个用,号隔开", paramType = "form"),
    })
    @PostMapping("/api/batch/update/service")
    public ResultBody batchUpdateService(
            @RequestParam(value = "serviceIds") String serviceIds
    ) {
        return systemApiServiceClient.batchUpdateService(serviceIds);
    }
}
