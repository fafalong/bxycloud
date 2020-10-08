package com.boxiaoyun.system.controller;

import cn.hutool.core.util.IdUtil;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.system.client.model.entity.SystemMenu;
import com.boxiaoyun.system.client.model.entity.SystemUser;
import com.boxiaoyun.system.client.service.ISystemDashboardServiceClient;
import com.boxiaoyun.system.client.service.ISystemMenuServiceClient;
//import com.boxiaoyun.system.service.LoginLogService;
import com.boxiaoyun.system.service.LoginLogService;
import com.boxiaoyun.system.service.SystemMenuService;
import com.boxiaoyun.system.service.SystemUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * 首页
 * </p>
 *
 * @author bxy
 * @date 2019-10-20
 */
@Slf4j
@Validated
@RestController
@Api(value = "dashboard", tags = "首页")
//public class DashboardController extends BaseController<> implements ISystemDashboardServiceClient {
public class DashboardController implements ISystemDashboardServiceClient {
    @Autowired
    private LoginLogService loginLogService;
/*    @Autowired
    private DatabaseProperties databaseProperties;*/

    /**
     * 最近10天访问记录
     *
     * @return
     */
    @GetMapping("/dashboard/visit")
    //@Override
    public ResultBody<Map<String, Object>> index(){//(@ApiIgnore @LoginUser SysUser user) {
        Map<String, Object> data = new HashMap<>();
        // 获取系统访问记录
        //data.put("totalVisitCount", loginLogService.findTotalVisitCount());
        //data.put("todayVisitCount", loginLogService.findTodayVisitCount());
        //data.put("todayIp", loginLogService.findTodayIp());

        //data.put("lastTenVisitCount", loginLogService.findLastTenDaysVisitCount(null));

        LocalDateTime startDay = LocalDateTime.now().plusDays(-9);
        LocalDateTime endDay = LocalDateTime.now();
        data.put("lastTenUserVisitCount", loginLogService.getAccessCountByDay(startDay,endDay,""));

        //data.put("browserCount", loginLogService.findByBrowser());
        //data.put("operatingSystemCount", loginLogService.findByOperatingSystem());

        //data.put("totalVisitCount", 100);
        //data.put("todayVisitCount", 200);
        //data.put("todayIp", 300);

        //data.put("lastTenVisitCount", 400);


//        data.put("browserCount", 600);
//        data.put("operatingSystemCount", 700);
        return ResultBody.success(data);
    }

    /**
     * 生成id
     *
     * @return
     */
    @GetMapping("/common/generateId")
    public ResultBody<Long> generate() {
        return ResultBody.success(new Long(800));
        //DatabaseProperties.Id id = databaseProperties.getId();
        //return success(IdUtil.getSnowflake(id.getWorkerId(), id.getDataCenterId()).nextId());
    }

}

