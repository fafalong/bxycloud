package com.boxiaoyun.system.client.model;

import com.google.common.collect.Lists;
import com.boxiaoyun.common.security.BaseAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author:
 * @date: 2018/11/12 11:35
 * @description:
 */
public class UserAuthority implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    private Collection<Map> roles = Lists.newArrayList();
    /**
     * 用户权限
     */
    private Collection<BaseAuthority> authorities = Lists.newArrayList();


    public Collection<BaseAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<BaseAuthority> authorities) {
        this.authorities = authorities;
    }

    public Collection<Map> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Map> roles) {
        this.roles = roles;
    }
}
