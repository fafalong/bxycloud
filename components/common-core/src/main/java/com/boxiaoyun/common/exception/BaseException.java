package com.boxiaoyun.common.exception;

import com.boxiaoyun.common.constants.ErrorCode;

/**
 * 基础错误异常
 *
 * @author admin
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private int code = ErrorCode.FAIL.getCode();

    public BaseException() {

    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
