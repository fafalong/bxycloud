package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.hysj.cms.client.model.entity.BaseEntity;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "hy_cms_announcement")//公告表
public class Announcement  extends BaseEntity {

    private Long id;

    /**
     * 标题
     */
    private String title;


    /**
     * 排序号
     */
    private int orders;

    /**
     * 站点ID
     */
    private Integer site_id;

    /**
     * 文章状态，枚举,默认发布状态
     */
    private String enable ;

   
    /**
     * 公告内容
     */
    private String content;

    /**
     * 开始时间
     */
    private Date start_date;

    /**
     * 截止时间
     */
    private Date expire_date;

}
