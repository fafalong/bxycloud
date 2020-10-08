package com.boxiaoyun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.common.utils.StringUtil;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.entity.GatewayRoute;
import com.boxiaoyun.system.mapper.GatewayRouteMapper;
import com.boxiaoyun.system.service.GatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRouteServiceImpl extends BaseServiceImpl<GatewayRouteMapper, GatewayRoute> implements GatewayRouteService {

    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public IPage<GatewayRoute> findPage(PageParams pageParams) {
        QueryWrapper<GatewayRoute> queryWrapper = new QueryWrapper();
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

    /**
     * 查询可用路由列表
     *
     * @return
     */
    @Override
    public List<GatewayRoute> findList() {
        QueryWrapper<GatewayRoute> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(GatewayRoute::getStatus, SystemConstants.ENABLED);
        List<GatewayRoute> list = list(queryWrapper);
        return list;
    }

    /**
     * 添加路由
     *
     * @param route
     */
    @Override
    public void add(GatewayRoute route) {
        if (StringUtil.isBlank(route.getPath())) {
            throw new BaseException(String.format("path不能为空!"));
        }
        if (isExist(route.getRouteName())) {
            throw new BaseException(String.format("路由名称已存在!"));
        }
        route.setIsPersist(0);
        save(route);
    }

    /**
     * 更新路由
     *
     * @param route
     */
    @Override
    public void update(GatewayRoute route) {
        if (StringUtil.isBlank(route.getPath())) {
            throw new BaseException(String.format("path不能为空"));
        }
        GatewayRoute saved = getById(route.getRouteId());
        if (saved == null) {
            throw new BaseException("路由信息不存在!");
        }
        if (saved != null && saved.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止修改"));
        }
        if (!saved.getRouteName().equals(route.getRouteName())) {
            // 和原来不一致重新检查唯一性
            if (isExist(route.getRouteName())) {
                throw new BaseException("路由名称已存在!");
            }
        }
        updateById(route);
    }

    /**
     * 删除路由
     *
     * @param routeId
     */
    @Override
    public void remove(Long routeId) {
        GatewayRoute saved = getById(routeId);
        if (saved != null && saved.getIsPersist().equals(SystemConstants.ENABLED)) {
            throw new BaseException(String.format("默认数据,禁止删除"));
        }
        removeById(routeId);
    }

    /**
     * 查询地址是否存在
     *
     * @param routeName
     */
    @Override
    public Boolean isExist(String routeName) {
        QueryWrapper<GatewayRoute> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(GatewayRoute::getRouteName, routeName);
        int count = count(queryWrapper);
        return count > 0;
    }
}
