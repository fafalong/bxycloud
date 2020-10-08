package com.boxiaoyun.common.utils;

import java.time.LocalDateTime;
import java.util.Collection;

import cn.hutool.core.util.ArrayUtil;
import com.boxiaoyun.common.constants.ErrorCode;
import com.boxiaoyun.common.exception.BaseException;

/**
 * 断言
 *
 * @author bxy
 * @@date 2019-07-22 14:44
 * @see BaseException
 * @since 4.0
 */
public class BizAssert {
    private BizAssert() {
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link BaseException} (<code>null</code>
     *                okay)
     * @
     * @see BaseException
     */
    public static void fail(int code, String message) {
        throw new BaseException(code, message);
    }

    public static void fail(String exceptionCode,String msg) {
/*        if (exceptionCode != null) {
            throw new BaseException(exceptionCode, msg);
        }*/
        fail(ErrorCode.FAIL.getCode(), "参数验证异常");
    }

    /**
     * Fails a test with no message.
     *
     * @
     */
    public static void fail() {
        fail(ErrorCode.FAIL.getCode(), "参数验证异常");
    }

    public static void fail(String message) {
        if (message == null || "".equals(message)) {
            message = "参数验证异常";
        }
        fail(ErrorCode.FAIL.getCode(), message);
    }

    /**
     * 断言条件为真。如果不是，它会抛出一个带有给定消息的异常
     * {@link BaseException}
     *
     * @param exceptionCode 错误码
     * @param condition     被检查的条件
     * @
     */
/*    public static void isTrue(boolean condition, String exceptionCode) {
        if (!condition) {
            fail(exceptionCode);
        }
    }*/

    /**
     * 断言条件为真。如果不是，它会抛出一个参数检测异常
     * {@link BaseException}
     *
     * @param condition 被检查的条件
     * @
     */
    public static void isTrue(boolean condition, String exceptionMessage) {
        if (!condition) {
            fail(exceptionMessage);
        }
    }

    /**
     * 断言条件为真。如果不是，它会抛出一个参数检测异常
     * {@link BaseException}
     *
     * @param condition 被检查的条件
     */
    public static void isTrue(boolean condition) {
        if (!condition) {
            fail();
        }
    }

    /**
     * 断言条件为假。如果不是，它会抛出一个带有给定消息的异常
     * {@link BaseException}
     *
     * @param exceptionCode 错误码
     * @param condition     被检查的条件
     * @
     */
    public static void isFalse(boolean condition, String exceptionCode) {
        if (condition) {
            fail(exceptionCode);
        }
    }

/*    public static void isFalse(boolean condition, String exceptionMessage) {
        if (condition) {
            fail(exceptionMessage);
        }
    }*/


    /**
     * 断言检查这个对象不是 Null。 如果是null，用给定的错误码<code>exceptionCode</code>抛出异常
     * {@link BaseException}
     *
     * @param exceptionCode 错误码
     * @param object        检查对象
     * @
     */
    public static void notNull(Object object, String exceptionCode) {
        if (object == null) {
            fail(exceptionCode);
        }
    }

    public static void notNull(Object object) {
        if (object == null) {
            fail();
        }
    }

/*    public static void notNull(Object object, String message) {
        if (object == null) {
            fail(message);
        }
    }*/

    /**
     * 断言检查这个对象是 Null。 如果不是null，用给定的错误码<code>exceptionCode</code>抛出异常
     * {@link BaseException}
     *
     * @param exceptionCode 错误码
     * @param object        检查对象
     * @
     */
/*    public static void isNull(Object object, String exceptionCode) {
        if (object != null) {
            fail(exceptionCode);
        }
    }*/


    /**
     * 断言集合不为空，如果为null或者empty，用指定错误码抛出异常
     * {@link BaseException}
     *
     * @param exceptionCode 错误码
     * @param collection    集合
     * @
     */
    public static void notEmpty(Collection<?> collection, String exceptionCode) {
        if (collection == null || collection.isEmpty()) {
            fail(exceptionCode);
        }
    }

    public static <T> void notEmpty(T[] array, String exceptionCode) {
        if (ArrayUtil.hasNull(array)) {
            fail(exceptionCode);
        }
    }

    /**
     * 断言字符串不为空，如果为null或者empty，用指定错误码抛出异常
     * {@link BaseException}
     *
     * @param exceptionCode 错误码
     * @param value         字符串
     * @
     */
    public static void notEmpty(String value, String exceptionCode) {
        if (value == null || value.isEmpty()) {
            fail(exceptionCode);
        }
    }

/*    public static void notEmpty(String value, String exceptionMsgs) {
        if (value == null || value.isEmpty()) {
            fail(exceptionMsgs);
        }
    }*/

    public static void notEmpty(String value) {
        if (value == null || value.isEmpty()) {
            fail();
        }
    }

    /**
     * 断言2个对象不是相等的。如果相等则抛出异常
     * {@link BaseException}。
     * 如果<code>unexpected</code> 和 <code>actual</code> 是 <code>null</code>,
     * 他们被认为是相等的。
     *
     * @param exceptionCode 错误码
     * @param unexpected    意想不到的值
     * @param actual        要检查的值 <code>unexpected</code>
     * @
     */
    public static void notEquals(Object unexpected, Object actual, String exceptionCode) {
        if (unexpected == actual) {
            fail(exceptionCode);
        }
        if (unexpected != null && unexpected.equals(actual)) {
            fail(exceptionCode);
        }

    }

    /**
     * 断言2个字符串是否相等，如果不等用指定错误码抛出异常
     * {@link BaseException}
     *
     * @param exceptionCode 错误码
     * @param expected      预期的值
     * @param actual        需要比较的字符串<code>expected</code>
     * @
     */
    public static void equals(String expected, String actual, String exceptionCode) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        fail(exceptionCode);
    }

/*    public static void equals(String expected, String actual, String exceptionMsgs) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        fail(exceptionMsgs);
    }*/

    public static void equals(Object expected, Object actual, String exceptionMsgs) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        fail(exceptionMsgs);
    }


    /**
     * 断言 预期值（expected） 大于 实际值（actual）
     *
     * @param expected      预期值
     * @param actual        实际值
     * @param exceptionMsgs
     */
    public static void gt(LocalDateTime expected, LocalDateTime actual, String exceptionMsgs) {
        if (expected == null || actual == null) {
            fail(exceptionMsgs);
        }

        if (expected.isAfter(actual)) {
            fail(exceptionMsgs);
        }
    }


}
