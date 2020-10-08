package com.boxiaoyun.common.mybatis.base.controller;


import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author: Administrator
 * @date: 2019/1/4 22:02
 * @desc: 类描述：基础控制器
 */
public class BaseController<Biz extends IBaseService<T>, T> {

    @Autowired
    //@Qualifier("bizService")
    protected Biz bizService;

    /**
     * 成功返回
     *
     * @param data
     * @return
     */
    public <T> ResultBody<T> success(T data) {
        return ResultBody.success(data);
    }
}
