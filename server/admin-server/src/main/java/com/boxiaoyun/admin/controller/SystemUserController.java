package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemUserServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统用户信息
 *
 * @author
 */
@Api(tags = "系统用户管理")
@RestController
public class SystemUserController {
    @Autowired
    private SystemUserServiceClient systemUserServiceClient;

    /**
     * 系统分页用户列表
     *
     * @return
     */
    @ApiOperation(value = "系统分页用户列表", notes = "系统分页用户列表")
    @GetMapping("/user/page")
    public ResultBody<Page<SystemUser>> getPage(@RequestParam(required = false) Map map) {
        return systemUserServiceClient.getPage(map);
    }

    /**
     * 获取所有用户列表
     *
     * @return
     */
    @ApiOperation(value = "获取所有用户列表", notes = "获取所有用户列表")
    @GetMapping("/user/list")
    public ResultBody<List<SystemUser>> getList() {
        return systemUserServiceClient.getList();
    }

    /**
     * 添加/更新系统用户
     *
     * @param userId
     * @param nickName
     * @param status
     * @param userType
     * @param email
     * @param mobile
     * @param userDesc
     * @param avatar
     * @return
     */
    @ApiOperation(value = "添加/更新系统用户", notes = "添加/更新系统用户")
    @PostMapping("/user/save")
    public ResultBody save(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "status") Integer status,
            @RequestParam(value = "userType") String userType,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "userDesc", required = false) String userDesc,
            @RequestParam(value = "avatar", required = false) String avatar
    ) {
        SystemUser user = new SystemUser();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setPassword(password);
        user.setUserType(userType);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setUserDesc(userDesc);
        user.setAvatar(avatar);
        user.setStatus(status);
        return systemUserServiceClient.save(user);
    }


    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     * @return
     */
    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @PostMapping("/user/update/password")
    public ResultBody updatePassword(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "password") String password
    ) {
        return systemUserServiceClient.updatePassword(userId, password);
    }

    /**
     * 用户分配角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @ApiOperation(value = "用户分配角色", notes = "用户分配角色")
    @PostMapping("/user/roles/save")
    public ResultBody saveUserRoles(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "roleIds", required = false) String roleIds
    ) {
        return systemUserServiceClient.saveRoles(userId, roleIds);
    }

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户已分配角色", notes = "获取用户已分配角色")
    @GetMapping("/user/roles")
    public ResultBody<List<SystemRole>> getUserRoles(
            @RequestParam(value = "userId") Long userId
    ) {
        return systemUserServiceClient.getUserRoles(userId);
    }
}
