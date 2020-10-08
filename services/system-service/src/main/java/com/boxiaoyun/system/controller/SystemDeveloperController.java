package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.system.client.model.UserInfo;
import com.boxiaoyun.system.client.model.entity.SystemDeveloper;
import com.boxiaoyun.system.client.service.ISystemDeveloperServiceClient;
import com.boxiaoyun.system.service.SystemDeveloperService;
import io.swagger.annotations.Api;
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
public class SystemDeveloperController extends BaseController<SystemDeveloperService, SystemDeveloper> implements ISystemDeveloperServiceClient {
    /**
     * 获取账号登录信息
     *
     * @param username
     * @return
     */
    @PostMapping("/developer/login")
    @Override
    public ResultBody<UserInfo> login(@RequestParam(value = "username") String username, @RequestParam(value = "thirdParty", required = false) String thirdParty) {
        UserInfo account = bizService.login(username, thirdParty);
        return ResultBody.success(account);
    }

    /**
     * 获取开发者列表
     *
     * @param map
     * @return
     */
    @GetMapping("/developer")
    @Override
    public ResultBody<Page<SystemDeveloper>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }

    /**
     * 获取开发者列表
     *
     * @return
     */
    @GetMapping("/developer/list")
    @Override
    public ResultBody<List<SystemDeveloper>> getList() {
        return ResultBody.success(bizService.findList());
    }

    /**
     * 添加/修改开发者信息
     *
     * @param systemDeveloper
     * @return
     */
    @PostMapping("/developer/save")
    @Override
    public ResultBody<Long> save(@RequestBody SystemDeveloper systemDeveloper) {
        if (systemDeveloper.getUserId() == null) {
            bizService.add(systemDeveloper);
        } else {
            bizService.update(systemDeveloper);
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
    @PostMapping("/developer/update/password")
    @Override
    public ResultBody updatePassword(@RequestParam(value = "userId") Long userId, @RequestParam(value = "password") String password) {
        bizService.updatePassword(userId, password);
        return ResultBody.ok();
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
    @PostMapping("/developer/add/thirdParty")
    @Override
    public ResultBody addThirdParty(
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "accountType") String accountType,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "avatar") String avatar
    ) {
        SystemDeveloper developer = new SystemDeveloper();
        developer.setNickName(nickName);
        developer.setUserName(account);
        developer.setPassword(password);
        developer.setAvatar(avatar);
        bizService.addThirdParty(developer, accountType);
        return ResultBody.ok();
    }

}
