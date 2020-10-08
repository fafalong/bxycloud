package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "hy_cms_dictionary_item")//指定表名
public class DictionaryItem extends BaseEntity {

    private Integer id;

    /**
     * 所属字典组
     */
    private DictionaryGroup group;

    /**
     * 字典名，如男/女
     */
    private String dictName;

    /**
     * 字典值，如male/femail
     */
    private String dictValue;

    /**
     * 字典展示在前端的提示文本
     */
    private String dictPrompt;

    /**
     * 排序号
     */
    private int orders;

}
