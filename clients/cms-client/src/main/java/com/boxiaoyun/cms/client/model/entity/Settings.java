package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_settings")//指定表名
public class Settings {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 标题
     */
    private String settingKey;

    /**
     * 封面图
     */
    private String settingValue;

    /**
     * 描述，用于功能介绍
     */
    private String description;


}
