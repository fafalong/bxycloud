package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_person_wx_official")//指定表名
public class WxOfficial {

    private Long id;
    private String officialName;//  varchar(30) not null comment '名称',
    private String accountName;// varchar(30) not null comment '账号',
    private String personDesc;// varchar(100) not null comment '简介',
    private int authenStatus;// int not null comment '1微信认证，',
    private String qrPath ;//varchar(300) not null comment '二维码地址',
    private int fansNum;//   int not null comment '粉丝数',
}
