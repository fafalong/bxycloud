package com.boxiaoyun.admin.controller;

import com.boxiaoyun.common.model.ResultBody;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author:
 * @date: 2019/5/24 13:31
 * @description:
 */
@Api(tags = "控制台")
@RestController
public class DashboardController {
    /*    @Autowired
    private LoginLogService loginLogService;
    @Autowired
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
        /*data.put("totalVisitCount", loginLogService.findTotalVisitCount());
        data.put("todayVisitCount", loginLogService.findTodayVisitCount());
        data.put("todayIp", loginLogService.findTodayIp());

        data.put("lastTenVisitCount", loginLogService.findLastTenDaysVisitCount(null));
        data.put("lastTenUserVisitCount", loginLogService.findLastTenDaysVisitCount(user.getAccount()));

        data.put("browserCount", loginLogService.findByBrowser());
        data.put("operatingSystemCount", loginLogService.findByOperatingSystem());*/
        data.put("totalVisitCount", 100);
        data.put("todayVisitCount", 200);
        data.put("todayIp", 300);

        Map<String, String> lastTenVisitCount = new HashMap<>();
        lastTenVisitCount.put("count","88");
        lastTenVisitCount.put("login_date","2020-06-19");

        List lastTenVisitCount_list=new ArrayList<>();
        lastTenVisitCount_list.add(lastTenVisitCount);
        data.put("lastTenVisitCount", lastTenVisitCount_list);

        //List<Map<String, Object>>
        Map<String, String> lastTenUserVisitCount = new HashMap<>();
        lastTenUserVisitCount.put("count","88");
        lastTenUserVisitCount.put("login_date","2020-06-19");
        List lll=new ArrayList<>();
        lll.add(lastTenUserVisitCount);
        data.put("lastTenUserVisitCount", lll);


        Map<String, String> browserCount = new HashMap<>();
        browserCount.put("count","88");
        browserCount.put("operating_system","2020-06-19");
        List browserCount_list=new ArrayList<>();
        browserCount_list.add(browserCount);
        data.put("browserCount", browserCount_list);

        Map<String, String> operatingSystemCount = new HashMap<>();
        operatingSystemCount.put("count","88");
        operatingSystemCount.put("operating_system","2020-06-19");
        List operatingSystemCount_list=new ArrayList<>();
        operatingSystemCount_list.add(lastTenUserVisitCount);
        data.put("operatingSystemCount", operatingSystemCount_list);
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
