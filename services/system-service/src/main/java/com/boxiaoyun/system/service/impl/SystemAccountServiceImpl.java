package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.entity.SystemAccount;
import com.boxiaoyun.system.mapper.SystemAccountMapper;
import com.boxiaoyun.system.service.SystemAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 通用账号
 *
 * @author
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemAccountServiceImpl extends BaseServiceImpl<SystemAccountMapper, SystemAccount> implements SystemAccountService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 获取账号信息
     *
     * @param account
     * @param accountType
     * @param domain
     * @return
     */
    @Override
    public SystemAccount get(String account, String accountType, String domain) {
        QueryWrapper<SystemAccount> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemAccount::getAccount, account)
                .eq(SystemAccount::getAccountType, accountType)
                .eq(SystemAccount::getDomain, domain);
        return getOne(queryWrapper);

    }

    /**
     * 注册账号
     *
     * @param userId
     * @param account
     * @param password
     * @param accountType
     * @param status
     * @param domain
     * @param registerIp
     * @return
     */
    @Override
    public SystemAccount register(Long userId, String account, String password, String accountType, Integer status, String domain, String registerIp) {
        if (isExist(account, accountType, domain)) {
            // 账号已被注册
            throw new RuntimeException(String.format("account=[%s],domain=[%s]", account, domain));
        }
        //加密
        String encodePassword = passwordEncoder.encode(password);
        SystemAccount systemAccount = new SystemAccount(userId, account, encodePassword, accountType, domain, registerIp);
        systemAccount.setCreateTime(new Date());
        systemAccount.setUpdateTime(systemAccount.getCreateTime());
        systemAccount.setStatus(status);
        save(systemAccount);
        return systemAccount;
    }


    /**
     * 检测账号是否存在
     *
     * @param account
     * @param accountType
     * @param domain
     * @return
     */
    @Override
    public Boolean isExist(String account, String accountType, String domain) {
        QueryWrapper<SystemAccount> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SystemAccount::getAccount, account)
                .eq(SystemAccount::getAccountType, accountType)
                .eq(SystemAccount::getDomain, domain);
        int count = count(queryWrapper);
        return count > 0 ? true : false;
    }


    /**
     * 更新账号状态
     *
     * @param accountId
     * @param status
     */
    @Override
    public Boolean updateStatus(Long accountId, Integer status) {
        SystemAccount systemAccount = new SystemAccount();
        systemAccount.setAccountId(accountId);
        systemAccount.setUpdateTime(new Date());
        systemAccount.setStatus(status);
        return updateById(systemAccount);
    }

    /**
     * 根据用户更新账户状态
     *
     * @param userId
     * @param domain
     * @param status
     */
    @Override
    public Boolean updateStatusByUserId(Long userId, String domain, Integer status) {
        if (status == null) {
            return false;
        }
        SystemAccount systemAccount = new SystemAccount();
        systemAccount.setUpdateTime(new Date());
        systemAccount.setStatus(status);
        QueryWrapper<SystemAccount> wrapper = new QueryWrapper();
        wrapper.lambda()
                .eq(SystemAccount::getDomain, domain)
                .eq(SystemAccount::getUserId, userId);
        return update(systemAccount, wrapper);
    }

    /**
     * 重置用户密码
     *
     * @param userId
     * @param domain
     * @param password
     */
    @Override
    public Boolean updatePasswordByUserId(Long userId, String domain, String password) {
        SystemAccount systemAccount = new SystemAccount();
        systemAccount.setUpdateTime(new Date());
        systemAccount.setPassword(passwordEncoder.encode(password));
        QueryWrapper<SystemAccount> wrapper = new QueryWrapper();
        wrapper.lambda()
                .in(SystemAccount::getAccountType, SystemConstants.ACCOUNT_TYPE_USERNAME, SystemConstants.ACCOUNT_TYPE_EMAIL, SystemConstants.ACCOUNT_TYPE_MOBILE)
                .eq(SystemAccount::getUserId, userId)
                .eq(SystemAccount::getDomain, domain);
        return update(systemAccount, wrapper);
    }

    /**
     * 根据用户ID删除账号
     *
     * @param userId
     * @param domain
     * @return
     */
    @Override
    public Boolean removeByUserId(Long userId, String domain) {
        QueryWrapper<SystemAccount> wrapper = new QueryWrapper();
        wrapper.lambda()
                .eq(SystemAccount::getUserId, userId)
                .eq(SystemAccount::getDomain, domain);
        return remove(wrapper);
    }
}
