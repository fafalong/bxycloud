package com.boxiaoyun.system.client.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 实体类
 * 岗位
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
@ApiModel(value = "SysStationPageDTO", description = "岗位")
public class SysStationPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @Length(max = 255, message = "名称长度不能超过255")
    private String name;
    /**
     * 组织ID
     * #c_core_org
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    // 前端传递 过来的参数，key会变成 String 类型
//    private RemoteData<Long, Org> orgId;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Boolean status;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "描述长度不能超过255")
    private String stationDesc;

    @ApiModelProperty(value = "开始创建时间")
    private Date startCreateTime;
    @ApiModelProperty(value = "截止创建时间")
    private Date endCreateTime;


}
