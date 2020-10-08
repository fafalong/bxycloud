package com.boxiaoyun.common.annotation;

import java.lang.annotation.*;

/**
 * table别名
 * @author LYD
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableAlias {
    String value();
}
