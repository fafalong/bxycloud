package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemDictionaryItem;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口
 * 字典项
 * </p>
 *
 * @author bxy
 * @date 2019-07-02
 */
public interface DictionaryItemService extends IBaseService<SystemDictionaryItem> {
    /**
     * 根据字典编码查询字典
     *
     * @param codes
     * @return
     */
    Map<String, Map<String, String>> map(String[] codes);

    Map<Serializable, Object> findDictionaryItem(Set<Serializable> codes);

    Page<SystemDictionaryItem> findPage(PageParams pageParams);
}
