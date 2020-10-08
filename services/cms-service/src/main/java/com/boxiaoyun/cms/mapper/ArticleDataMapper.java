package com.boxiaoyun.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hysj.cms.client.model.entity.ArticleData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDataMapper extends BaseMapper<ArticleData> {
    List<ArticleData> findArticleDataListByArticleId(String articleId);
}
