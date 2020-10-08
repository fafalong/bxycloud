package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_template")//指定表名
public class Template {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 标题
     */
    private String name;

    /**
     * 模版编码，对应于前端的模版目录
     */
    private String code;

    /**
     * 封面图
     */
    private String frontCover;

    /**
     * 图片
     */
    private String images;

    /**
     * 描述，摘要
     */
    private String description;

    /**
     * 模版类型:企业宣传、个人博客、开源项目、政府官网、电子商务、bbs
     */
    private String type;

    /**
     * 风格：简约 / 唯美
     */
    private String style;

    /**
     * 色系：蓝色 / 红色 / 绿色 / 黄色
     */
    private String color;

    /**
     * 状态：1启用，0禁用
     */
    private int enable;

    /**
     * 是否单页模版，1：单页，0：多页
     */
    private int singlePage;

    /**
     * 数据收集器，以逗号隔开
     */
    private String dataCollectors;


}
