package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.UserInfo;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface ISystemUserServiceClient {


    /**
     * 获取账号登录信息
     *
     * @param username
     * @return
     */
    @PostMapping("/user/login")
    ResultBody<UserInfo> login(@RequestParam(value = "username") String username, @RequestParam(value = "thirdParty",required = false)  String thirdParty);

    /**
     * 获取系统用户列表
     *
     * @return
     */
    @GetMapping("/user/page")
    ResultBody<Page<SystemUser>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取用户详情
     * @param userId
     * @return
     */
    @GetMapping("/user/info")
    ResultBody<SystemUser> get(@RequestParam(value = "userId") Long userId);

    /**
     * 获取所有用户列表
     *
     * @return
     */
    @GetMapping("/user/list")
    ResultBody<List<SystemUser>> getList();

    /**
     * 添加/修改系统用户
     *
     * @param systemUser
     * @return
     */
    @PostMapping("/user/save")
    ResultBody<Long> save(@RequestBody SystemUser systemUser);

    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/user/update/password")
    ResultBody updatePassword(@RequestParam(value = "userId") Long userId, @RequestParam(value = "password") String password);

    /**
     * 用户分配角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping("/user/save/roles")
    ResultBody saveRoles(@RequestParam(value = "userId") Long userId, @RequestParam(value = "roleIds", required = false) String roleIds);

    /**
     * 获取用户已分配角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/roles")
    ResultBody<List<SystemRole>> getUserRoles(@RequestParam(value = "userId") Long userId);

    /**
     * 注册第三方系统登录账号
     *
     * @param account
     * @param password
     * @param accountType
     * @param nickName
     * @param avatar
     * @return
     */
    @PostMapping("/user/add/thirdParty")
    ResultBody addThirdParty(
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "accountType") String accountType,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "avatar") String avatar
    );

}
