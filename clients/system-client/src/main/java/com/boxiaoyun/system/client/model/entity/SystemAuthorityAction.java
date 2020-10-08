package com.boxiaoyun.system.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.mybatis.base.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统权限-功能操作关联表
 *
 * @author:
 * @date: 2018/10/24 16:21
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("system_authority_action")
public class SystemAuthorityAction extends AbstractEntity {
    private static final long serialVersionUID = 1471599074044557390L;
    /**
     * 操作资源ID
     */
    private Long actionId;

    /**
     * 权限ID
     */
    private Long authorityId;
}
