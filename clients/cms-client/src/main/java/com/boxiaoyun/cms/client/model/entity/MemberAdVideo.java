package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.sql.Timestamp;

@Data
@TableName(value = "hy_cms_member_ad_video")//指定表名
public class MemberAdVideo extends BaseEntity{

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 网站名称 kuaishou，douyin，meitu，meipai
     */
    private String siteName;

    /**
     * 活动名称
     */
    private String actionName;

    /**
     * 活动开始时间
     */
    private Timestamp actionTime;

    /**
     * 是否直播前通知 y,n
     */
    private String preMsg;

    /**
     * 状态 是否启用: 1,启用，0，关闭,-1 已删除
     */
    private int status;

    /**
     * 最晚通知时间
     */
    private Timestamp lastnoteDate;

    /**
     * 视频文件
     */
    private String videoFile;

    /**
     * 视频类别
     */
    private int videoType;

    /**
     * 站点id
     */
    private String videoTitle;

    private String videoDesc;

    private String industryType;
    
    //private Timestamp add_time;

}
