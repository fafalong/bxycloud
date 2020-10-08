package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_website_nav")//指定表名
public class WebsiteNav extends BaseEntity {

    //private Long id;

    /**
     * 状态id
     */
    private String statusId;

    /**
     * 链接名
     */
    private String navName;

    /**
     * 排序
     */
    private int sort;

    /**
     * 站点ID
     */
    private Integer siteId;

}
