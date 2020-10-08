package com.hysj.cms.client.model.entity;

import java.util.List;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_person_media_category")//指定表名
public class PersonMediaCategory extends BaseEntity{


    private Long id;
 
    private int  mediaCode;// '媒体代码',
    private String mediaName ;//  '账号头像',
    private String mediaLogo;//主页链接
    private String mediaUrl ;// varchar(30) not null comment '名称',
    private String mediaDesc;// varchar(30) not null comment '账号ID',
    private int score;// 排序
    

}
