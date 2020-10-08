package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "hy_cms_advice_tab_list")//指定表名
public class AdviceTab extends BaseEntity {

    private Long id;

    /**
     * 名称
     */
    private String name;


}
