package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_plugin")//指定表名
public class Plugin extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 插件名字
     */
    private String name;

    /**
     * 插件类型
     */
    private String type;

    /**
     * 插件内容
     */
    private String content;

    /**
     * 是否启用: 1,启用，0，关闭
     */
    private int enable;

    /**
     * 排序号
     */
    private int orders;

    /**
     * 站点ID
     */
    private Integer siteId;

}
