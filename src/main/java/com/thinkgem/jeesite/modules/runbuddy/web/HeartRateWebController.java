package com.thinkgem.jeesite.modules.runbuddy.web;

import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 心率监控页面
 * @Author: Johnny Chou
 * @Create: 2017-02-25-23:00
 **/

@Controller
@RequestMapping(value = "${adminPath}/function")
public class HeartRateWebController extends BaseController{

    private static Logger logger = Logger.getLogger(HeartRateWebController.class);

    /**
     * 查看心率图表的拦截器
     * @return
     */
    @RequiresPermissions("sys:user:function")
    @RequestMapping(value = "/heartrate")
    public String showHeartRateChart(HttpServletRequest request, HttpServletResponse response){
        //do nothing
        ///function/heartrate
        logger.debug("-->>心率视图拦截成功");
        System.out.println("-->>打印测试，lanjie");
        // TODO: 2017/2/23
        return "modules/heartrate/heartRateChart";
    }





}
