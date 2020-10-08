package com.boxiaoyun.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SysAreaServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysAreaSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysAreaUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SysArea;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.Area;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * 地区表
 * </p>
 *
 * @author bxy
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysArea")
@Api(value = "SysArea", tags = "地区表")
public class SysAreaController {

    @Autowired
    private SysAreaServiceClient sysAreaServiceClient;
    /**
     * 分页查询地区表
     *
     * @param data 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询地区表", notes = "分页查询地区表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    public ResultBody<Page<SysArea>> page(@RequestParam Map<String, Object> map) {
        return null;
    }

    /**
     * 查询地区表
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "查询地区表", notes = "查询地区表")
    @GetMapping("/{id}")
    public ResultBody<SysArea> get(@PathVariable Long id) {
        return null;
    }


    @ApiOperation(value = "检测地区编码是否重复", notes = "检测地区编码是否重复")
    @GetMapping("/check/{code}")
    public ResultBody<Boolean> check(@RequestParam(required = false) Long id, @PathVariable String code) {

        return null;
    }

    /**
     * 新增地区表
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增地区表", notes = "新增地区表不为空的字段")
    @PostMapping
    public ResultBody<SysArea> save(@RequestBody @Validated SysAreaSaveDTO data) {

        return null;
    }

    /**
     * 修改地区表
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改地区表", notes = "修改地区表不为空的字段")
    @PutMapping
    public ResultBody<Area> update(@RequestBody @Validated(SuperEntity.Update.class) SysAreaUpdateDTO data) {

        return null;
    }

    /**
     * 删除地区表
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除地区表", notes = "根据id物理删除地区表")
    @DeleteMapping
    public ResultBody<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        //TODO 递归删除
        return null;
    }

    /**
     * 级联查询地区
     *
     * @param data 级联查询地区
     * @return 查询结果
     */
    @ApiOperation(value = "级联查询地区", notes = "级联查询地区")
    @GetMapping
    public ResultBody<List<SysArea>> list(SysArea data) {
        return null;
    }

    /**
     * 查询树形地区
     * <p>
     * 该方法，第一次查询时 性能很差！待优化
     *
     * @return 查询结果
     */
    @ApiOperation(value = "查询树形地区", notes = "查询树形地区")
    @GetMapping("/tree")
    public ResultBody<List<SysArea>> tree() {
        return null;
    }
}
