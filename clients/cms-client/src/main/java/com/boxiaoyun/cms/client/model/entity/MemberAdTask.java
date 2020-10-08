package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_member_ad_task")//指定表名
public class MemberAdTask  extends BaseEntity{

    
    private int id;//int not null auto_increment primary key,
    private int orderId;// bigint not null comment '广告id',
    private String taskStatus;// char(10) not null comment '执行中running，',
    private int price ;//int not null default 0 comment '价格',
    private int playCount;// int not null default 0 comment '播放次数',
    private int publishCount;// int not null default 0 comment '发布次数',
    private String ipAddr;// varchar(110) comment '执行者ip地址',
 

}
