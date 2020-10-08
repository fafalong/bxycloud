package com.boxiaoyun.common.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Map;
import java.util.ResourceBundle;
import com.boxiaoyun.common.constants.ErrorCode;

/**
 * @author bxy
 * @createTime 2017-12-13 10:55
 */
@Getter
@Setter
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel"})
@Accessors(chain = true)
public class ResultBody<T> {
    public static final String DEF_ERROR_MESSAGE = "系统繁忙，请稍候再试";
    public static final String HYSTRIX_ERROR_MESSAGE = "请求超时，请稍候再试";
    public static final int SUCCESS_CODE = 0;
    public static final int FAIL_CODE = -1;
    public static final int TIMEOUT_CODE = -2;
    /**
     * 统一参数验证异常
     */
    public static final int VALID_EX_CODE = -9;
    public static final int OPERATION_EX_CODE = -10;
    /**
     * 调用是否成功标识，0：成功，-1:系统繁忙，此时请开发者稍候再试 详情见[ExceptionCode]
     */
    @ApiModelProperty(value = "响应编码:0/200-请求处理成功")
    private int code;

    /**
     * 时候执行默认操作
     */
    @JsonIgnore
    private Boolean defExec = true;

    /**
     * 调用结果
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * http状态码
     */
    private int httpStatus;

    /**
     * 结果消息，如果调用成功，消息通常为空T
     */
    @ApiModelProperty(value = "提示消息")
    private String msg = "ok";

    @ApiModelProperty(value = "请求路径")
    private String path;
    /**
     * 附加数据
     */
    @ApiModelProperty(value = "附加数据")
    private Map<String, Object> extra;

    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间戳")
    private long timestamp = System.currentTimeMillis();

    private ResultBody() {
        super();
    }

    public ResultBody(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.defExec = false;
    }

    public ResultBody(int code, T data, String msg, boolean defExec) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.defExec = defExec;
    }

    public static <E> ResultBody<E> result(int code, E data, String msg) {
        return new ResultBody<>(code, data, msg);
    }
    public T getData() {
        return data;
    }
    public ResultBody data(T data) {
        this.data = data;
        return this;
    }
    /**
     * 请求成功消息
     *
     * @param data 结果
     * @return RPC调用结果
     */
    public static <E> ResultBody<E> success(E data) {
        return new ResultBody<>(SUCCESS_CODE, data, "ok");
    }

/*    public static ResultBody<Boolean> success() {
        return new ResultBody<>(SUCCESS_CODE, true, "ok");
    }*/
    public static ResultBody success() {
        return new ResultBody().code(ErrorCode.OK.getCode());
        //return new ResultBody<>(SUCCESS_CODE, true, "ok");
    }

    public static ResultBody ok() {
        return new ResultBody().code(ErrorCode.OK.getCode());
    }

    public static <E> ResultBody<E> successDef(E data) {
        return new ResultBody<>(SUCCESS_CODE, data, "ok", true);
    }

    public static <E> ResultBody<E> successDef() {
        return new ResultBody<>(SUCCESS_CODE, null, "ok", true);
    }

    /**
     * 请求成功方法 ，data返回值，msg提示信息
     *
     * @param data 结果
     * @param msg  消息
     * @return RPC调用结果
     */
    public static <E> ResultBody<E> success(E data, String msg) {
        return new ResultBody<>(SUCCESS_CODE, data, msg);
    }
    public ResultBody code(int code) {
        this.code = code;
        return this;
    }
    public ResultBody msg(String message) {
        this.msg = message;//i18n(message,message);
        return this;
    }
    public ResultBody path(String path) {
        this.path = path;
        return this;
    }

/*    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    public boolean isOk() {
        return this.code == ErrorCode.OK.getCode();
    }*/
    public static ResultBody fail() {
        return new ResultBody().code(ErrorCode.FAIL.getCode());
    }
    /**
     * 请求失败消息
     *
     * @param msg
     * @return
     */
    public static <E> ResultBody<E> fail(int code, String msg) {
        return new ResultBody<>(code, null, (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg);
    }

    public static <E> ResultBody<E> fail(String msg) {
        return fail(OPERATION_EX_CODE, msg);
    }

    public static <E> ResultBody<E> fail(String msg, Object... args) {
        String message = (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg;
        return new ResultBody<>(OPERATION_EX_CODE, null, String.format(message, args));
    }

/*    public static <E> ResultBody<E> fail(String msg) {
        return validFail(msg);
    }*/

    public static <E> ResultBody<E> fail(Exception exception) {
        if (exception == null) {
            return fail(DEF_ERROR_MESSAGE);
        }
        return null;//new ResultBody<>(exception.getCode(), null, exception.getMessage());
    }

    /**
     * 请求失败消息，根据异常类型，获取不同的提供消息
     *
     * @param throwable 异常
     * @return RPC调用结果
     */
    public static <E> ResultBody<E> fail(Throwable throwable) {
        return fail(FAIL_CODE, throwable != null ? throwable.getMessage() : DEF_ERROR_MESSAGE);
    }

    public static <E> ResultBody<E> validFail(String msg) {
        return new ResultBody<>(VALID_EX_CODE, null, (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg);
    }

    public static <E> ResultBody<E> validFail(String msg, Object... args) {
        String message = (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg;
        return new ResultBody<>(VALID_EX_CODE, null, String.format(message, args));
    }

/*    public static <E> ResultBody<E> validFail(String exceptionCode) {
        return new ResultBody<>(exceptionCode.getCode(), null,
                (exceptionCode.getMsg() == null || exceptionCode.getMsg().isEmpty()) ? DEF_ERROR_MESSAGE : exceptionCode.getMsg());
    }*/

    public static <E> ResultBody<E> timeout() {
        return fail(TIMEOUT_CODE, HYSTRIX_ERROR_MESSAGE);
    }


    public ResultBody<T> put(String key, Object value) {
        if (this.extra == null) {
            this.extra = Maps.newHashMap();
        }
        this.extra.put(key, value);
        return this;
    }

    /**
     * 逻辑处理是否成功
     *
     * @return 是否成功
     */
    public Boolean getIsSuccess() {
        return this.code == SUCCESS_CODE || this.code == 200;
    }

    /**
     * 逻辑处理是否失败
     *
     * @return
     */
    public Boolean getIsError() {
        return !getIsSuccess();
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public ResultBody httpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    public int getHttpStatus() {
        return httpStatus;
    }
    /**
     * 错误信息配置
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("error");

    /**
     * 提示信息国际化
     *
     * @param message
     * @param defaultMessage
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private static String i18n(String message, String defaultMessage) {
        return resourceBundle.containsKey(message)?resourceBundle.getString(message):defaultMessage;
    }

}
