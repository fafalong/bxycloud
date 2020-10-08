package com.boxiaoyun.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.AdviceSearch;
import com.opencloud.common.model.PageParams;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdviceSearchMapper extends BaseMapper<AdviceSearch> {
    IPage<AdviceSearch> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param article
     */
    void addAdviceSearch(AdviceSearch adviceSearch);

    /**
     * 更细文章
     *
     * @param article
     */
    void modifyAdviceSearch(AdviceSearch adviceSearch);


    /**
     * 根据主键获取文章
     *
     * @param articleId
     * @return
     */
    AdviceSearch getAdviceSearch(Long adviceSearchId);

    /**
     * 通过文章id移除文章
     *
     * @param articleId
     * @return
     */
    public void removeAdviceSearch(Long adviceSearchId);
}
