package com.boxiaoyun.admin.controller;

import com.boxiaoyun.common.model.ResultBody;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "test")
@RestController
public class TestController {

    @PostMapping("/test")
    public void test(
            @RequestParam("testName") String testName
    ) {
        System.out.println("ffffffffffffffffffffff");
    }

    @RequestMapping("/test/limit/rate/save5")
    public ResultBody save5(HttpServletRequest request, HttpServletResponse response
    ) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
            System.out.println(key+">>>>>>>>>>>>>>>>>>>>>>>>>>>"+value);
        }
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
                System.out.println(">>>>>>="+str);
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(">>>>>>"+sb.toString());
        /*
        Map<String, String[]> map = request.getParameterMap();
        Set<Map.Entry<String, String[]>> keys = map.entrySet();
        Iterator<Map.Entry<String, String[]>> i = keys.iterator();
        System.out.printf("+__+"+keys.size()+">>>>>>>>>"+keys.toString());
        System.out.printf("+__+");
        while (i.hasNext()){
            Map.Entry<String, String[]> it = i.next();
            System.out.println(it.getKey()+":"+ Arrays.toString(it.getValue()));
        }*/
        return null;
    }
}
