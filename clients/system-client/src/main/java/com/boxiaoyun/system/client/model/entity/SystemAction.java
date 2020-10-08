package com.boxiaoyun.system.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.mybatis.base.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统资源-功能操作
 *
 * @author:
 * @date: 2018/10/24 16:21
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("system_action")
public class SystemAction extends AbstractEntity {
    private static final long serialVersionUID = 1471599074044557390L;
    /**
     * 资源ID
     */
    @TableId(type= IdType.ID_WORKER)
    private Long actionId;

    /**
     * 资源编码
     */
    private String actionCode;

    /**
     * 资源名称
     */
    private String actionName;

    /**
     * 资源父节点
     */
    private Long menuId;

    /**
     * 优先级 越小越靠前
     */
    private Integer priority;

    /**
     * 资源描述
     */
    private String actionDesc;

    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;

    /**
     * 默认数据0-否 1-是 禁止删除
     */
    private Integer isPersist;

    /**
     * 服务ID
     */
    private String serviceId;
}
