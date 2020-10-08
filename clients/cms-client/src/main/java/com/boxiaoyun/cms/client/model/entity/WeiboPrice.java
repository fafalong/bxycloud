package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_person_weibo_price")//指定表名
public class WeiboPrice {
    private Long id;
    private int personId;//  '用户id',
    private String typeName;// '报价名称',
    private int price;// '原创视频+发布',   
    private String expireDate;// '价格有效期',
}
