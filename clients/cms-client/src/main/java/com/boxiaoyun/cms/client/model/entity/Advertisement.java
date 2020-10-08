package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_advertisement")//指定表名
public class Advertisement extends BaseEntity {


    private Long id;

    /**
     * 广告位，取自字典
     */
    private String location;

    /**
     * 广告类型，取自字典
     */
    private String type;

    /**
     * 广告名字
     */
    private String name;

    /**
     * 广告代码内容
     */
    private String content;

    /**
     * 广告图片内容
     */
    private String img;

    /**
     * 广告文本
     */
    private String text;

    /**
     * 超链接
     */
    private String url;

    /**
     * 状态
     */
    private int enable;

    /**
     * 点击数
     */
    private int hits;

    /**
     * 站点ID
     */
    private Integer siteId;
}
