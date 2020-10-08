package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SysStationServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysStationSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysStationUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SysStation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 岗位控制器
 * 岗位项
 * </p>
 *
 * @author bxy
 * @date 2019-07-22
 */
@RestController
//@RequestMapping("/sysStation")
@Api(value = "SysStation", tags = "字典项")
public class SysStationController {
    @Autowired
    private SysStationServiceClient sysStationServiceClient;

    @ApiOperation(value = "分页查询岗位", notes = "分页查询岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/sysStation/page")
    public ResultBody<Page<SysStation>> getPage(@RequestParam Map<String, Object> map) {
        return sysStationServiceClient.getPage(map);
    }

    /**
     * 查询岗位
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "查询岗位", notes = "查询岗位")
    @GetMapping("/sysStation/get")
    public ResultBody get(@RequestParam(value = "id") Long id) {
        return sysStationServiceClient.get(id);
    }

    /**
     * 新增岗位
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增岗位", notes = "新增岗位不为空的字段")
    @PostMapping("/sysStation/save")
    public ResultBody save(@RequestBody @Validated SysStationSaveDTO data){
        return sysStationServiceClient.save(data);
    }

    /**
     * 修改岗位
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改岗位", notes = "修改岗位不为空的字段")
    @PutMapping("/sysStation/update")
    public ResultBody update(@RequestBody @Validated(SuperEntity.Update.class) SysStationUpdateDTO data){
        return sysStationServiceClient.update(data);
    }

    /**
     * 删除岗位
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除岗位", notes = "根据id物理删除岗位")
    @DeleteMapping("/sysStation/delete")
    public ResultBody delete(@RequestParam("ids[]") List<Long> ids) {
        return sysStationServiceClient.delete(ids);
    }

    /**
     * 调用方传递的参数类型是 Set<Serializable> ，但接收方必须指定为Long类型（实体的主键类型），否则在调用mp提供的方法时，会使得mysql出现类型隐式转换问题。
     * 问题如下： select * from org where id in ('100');
     * <p>
     * 强制转换成Long后，sql就能正常执行： select * from org where id in (100);
     *
     * <p>
     * 接口和实现类的类型不一致，但也能调用，归功于 SpingBoot 的自动转换功能
     * {@link com.github.bxy.admin.api.StationApi#findStationByIds} 方法的实现类
     *
     * @param codes id
     * @return
     */
    @GetMapping("/sysStation/findStationByIds")
    public Map<Serializable, Object> findStationByIds(@RequestParam("ids") Set<Long> ids){
        return sysStationServiceClient.findStationByIds(ids);
    }

    /**
     * 调用方传递的参数类型是 Set<Serializable> ，但接收方必须指定为Long类型（实体的主键类型），否则在调用mp提供的方法时，会使得mysql出现类型隐式转换问题。
     * 问题如下： select * from org where id in ('100');
     * <p>
     * 强制转换成Long后，sql就能正常执行： select * from org where id in (100);
     *
     * <p>
     * 接口和实现类的类型不一致，但也能调用，归功于 SpingBoot 的自动转换功能
     * {@link com.github.bxy.admin.api.StationApi#findStationNameByIds} 方法的实现类
     *
     * @param codes id
     * @return
     */
    @GetMapping("/sysStation/findStationNameByIds")
    public Map<Serializable, Object> findStationNameByIds(@RequestParam("ids") Set<Long> ids){
        return sysStationServiceClient.findStationNameByIds(ids);
    }

}
