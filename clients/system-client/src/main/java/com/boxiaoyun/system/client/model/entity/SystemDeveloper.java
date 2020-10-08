package com.boxiaoyun.system.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.annotation.TableAlias;
import com.boxiaoyun.common.mybatis.base.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 系统用户-管理员信息
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableAlias("developer")
@TableName("system_developer")
public class SystemDeveloper extends AbstractEntity {
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
     * 开发者类型:isp-服务提供商 dev-自研开发者
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
}
