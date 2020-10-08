package com.boxiaoyun.common.mybatis;

import com.alibaba.fastjson.annotation.JSONType;
import com.boxiaoyun.common.utils.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 角色
 * </p>
 *
 * @author bxy
 * @date 2019-07-21
 */
@Getter
@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
//@ApiModel(value = "DataScopeType", description = "数据权限类型-枚举")
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//@JSONType(serializeEnumAsJavaBean = true)
//@JsonDeserialize(using = class)
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DataScopeType implements BaseEnum,Serializable {

    /**
     * ALL=1全部
     */
    ALL(1, "全部"),
    /**
     * THIS_LEVEL=2本级
     */
    THIS_LEVEL(2, "本级"),
    /**
     * THIS_LEVEL_CHILDREN=3本级以及子级
     */
    THIS_LEVEL_CHILDREN(3, "本级以及子级"),
    /**
     * CUSTOMIZE=4自定义
     */
    CUSTOMIZE(4, "自定义"),
    /**
     * SELF=5个人
     */
    SELF(5, "个人"),
    ;

    @ApiModelProperty(value = "描述")
    private int val;

    private String desc;

    //private String code;

    public static DataScopeType match(String val, DataScopeType def) {
        for (DataScopeType enm : DataScopeType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DataScopeType match(Integer val, DataScopeType def) {
        if (val == null) {
            return def;
        }
        for (DataScopeType enm : DataScopeType.values()) {
            if (val.equals(enm.getVal())) {
                return enm;
            }
        }
        return def;
    }

    public static DataScopeType get(String val) {
        return match(val, null);
    }

    public static DataScopeType get(Integer val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DataScopeType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "ALL,THIS_LEVEL,THIS_LEVEL_CHILDREN,CUSTOMIZE,SELF", example = "ALL")
    public String getCode() {
        return this.name();
    }

}
