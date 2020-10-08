package com.boxiaoyun.common.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 自定义oauth2异常提示
 * @author
 */
@JsonSerialize(using = BaseOauth2ExceptionSerializer.class)
public class BaseOauth2Exception extends org.springframework.security.oauth2.common.exceptions.OAuth2Exception {
    private static final long serialVersionUID = 4257807899611076101L;

    public BaseOauth2Exception(String msg) {
        super(msg);
    }
}
