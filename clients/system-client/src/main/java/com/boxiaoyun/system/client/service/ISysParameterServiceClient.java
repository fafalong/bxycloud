package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysParameterSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysParameterUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SysParameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lj
 */
public interface ISysParameterServiceClient {
    /**
     * 分页查询参数配置
     *
     * @param map 分页查询对象
     * @return 查询结果
     */
    @GetMapping("/sysParameter/page")
    ResultBody<Page<SysParameter>> page(@RequestParam Map<String, Object> map) ;

    /**
     * 查询参数配置
     *
     * @param id 主键id
     * @return 查询结果
     */
    @GetMapping("/sysParameter/get")
    ResultBody<SysParameter> get(@RequestParam(value = "id") Long id) ;

    /**
     * 新增参数配置
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping("/sysParameter/save")
    ResultBody save(@RequestBody @Validated SysParameterSaveDTO data);

    /**
     * 修改参数配置
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @PutMapping("/sysParameter/update")
    ResultBody update(@RequestBody @Validated(SuperEntity.Update.class) SysParameterUpdateDTO data);

    /**
     * 删除参数配置
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @DeleteMapping("/sysParameter/remove")
    ResultBody delete(@RequestParam("ids[]") List<Long> ids);
}
