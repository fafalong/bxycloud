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
@ApiModel(value = "SysAuthorityGrantSaveDTO", description = "分配接口权限")
public class SysAuthorityGrantSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;



    /**
     * 名称
     */
    @ApiModelProperty(value = "资源Id")
    private Long actionId;

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
