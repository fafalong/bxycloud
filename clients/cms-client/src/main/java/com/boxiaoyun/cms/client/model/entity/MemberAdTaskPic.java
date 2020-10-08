package com.hysj.cms.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hy_cms_member_ad_task_pic")//指定表名
public class MemberAdTaskPic  extends BaseEntity{

    
    private int id;
    private int taskId ;
    private String picPath ;
    private String ipAddr;// 
    private String taskDesc ;
 

}
