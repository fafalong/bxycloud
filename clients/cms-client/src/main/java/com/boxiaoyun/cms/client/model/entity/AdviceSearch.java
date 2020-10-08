package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "hy_cms_advice_search")//指定表名
public class AdviceSearch extends BaseEntity {

    private Long id;

    /**
     * 搜索关键词
     */
    private String searchKey;


}
