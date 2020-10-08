package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_tag")//指定表名
public class Tag {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 站点ID
     */
    private Integer siteId;
}
