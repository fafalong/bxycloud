package com.boxiaoyun.msg.service.impl;

import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.msg.client.model.entity.EmailLogs;
import com.boxiaoyun.msg.mapper.EmailLogsMapper;
import com.boxiaoyun.msg.service.EmailLogsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮件发送日志 服务实现类
 *
 * @author
 * @date 2019-07-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmailLogsServiceImpl extends BaseServiceImpl<EmailLogsMapper, EmailLogs> implements EmailLogsService {

}
