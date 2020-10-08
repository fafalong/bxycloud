package com.boxiaoyun.admin.controller;

import com.boxiaoyun.admin.dto.SysUserInfoDTO;
import com.boxiaoyun.admin.service.feign.SystemAuthorityServiceClient;
import com.boxiaoyun.admin.service.feign.SystemUserServiceClient;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.common.security.BaseUserDetails;
import com.boxiaoyun.system.client.model.AuthorityMenu;
import com.boxiaoyun.system.client.model.dto.VueRouter;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:
 * @date: 2019/5/24 13:31
 * @description:
 */
@Api(tags = "当前登陆用户")
@RestController
public class CurrentUserController {

    @Autowired
    private SystemUserServiceClient systemUserServiceClient;
    @Autowired
    private SystemAuthorityServiceClient systemAuthorityServiceClient;
    @Autowired
    private RedisTokenStore redisTokenStore;


    /**
     * 获取当前登录用户基本信息
     *
     * @param nickName
     * @param userDesc
     * @param avatar
     * @return
     */
/*    @ApiOperation(value = "修改当前登录用户基本信息", notes = "修改当前登录用户基本信息")
    @GetMapping("/current/user")
    public ResultBody getCurrentUserInfo() {
        BaseUserDetails baseUserDetails = SecurityHelper.getUser();
        SysUserInfoDTO sysUserInfoDTO=new SysUserInfoDTO();
        //sysUserInfoDTO.setTenant();
        sysUserInfoDTO.setUserName(baseUserDetails.getUsername());
        sysUserInfoDTO.setNickName(baseUserDetails.getNickName());
        sysUserInfoDTO.setAvatar(baseUserDetails.getAvatar());
        return ResultBody.ok().data(sysUserInfoDTO);
    }*/

    /**
     * 修改当前登录用户密码
     *
     * @return
     */
    @ApiOperation(value = "修改当前登录用户密码", notes = "修改当前登录用户密码")
    @GetMapping("/current/user/rest/password")
    public ResultBody restPassword(@RequestParam(value = "password") String password) {
        systemUserServiceClient.updatePassword(SecurityHelper.getUser().getUserId(), password);
        return ResultBody.ok();
    }

    /**
     * 修改当前登录用户基本信息
     *
     * @param nickName
     * @param userDesc
     * @param avatar
     * @return
     */
    @ApiOperation(value = "修改当前登录用户基本信息", notes = "修改当前登录用户基本信息")
    @PostMapping("/current/user/update")
    public ResultBody updateUserInfo(
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "userDesc", required = false) String userDesc,
            @RequestParam(value = "avatar", required = false) String avatar
    ) {
        BaseUserDetails baseUserDetails = SecurityHelper.getUser();
        SystemUser user = new SystemUser();
        user.setUserId(baseUserDetails.getUserId());
        user.setNickName(nickName);
        user.setUserDesc(userDesc);
        user.setAvatar(avatar);
        systemUserServiceClient.save(user);
        baseUserDetails.setNickName(nickName);
        baseUserDetails.setAvatar(avatar);
        SecurityHelper.updateUser(redisTokenStore, baseUserDetails);
        return ResultBody.ok();
    }

    /**
     * 获取登陆用户已分配权限
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户已分配菜单权限", notes = "获取当前登录用户已分配菜单权限")
    @GetMapping("/current/user/menu")
    //public ResultBody<List<VueRouter>> getList()
    public ResultBody<List<VueRouter>> findAuthorityMenu() {
    //public ResultBody<List<AuthorityMenu>> findAuthorityMenu() {
        return systemAuthorityServiceClient.findAuthorityMenuByUser(SecurityHelper.getUser().getUserId(), CommonConstants.ROOT.equals(SecurityHelper.getUser().getUsername()));
    }
}
