package com.boxiaoyun.common.exception;

/**
 * 提示消息异常
 *
 * @author admin
 */
public class BaseFeginException extends BaseException {
    private static final long serialVersionUID = 4908906410210213271L;

    public BaseFeginException() {
    }

    public BaseFeginException(String msg) {
        super(msg);
    }

    public BaseFeginException(int code, String msg) {
        super(code, msg);
    }

    public BaseFeginException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
