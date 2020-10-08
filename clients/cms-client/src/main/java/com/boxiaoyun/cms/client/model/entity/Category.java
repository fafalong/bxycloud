package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hysj.cms.client.model.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "hy_cms_category")//指定表名
public class Category extends BaseEntity {

    private Long id;

    /**
     * 父级栏目
     */
    private long pid; //默认是0

    /**
     * 栏目标题
     */
    private String title;

    /**
     * 栏目外链（如果是外部URL）
     */
    private String url;

    /**
     * 目标
     */
    private String target;

    /**
     * 排序
     */
    private int orders;

    /**
     * 封面图
     */
    private String frontCover;

    /**
     * 子栏目
     */
    private List<Category> childCategory;

    /**
     * 关键字，用于SEO
     */
    private String keywords;

    /**
     * 描述，摘要，用于SEO和列表摘要
     */
    private String description;

    /**
     * 文章分类
     */
    private String type;

    /**
     * 分类展示方式
     */
    private String showType;

    /**
     * 是否展示在导航条上
     */
    private String inMenu;

    /**
     * 状态，枚举,默认发布状态
     */
    private String status ;

    /**
     * 模版
     */
    private Template template;

    /**
     * 栏目标签，只用于标签虚拟栏目
     */
    private List<Tag> tags;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 栏目编码，用于导航URL
     */
    private String code;
}
