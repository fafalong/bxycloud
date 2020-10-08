package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemCommonDictionaryItemServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysDictionaryItemSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysDictionaryItemUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemDictionaryItem;
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
 * 前端控制器
 * 字典项
 * </p>
 *
 * @author bxy
 * @date 2019-07-22
 */
@RestController
//@RequestMapping("/sysDictItem")
@Api(value = "SysDictionaryItem", tags = "字典项")
public class SysDictionaryItemController {

    @Autowired
    private SystemCommonDictionaryItemServiceClient systemCommonDictionaryItemServiceClient;

    /**
     * 分页查询字典项
     *
     * @param map 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询字典项", notes = "分页查询字典项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/sysDictItem/page")
    //@SysLog("分页查询字典项")
    //public ResultBody<Page<SystemDictionaryItem>> page(@RequestParam(value = "map",required = false) Map<String, Object> map) {
    public ResultBody<Page<SystemDictionaryItem>> page(@RequestParam Map<String, Object> map) {
        return systemCommonDictionaryItemServiceClient.page(map);
    }

    /**
     * 查询字典项
     *
     * @param id 主键id
     * @return 查询结果
     */
    //@SysLog("查询字典项")
    @ApiOperation(value = "查询字典项", notes = "查询字典项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "字典项id", paramType = "id"),
    })
    @GetMapping("/sysDictItem/get")
    public ResultBody<SystemDictionaryItem> get(@RequestParam(value = "id") Long id) {
        return systemCommonDictionaryItemServiceClient.get(id);
    }

    /**
     * 新增字典项
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增字典项", notes = "新增字典项不为空的字段")
    @PostMapping("/sysDictItem/save")
    //@SysLog("新增字典项")
    public ResultBody save(@RequestBody @Validated SysDictionaryItemSaveDTO data) {
        return systemCommonDictionaryItemServiceClient.save(data);
    }

    /**
     * 修改字典项
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改字典项", notes = "修改字典项不为空的字段")
    @PutMapping("/sysDictItem/update")
    //@SysLog("修改字典项")
    public ResultBody update(@RequestBody @Validated(SuperEntity.Update.class) SysDictionaryItemUpdateDTO data) {
        return systemCommonDictionaryItemServiceClient.update(data);
    }

    /**
     * 删除字典项
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除字典项", notes = "根据id物理删除字典项")
    @DeleteMapping("/sysDictItem/delete")
    //@SysLog("删除字典项")
    public ResultBody delete(@RequestParam(value = "ids[]") List<Long> ids) {
        return systemCommonDictionaryItemServiceClient.delete(ids);
    }


    /**
     * 由于字典编码本身就是String 类型，所以不会出现mysql 的隐式转换问题，所以无需转换
     *
     * <p>
     * 接口和实现类的类型不一致，但也能调用，归功于 SpingBoot 的自动转换功能
     * {@link com.github.bxy.authority.api.DictionaryItemApi#findDictionaryItem} 方法的实现类
     *
     * @param codes 字典项编码
     * @return
     */
    @ApiOperation(value = "查询字典项", notes = "根据id查询字典项")
    @GetMapping("/sysDictItem/findDictionaryItem")
    public ResultBody findDictionaryItem(@RequestParam(value = "codes[]") Set<Serializable> codes) {
        return systemCommonDictionaryItemServiceClient.findDictionaryItem(codes);
        //return sysCommonDictionaryItemServiceClient.test(null);
    }

    @ApiOperation(value = "根据字典编码查询字典条目", notes = "根据字典编码查询字典条目")
    @GetMapping("/sysDictItem/list")
    public ResultBody<Map<String, Map<String, String>>> list(@RequestParam(value = "codes[]") String[] codes) {
        return systemCommonDictionaryItemServiceClient.list(codes);
    }
}
