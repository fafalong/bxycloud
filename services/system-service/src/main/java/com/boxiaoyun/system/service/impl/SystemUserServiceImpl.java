package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.security.BaseAuthority;
import com.boxiaoyun.common.security.BaseSecurityConstants;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.UserInfo;
import com.boxiaoyun.system.client.model.entity.SystemAccount;
import com.boxiaoyun.system.client.model.entity.SystemRole;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import com.boxiaoyun.system.mapper.SystemUserMapper;
import com.boxiaoyun.system.service.SystemAccountService;
import com.boxiaoyun.system.service.SystemAuthorityService;
import com.boxiaoyun.system.service.SystemRoleService;
import com.boxiaoyun.system.service.SystemUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author:
 * @date: 2018/10/24 16:33
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Autowired
    private SystemRoleService roleService;
    @Autowired
    private SystemAuthorityService systemAuthorityService;
    @Autowired
    private SystemAccountService systemAccountService;

    private final String ACCOUNT_DOMAIN = SystemConstants.ACCOUNT_DOMAIN_ADMIN;

    /**
     * 添加系统用户
     *
     * @param systemUser
     * @return
     */
    @Override
    public void add(SystemUser systemUser) {
        if(getByUsername(systemUser.getUserName())!=null){
            throw new BaseException("用户名:"+ systemUser.getUserName()+"已存在!");
        }
        systemUser.setCreateTime(new Date());
        systemUser.setUpdateTime(systemUser.getCreateTime());
        //保存系统用户信息
        save(systemUser);
        //默认注册用户名账户
        systemAccountService.register(systemUser.getUserId(), systemUser.getUserName(), systemUser.getPassword(), SystemConstants.ACCOUNT_TYPE_USERNAME, systemUser.getStatus(), ACCOUNT_DOMAIN, null);
        if (StringUtil.matchEmail(systemUser.getEmail())) {
            //注册email账号登陆
            systemAccountService.register(systemUser.getUserId(), systemUser.getEmail(), systemUser.getPassword(), SystemConstants.ACCOUNT_TYPE_EMAIL, systemUser.getStatus(), ACCOUNT_DOMAIN, null);
        }
        if (StringUtil.matchMobile(systemUser.getMobile())) {
            //注册手机号账号登陆
            systemAccountService.register(systemUser.getUserId(), systemUser.getMobile(), systemUser.getPassword(), SystemConstants.ACCOUNT_TYPE_MOBILE, systemUser.getStatus(), ACCOUNT_DOMAIN, null);
        }
    }

    /**
     * 更新系统用户
     *
     * @param systemUser
     * @return
     */
    @Override
    public void update(SystemUser systemUser) {
        if (systemUser == null || systemUser.getUserId() == null) {
            return;
        }
        if (systemUser.getStatus() != null) {
            systemAccountService.updateStatusByUserId(systemUser.getUserId(), ACCOUNT_DOMAIN, systemUser.getStatus());
        }
        updateById(systemUser);
    }

    /**
     * 添加第三方登录用户
     *
     * @param systemUser
     * @param accountType
     */
    @Override
    public void addThirdParty(SystemUser systemUser, String accountType) {
        if (!systemAccountService.isExist(systemUser.getUserName(), accountType, ACCOUNT_DOMAIN)) {
            systemUser.setUserType(SystemConstants.USER_TYPE_ADMIN);
            systemUser.setCreateTime(new Date());
            systemUser.setUpdateTime(systemUser.getCreateTime());
            //保存系统用户信息
            save(systemUser);
            // 注册账号信息
            systemAccountService.register(systemUser.getUserId(), systemUser.getUserName(), systemUser.getPassword(), accountType, SystemConstants.ACCOUNT_STATUS_NORMAL, ACCOUNT_DOMAIN, null);
        }
    }

    /**
     * 更新密码
     *
     * @param userId
     * @param password
     */
    @Override
    public void updatePassword(Long userId, String password) {
        systemAccountService.updatePasswordByUserId(userId, ACCOUNT_DOMAIN, password);
    }

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<SystemUser> findPage(PageParams pageParams) {
        SystemUser query = pageParams.mapToBean(SystemUser.class);
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(ObjectUtils.isNotEmpty(query.getUserId()), SystemUser::getUserId, query.getUserId())
                .eq(ObjectUtils.isNotEmpty(query.getUserType()), SystemUser::getUserType, query.getUserType())
                .eq(ObjectUtils.isNotEmpty(query.getUserName()), SystemUser::getUserName, query.getUserName())
                .eq(ObjectUtils.isNotEmpty(query.getMobile()), SystemUser::getMobile, query.getMobile());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<SystemUser> findList() {
        List<SystemUser> list = list(new QueryWrapper<>());
        return list;
    }

    /**
     * 根据用户ID获取用户信息和权限
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo getUserInfo(Long userId) {
        // 用户权限列表
        List<BaseAuthority> authorities = Lists.newArrayList();
        // 用户角色列表
        List<Map> roles = Lists.newArrayList();
        List<SystemRole> rolesList = roleService.findRolesByUserId(userId);
        if (rolesList != null) {
            for (SystemRole role : rolesList) {
                Map roleMap = Maps.newHashMap();
                roleMap.put("roleId", role.getRoleId());
                roleMap.put("code", role.getCode());
                roleMap.put("name", role.getName());
                // 用户角色详情
                roles.add(roleMap);
                // 加入角色标识
                BaseAuthority authority = new BaseAuthority(role.getRoleId().toString(), BaseSecurityConstants.AUTHORITY_PREFIX_ROLE + role.getCode(), null, "role");
                authorities.add(authority);
            }
        }

        //查询系统用户资料
        SystemUser systemUser = getById(userId);

        // 加入用户权限
        List<BaseAuthority> userGrantedAuthority = systemAuthorityService.findAuthorityByUser(userId, CommonConstants.ROOT.equals(systemUser.getUserName()));
        if (userGrantedAuthority != null && userGrantedAuthority.size() > 0) {
            authorities.addAll(userGrantedAuthority);
        }
        UserInfo userInfo = new UserInfo();
        // 昵称
        userInfo.setNickName(systemUser.getNickName());
        // 头像
        userInfo.setAvatar(systemUser.getAvatar());
        // 权限信息
        userInfo.setAuthorities(authorities);
        userInfo.setRoles(roles);
        return userInfo;
    }


    /**
     * 依据登录名查询系统用户信息
     *
     * @param username
     * @return
     */
    @Override
    public SystemUser getByUsername(String username) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemUser::getUserName, username);
        SystemUser saved = getOne(queryWrapper);
        return saved;
    }


    /**
     * 支持系统用户名、手机号、email登陆
     *
     * @param account
     * @return
     */
    @Override
    public UserInfo login(String account,String thirdParty) {
        if (StringUtil.isBlank(account)) {
            return null;
        }
        SystemAccount systemAccount = null;
        if (StringUtil.isNotBlank(thirdParty)) {
            // 第三方登录
            systemAccount = systemAccountService.get(account, thirdParty, ACCOUNT_DOMAIN);
        } else {
            //用户名登录
            systemAccount = systemAccountService.get(account, SystemConstants.ACCOUNT_TYPE_USERNAME, ACCOUNT_DOMAIN);

            // 手机号登陆
            if (StringUtil.matchMobile(account)) {
                systemAccount = systemAccountService.get(account, SystemConstants.ACCOUNT_TYPE_MOBILE, ACCOUNT_DOMAIN);
            }
            // 邮箱登陆
            if (StringUtil.matchEmail(account)) {
                systemAccount = systemAccountService.get(account, SystemConstants.ACCOUNT_TYPE_EMAIL, ACCOUNT_DOMAIN);
            }
        }

        // 获取用户详细信息
        if (systemAccount != null) {
            // 用户权限信息
            UserInfo userInfo = getUserInfo(systemAccount.getUserId());
            // 复制账号信息
            BeanUtils.copyProperties(systemAccount, userInfo);
            return userInfo;
        }
        return null;
    }
}
