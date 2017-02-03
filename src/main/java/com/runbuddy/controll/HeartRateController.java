package com.runbuddy.controll;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jonney Chou on 2017/1/8.
 */

//@RestController
//@RequestMapping("${adminPath}/heartrate")
@Controller
public class HeartRateController extends BaseController{

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(HeartRateController.class);

    @RequestMapping(value="${adminPath}/heartrate/upload", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String upLoadHeartRate(@RequestBody JSONObject json){
        logger.debug("-->>拦截测试输出");
        return null;
    }

}
