package com.boxiaoyun.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemCommonDictionaryServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysDictionarySaveDTO;
import com.boxiaoyun.system.client.model.dto.SysDictionaryUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * 字典目录
 * </p>
 *
 * @author bxy
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dictionary")
@Api(value = "Dictionary", tags = "字典目录")
public class DictionaryController {

    @Autowired
    private SystemCommonDictionaryServiceClient systemCommonDictionaryServiceClient;
    /**
     * 分页查询字典目录
     *
     * @param data 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询字典目录", notes = "分页查询字典目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    public ResultBody<Page<SystemDictionary>> getPage(@RequestParam Map<String, Object> map) {
        return systemCommonDictionaryServiceClient.getPage(map);
    }

    /**
     * 新增字典目录
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping
    public ResultBody<SystemDictionary> save(@RequestBody @Validated SysDictionarySaveDTO data){
        return systemCommonDictionaryServiceClient.save(data);
    }

    /**
     * 修改字典目录
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @PutMapping
    ResultBody<SystemDictionary> update(@RequestBody @Validated(SuperEntity.Update.class) SysDictionaryUpdateDTO data) {
        return systemCommonDictionaryServiceClient.update(data);
    }
    /**
     * 删除字典目录
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @DeleteMapping
    ResultBody<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        return systemCommonDictionaryServiceClient.delete(ids);
    }
}
