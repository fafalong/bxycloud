package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_channel")//指定表名
public class Channel {

    @TableId(type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 频道名称
     */
    private String showName;

    /**
     * 频道图表
     */
    private String showIcon;

    /**
     * 频道父id
     */
    private int pid;

    /**
     * 频道描述
     */
    private String channelDesc;

    /**
     * 频道顺序
     */
    private String orders;

    /**
     * 频道状态 是否启用: 1,启用，0，关闭,-1 已删除
     */
    private int status;

    /**
     * 打开方式
     */
    private String target;

    /**
     * 频道链接
     */
    private String url;

    /**
     * 频道模板id,
     */
    private int template_id;

    /**
     * 站点id
     */
    private int site_id;

  

}
