package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 站点设置表
 */
@Data
@TableName(value = "hy_cms_site_settings")//指定表名
public class SiteSettings  {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 设置名，如title
     */
    private String name;

    /**
     * 值
     */
    private String value;



}
