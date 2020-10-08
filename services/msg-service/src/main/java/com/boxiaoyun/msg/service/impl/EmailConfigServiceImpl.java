package com.boxiaoyun.msg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boxiaoyun.common.mybatis.base.service.impl.BaseServiceImpl;
import com.boxiaoyun.msg.client.model.entity.EmailConfig;
import com.boxiaoyun.msg.mapper.EmailConfigMapper;
import com.boxiaoyun.msg.service.EmailConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 邮件发送配置 服务实现类
 *
 * @author
 * @date 2019-07-17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EmailConfigServiceImpl extends BaseServiceImpl<EmailConfigMapper, EmailConfig> implements EmailConfigService {

    private List<EmailConfig> emailConfigs;

    /**
     * 加载配置
     */
    @Override
    public void loadCacheConfig() {
        QueryWrapper<EmailConfig> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("is_default");
        emailConfigs = baseMapper.selectList(queryWrapper);
        log.debug("加载邮件配置:{}",emailConfigs.size());
    }

    /**
     * 获取缓存的配置
     *
     * @return
     */
    @Override
    public List<EmailConfig> getCacheConfig() {
        return emailConfigs != null ? emailConfigs : Collections.emptyList();
    }

}
