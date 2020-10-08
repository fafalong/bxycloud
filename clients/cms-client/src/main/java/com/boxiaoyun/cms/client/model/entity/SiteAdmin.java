package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 站点管理员
 */
@Data
@TableName(value = "hy_cms_site_admin")//指定表名
public class SiteAdmin extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 用户ID
     */
    private Integer userId;

}
