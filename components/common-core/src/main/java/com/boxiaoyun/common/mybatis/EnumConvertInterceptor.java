package com.boxiaoyun.common.mybatis;

/**
 * @author LYD
 */
public interface EnumConvertInterceptor {
    boolean convert(EntityMap map, String key, Object v);
}
