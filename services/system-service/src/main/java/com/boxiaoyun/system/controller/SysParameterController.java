package com.boxiaoyun.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.system.client.model.dto.SysParameterSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysParameterUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SysParameter;
import com.boxiaoyun.system.client.service.ISysParameterServiceClient;
import com.boxiaoyun.system.service.SysParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.boxiaoyun.common.model.ResultBody.success;

/**
 * @author:
 * @date: 2019/5/24 13:31
 * @description:
 */
@Api(tags = "系统参数配置")
@RestController
public class SysParameterController extends BaseController<SysParameterService, SysParameter> implements ISysParameterServiceClient {

    @Autowired
    SysParameterService sysParameterService;
    /**
     * 分页查询参数配置
     *
     * @param map 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询参数配置", notes = "分页查询参数配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/sysParameter/page")
    public ResultBody<Page<SysParameter>> page(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 查询参数配置
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "查询参数配置", notes = "查询参数配置")
    @GetMapping("/sysParameter/get")
    public ResultBody<SysParameter> get(@RequestParam Long id) {
        return ResultBody.ok().data(bizService.getById(id));
    }

    /**
     * 新增参数配置
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增参数配置", notes = "新增参数配置不为空的字段")
    @PostMapping("/sysParameter/save")
    public ResultBody save(@RequestBody SysParameterSaveDTO data) {
        SysParameter sysParameter = BeanUtil.toBean(data, SysParameter.class);
        if(sysParameter.getId()==null){
            bizService.add(sysParameter);
        }else{
            bizService.update(sysParameter);
        }
        return ResultBody.ok().data(sysParameter);
    }

    /**
     * 修改参数配置
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改参数配置", notes = "修改参数配置不为空的字段")
    @PutMapping("/sysParameter/update")
    public ResultBody update(@RequestBody SysParameterUpdateDTO data) {
        SysParameter sysParameter = BeanUtil.toBean(data, SysParameter.class);
        bizService.update(sysParameter);
        return ResultBody.ok().data(sysParameter);
    }

    /**
     * 删除参数配置
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除参数配置", notes = "根据id物理删除参数配置")
    @DeleteMapping("/sysParameter/remove")
    public ResultBody delete(@RequestParam("ids[]") List<Long> ids) {
        for(Long id:ids) {
            bizService.remove(id);
        }
        return ResultBody.ok();
    }

}

