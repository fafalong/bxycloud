package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_person_mediaprice")//指定表名
public class MediaPrice {
    private Long id;
    private Long personId;//  '用户id',
    private String typeName;// '报价名称',
    private int originalVideo;// '原创视频+发布',
    //private int pubVideo ;// '视频发布',
    //private int liveCct ;// '活动现场直播',
    //private int liveInAd ;//'直播广告植入',
    private String expireDate;// '价格有效期',
    private int updateRate;//  '更新频率',
    private int pubPrice; //发布价格
    private String status;
}
