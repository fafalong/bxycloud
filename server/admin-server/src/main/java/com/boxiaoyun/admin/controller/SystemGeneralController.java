package com.boxiaoyun.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.DataScopeType;
import com.boxiaoyun.common.utils.BaseEnum;
import com.boxiaoyun.common.utils.HttpMethod;
import com.boxiaoyun.common.utils.Sex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用 控制器
 *
 * @author bxy
 * @date 2019/07/25
 */

@Slf4j
@RestController
@RefreshScope
@Api(value = "Common", tags = "通用Controller")
public class SystemGeneralController {
    private final static Map<String, Map<String, String>> ENUM_MAP = new HashMap<>(8);

    static {
        ENUM_MAP.put(HttpMethod.class.getSimpleName(), BaseEnum.getMap(HttpMethod.values()));
        ENUM_MAP.put(DataScopeType.class.getSimpleName(), BaseEnum.getMap(DataScopeType.values()));
//        ENUM_MAP.put(LogType.class.getSimpleName(), BaseEnum.getMap(LogType.values()));
//        ENUM_MAP.put(AuthorizeType.class.getSimpleName(), BaseEnum.getMap(AuthorizeType.values()));
        ENUM_MAP.put(Sex.class.getSimpleName(), BaseEnum.getMap(Sex.values()));
//        ENUM_MAP.put(TenantTypeEnum.class.getSimpleName(), BaseEnum.getMap(TenantTypeEnum.values()));
//        ENUM_MAP.put(TenantStatusEnum.class.getSimpleName(), BaseEnum.getMap(TenantStatusEnum.values()));
//        ENUM_MAP.put(ApplicationAppTypeEnum.class.getSimpleName(), BaseEnum.getMap(ApplicationAppTypeEnum.values()));
    }

    @ApiOperation(value = "获取当前系统指定枚举", notes = "获取当前系统指定枚举")
    @GetMapping("/enums")
    public ResultBody<Map<String, Map<String, String>>> enums(@RequestParam(value = "codes[]", required = false) String[] codes) {
        if (ArrayUtil.isEmpty(codes)) {
            return ResultBody.success(ENUM_MAP);
        }

        Map<String, Map<String, String>> map = new HashMap<>(codes.length);

        for (String code : codes) {
            if (ENUM_MAP.containsKey(code)) {
                map.put(code, ENUM_MAP.get(code));
            }
        }
        return ResultBody.success(map);
    }

    @Value("${bxy.database.isNotWrite:false}")
    private Boolean isNotWrite;


    @GetMapping("/test")
    public ResultBody<Object> test() {
        return ResultBody.success(isNotWrite);
    }
}

