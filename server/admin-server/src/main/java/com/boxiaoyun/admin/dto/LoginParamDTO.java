package com.boxiaoyun.admin.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * 登录参数
 *
 * @author bxy
 * @date 2020年01月05日22:18:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "LoginParamDTO", description = "登录参数")
public class LoginParamDTO {

    @ApiModelProperty(value = "验证码KEY")
    @NotEmpty(message = "验证码KEY不能为空")
    private String key;

    @ApiModelProperty(value = "验证码")
    @NotEmpty(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(value = "企业编号")
    private String tenant;

    @ApiModelProperty(value = "手机号")
    //@NotEmpty(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    //@NotEmpty(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "账号")
    //@NotEmpty(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    private String password;
}
