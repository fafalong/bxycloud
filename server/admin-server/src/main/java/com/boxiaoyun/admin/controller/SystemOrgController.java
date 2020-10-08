package com.boxiaoyun.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemOrgServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.common.utils.TreeUtil;
import com.boxiaoyun.system.client.model.dto.OrgSaveDTO;
import com.boxiaoyun.system.client.model.dto.OrgUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemOrg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * 组织
 * </p>
 *
 * @author bxy
 * @date 2019-07-22
 */
@Slf4j
@RestController
//@RequestMapping("/org")
@Api(value = "Org", tags = "组织")
public class SystemOrgController {

    @Autowired
    private SystemOrgServiceClient systemOrgServiceClient;

    /**
     * 分页查询组织
     *
     * @param data 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询组织", notes = "分页查询组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/sysOrg/page")
    //@SysLog("分页查询组织")
    public ResultBody<Page<SystemOrg>> page(@RequestParam Map<String, Object> map) {
        return systemOrgServiceClient.getPage(map);
    }

    /**
     * 查询组织
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "查询组织", notes = "查询组织")
    @GetMapping("/sysOrg/info")
    //@SysLog("查询组织")
    public ResultBody<SystemOrg> get(@PathVariable Long orgId) {
        return systemOrgServiceClient.get(orgId);
    }

    /**
     * 新增组织
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @PostMapping("/sysOrg/save")
    @ApiOperation(value = "新增组织", notes = "新增组织不为空的字段")
    public ResultBody<Long> save(@RequestBody @Validated OrgSaveDTO data) {
        return systemOrgServiceClient.save(data);
    }

    /**
     * 修改组织
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改组织", notes = "修改组织不为空的字段")
    @PostMapping("/sysOrg/update")
    //@SysLog("修改组织")
    public ResultBody<Long> update(@RequestBody @Validated(SuperEntity.Update.class) OrgUpdateDTO data) {
        return systemOrgServiceClient.update(data);
    }

    /**
     * 删除组织
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除组织", notes = "根据id物理删除组织")
    //@SysLog("删除组织")
    @PostMapping("/sysOrg/remove")
    public ResultBody<Boolean> delete(Long ids) {
    //public ResultBody<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        return systemOrgServiceClient.remove(ids);
    }

    /**
     * 查询系统所有的组织树
     *
     * @param status 状态
     * @return
     * @author bxy
     * @date 2019-07-29 11:59
     */
    @ApiOperation(value = "查询系统所有的组织树", notes = "查询系统所有的组织树")
    @GetMapping("/sysOrg/tree")
    //@SysLog("查询系统所有的组织树")
    public ResultBody<List<SystemOrg>> tree(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "status", required = false) Boolean status) {
        return systemOrgServiceClient.tree(name,status);
    }

    /**
     * 调用方传递的参数类型是 Set<Serializable> ，但接收方必须指定为Long类型（实体的主键类型），否则在调用mp提供的方法时，会使得mysql出现类型隐式转换问题。
     * 问题如下： select * from org where id in ('100');
     * <p>
     * 强制转换成Long后，sql就能正常执行： select * from org where id in (100);
     *
     * <p>
     * 接口和实现类的类型不一致，但也能调用，归功于 SpingBoot 的自动转换功能
     * {@link com.github.bxy.authority.api.OrgApi#findOrgByIds} 方法的实现类
     *
     * @param codes id
     * @return
     */
/*    @GetMapping("/findOrgByIds")
    public Map<Serializable, Object> findOrgByIds(@RequestParam("ids") Set<Long> ids) {
        return systemOrgServiceClient.findOrgByIds(ids);
    }*/

    /**
     * 调用方传递的参数类型是 Set<Serializable> ，但接收方必须指定为Long类型（实体的主键类型），否则在调用mp提供的方法时，会使得mysql出现类型隐式转换问题。
     * 问题如下： select * from org where id in ('100');
     * <p>
     * 强制转换成Long后，sql就能正常执行： select * from org where id in (100);
     *
     * <p>
     * 接口和实现类的类型不一致，但也能调用，归功于 SpingBoot 的自动转换功能
     * {@link com.github.bxy.authority.api.OrgApi#findUserNameByIds} 方法的实现类
     *
     * @param codes id
     * @return
     */
/*    @GetMapping("/findOrgNameByIds")
    public Map<Serializable, Object> findOrgNameByIds(@RequestParam("ids") Set<Long> ids) {
        return orgService.findOrgNameByIds(ids);
    }*/

}

