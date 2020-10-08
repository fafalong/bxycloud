package com.boxiaoyun.msg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.msg.client.model.entity.EmailLogs;
import com.boxiaoyun.msg.service.EmailLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 邮件发送日志 前端控制器
 *
 * @author admin
 * @date 2019-07-25
 */
@Api(value = "邮件发送日志", tags = "邮件发送日志")
    @RestController
@RequestMapping("/emailLogs")
    public class EmailLogsController {

    @Autowired
    private EmailLogsService targetService;

    /**
     * 获取分页数据
     *
     * @return
     */
    @ApiOperation(value = "获取分页数据", notes = "获取分页数据")
    @GetMapping(value = "/list")
    public ResultBody<Page<EmailLogs>> list(@RequestParam(required = false) Map map) {
        PageParams pageParams = new PageParams(map);
        EmailLogs query = pageParams.mapToBean(EmailLogs.class);
        QueryWrapper<EmailLogs> queryWrapper = new QueryWrapper();
        return ResultBody.success(targetService.page(new PageParams(map), queryWrapper));
    }

    /**
     * 根据ID查找数据
     */
    @ApiOperation(value = "根据ID查找数据", notes = "根据ID查找数据")
    @ResponseBody
    @RequestMapping("/get")
    public ResultBody<EmailLogs> get(@RequestParam("id") Long id) {
        EmailLogs entity = targetService.getById(id);
        return ResultBody.success(entity);
    }

}
