package com.boxiaoyun.system.client.model.entity;


import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.boxiaoyun.common.utils.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类
 * 角色组织关系
 * </p>
 *
 * @author bxy
 * @since 2019-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("system_role_org")
@ApiModel(value = "SystemRoleOrg", description = "角色组织关系")
public class SystemRoleOrg extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     * #c_auth_role
     */
    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private Long roleId;

    /**
     * 部门ID
     * #c_core_org
     */
    @ApiModelProperty(value = "部门ID")
    @TableField("org_id")
    private Long orgId;


    @Builder
    public SystemRoleOrg(Long id, Date createTime, Long createUser,
                         Long roleId, Long orgId) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.roleId = roleId;
        this.orgId = orgId;
    }

}

