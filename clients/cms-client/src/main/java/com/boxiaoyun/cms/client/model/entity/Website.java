package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_website")//指定表名
public class Website {

    @TableId(type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 网站名称
     */
    private String websiteTitle;

    /**
     * 网站域名
     */
    private String siteDomain;

    /**
     * 网站类型
     */
    private String siteType;

    /**
     * 网站logo路径
     */
    private String logoImg;
    /**
     * 状态(1有效, 0无效)
     */
    private String statusId;

    /**
     * 网站ico
     */
    private String logoIco;
    private String websiteKeyword;
    private String websiteDesc;
    private String copyright;
    private String icp;
    private String prn;
    private String weixin;
    private String weixinXcx;
    private String weibo;
    private String is_enableStatistics;
    private String statisticsCode;
    private String isShowService;
    private String service1;
    private String service2;
    private String userAgreement;
    private String recruitTitle;
    private String recruitInfo;
    private String entryAgreement;

    /**
     * 父级网站ID,
     */
    private int pid;

    /**
     * 序号
     */
    private int sort;

}
