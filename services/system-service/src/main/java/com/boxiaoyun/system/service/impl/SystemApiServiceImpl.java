package com.boxiaoyun.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.common.collect.Lists;
import com.boxiaoyun.common.constants.CommonConstants;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.utils.RedisUtil;
import com.boxiaoyun.system.client.constants.ResourceType;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.entity.SystemApi;
import com.boxiaoyun.system.mapper.SystemApiMapper;
import com.boxiaoyun.system.service.SystemApiService;
import com.boxiaoyun.system.service.SystemAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemApiServiceImpl extends BaseServiceImpl<SystemApiMapper, SystemApi> implements SystemApiService {
    @Autowired
    private SystemAuthorityService systemAuthorityService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<SystemApi> findPage(PageParams pageParams) {
        SystemApi query = pageParams.mapToBean(SystemApi.class);
        QueryWrapper<SystemApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .likeRight(ObjectUtils.isNotEmpty(query.getPath()), SystemApi::getPath, query.getPath())
                .likeRight(ObjectUtils.isNotEmpty(query.getApiName()), SystemApi::getApiName, query.getApiName())
                .likeRight(ObjectUtils.isNotEmpty(query.getApiCode()), SystemApi::getApiCode, query.getApiCode())
                .eq(ObjectUtils.isNotEmpty(query.getServiceId()), SystemApi::getServiceId, query.getServiceId())
                .eq(ObjectUtils.isNotEmpty(query.getStatus()), SystemApi::getStatus, query.getStatus())
                .eq(ObjectUtils.isNotEmpty(query.getIsAuth()), SystemApi::getIsAuth, query.getIsAuth());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<SystemApi> findList(String serviceId) {
        QueryWrapper<SystemApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(ObjectUtils.isNotEmpty(serviceId), SystemApi::getServiceId, serviceId);
        List<SystemApi> list = list(queryWrapper);
        return list;
    }

    /**
     * 检查接口编码是否存在
     *
     * @param apiCode
     * @return
     */
    @Override
    public Boolean isExist(String apiCode) {
        QueryWrapper<SystemApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemApi::getApiCode, apiCode);
        int count = count(queryWrapper);
        return count > 0 ? true : false;
    }

    /**
     * 添加接口
     *
     * @param api
     * @return
     */
    @Override
    public void add(SystemApi api) {
        if (isExist(api.getApiCode())) {
            throw new BaseException(String.format("%s编码已存在!", api.getApiCode()));
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        if (api.getStatus() == null) {
            api.setStatus(SystemConstants.ENABLED);
        }
        if (api.getIsPersist() == null) {
            api.setIsPersist(0);
        }
        if (api.getIsAuth() == null) {
            api.setIsAuth(1);
        }
        save(api);
        // 同步权限表里的信息
        systemAuthorityService.saveOrUpdateAuthority(api.getApiId(), ResourceType.api);
    }

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    @Override
    public void update(SystemApi api) {
        SystemApi saved = getById(api.getApiId());
        if (saved == null) {
            throw new BaseException("信息不存在!");
        }
        if (!saved.getApiCode().equals(api.getApiCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(api.getApiCode())) {
                throw new BaseException(String.format("%s编码已存在!", api.getApiCode()));
            }
        }
        api.setUpdateTime(new Date());
        updateById(api);
        // 同步权限表里的信息
        systemAuthorityService.saveOrUpdateAuthority(api.getApiId(), ResourceType.api);
    }

    /**
     * 查询接口
     *
     * @param apiCode
     * @return
     */
    @Override
    public SystemApi getByCode(String apiCode) {
        QueryWrapper<SystemApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SystemApi::getApiCode, apiCode);
        return getOne(queryWrapper);
    }


    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    @Override
    public void remove(Long apiId) {
        SystemApi api = getById(apiId);
        if (api != null && api.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止删除"));
        }
        systemAuthorityService.removeAuthority(apiId, ResourceType.api);
        removeById(apiId);
    }

    /**
     * 更新资源服务器
     *
     * @param serviceIds
     * @return
     */
    @Override
    public boolean batchUpdateService(String... serviceIds) {
        if (serviceIds != null && serviceIds.length > 0) {
            for (String serviceId : serviceIds) {
                if (redisUtil.hHasKey(CommonConstants.API_RESOURCE, serviceId)) {
                    List<String> codes = Lists.newArrayList();
                    Map<String, Object> map = (Map<String, Object>) redisUtil.hget(CommonConstants.API_RESOURCE, serviceId);
                    List<Map<String, String>> list = (List<Map<String, String>>) map.get("list");
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Map obj = (Map) iterator.next();
                        try {
                            SystemApi api = BeanUtil.mapToBean(obj, SystemApi.class, true);
                            codes.add(api.getApiCode());
                            SystemApi save = getByCode(api.getApiCode());
                            if (save == null) {
                                api.setIsPersist(1);
                                add(api);
                            } else {
                                api.setApiId(save.getApiId());
                                update(api);
                            }
                        } catch (Exception e) {
                            log.error("添加资源error:", e);
                        }
                    }
                    if (codes != null && codes.size() > 0) {
                        // 清理无效权限数据
                        systemAuthorityService.clearInvalidApi(serviceId, codes);
                    }
                }
                redisUtil.hdel(CommonConstants.API_RESOURCE, serviceId);
            }
            return true;
        }
        return false;
    }

    /**
     * 获取等待更新的服务器列表
     */
    @Override
    public List<Map> getBatchUpdateServiceList() {
        List<Map> list = Lists.newArrayList();
        Set<String> keys = redisUtil.hkeys(CommonConstants.API_RESOURCE);
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String serviceId = iterator.next();
            Map<String, Object> map = (Map<String, Object>) redisUtil.hget(CommonConstants.API_RESOURCE, serviceId);
            map.remove("list");
            list.add(map);
        }
        return list;
    }

    /**
     * 获取服务列表
     *
     * @return
     */
    @Override
    public List<String> getServiceList() {
        return baseMapper.selectServiceList();
    }


}
