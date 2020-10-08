package com.boxiaoyun.common.mybatis;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.util.HashMap;

/**
 * 自定义Map
 * @author LYD
 */
public class EntityMap extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    public static EnumConvertInterceptor interceptors = null;
   // private RedisUtil redisUtils = SpringContextHolder.getBean("redisUtils");
    //private Map<Object, Object> dataMaps = redisUtils.getMap("DICTDATA_MAPS");

    public EntityMap() {

    }

    private static String getField(String field) {
        String str = "";
        int s = field.indexOf(".");
        if (s > -1) {
            str = field.substring(s + 1);
        } else {
            str = field;
        }
        return str;
    }

    public static void setEnumConvertInterceptor(EnumConvertInterceptor interceptor) {
        interceptors = interceptor;
    }


    public EntityMap put(String key, Object value) {
        if (ObjectUtils.isNotEmpty(interceptors)) {
            interceptors.convert(this, key, value);
        }
        if (ObjectUtils.isNotNull(value)) {
            super.put(key, value);
        } else {
            super.put(key, "");
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        T t = null;
        Object obj = super.get(key);
        if (ObjectUtils.isNotEmpty(obj)) {
            t = (T) obj;
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T def) {
        Object obj = super.get(key);
        if (ObjectUtils.isNotEmpty(obj)) {
            return (T) obj;
        } else {
            return def;
        }
    }

}
