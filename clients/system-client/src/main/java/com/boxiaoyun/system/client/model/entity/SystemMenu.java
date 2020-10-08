package com.boxiaoyun.system.client.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.utils.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 菜单
 * </p>
 *
 * @author bxy
 * @since 2019-11-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("system_menu")
@ApiModel(value = "SystemMenu", description = "菜单")
public class SystemMenu extends TreeEntity<SystemMenu, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 路由ID
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 菜单编码
     */
    private String menuCode;
    /**
     * 功能描述
     */
    @ApiModelProperty(value = "功能描述")
    @Length(max = 200, message = "功能描述长度不能超过200")
    @TableField(value = "menu_desc", condition = LIKE)
    private String menuDesc;

    /**
     * 是否公开菜单
     * 就是无需分配就可以访问的。所有人可见
     */
    @ApiModelProperty(value = "是否公开菜单")
    @TableField("is_public")
    private String isPublic;

    /**
     * 对应路由path
     */
    @ApiModelProperty(value = "对应路由path")
    @Length(max = 255, message = "对应路由path长度不能超过255")
    @TableField(value = "path", condition = LIKE)
    private String path;

    /**
     * 对应路由组件component
     */
    @ApiModelProperty(value = "对应路由组件component")
    @Length(max = 255, message = "对应路由组件component长度不能超过255")
    @TableField(value = "component", condition = LIKE)
    private String component;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用 状态:0-无效 1-有效")
    @TableField("status")
    private Integer status;

    /**
     * 优先级 越小越靠前
     */
    private Integer priority;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    @Length(max = 255, message = "菜单图标长度不能超过255")
    @TableField(value = "icon", condition = LIKE)
    private String icon;

    /**
     * 菜单分组
     */
    @ApiModelProperty(value = "菜单分组")
    @Length(max = 20, message = "菜单分组长度不能超过20")
    @TableField(value = "group_", condition = LIKE)
    private String group;
    /**
     * 请求协议:/,http://,https://
     */
    private String scheme;

    /**
     * 打开方式:_self窗口内,_blank新窗口
     */
    private String target;

    /**
     * 默认数据0-否 1-是 禁止删除
     */
    private Integer isPersist;

    /**
     * 服务ID
     */
    private String serviceId;

    @Builder
    public SystemMenu(Long id, Long createUser, Date createTime, Long updateUser, Date updateTime,
                      String label, String role_desc, String path, String component,
                      Integer status, Integer priority, String icon, String group, Long parentId) {
        this.id = id;
        //this.createUser = createUser;
        this.createTime = createTime;
        //this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.label = label;
        this.menuDesc = role_desc;
        //this.isPublic = isPublic;
        this.path = path;
        this.component = component;
        this.status = status;
        this.priority = priority;
        this.icon = icon;
        this.group = group;
        this.parentId = parentId;
    }

}

