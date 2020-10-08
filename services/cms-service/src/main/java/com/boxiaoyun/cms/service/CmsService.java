package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hysj.cms.client.model.entity.*;
import com.boxiaoyun.cms.mapper.ChannelMapper;
import com.opencloud.common.model.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容管理类
 *
 * @author liujian
 */
public interface CmsService {

        /**
         * 获取网站频道列表
         *
         * @param siteId
         */

        public List<Channel> findChannelList(Long siteId) ;


        public IPage<WebsiteLink> findWebsiteLinkListPage(PageParams pageParams);

        public IPage<WebsiteNav> findWebsiteNavListPage(PageParams pageParams);



}
