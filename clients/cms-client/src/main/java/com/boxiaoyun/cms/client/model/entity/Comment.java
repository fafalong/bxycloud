package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_comment")//指定表名
public class Comment extends BaseEntity {
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * IP
     */
    private String ip;

    /**
     * 状态
     */
    private String status;

    /**
     * 评论姓名
     */
    private String username;

    /**
     * 父评论ID
     */
    private Long pid;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 站点ID
     */
    private Integer siteId;
}
