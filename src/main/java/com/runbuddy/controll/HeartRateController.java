package com.runbuddy.controll;

import com.thinkgem.jeesite.common.web.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jonney Chou on 2017/1/8.
 */

// @RestController
// @RequestMapping("heartrate")
@RestController
@RequestMapping(value = "/test",method = RequestMethod.GET)
public class HeartRateController extends BaseController{

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory
            .getLogger(HeartRateController.class);

    @RequestMapping(value = "/bb")
    @ResponseBody
    public String upLoadHeartRate(String json) {
        logger.debug("-->>拦截测试输出");
        int i = 15;
        System.out.println("------>>look here:" + i + "测试~~~");
        return null;
    }



}
