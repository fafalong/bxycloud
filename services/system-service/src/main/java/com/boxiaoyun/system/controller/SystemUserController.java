package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.system.client.model.UserInfo;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import com.boxiaoyun.system.client.service.ISystemUserServiceClient;
import com.boxiaoyun.system.service.SystemRoleService;
import com.boxiaoyun.system.service.SystemUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户信息
 *
 * @author
 */
@Api(tags = "系统用户管理")
@RestController
public class SystemUserController extends BaseController<SystemUserService, SystemUser> implements ISystemUserServiceClient {
    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 获取账号登录信息
     *
     * @param username
     * @return
     */
    @PostMapping("/user/login")
    @Override
    public ResultBody<UserInfo> login(@RequestParam(value = "username") String username, @RequestParam(value = "thirdParty", required = false) String thirdParty) {
        UserInfo account = bizService.login(username, thirdParty);
        return ResultBody.success(account);
    }

    /**
     * 获取系统用户列表
     *
     * @return
     */
    @GetMapping("/user/page")
    @Override
    public ResultBody<Page<SystemUser>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/info")
    @Override
    public ResultBody<SystemUser> get(Long userId) {
        return ResultBody.success(bizService.getById(userId));
    }

    /**
     * 获取所有用户列表
     *
     * @return
     */
    @GetMapping("/user/list")
    @Override
    public ResultBody<List<SystemUser>> getList() {
        return ResultBody.success(bizService.findList());
    }

    /**
     * 添加/修改系统用户
     *
     * @param systemUser
     * @return
     */
    @PostMapping("/user/save")
    @Override
    public ResultBody<Long> save(@RequestBody SystemUser systemUser) {
        if (systemUser.getUserId() == null) {
            bizService.add(systemUser);
        } else {
            bizService.update(systemUser);
        }
        return ResultBody.ok();
    }

    @PostMapping("/user/savetest")
    public ResultBody<Long> savetest() {
        SystemUser systemUser =new SystemUser();
        systemUser.setPassword("123456");
        systemUser.setUserName("admin");
        systemUser.setMobile("13990560675");
        if (systemUser.getUserId() == null) {
            bizService.add(systemUser);
        } else {
            bizService.update(systemUser);
        }
        return ResultBody.ok();
    }
    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/user/update/password")
    @Override
    public ResultBody updatePassword(@RequestParam(value = "userId") Long userId, @RequestParam(value = "password") String password) {
        bizService.updatePassword(userId, password);
        return ResultBody.ok();
    }

    /**
     * 用户分配角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping("/user/save/roles")
    @Override
    public ResultBody saveRoles(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "roleIds", required = false) String roleIds
    ) {
        systemRoleService.saveRoles(userId, roleIds != null ? StringUtils.commaDelimitedListToStringArray(roleIds) : null);
        return ResultBody.ok();
    }

    /**
     * 获取用户已分配角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/roles")
    @Override
    public ResultBody<List<SystemRole>> getUserRoles(@RequestParam(value = "userId") Long userId) {
        return ResultBody.success(systemRoleService.findRolesByUserId(userId));
    }

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
    @Override
    public ResultBody addThirdParty(
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "accountType") String accountType,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "avatar") String avatar
    ) {
        SystemUser user = new SystemUser();
        user.setNickName(nickName);
        user.setUserName(account);
        user.setPassword(password);
        user.setAvatar(avatar);
        bizService.addThirdParty(user, accountType);
        return ResultBody.ok();
    }

}
