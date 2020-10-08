package com.boxiaoyun.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.admin.service.feign.SystemDeveloperServiceClient;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.entity.SystemDeveloper;
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
@Api(tags = "开发者管理")
@RestController
public class SystemDeveloperController {
    @Autowired
    private SystemDeveloperServiceClient systemDeveloperServiceClient;


    /**
     * 系统分页用户列表
     *
     * @return
     */
    @ApiOperation(value = "系统分页用户列表", notes = "系统分页用户列表")
    @GetMapping("/developer")
    public ResultBody<Page<SystemDeveloper>> getPage(@RequestParam(required = false) Map map) {
        return systemDeveloperServiceClient.getPage(map);
    }

    /**
     * 获取所有用户列表
     *
     * @return
     */
    @ApiOperation(value = "获取所有用户列表", notes = "获取所有用户列表")
    @GetMapping("/developer/list")
    public ResultBody<List<SystemDeveloper>> getList() {
        return systemDeveloperServiceClient.getList();
    }

    /**
     * 添加/编辑开发者信息
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
    @ApiOperation(value = "添加/编辑开发者信息", notes = "添加/编辑开发者信息")
    @PostMapping("/developer/save")
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
        SystemDeveloper developer = new SystemDeveloper();
        developer.setUserId(userId);
        developer.setNickName(nickName);
        developer.setUserName(userName);
        developer.setPassword(password);
        developer.setUserType(userType);
        developer.setEmail(email);
        developer.setMobile(mobile);
        developer.setUserDesc(userDesc);
        developer.setAvatar(avatar);
        developer.setStatus(status);
        return systemDeveloperServiceClient.save(developer);
    }


    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     * @return
     */
    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @PostMapping("/developer/update/password")
    public ResultBody updatePassword(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "password") String password
    ) {
        return systemDeveloperServiceClient.updatePassword(userId, password);
    }
}
