package com.boxiaoyun.sso.ui.controller;

import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.common.security.BaseUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author:
 * @date: 2018/10/29 15:59
 * @description:
 */
@Controller
public class IndexController {

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap modelMap) {
        BaseUserDetails user = SecurityHelper.getUser();
        modelMap.put("user", user);
        return "index";
    }
}
