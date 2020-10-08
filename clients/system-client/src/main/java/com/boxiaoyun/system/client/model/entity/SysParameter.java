package com.boxiaoyun.system.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.mybatis.base.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * <p>
 * 实体类
 * 参数配置
 * </p>
 *
 * @author bxy
 * @since 2020-02-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_common_parameter")
@ApiModel(value = "SysParameter", description = "系统参数配置")
@AllArgsConstructor
public class SysParameter extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;
    /**
     * 参数键
     */
    @ApiModelProperty(value = "参数键")
    @NotEmpty(message = "参数键不能为空")
    @Length(max = 255, message = "参数键长度不能超过255")
    @TableField(value = "param_key")
    private String paramKey;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    @NotEmpty(message = "参数名称不能为空")
    @Length(max = 255, message = "参数名称长度不能超过255")
    @TableField(value = "name")//, condition = LIKE)
    private String name;

    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    @NotEmpty(message = "参数值不能为空")
    @Length(max = 255, message = "参数值长度不能超过255")
    @TableField(value = "param_value")//, condition = LIKE)
    private String paramValue;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 200, message = "描述长度不能超过200")
    @TableField(value = "param_desc")//, condition = LIKE)
    private String paramDesc;

    /**
     * 状态:0-无效 1-有效
     */
    @ApiModelProperty(value = "状态")
    @TableField("param_status")
    private Integer paramStatus;
    /**
     * 只读
     * 0-只读 1-可修改
     */
    @ApiModelProperty(value = "只读")
    @TableField("param_readonly")
    private Integer paramReadonly;


    @Builder
    public SysParameter(Long id, Long createUser, Date createTime, Long updateUser, Date updateTime,
                     String paramKey, String name, String paramValue, String paramDesc, Integer status, Integer paramReadonly) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.paramKey = paramKey;
        this.name = name;
        this.paramValue = paramValue;
        this.paramDesc = paramDesc;
        this.paramStatus = status;
        this.paramReadonly = paramReadonly;
    }

}