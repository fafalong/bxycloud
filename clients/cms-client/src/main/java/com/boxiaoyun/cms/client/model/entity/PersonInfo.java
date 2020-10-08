package com.hysj.cms.client.model.entity;

import java.sql.Timestamp;
import java.util.List;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_person_info")//指定表名
public class PersonInfo extends BaseEntity{


    private Long id;
    /**
     * 用户id
     */
    private Long uid;
    private int puid;    //用户登录父id',
    private int  mediaCode;// '媒体代码',
    private String personLogo ;//  '账号头像',
    private String homePage;//主页链接
    private String personName ;// varchar(30) not null comment '名称',
    private String accountId;// varchar(30) not null comment '账号ID',
    private String accountName;// varchar(30) not null comment '账号',
    private String personDesc;// varchar(100) not null comment '简介',
    private int fansNum  ;// int not null comment '粉丝数',
    private int leadNum  ;// int not null comment '影响力指数',
    private int categoryId ;//int not null comment '分类 如：情感心理等',
    private int isSubscribe;//int not null default 1 comment '支持预约',
    private int isOriginal ;//int not null default 1 comment '是否支持原创写稿',
    private int status;// int not null default 0 comment '状态 0正常 ',
    private int area  ;// int comment '地域 省、市两级',
    private Timestamp publishTime ;// datetime  comment '发布日期',
    //private int addTime ;//datetime not null comment '入库日期'
    private String mediaCategory;//video视频，weibo微博，weichat微信
    private String approveStatus;//ini初次填写，check审核中，fail审核失败，run正常
    private int personStatus;//0未接单，1订单执行中
    private String orderStatus;//A可以接单，S停止接单
    private List<Object> item;

}
