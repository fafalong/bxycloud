package com.boxiaoyun.common.exception;

/**
 * 签名异常
 *
 * @author admin
 */
public class BaseSignatureException extends BaseException {
    private static final long serialVersionUID = 4908906410210213271L;

    public BaseSignatureException() {
    }

    public BaseSignatureException(String msg) {
        super(msg);
    }

    public BaseSignatureException(int code, String msg) {
        super(code, msg);
    }

    public BaseSignatureException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
