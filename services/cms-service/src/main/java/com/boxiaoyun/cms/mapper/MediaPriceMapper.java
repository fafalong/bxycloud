package com.boxiaoyun.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hysj.cms.client.model.entity.MediaPrice;
import com.opencloud.common.model.PageParams;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MediaPriceMapper extends BaseMapper<MediaPrice> {
	
	    List<MediaPrice> findVideoPriceByPersionId(String persionId);

}
