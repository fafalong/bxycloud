package com.boxiaoyun.system.strategy.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.boxiaoyun.system.client.model.entity.SystemOrg;
import com.boxiaoyun.system.service.SystemOrgService;

import com.boxiaoyun.system.strategy.AbstractDataScopeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname AllDataScope
 * @Description 所有
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 16:27
 * @Version 1.0
 */
@Component("ALL")
public class AllDataScope implements AbstractDataScopeHandler {

    @Autowired
    private SystemOrgService orgService;

    @Override
    public List<Long> getOrgIds(List<Long> orgList, Long userId) {
        List<SystemOrg> list = orgService.lambdaQuery().select(SystemOrg::getId).list();
        return list.stream().map(SystemOrg::getId).collect(Collectors.toList());
    }


}
