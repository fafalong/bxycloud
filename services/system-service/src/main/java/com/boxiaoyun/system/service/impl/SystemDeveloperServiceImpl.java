package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.UserInfo;
import com.boxiaoyun.system.client.model.entity.SystemAccount;
import com.boxiaoyun.system.client.model.entity.SystemDeveloper;
import com.boxiaoyun.system.mapper.SystemDeveloperMapper;
import com.boxiaoyun.system.service.SystemAccountService;
import com.boxiaoyun.system.service.SystemDeveloperService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author:
 * @date: 2018/10/24 16:33
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemDeveloperServiceImpl extends BaseServiceImpl<SystemDeveloperMapper, SystemDeveloper> implements SystemDeveloperService {
    @Autowired
    private SystemAccountService systemAccountService;

    private final String ACCOUNT_DOMAIN = SystemConstants.ACCOUNT_DOMAIN_SITE;

    /**
     * 添加系统用户
     *
     * @param systemDeveloper
     * @return
     */
    @Override
    public void add(SystemDeveloper systemDeveloper) {
        if(getByUsername(systemDeveloper.getUserName())!=null){
            throw new BaseException("用户名:"+ systemDeveloper.getUserName()+"已存在!");
        }
        systemDeveloper.setCreateTime(new Date());
        systemDeveloper.setUpdateTime(systemDeveloper.getCreateTime());
        //保存系统用户信息
        save(systemDeveloper);
        //默认注册用户名账户
        systemAccountService.register(systemDeveloper.getUserId(), systemDeveloper.getUserName(), systemDeveloper.getPassword(), SystemConstants.ACCOUNT_TYPE_USERNAME, systemDeveloper.getStatus(), ACCOUNT_DOMAIN, null);
        if (StringUtil.matchEmail(systemDeveloper.getEmail())) {
            //注册email账号登陆
            systemAccountService.register(systemDeveloper.getUserId(), systemDeveloper.getEmail(), systemDeveloper.getPassword(), SystemConstants.ACCOUNT_TYPE_EMAIL, systemDeveloper.getStatus(), ACCOUNT_DOMAIN, null);
        }
        if (StringUtil.matchMobile(systemDeveloper.getMobile())) {
            //注册手机号账号登陆
            systemAccountService.register(systemDeveloper.getUserId(), systemDeveloper.getMobile(), systemDeveloper.getPassword(), SystemConstants.ACCOUNT_TYPE_MOBILE, systemDeveloper.getStatus(), ACCOUNT_DOMAIN, null);
        }
    }

    /**
     * 更新系统用户
     *
     * @param systemDeveloper
     * @return
     */
    @Override
    public void update(SystemDeveloper systemDeveloper) {
        if (systemDeveloper == null || systemDeveloper.getUserId() == null) {
            return;
        }
        if (systemDeveloper.getStatus() != null) {
            systemAccountService.updateStatusByUserId(systemDeveloper.getUserId(), ACCOUNT_DOMAIN, systemDeveloper.getStatus());
        }
       updateById(systemDeveloper);
    }

    /**
     * 添加第三方登录用户
     *
     * @param systemDeveloper
     * @param accountType
     */
    @Override
    public void addThirdParty(SystemDeveloper systemDeveloper, String accountType) {
        if (!systemAccountService.isExist(systemDeveloper.getUserName(), accountType, ACCOUNT_DOMAIN)) {
            systemDeveloper.setUserType(SystemConstants.USER_TYPE_ADMIN);
            systemDeveloper.setCreateTime(new Date());
            systemDeveloper.setUpdateTime(systemDeveloper.getCreateTime());
            //保存系统用户信息
            save(systemDeveloper);
            // 注册账号信息
            systemAccountService.register(systemDeveloper.getUserId(), systemDeveloper.getUserName(), systemDeveloper.getPassword(), accountType, SystemConstants.ACCOUNT_STATUS_NORMAL, ACCOUNT_DOMAIN, null);
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
    public IPage<SystemDeveloper> findPage(PageParams pageParams) {
        SystemDeveloper query = pageParams.mapToBean(SystemDeveloper.class);
        QueryWrapper<SystemDeveloper> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(ObjectUtils.isNotEmpty(query.getUserId()), SystemDeveloper::getUserId, query.getUserId())
                .eq(ObjectUtils.isNotEmpty(query.getUserType()), SystemDeveloper::getUserType, query.getUserType())
                .eq(ObjectUtils.isNotEmpty(query.getUserName()), SystemDeveloper::getUserName, query.getUserName())
                .eq(ObjectUtils.isNotEmpty(query.getMobile()), SystemDeveloper::getMobile, query.getMobile());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<SystemDeveloper> findList() {
        List<SystemDeveloper> list = list(new QueryWrapper<>());
        return list;
    }

    /**
     * 依据登录名查询系统用户信息
     *
     * @param username
     * @return
     */
    @Override
    public SystemDeveloper getByUsername(String username) {
        QueryWrapper<SystemDeveloper> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemDeveloper::getUserName, username);
        SystemDeveloper saved = getOne(queryWrapper);
        return saved;
    }


    /**
     * 支持系统用户名、手机号、email登陆
     *
     * @param account
     * @return
     */
    @Override
    public UserInfo login(String account, String thirdParty) {
        if (StringUtil.isBlank(account)) {
            return null;
        }
        SystemAccount systemAccount = null;
        if (StringUtil.isNotBlank(thirdParty)) {
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
            // 复制账号信息
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfo, systemAccount);
            return userInfo;
        }
        return null;
    }
}
