package com.boxiaoyun.system.client.model.dto;

import com.boxiaoyun.common.utils.SuperEntity;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 参数配置
 * </p>
 *
 * @author lj
 * @since 2020-02-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SysParameterUpdateDTO", description = "系统参数配置")
public class SysParameterUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    //@NotNull(message = "id不能为空")
    private Long id;

    /**
     * 参数键
     */
    @ApiModelProperty(value = "参数键")
    @NotEmpty(message = "参数键不能为空")
    @Length(max = 255, message = "参数键长度不能超过255")
    private String paramKey;
    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    @NotEmpty(message = "参数名称不能为空")
    @Length(max = 255, message = "参数名称长度不能超过255")
    private String name;
    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    @NotEmpty(message = "参数值不能为空")
    @Length(max = 255, message = "参数值长度不能超过255")
    private String paramValue;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 200, message = "描述长度不能超过200")
    private String paramDesc;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Boolean paramStatus;
    /**
     * 只读
     */
    @ApiModelProperty(value = "只读")
    private Boolean paramReadonly;
}
