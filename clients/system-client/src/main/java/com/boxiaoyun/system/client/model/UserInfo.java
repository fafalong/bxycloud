package com.boxiaoyun.system.client.model;

import com.google.common.collect.Lists;
import com.boxiaoyun.system.client.model.entity.SystemAccount;
import com.boxiaoyun.common.security.BaseAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author:
 * @date: 2018/11/12 11:35
 * @description:
 */
public class UserInfo extends SystemAccount implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    private Collection<Map> roles = Lists.newArrayList();
    /**
     * 用户权限
     */
    private Collection<BaseAuthority> authorities = Lists.newArrayList();
    /**
     * 第三方账号
     */
    private String thirdParty;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    public Collection<BaseAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<BaseAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Collection<Map> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Map> roles) {
        this.roles = roles;
    }
}
