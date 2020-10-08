package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.hysj.cms.client.model.entity.BaseEntity;
import java.util.List;

@Data
@TableName(value = "hy_cms_article")//指定表名
public class Article  extends BaseEntity {

    private Long id;


    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 文章来源
     */
    private String articleSource;

    /**
     * 文章来源链接
     */
    private String articleSourceLink;

    /**
     * 封面图
     */
    private String frontCover;

    /**
     * 关键字，用于SEO
     */
    private String keywords;

    /**
     * 描述，摘要，用于SEO和列表摘要
     */
    private String digest;

    /**
     * 文章图片
     */
    private String imgsrc;

    /**
     * 类型 doc,special
     */
    private String articleType;

    /**
     * 点击量
     */
    private int hits;

    /**
     * 排序号
     */
    private int orders;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 文章状态，枚举,默认发布状态
     */
    private String status ;

    /**
     * 是否允许评论
     */
    private String allowComment;

    /**
     * 是否删除，0未删除，1已删除
     */
    private String deleted;

    /**
     * 评论数量
     */
    private int commentNum;

    /**
     * 喜欢数量
     */
    private int likeNum;


    /**
     * 文章内容，双向关联
     */
    private List<ArticleData> articleData;

    /**
     * 栏目
     */
    //private Category category;

    /**
     * 文章模版Id
     */
    private int categoryId;

    /**
     * 频道Id
     */
    private int channelId;

    /**
     * 模板Id
     */
    private int templateId;

    /**
     * 文章标签
     */
    //private List<Tag> tags;

}
