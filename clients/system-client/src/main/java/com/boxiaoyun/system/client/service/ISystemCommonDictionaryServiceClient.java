package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysDictionarySaveDTO;
import com.boxiaoyun.system.client.model.dto.SysDictionaryUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemDictionary;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface ISystemCommonDictionaryServiceClient {

    /**
     * 获取字典列表
     * @param map
     * @return
     */
    @GetMapping("/dict/page")
    ResultBody<Page<SystemDictionary>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 分页查询字典目录
     *
     * @param data 分页查询对象
     * @return 查询结果
     */
    //@GetMapping("/page")
    //ResultBody<Page<Dictionary>> getPage(Dictionary data) ;

    /**
     * 查询字典目录
     *
     * @param id 主键id
     * @return 查询结果
     */
    @GetMapping("/dict/info")
    ResultBody<SystemDictionary> get(@RequestParam(value = "id") Long id);

    /**
     * 新增字典目录
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping("/dict/save")
    ResultBody save(@RequestBody @Validated SysDictionarySaveDTO data);

    /**
     * 修改字典目录
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @PutMapping("/dict/update")
    ResultBody update(@RequestBody @Validated(SuperEntity.Update.class) SysDictionaryUpdateDTO data) ;

    /**
     * 删除字典目录
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @DeleteMapping("/dict/remove")
    ResultBody delete(@RequestParam("ids[]") List<Long> ids) ;
}
