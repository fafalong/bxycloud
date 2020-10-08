package com.boxiaoyun.system.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SysRoleGrantSaveDTO", description = "分配角色权限")
public class SysRoleGrantSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;



    /**
     * 名称
     */
    @ApiModelProperty(value = "角色Id")
    private Long roleId;

    /**
     * 过期时间.选填
     */
    @ApiModelProperty(value = "过期时间.选填")
    private Date expireTime;

    /**
     * 已选权限，用，分隔
     */
    @ApiModelProperty(value = "已选权限，用，分隔")
    private String authorityIds;
}
