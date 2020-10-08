package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName(value = "hy_cms_media_living")//指定表名
public class MediaLiving extends BaseEntity{
    private Long id;

    private Long memberId;// '0非注册会员',
    private String homepage;// VARCHAR(200) COMMENT '账户主页',
    private String avatar ;//VARCHAR(200) COMMENT '账户头像',
    private String personName;// VARCHAR(200) COMMENT '账户名称',
    private int fansNum ;//INT NOT NULL DEFAULT 0 COMMENT '粉丝数',
    private int score;// INT NOT NULL DEFAULT 0 COMMENT '排序，从小到大',
    private String title ;//VARCHAR(200) NOT NULL COMMENT '直播主题',
    private String categoryname;//  VARCHAR(100)  COMMENT '直播类型',
    private String websiteId;//  VARCHAR(100)  COMMENT '直播类型',
    private String accountStatus;// CHAR(1) COMMENT '状态S停止，A正在进行，W即将开始',
    private Timestamp liveTime;// '开始时间',
    private Timestamp addTime;// datetime comment '入库时间'
}
