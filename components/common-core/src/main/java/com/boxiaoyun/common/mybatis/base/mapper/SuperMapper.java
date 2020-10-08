package com.boxiaoyun.common.mybatis.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.mybatis.EntityMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LYD
 */
public interface SuperMapper<T> extends BaseMapper<T> {
    /**
     * 自定义分页
     * @param page
     * @param wrapper
     * @return
     */
    Page<T> pageList(Page<T> page, @Param("ew") Wrapper<?> wrapper);

    /**
     * 自定义查询
     * @param wrapper
     * @return
     */
    List<EntityMap> getEntityMap(@Param("ew") Wrapper<?> wrapper);
}
