package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "hy_cms_dictionary_group")//指定表名
public class DictionaryGroup extends BaseEntity {


    private Integer id;

    /**
     * 标题
     */
    private String code;

    /**
     * 封面图
     */
    private String name;

    /**
     * 子目录
     */
    private List<DictionaryItem> items;

}
