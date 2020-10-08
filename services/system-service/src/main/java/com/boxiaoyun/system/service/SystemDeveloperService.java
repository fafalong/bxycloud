package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.UserInfo;
import com.boxiaoyun.system.client.model.entity.SystemDeveloper;

import java.util.List;

/**
 * 系统用户资料管理
 *
 * @author:
 * @date: 2018/10/24 16:38
 * @description:
 */
public interface SystemDeveloperService extends IBaseService<SystemDeveloper> {

    /**
     * 添加用户信息
     * @param systemDeveloper
     * @return
     */
    void add(SystemDeveloper systemDeveloper);

    /**
     * 更新系统用户
     *
     * @param systemDeveloper
     * @return
     */
    void update(SystemDeveloper systemDeveloper);

    /**
     * 添加第三方登录用户
     *
     * @param systemDeveloper
     * @param accountType
     * @param
     */
    void addThirdParty(SystemDeveloper systemDeveloper, String accountType);

    /**
     * 更新密码
     *
     * @param userId
     * @param password
     */
    void updatePassword(Long userId, String password);

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SystemDeveloper> findPage(PageParams pageParams);

    /**
     * 查询列表
     *
     * @return
     */
    List<SystemDeveloper> findList();

    /**
     * 依据登录名查询系统用户信息
     *
     * @param username
     * @return
     */
    SystemDeveloper getByUsername(String username);


    /**
     * 支持密码、手机号、email登陆
     * 其他方式没有规则，无法自动识别。需要单独开发
     *
     * @param account 登陆账号
     * @param thirdParty
     * @return
     */
    UserInfo login(String account, String thirdParty);
}
