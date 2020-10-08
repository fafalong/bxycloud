package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hysj.cms.client.model.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 专题栏目目录
 */
@Data
@TableName(value = "hy_cms_category_toc")//指定表名
public class CategoryToc extends BaseEntity {

    private Long id;

    /**
     * 父级栏目
     */
    private long pid;

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
     * 子目录
     */
    private List<CategoryToc> childCategoryToc;

    /**
     * 所属栏目
     */
    private Category category;

    /**
     * 关联文章
     */
    private Article article;


    /**
     * 类型
     */
    private String type;

}
