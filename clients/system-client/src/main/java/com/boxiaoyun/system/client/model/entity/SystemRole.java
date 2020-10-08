package com.boxiaoyun.system.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.mybatis.DataScopeType;
import com.boxiaoyun.common.mybatis.base.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色-基础信息
 *
 * @author:
 * @date: 2018/10/24 16:21
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("system_role")
@ApiModel(value = "SystemRole", description = "角色")
public class SystemRole extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 5197785628543375591L;
    /**
     * 角色ID
     */
    @TableId(type= IdType.ID_WORKER)
    private Long roleId;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;

    /**
     * 默认数据0-否 1-是 禁止删除
     */
    private Integer isPersist;

    /**
     * 数据权限类型
     * #DataScopeType{ALL:1,全部;THIS_LEVEL:2,本级;THIS_LEVEL_CHILDREN:3,本级以及子级;CUSTOMIZE:4,自定义;SELF:5,个人;}
     */
    private DataScopeType dsType;

    @Builder
    public SystemRole(Long id, Long createUser, Date createTime, Long updateUser, Date updateTime,
                      String name, String code, String roleDesc, Integer status, Integer isPersist, DataScopeType dsType) {
        this.roleId = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        //this.updateTime = updateTime;
        this.name = name;
        this.code = code;
        this.roleDesc = roleDesc;
        this.status = status;
        this.isPersist = isPersist;
        this.dsType = dsType;
    }
}
