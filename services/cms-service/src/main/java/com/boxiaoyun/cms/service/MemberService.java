package com.boxiaoyun.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hysj.cms.client.model.entity.Member;
import com.opencloud.common.model.PageParams;

/**
 * 会员接口
 *
 * @author: liujian
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface MemberService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<Member> findListPage(PageParams pageParams);

    /**
     * 添加文章
     *
     * @param member
     */
    void addMember(Member member);

    /**
     * 更细文章
     *
     * @param member
     */
    void modifyMember(Member member);


    /**
     * 根据主键获取站点信息
     *
     * @param memberId
     * @return
     */
    Member getMember(Long memberId);
}
