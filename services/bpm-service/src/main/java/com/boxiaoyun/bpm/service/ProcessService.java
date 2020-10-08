package com.boxiaoyun.bpm.service;

import com.boxiaoyun.bpm.client.model.TaskOperate;

/**
 * 自定义流程接口
 * @author:
 * @date: 2019/4/4 13:54
 * @description:
 */
public interface ProcessService {

    /**
     * 执行日志
     * @param taskOperate
     */
    void complete(TaskOperate taskOperate);
}
