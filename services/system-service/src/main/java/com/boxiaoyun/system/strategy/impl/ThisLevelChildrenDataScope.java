package com.boxiaoyun.system.strategy.impl;

import com.boxiaoyun.common.model.RemoteData;
import com.boxiaoyun.system.client.model.entity.SystemOrg;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import com.boxiaoyun.system.service.SystemOrgService;
import com.boxiaoyun.system.service.SystemUserService;
import com.boxiaoyun.system.strategy.AbstractDataScopeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname ThisLevelChildenDataScope
 * @Description 本级以及子级
 * @Date 2019-06-08 16:30
 * @Version 1.0
 */
@Component("THIS_LEVEL_CHILDREN")
public class ThisLevelChildrenDataScope implements AbstractDataScopeHandler {
    @Autowired
    private SystemUserService userService;
    @Autowired
    private SystemOrgService orgService;

    @Override
    public List<Long> getOrgIds(List<Long> orgList, Long userId) {
        SystemUser user = userService.getById(userId);
        if (user == null) {
            return Collections.emptyList();
        }
        Long orgId = RemoteData.getKey(user.getOrg());
        List<SystemOrg> children = orgService.findChildren(Arrays.asList(orgId));
        return children.stream().mapToLong(SystemOrg::getId).boxed().collect(Collectors.toList());
    }
}
