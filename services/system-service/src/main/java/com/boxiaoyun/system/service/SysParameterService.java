package com.boxiaoyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import com.boxiaoyun.system.client.model.entity.SysParameter;

public interface SysParameterService extends IBaseService<SysParameter> {
    IPage<SysParameter> findPage(PageParams pageParams);

    /**
     * 检查key是否存在
     *
     * @param paramKey
     * @return
     */
    Boolean isExist(String paramKey);

    /**
     * 添加系统参数
     *
     * @param sysParameter
     * @return
     */
    void add(SysParameter sysParameter);

    /**
     * 修改系统参数
     *
     * @param sysParameter
     * @return
     */
    boolean update(SysParameter sysParameter);

    /**
     * 查询key
     *
     * @param paramKey
     * @return
     */
    SysParameter getByParamKey(String paramKey);

    /**
     * 移除接口
     *
     * @param id
     * @return
     */
    boolean remove(Long id);
}
