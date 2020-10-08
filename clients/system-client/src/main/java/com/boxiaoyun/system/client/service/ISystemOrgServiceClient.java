package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.dto.OrgSaveDTO;
import com.boxiaoyun.system.client.model.dto.OrgUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemOrg;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ISystemOrgServiceClient {
    /**
     * 获取组织列表
     *
     * @param map
     * @return
     */
    @GetMapping("/sysOrg/page")
    ResultBody<Page<SystemOrg>> getPage(@RequestParam Map<String, Object> map);
    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("/sysOrg/list")
    ResultBody<List<SystemOrg>> getList();

    /**
     * 获取角色详情
     */
    @GetMapping("/sysOrg/info")
    ResultBody<SystemOrg> get(@RequestParam(value = "orgId") Long orgId);

    /**
     * 添加/修改组织
     *
     * @param orgSaveDTO
     * @return
     */
    @ApiOperation(value = "", notes = "")
    @PostMapping("/sysOrg/save")
    ResultBody<Long> save(@RequestBody OrgSaveDTO orgSaveDTO);

    /**
     * 添加/修改组织
     *
     * @param orgUpdateDTO
     * @return
     */
    @ApiOperation(value = "", notes = "")
    @PostMapping("/sysOrg/update")
    ResultBody<Long> update(@RequestBody OrgUpdateDTO orgUpdateDTO);

    /**
     * 删除组织
     *
     * @param orgId
     * @return
     */
    @PostMapping("/sysOrg/remove")
    ResultBody remove(@RequestParam(value = "id") Long orgId);

    /**
     * 组织添加成员列表
     *
     * @param roleId
     * @param userIds
     * @return
     */
 //   @PostMapping("/org/save/users")
//    ResultBody saveUsers(@RequestParam(value = "orgId") Long roleId, @RequestParam(value = "userIds", required = false) String userIds);

    /*
    * 返回组织关系树
    * */
    @GetMapping("/sysOrg/tree")
    public ResultBody<List<SystemOrg>> tree(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "status", required = false) Boolean status);
    /**
     * 获取角色成员列表
     *
     * @param roleId
     * @return
     */
    //@GetMapping("/org/users")
    //ResultBody<List<SystemOrgUser>> getRoleUsers(@RequestParam(value = "roleId") Long roleId);

}
