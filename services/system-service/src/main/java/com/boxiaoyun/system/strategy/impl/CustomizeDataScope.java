package com.boxiaoyun.system.strategy.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.boxiaoyun.common.constants.ErrorCode;
import com.boxiaoyun.common.exception.BaseException;
import com.boxiaoyun.system.client.model.entity.SystemOrg;
import com.boxiaoyun.system.service.SystemOrgService;

import com.boxiaoyun.system.strategy.AbstractDataScopeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname CustomizeDataScope
 * @Description 自定义
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 16:31
 * @Version 1.0
 */
@Component("CUSTOMIZE")
public class CustomizeDataScope implements AbstractDataScopeHandler {

    @Autowired
    private SystemOrgService orgService;

    @Override
    public List<Long> getOrgIds(List<Long> orgList, Long userId) {
        if (orgList == null || orgList.isEmpty()) {
            throw new BaseException(ErrorCode.BASE_VALID_PARAM.getCode(), "自定义数据权限类型时，组织不能为空");
        }
        List<SystemOrg> children = orgService.findChildren(orgList);
        return children.stream().mapToLong(SystemOrg::getId).boxed().collect(Collectors.toList());
    }
}
