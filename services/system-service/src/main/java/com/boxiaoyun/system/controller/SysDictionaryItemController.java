package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.BeanPlusUtil;
import com.boxiaoyun.system.client.model.dto.SysDictionaryItemSaveDTO;
import com.boxiaoyun.system.client.model.dto.SysDictionaryItemUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemDictionaryItem;
import com.boxiaoyun.system.client.service.ISystemCommonDictionaryItemServiceClient;
import com.boxiaoyun.system.service.DictionaryItemService;
import io.swagger.annotations.Api;
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
///@Slf4j
//@Validated
@RestController
//@RequestMapping("/sysDictItem")
@Api(value = "SysDictionaryItem", tags = "字典项")
public class SysDictionaryItemController extends BaseController<DictionaryItemService, SystemDictionaryItem> implements ISystemCommonDictionaryItemServiceClient {//ISysCommonDictionaryItemServiceClient {//

    @Autowired
    private DictionaryItemService dictionaryItemService;

    /**
     * 分页查询字典项
     *
     * @param map 分页查询对象
     * @return 查询结果
     */
    @GetMapping("/sysDictItem/page")
    @Override
    //@SysLog("分页查询字典项")
    public ResultBody<Page<SystemDictionaryItem>> page(@RequestParam(required = false) Map<String, Object> map) {
        return ResultBody.ok().data(dictionaryItemService.findPage(new PageParams(map)));
    }

    /**
     * 查询字典项
     *
     * @param id 主键id
     * @return 查询结果
     */
    @GetMapping("/sysDictItem/get")
    @Override
    public ResultBody<SystemDictionaryItem> get(@RequestParam(value = "id") Long id){
        return ResultBody.success().data(dictionaryItemService.getById(id));
    }

    /**
     * 新增字典项
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping("/sysDictItem/save")
    @Override
    public ResultBody save(@RequestBody @Validated SysDictionaryItemSaveDTO data) {
        SystemDictionaryItem dictionaryItem = BeanPlusUtil.toBean(data, SystemDictionaryItem.class);
        if(dictionaryItemService.save(dictionaryItem)){
            return ResultBody.success();
        }else{
            return ResultBody.fail();
        }

    }

    /**
     * 修改字典项
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @PostMapping("/sysDictItem/update")
    //@SysLog("修改字典项")
    @Override
    public ResultBody update(@RequestBody SysDictionaryItemUpdateDTO data) {
        SystemDictionaryItem dictionaryItem = BeanPlusUtil.toBean(data, SystemDictionaryItem.class);
        if(dictionaryItemService.updateById(dictionaryItem)){
            return ResultBody.success();
        }else{
            return ResultBody.fail();
        }
    }

    /**
     * 删除字典项
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @DeleteMapping("/sysDictItem/delete")
    //@SysLog("删除字典项")
    @Override
    public ResultBody delete(@RequestParam(value = "ids[]") List<Long> ids) {
        if(dictionaryItemService.removeByIds(ids)){
            return ResultBody.success();
        }else{
            return ResultBody.fail();
        }
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
    @GetMapping("/sysDictItem/findDictionaryItem")
    @Override
    public ResultBody findDictionaryItem(@RequestParam(value = "codes[]") Set<Serializable> codes) {
        return ResultBody.success().data(dictionaryItemService.findDictionaryItem(codes));
    }


    @GetMapping("/sysDictItem/list")
    @Override
    public ResultBody<Map<String, Map<String, String>>> list(@RequestParam(value = "codes[]") String[] codes) {
        return ResultBody.success().data(dictionaryItemService.map(codes));
    }


}
