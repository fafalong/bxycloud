package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SystemDictionary;

/**
 * 系统字典
 * @author
 */
public interface DictionaryService extends IBaseService<SystemDictionary> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<SystemDictionary> findPage(PageParams pageParams) ;
    /**
     * 检查dictCode编码是否存在
     *
     * @param dictCode
     * @return
     */
    Boolean isExist(String dictCode);
    /**
     * 添加dictionary操作
     *
     * @param dictionary
     * @return
     */
    SystemDictionary add(SystemDictionary dictionary);
    /**
     * 修改dictionary操作
     *
     * @param dictionary
     * @return
     */
    SystemDictionary update(SystemDictionary dictionary);
    /**
     * 移除字典
     *
     * @param id
     * @return
     */
    void remove(Long id);
}
