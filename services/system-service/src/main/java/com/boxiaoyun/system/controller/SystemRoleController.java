package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.security.BaseUserDetails;
import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.common.utils.BeanPlusUtil;
import com.boxiaoyun.system.client.model.dto.RoleSaveDTO;
import com.boxiaoyun.system.client.model.dto.UserRoleSaveDTO;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemRoleUser;
import com.boxiaoyun.system.client.service.ISystemRoleServiceClient;
import com.boxiaoyun.system.service.SystemRoleService;
import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Api(tags = "系统角色管理")
@RestController
public class SystemRoleController extends BaseController<SystemRoleService, SystemRole> implements ISystemRoleServiceClient {

    /**
     * 获取角色列表
     *
     * @param map
     * @return
     */
    @GetMapping("/role/page")
    @Override
    public ResultBody<Page<SystemRole>> getPage(@RequestParam Map<String, Object> map) {

        BaseUserDetails userDetails = SecurityHelper.getUser();
        if(userDetails==null) {
            System.out.println("my user id null>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }else{
            System.out.println("my user id >>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDetails.getUserId());
        }
        //return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
        //return ResultBody.success(bizService.findPage(new PageParams(map)));
        //T data = (T) bizService.findPage(new PageParams(map));
        Page<SystemRole> gg= bizService.findPage(new PageParams(map));
List<SystemRole> xx= gg.getRecords();
for(SystemRole one:xx){
    System.out.println(one.getDsType());
}
        //return success(gg);
        //return ResultBody.success(gg);
        return ResultBody.ok().data(gg);
//return success(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("/role/list")
    @Override
    public ResultBody<List<SystemRole>> getList() {
        return ResultBody.success(bizService.findList());
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/role/info")
    @Override
    public ResultBody<SystemRole> get(@RequestParam(value = "roleId") Long roleId) {
        SystemRole result = bizService.getById(roleId);
        return ResultBody.success(result);
    }

    /**
     * 添加/修改角色
     *
     * @param data
     * @return
     */
    @PostMapping("/role/save")
    @Override
    public ResultBody<Long> save(@RequestBody @Validated RoleSaveDTO data) {
        //BaseUserDetails userDetails = SecurityHelper.getUser();
        //System.out.println("my user id >>>>>>>>>>>>>>>>>>>>>>>>>>>"+userDetails.getUserId());
        //SystemRole systemRole = BeanPlusUtil.toBean(data, SystemRole.class);
        //if (data.getRoleId() == null) {
            bizService.add(data.getUserId(),data);
        //} else {
        //    bizService.update(systemRole);
        //}
        return ResultBody.ok();
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @PostMapping("/role/remove")
    @Override
    public ResultBody remove(@RequestParam(value = "roleId") Long roleId) {
        bizService.remove(roleId);
        return ResultBody.ok();
    }

    /**
     * 角色添加成员列表
     *
     * @param roleId
     * @param userIds
     * @return/role/users/save
     */
    @PostMapping("/role/save/users")
    @Override
/*    public ResultBody saveUsers(
            @RequestParam(value = "roleId", required = false) Long roleId,
            @RequestParam(value = "userIds", required = true) String userIds
    ) {*/
    public ResultBody saveUsers(@RequestBody UserRoleSaveDTO userRole) {
        //bizService.saveUsers(roleId, userIds != null ? StringUtils.commaDelimitedListToStringArray(userIds) : null);
        //userRole.getUserIdList().toString()
        bizService.saveUsers(userRole.getRoleId(), userRole.getUserIds());
        return ResultBody.ok();
    }

    /**
     * 获取角色成员列表
     *
     * @param roleId
     * @return
     */
    @GetMapping("/role/users")
    @Override
    public ResultBody<List<SystemRoleUser>> getRoleUsers(@RequestParam(value = "roleId") Long roleId) {
        return ResultBody.success(bizService.findUsersByRoleId(roleId));
    }

}
