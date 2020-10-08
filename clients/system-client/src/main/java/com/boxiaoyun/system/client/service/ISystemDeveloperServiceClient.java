package com.boxiaoyun.system.client.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.system.client.model.UserInfo;
import com.boxiaoyun.system.client.model.entity.SystemDeveloper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface ISystemDeveloperServiceClient {

    /**
     * 获取账号登录信息
     *
     * @param username
     * @return
     */
    @PostMapping("/developer/login")
    ResultBody<UserInfo> login(@RequestParam(value = "username") String username, @RequestParam(value = "thirdParty",required = false)  String thirdParty);

    /**
     * 获取开发者列表
     *
     * @param map
     * @return
     */
    @GetMapping("/developer")
    ResultBody<Page<SystemDeveloper>> getPage(@RequestParam Map<String, Object> map);

    /**
     * 获取开发者列表
     *
     * @return
     */
    @GetMapping("/developer/list")
    ResultBody<List<SystemDeveloper>> getList();


    /**
     * 添加/修改开发者信息
     *
     * @param systemDeveloper
     * @return
     */
    @PostMapping("/developer/save")
    ResultBody<Long> save(@RequestBody SystemDeveloper systemDeveloper);

    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/developer/update/password")
    ResultBody updatePassword(@RequestParam(value = "userId") Long userId, @RequestParam(value = "password") String password);

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
    ResultBody addThirdParty(
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "accountType") String accountType,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "avatar") String avatar
    );

}
