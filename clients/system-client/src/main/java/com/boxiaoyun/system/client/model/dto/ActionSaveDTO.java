package com.boxiaoyun.system.client.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 组织
 * </p>
 *
 * @author bxy
 * @since 2019-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ActionSaveDTO", description = "菜单功能资源")
public class ActionSaveDTO implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 名称
         */
        @ApiModelProperty(value = "actionId主键")
        private Long actionId;
        /**
         * 简称
         */
        @ApiModelProperty(value = "资源编码")
        @Length(max = 255, message = "资源编码长度不能超过100")
        private String actionCode;

        @ApiModelProperty(value = "资源名称")
        @Length(max = 255, message = "资源名称长度不能超过100")
        private String actionName;

        @ApiModelProperty(value = "资源描述")
        @Length(max = 255, message = "资源描述长度不能超过255")
        private String actionDesc;

        @ApiModelProperty(value = "资源父节点")
        private Long menuId;

        @ApiModelProperty(value = "状态:0-无效 1-有效")
        private Long status;

        @ApiModelProperty(value = "保留数据0-否 1-是 不允许删除")
        private Long isPersist;

        @ApiModelProperty(value = "优先级 越小越靠前")
        private String priority;

        @ApiModelProperty(value = "服务名称")
        @Length(max = 255, message = "服务名称长度不能超过100")
        private String serviceId;


}
