package com.boxiaoyun.system.service;

import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemAccount;

/**
 * 系统用户登录账号管理
 * 支持多账号登陆
 *
 * @author
 */
public interface SystemAccountService extends IBaseService<SystemAccount> {

    /**
     * 获取账号信息
     *
     * @param account
     * @param accountType
     * @param domain
     * @return
     */
    SystemAccount get(String account, String accountType, String domain);


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
    SystemAccount register(Long userId, String account, String password, String accountType, Integer status, String domain, String registerIp);


    /**
     * 检查账号是否存在
     *
     * @param account
     * @param accountType
     * @param domain
     * @return
     */
    Boolean isExist(String account, String accountType, String domain);


    /**
     * 更新账号状态
     *
     * @param accountId
     * @param status
     * @return
     */
    Boolean updateStatus(Long accountId, Integer status);

    /**
     * 根据用户更新账户状态
     *
     * @param userId
     * @param domain
     * @param status
     * @return
     */
    Boolean updateStatusByUserId(Long userId, String domain, Integer status);

    /**
     * 重置用户密码
     *
     * @param userId
     * @param domain
     * @param password
     * @return
     */
    Boolean updatePasswordByUserId(Long userId, String domain, String password);

    /**
     * 根据用户ID删除账号
     *
     * @param userId
     * @param domain
     * @return
     */
    Boolean removeByUserId(Long userId, String domain);
}
