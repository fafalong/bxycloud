package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.system.client.model.dto.SysAreaSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysAreaUpdateDTO;
import com.boxiaoyun.system.client.model.entity.GatewayAccessLogs;
import com.boxiaoyun.system.client.model.entity.SysArea;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface ISysAreaServiceClient {

    /**
     * 分页查询地区表
     *
     * @param map 分页查询对象
     * @return 查询结果
     */
    @GetMapping("/page")
    public ResultBody<Page<SysArea>> page(@RequestParam Map<String, Object> map);

    /**
     * 查询地区表
     *
     * @param id 主键id
     * @return 查询结果
     */
    @GetMapping("/find")
    public ResultBody<SysArea> get(@RequestParam(value = "id", required = true) Long id) ;

    @GetMapping("/check")
    public ResultBody<Boolean> check(@RequestParam(value = "id",required = false) Long id, @RequestParam(value = "code",required = true) String code);

    /**
     * 新增地区表
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping
    public ResultBody<SysArea> save(@RequestBody @Validated SysAreaSaveDTO data);

    /**
     * 修改地区表
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @PutMapping
    public ResultBody<SysArea> update(@RequestBody @Validated(SuperEntity.Update.class) SysAreaUpdateDTO data) ;

    /**
     * 删除地区表
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @DeleteMapping
    public ResultBody<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * 级联查询地区
     *
     * @param data 级联查询地区
     * @return 查询结果
     */
    @GetMapping
    public ResultBody<List<SysArea>> list(SysArea data);

    /**
     * 查询树形地区
     * <p>
     * 该方法，第一次查询时 性能很差！待优化
     *
     * @return 查询结果
     */
    @GetMapping("/tree")
    public ResultBody<List<SysArea>> tree() ;

}
