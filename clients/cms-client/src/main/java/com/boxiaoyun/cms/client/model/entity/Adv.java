package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_adv")//指定表名
public class Adv {

    

    /**
     * 广告名称
     */
    private String advTitle;

    /**
     * 广告图片位置
     */
    private String advImg;

    /**
     * 网站类型
     */
    private String advUrl;

    /**
     * 网站logo路径
     */
    private String advTarget;
    /**
     * 状态(1有效, 0无效)
     */
    private String statusId;

    /**
     * 网站ico
     */
    private String advLocation;
    private String beginTime;
    private String expireTime;
    private String platShow;

  
    /**
     * 序号
     */
    private int sort;

}
