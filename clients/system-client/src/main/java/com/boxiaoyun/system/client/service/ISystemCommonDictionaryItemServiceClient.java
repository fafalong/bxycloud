package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysDictionaryItemSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysDictionaryItemUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemDictionaryItem;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface  ISystemCommonDictionaryItemServiceClient{
    /**
     * 分页查询字典项
     *
     * @param map 分页查询对象
     * @return 查询结果
     */
    @GetMapping("/sysDictItem/page")
    ResultBody<Page<SystemDictionaryItem>> page(@RequestParam Map<String, Object> map);

    /**
     * 查询字典项
     *
     * @param id 主键id
     * @return 查询结果
     */
    @GetMapping("/sysDictItem/get")
    ResultBody<SystemDictionaryItem> get(@RequestParam(value = "id") Long id);
    /**
     * 新增字典项
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping("/sysDictItem/save")
    ResultBody save(@RequestBody @Validated SysDictionaryItemSaveDTO data);

    /**
     * 修改字典项
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @PostMapping("/sysDictItem/update")
    ResultBody update(@RequestBody SysDictionaryItemUpdateDTO data);

    /**
     * 删除字典项
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @DeleteMapping("/sysDictItem/delete")
    ResultBody delete(@RequestParam(value = "ids[]") List<Long> ids);

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
    @GetMapping("/sysDictItem/findDictionaryItem")
    ResultBody findDictionaryItem(@RequestParam(value = "codes[]") Set<Serializable> codes);

    @GetMapping("/sysDictItem/list")
    ResultBody list(@RequestParam(value = "codes[]") String[] codes) ;
}
