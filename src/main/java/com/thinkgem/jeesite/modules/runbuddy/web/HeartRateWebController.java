package com.thinkgem.jeesite.modules.runbuddy.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.runbuddy.entity.RealTimeRate;
import com.thinkgem.jeesite.modules.runbuddy.service.HeartService;
import com.thinkgem.jeesite.modules.runbuddy.service.RealRateService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 心率监控页面
 * @Author: Johnny Chou
 * @Create: 2017-02-25-23:00
 **/

@Controller
@RequestMapping(value = "${adminPath}/function")
public class HeartRateWebController extends BaseController {

    private static Logger logger = Logger.getLogger(HeartRateWebController.class);

    //@Autowired
    //private HeartService heartService;
    @Autowired
    private RealRateService realRateService;


    /**
     * 查看心率图表的拦截器
     * @return
     */
    //@RequiresPermissions("sys:user:function")
    //@RequestMapping(value = "/heartrate")
    public String showHeartRateChart(HttpServletRequest request, HttpServletResponse response ,Model model){
        //do nothing
        ///function/heartrate
        logger.debug("-->>心率视图拦截成功");
        System.out.println("-->>打印测试，拦截数据啦啊啊啊啊啊啊 啊啊啊啊啊啊啊啊啊啊啊");
        // TODO: 2017/2/23
        return "modules/heartrate/heartRateChart";
    }

    /**
     * 加载并返回数组
     *
     * @param real
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:function")
    @RequestMapping(value = "/heartrate")
    //@RequestMapping(value = {"heartrate", ""})
    public String list(RealTimeRate real, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<String> list = new ArrayList<String>();
        String str = "61:0|64:0|71:0|91:0|59:0|59:0|59:0|59:0|59:0";
        logger.debug("------------------------------>>加载心率数组测试");
        //List<RealTimeRate> realList = heartService.findAllRealData();

        List<RealTimeRate> realList = realRateService.findAllRearRateList();//查询数据
        logger.debug("------------------------------>>加载心率数组测试,数据打印:" + realList.toString());
        model.addAttribute("list", realList);
        Page<RealTimeRate> page = realRateService.findPage(new Page<RealTimeRate>(request, response), real);
        logger.debug("-->>现在输出page日志，查询是否返回心率数据：" + page.toString());
        model.addAttribute("page", page);

        for(RealTimeRate realArr : realList){
            char[] ch  = realArr.getRealTimeRate().toCharArray();
            int temp = 1;
            for (int i = 0; i < ch.length; i++) {
                if (ch[i] == '|') {
                    StringBuilder builder = new StringBuilder();
                    if (temp == 1) {
                        for (int j = 0; j < i; j++) {
                            builder.append(ch[j]);
                        }
                        for(int k = 0 ; k < i ; k++){
                            if(ch[i] == ':'){
                                logger.debug("---------------->>list printing:fdsgdfghfgjghjkgjk");
                            }
                        }
                        list.add(builder + "");
                        temp = i;
                        ch[temp] = ',';
                    } else {
                        for (int j = temp+1; j < i; j++) {
                            builder.append(ch[j]);
                        }
                        list.add(builder + "");
                        temp = i;
                        ch[temp] = ',';
                    }
                }
            }
        }
        for(int i = 0 ; i < list.size() ; i++){
            logger.debug("---------------->>list printing:"+list.get(i).toString());
        }


        return "modules/heartrate/heartRateChart";
    }


}
