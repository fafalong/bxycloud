package com.boxiaoyun.common.exception;

import com.boxiaoyun.common.constants.ErrorCode;
import com.boxiaoyun.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理器
 *
 * @author LYD
 * @date 2017/7/3
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    private static ThrowableAnalyzer throwableAnalyzer = new ThrowableAnalyzer();

    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    public static ResultBody authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, request.getRequestURI());
        return resultBody;
    }

    /**
     * OAuth2Exception
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OAuth2Exception.class, InvalidTokenException.class})
    public static ResultBody oauth2Exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, request.getRequestURI());
        return resultBody;
    }

    /**
     * 自定义异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({BaseException.class})
    public static ResultBody systemException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, request.getRequestURI());
        return resultBody;
    }

    /**
     * 其他异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({Exception.class})
    public static ResultBody exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, request.getRequestURI());
        return resultBody;
    }

    /**
     * 静态解析异常。可以直接调用
     *
     * @param e
     * @return
     */
    public static ResultBody resolveException(Exception e, String path) {
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorCode code = ErrorCode.getResultEnum(message);
        code.setMessage(message);
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);

        if (ase != null) {
            if ("Bad credentials".contains(message)) {
                code = ErrorCode.BAD_CREDENTIALS;
            } else if ("User is disabled".contains(message)) {
                code = ErrorCode.ACCOUNT_DISABLED;
            } else if ("User account is locked".contains(message)) {
                code = ErrorCode.ACCOUNT_LOCKED;
            }else {
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
            return buildBody(e, code, path, httpStatus);
        }

        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
                causeChain);
        if (ase != null) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            return buildBody(e, code, path, httpStatus);
        }

        ase = (AccessDeniedException) throwableAnalyzer
                .getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase != null && ase instanceof AccessDeniedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            return buildBody(e, code, path, httpStatus);
        }

        ase = (BaseException) throwableAnalyzer
                .getFirstThrowableOfType(BaseException.class, causeChain);
        if (ase != null && ase instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            code.setCode(baseException.getCode());
            code.setMessage(baseException.getMessage());
            return buildBody(e, code, path, httpStatus);
        }

        ase = (BaseFeginException) throwableAnalyzer
                .getFirstThrowableOfType(BaseFeginException.class, causeChain);
        if (ase != null && ase instanceof BaseFeginException) {
            // fegin调用错误
            BaseFeginException feginException = (BaseFeginException) ase;
            code.setCode(feginException.getCode());
            code.setMessage(feginException.getMessage());
            return buildBody(e, code, path, httpStatus);
        }

        ase = (BaseSignatureException) throwableAnalyzer
                .getFirstThrowableOfType(BaseSignatureException.class, causeChain);
        if (ase != null && ase instanceof BaseSignatureException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            code = ErrorCode.SIGNATURE_DENIED;
            return buildBody(e, code, path, httpStatus);
        }

        return buildBody(e, code, path, httpStatus);
    }

    private static ResultBody buildBody(Exception e, ErrorCode errorCode, String path, HttpStatus httpStatus) {
        ResultBody resultBody = ResultBody.fail().code(errorCode.getCode()).msg(errorCode.getMessage()).path(path).httpStatus(httpStatus.value());
        log.error("==>{}", resultBody, e);
        return resultBody;
    }

}
