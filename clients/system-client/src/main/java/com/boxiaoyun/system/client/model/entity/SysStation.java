package com.boxiaoyun.system.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boxiaoyun.common.model.RemoteData;
import com.boxiaoyun.common.mybatis.base.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 岗位
 * </p>
 *
 * @author bxy
 * @since 2019-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_station")
@ApiModel(value = "SysStation", description = "岗位")
public class SysStation extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableField("id")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    @TableField(value = "name", condition = LIKE)
    private String name;

    /**
     * 组织ID
     * #system_org
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private Long orgId;

    private SystemOrg org;
    //private RemoteData<Long, com.boxiaoyun.system.client.model.entity.SystemOrg> org;
    //private RemoteData<Long, SystemOrg> sysOrg;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Boolean status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "描述长度不能超过255")
    @TableField(value = "station_desc", condition = LIKE)
    private String stationDesc;


    @Builder
    public SysStation(Long id, Date createTime, Long createUser, Date updateTime, Long updateUser,
                      String name,
                      //RemoteData<Long, SystemOrg> orgId,
                      Long orgId,
                      Boolean status, String describe) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.name = name;
        this.orgId = orgId;
        this.status = status;
        this.stationDesc = describe;
    }

}
