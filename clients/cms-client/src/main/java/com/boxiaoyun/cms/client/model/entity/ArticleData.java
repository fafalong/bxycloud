package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "hy_cms_article_data")//指定表名
public class ArticleData extends BaseEntity {

    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 对应的文章实体
     */
    private Long articleId;

    /**
     * 文章目录
     */
    private String tableOfContents;

}
