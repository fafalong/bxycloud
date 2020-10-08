package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_website_link")//指定表名
public class WebsiteLink extends BaseEntity {

    private Long id;

    /**
     * 状态id
     */
    private String statusId;

    /**
     * 链接名
     */
    private String linkName;



    /**
     * 封面图
     */
    private String frontCover;

    /**
     * url
     */
    private String linkUrl;

    /**
     * 打开方式
     */
    private String linkTarget;

    /**
     * 排序
     */
    private int sort;

    /**
     * 站点ID
     */
    private Integer siteId;

}
