package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName(value = "hy_cms_member")//指定表名
public class Member extends BaseEntity {

    private String memberType;
    private Long id;
    private Long uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 男女
     */
    private String gender;

    /**
     * 个人头像
     */
    private String avatar;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 个人简介
     */
    private String summary;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 登陆次数
     */
    private int loginCount;

    /**
     * 生日
     */
    private Date birthday;
    /**
     * 最后登录时间
     */
    private String lastLoginTime;
    /**
     * 最后登录ip
     */
    private String lastLoginIp;
    /**
     * 状态 0正常，1禁止登录
     */
    private int status;
    /**
     * 创建时间
     */
    //private String createTime;
    /**
     * 更新时间
     */
    //private String updateTime;




}
