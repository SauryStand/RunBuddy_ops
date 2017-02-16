package com.thinkgem.jeesite.modules.runbuddy.web;

import com.alibaba.fastjson.JSONObject;
import com.runbuddy.common.ReplyCollection;
import com.thinkgem.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

@Controller
public class HeartRateController extends BaseController {

    private static Logger logger = Logger.getLogger(HeartRateController.class);

    @RequestMapping(value = "/hearts")
    @ResponseBody
    public String upLoadHeartRate(String json) {
        logger.debug("-->>RunBuddy_ops拦截，测试输出");

        return null;
    }


    @RequestMapping(value="/heartrate")
    @ResponseBody
    public ReplyCollection upLoadHeartRate(@RequestBody JSONObject json){

        ReplyCollection reply = new ReplyCollection();
        logger.debug("-->>拦截测试输出");

        System.out.println("------>>look here:" + json.toJSONString() + "测试~~~");
        reply.setCode("8888");
        reply.setMessage("-->>upload successful");

        return reply;
    }
}
