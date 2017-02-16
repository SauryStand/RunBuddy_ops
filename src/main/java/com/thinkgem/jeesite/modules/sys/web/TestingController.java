package com.thinkgem.jeesite.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;

@Controller
public class TestingController extends BaseController {
	
    @RequestMapping(value = "/testing")
    @ResponseBody
    public String upLoadHeartRate(String json) {
        logger.debug("-->>测试类拦截测试输出");
        int i = 15;
        System.out.println("------>>look here:" + i + "测试~~~");
        return null;
    }
}
