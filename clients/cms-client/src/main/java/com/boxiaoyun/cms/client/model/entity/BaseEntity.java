package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class BaseEntity implements Serializable {

    /**
     * 添加时间
     */
    private Timestamp addTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 版本号
     */
    private int version;

    /**
     * 创建者
     */
    private Integer creator;
}
