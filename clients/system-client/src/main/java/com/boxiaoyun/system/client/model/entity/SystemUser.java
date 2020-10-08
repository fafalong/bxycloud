package com.boxiaoyun.system.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.annotation.TableAlias;
import com.boxiaoyun.common.model.RemoteData;
import com.boxiaoyun.common.mybatis.base.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

/**
 * 系统用户-基础信息
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableAlias("user")
@TableName("system_user")
public class SystemUser extends AbstractEntity {
    private static final long serialVersionUID = -735161640894047414L;
    /**
     * 系统用户ID
     */
    @TableId(type = IdType.ID_WORKER)
    private Long userId;

    /**
     * 登陆名
     */
    private String userName;

    /**
     * 用户类型:super-超级管理员 normal-普通管理员
     */
    private String userType;

    /**
     * 企业ID
     */
    private Long companyId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 描述
     */
    private String userDesc;

    /**
     * 密码
     */
    @JsonIgnore
    @TableField(exist = false)
    private String password;

    /**
     * 状态:0-禁用 1-正常 2-锁定
     */
    private Integer status;


    /**
     * 组织ID
     * #system_org
     *
     * @InjectionField(api = ORG_ID_CLASS, method = ORG_ID_METHOD, beanClass = Org.class) RemoteData<Long, com.github.bxy.authority.entity.core.Org>
     */
    @TableField("org_id")
    private RemoteData<Long, SystemOrg> org;

    /**
     * 岗位ID
     * #system_station
     *
     * @InjectionField(api = STATION_ID_CLASS, method = STATION_ID_NAME_METHOD) RemoteData<Long, String>
     */
    @TableField("station_id")
    private RemoteData<Long, String> station;
    /**
     * 民族
     * nation
     *
     */
    @TableField("nation")
    private String nation;

    /**
     * 学历
     * education
     *
     */
    @TableField("education ")
    private String education ;

    /**
     * 职位状态
     *
     * @InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>
     */
    //@Length(max = 20, message = "职位状态长度不能超过20")
    @TableField(value = "position_status")
    private RemoteData<String, String> positionStatus;

    /**
     * 最后一次输错密码时间
     */
    @TableField("password_error_last_time")
    private Date passwordErrorLastTime;

    /**
     * 密码错误次数
     */
    //@ApiModelProperty(value = "密码错误次数")
    @TableField("password_error_num")
    private Integer passwordErrorNum;

    /**
     * 密码过期时间
     */
    //@ApiModelProperty(value = "密码过期时间")
    @TableField("password_expire_time")
    private Date passwordExpireTime;
    /**
     * 最后登录时间
     */
    //@ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time")
    private Date lastLoginTime;

}
