package com.boxiaoyun.common.constants;

/**
 * 自定义返回码
 *
 * @author admin
 */

public enum ErrorCode {
    /**
     * 成功
     */
    OK(0, "success"),
    FAIL(10000, "fail"),

    SIGNATURE_DENIED(10001, "signature_denied"),
    BAD_CREDENTIALS(10002, "bad_credentials"),
    ACCOUNT_DISABLED(10003, "account_disabled"),
    ACCOUNT_EXPIRED(10004, "account_expired"),
    CREDENTIALS_EXPIRED(10005, "credentials_expired"),
    ACCOUNT_LOCKED(10006, "account_locked"),
    INVALID_TOKEN(10007, "invalid_token"),

    BAD_REQUEST(40000, "bad_request"),
    NOT_FOUND(40004, "not_found"),
    METHOD_NOT_ALLOWED(40005, "method_not_allowed"),
    MEDIA_TYPE_NOT_ACCEPTABLE(40006, "media_type_not_acceptable"),
    TOO_MANY_REQUESTS(40029, "too_many_requests"),

    UNAUTHORIZED(40001, "unauthorized"),
    ACCESS_DENIED(40003, "access_denied"),

    ACCESS_DENIED_BLACK_LIMITED(40031, "access_denied_black_limited"),
    ACCESS_DENIED_WHITE_LIMITED(40032, "access_denied_white_limited"),
    ACCESS_DENIED_AUTHORITY_EXPIRED(40033, "access_denied_authority_expired"),
    ACCESS_DENIED_UPDATING(40034, "access_denied_updating"),
    ACCESS_DENIED_DISABLED(40035, "access_denied_disabled"),

    ERROR(50000, "error"),
    GATEWAY_TIMEOUT(50004, "gateway_timeout"),
    SERVICE_UNAVAILABLE(50003, "service_unavailable"),

    BASE_VALID_PARAM(60000,"base_valid_param");

    private int code;
    private String message;

    ErrorCode() {
    }

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode getResultEnum(int code) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return ERROR;
    }

    public static ErrorCode getResultEnum(String message) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }
        return ERROR;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
