package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.BeanPlusUtil;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysDictionarySaveDTO;
import com.boxiaoyun.system.client.model.dto.SysDictionaryUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemDictionary;
import com.boxiaoyun.system.client.service.ISystemCommonDictionaryServiceClient;
import com.boxiaoyun.system.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统字典接口
 *
 * @author:
 * @date: 2019/3/12 15:12
 * @description:
 */
@Api(tags = "系统字典管理")
@RestController
public class DictionaryController extends BaseController<DictionaryService, SystemDictionary> implements ISystemCommonDictionaryServiceClient {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 分页查询字典目录
     *
     * @param map 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询字典目录", notes = "分页查询字典目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/dict/page")
    public ResultBody<Page<SystemDictionary>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }
    /**
     * 获取字典详情
     *
     * @param dictId
     * @return
     */
    @GetMapping("/dict/info")
    @Override
    public ResultBody<SystemDictionary> get(@RequestParam(value = "id") Long id) {
        return ResultBody.success(bizService.getById(id));
    }
    /**
     * 新增字典目录
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping("/dict/save")
    public ResultBody save(@RequestBody @Validated SysDictionarySaveDTO data){
        SystemDictionary dict = BeanPlusUtil.toBean(data, SystemDictionary.class);
        bizService.save(dict);
        return ResultBody.success();
    }

    /**
     * 修改字典目录
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @PutMapping("/dict/update")
    public ResultBody update(@RequestBody @Validated(SuperEntity.Update.class) SysDictionaryUpdateDTO data) {
        SystemDictionary dict = BeanPlusUtil.toBean(data, SystemDictionary.class);
        bizService.update(dict);
        return ResultBody.success();
    }
    /**
     * 删除字典目录
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @DeleteMapping("/dict/remove")
    public ResultBody delete(@RequestParam("ids[]") List<Long> ids) {
        //bizService.remove(ids);
        return ResultBody.ok();
    }
}
