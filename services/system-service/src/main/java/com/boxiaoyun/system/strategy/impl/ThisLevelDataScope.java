package com.boxiaoyun.system.strategy.impl;

import com.boxiaoyun.common.model.RemoteData;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import com.boxiaoyun.system.service.SystemUserService;
import com.boxiaoyun.system.strategy.AbstractDataScopeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Classname ThisLevelHandler
 * @Description 本级
 * @Author
 * @Date 2019-06-08 15:44
 * @Version 1.0
 */
@Component("THIS_LEVEL")
public class ThisLevelDataScope implements AbstractDataScopeHandler {
    @Autowired
    private SystemUserService userService;

    @Override
    public List<Long> getOrgIds(List<Long> orgList, Long userId) {
        SystemUser user = userService.getById(userId);
        if (user == null) {
            return Collections.emptyList();
        }
        Long orgId = RemoteData.getKey(user.getOrg());
        return Arrays.asList(orgId);
    }
}
