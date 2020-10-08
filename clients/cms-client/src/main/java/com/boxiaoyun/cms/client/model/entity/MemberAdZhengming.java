package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_member_ad_zhengming")//指定表名
public class MemberAdZhengming extends BaseEntity{

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 网站名称 video视频
     */
    private String adType;

    private String filepath;

}
