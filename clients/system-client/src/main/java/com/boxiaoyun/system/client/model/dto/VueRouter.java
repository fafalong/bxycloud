package com.boxiaoyun.system.client.model.dto;


import com.boxiaoyun.common.utils.TreeEntity;
import com.boxiaoyun.system.client.model.AuthorityAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 构建 Vue路由
 *
 * @author bxy
 * @date 2019-10-20 15:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouter extends TreeEntity<VueRouter, Long> {
//public class VueRouter implements ITreeNode<VueRouter, Long>, Serializable {

    private static final long serialVersionUID = -3327478146308500708L;

/*
    @Override
    //@JsonIgnore
    @ApiModelProperty(value = "菜单ID")
    public Long getMenuId() {
        return this.menuId;
    }
*/
    @ApiModelProperty(value = "权限")
    public List<AuthorityAction> actionList;

    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID")
    private Long authorityId;

    /**
     *
     */
    @ApiModelProperty(value = "权限标识")
    private String authority;

    @Override
    //@JsonIgnore
    @ApiModelProperty(value = "父ID")
    public Long getParentId() {
        return this.parentId;
    }

    //private Long menuId;
    //@ApiModelProperty(value = "菜单名称")
    //protected String label=super.menuName;

    @ApiModelProperty(value = "菜单名")
    public String getLabel() {
        return super.label;
    }

    @ApiModelProperty(value = "路径")
    private String path;

//    @ApiModelProperty(value = "菜单名称")
//    private String menuName;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "重定向")
    private String redirect;

    @ApiModelProperty(value = "元数据")
    private RouterMeta meta;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden = false;

    @ApiModelProperty(value = "总是显示")
    private Boolean alwaysShow = false;

    @ApiModelProperty(value = "菜单描述")
    private String menuDesc;

    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 服务ID
     */
    @ApiModelProperty(value = "服务ID")
    private String serviceId;

//    @ApiModelProperty(value = "子路由")
//    private List<VueRouter> children;
//
//    @Override
//    public List<VueRouter> getChildren() {
//        return this.children;
//    }
public Boolean getHidden() {
    return false;
}
    public RouterMeta getMeta() {
        meta = new RouterMeta();
        if(meta!=null){
            meta.setTitle(super.label);
            meta.setIcon(this.icon);
            meta.setBreadcrumb(true);
        }
        return meta;
    }

    public Boolean getAlwaysShow() {
        return getChildren() != null && !getChildren().isEmpty();
    }

    public String getComponent() {
        if (getChildren() != null && !getChildren().isEmpty()) {
            return "Layout";
        }
        return this.component;
    }
}
