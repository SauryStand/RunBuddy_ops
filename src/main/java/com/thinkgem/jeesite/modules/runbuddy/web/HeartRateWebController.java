package com.thinkgem.jeesite.modules.runbuddy.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import com.thinkgem.jeesite.modules.runbuddy.entity.Rate;
import com.thinkgem.jeesite.modules.runbuddy.entity.RealTimeRate;
import com.thinkgem.jeesite.modules.runbuddy.service.HeartRateService;
import com.thinkgem.jeesite.modules.runbuddy.service.RateService;
import com.thinkgem.jeesite.modules.runbuddy.service.RealRateService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private HeartRateService heartRateService;

    @Autowired
    private RealRateService realRateService;
    @Autowired
    private RateService rateService;

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
        System.out.println("-->>打印测试，拦截数据 嘿嘿嘿");
        // TODO: 2017/2/23
        return "modules/heartrate/heartRateChart";
    }

    /**
     * 加载并返回数组
     * @param real
     * @param model
     * @return     */
    @RequiresPermissions("sys:user:function")
    @RequestMapping(value = "/heartrate")
    public String list(RealTimeRate real, HttpServletRequest request, HttpServletResponse response, Model model) {

        Rate rateModel = new Rate();
        HttpSession session = request.getSession();
        //List<RealTimeRate> realList = heartService.findAllRealData();
        List<RealTimeRate> realList = realRateService.findAllRearRateList2();//查询所有心率数据
        logger.debug("------------------------------>>加载心率数组测试,数据打印:" + realList.toString());
        Page<RealTimeRate> page = realRateService.findPage(new Page<RealTimeRate>(request, response), real);
        Page<Rate> page2 = rateService.findPage(new Page<Rate>(request, response), rateModel);

        logger.debug("-->>现在输出page日志，查询是否返回心率数据：" + page.toString());

        List<String> list = new ArrayList<String>();
        List<Rate> firstTempRateList = new ArrayList<>();
        for(RealTimeRate realArr : realList){
            char[] ch  = realArr.getRealTimeRate().toCharArray();//这里把读到的RealRate放进来
            int temp = 1;
            for (int i = 0; i < ch.length; i++) {
                if (ch[i] == '|') {
                    StringBuilder builder = new StringBuilder();
                    Rate firstSplitRate = new Rate();//需要放在这里，因为放在外面你想想一直都是用的同一个对象，所有数组都会是同样的
                    if (temp == 1) {
                        for (int j = 0; j < i; j++) {
                            builder.append(ch[j]);
                        }
                        list.add(builder + "");
                        //这里加
                        firstSplitRate.setRealRate(builder+"");
                        firstSplitRate.setRecordTime(realArr.getRecordTime());
                        firstTempRateList.add(firstSplitRate);//将第一步的“|”分离出去，得到第一轮后处理的数据,初衷是为了获取到时间，设计错误啊
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

//        for(int i = 0 ; i < firstTempRateList.size();i++){
//            logger.debug("---------------->>第一轮打印Rate处理出来的数组：:" + firstTempRateList.get(i).getRealRate() + "," + firstTempRateList.get(i).getRecordTime());
//        }
        //進一步分離 :
        //放上面為了減少系統開銷，不用每次有new一個stringBuilder
        StringBuilder builder = new StringBuilder();
        StringBuilder timeBuilder = new StringBuilder();
        List<Rate> splitRealArr = new ArrayList<Rate>();
        for(int i = 0 ; i < list.size() ; i++){
            char[] ch  = list.get(i).toString().toCharArray();
            for(int j = 0 ; j < ch.length ; j++){
                if(ch[j] == ':'){
                    Rate temp = new Rate();//需要放在这里，因为放在外面你想想一直都是用的同一个对象，所有数组都会是同样的
                    for(int m = 0 ; m < j ; m++){
                        builder.append(ch[m]);
                    }
                    temp.setRealRate(builder+"");//添加心率
                    temp.setStatus(ch[j+1]+"");//添加状态,0,表示有效,1，表示无效
                    for(int g = j+2 ; g < ch.length ; g++){
                        timeBuilder.append(ch[g]);//将时间再拼接回来
                    }
                    //temp.setRecordTime();

                    //在推进分离之后的数组
                    splitRealArr.add(temp);//即加入real数组
                    //清空StringBuilder
                    builder.setLength(0);
                    builder.delete(0,builder.length());
                    timeBuilder.setLength(0);
                    timeBuilder.delete(0,timeBuilder.length());
                }
            }
        }
//        for(int i = 0 ; i < splitRealArr.size();i++){
//            logger.debug("---------------->>这是打印Rate数组：:" + splitRealArr.get(i).getRealRate() + "," + splitRealArr.get(i).getStatus()+","+splitRealArr.get(i).getRecordTime());
//        }

        //model.addAttribute("realArr", splitRealArr);
        //model.addAttribute("page2",page2);//并没有什么实际意义的返回前台数据，只是为了往前台发送我处理好的string而已
        model.addAttribute("list", realList);
        model.addAttribute("page", page);
        session.setAttribute("realArr",splitRealArr);
        //这里要做啥？？
        return "modules/heartrate/heartRateChart";

    }


    @RequiresPermissions("sys:user:function")
    @RequestMapping(value = "uploadAnalyse")
    public String analyseList(HeartRate heartRate, HttpServletRequest request, HttpServletResponse response, Model model){

        List<HeartRate> heartRateList = heartRateService.findAllHeartRateList();
        logger.debug("--->>测试咯"+heartRateList.size());
        Page<HeartRate> page = heartRateService.findPage(new Page<HeartRate>(request, response), heartRate);

        model.addAttribute("list",heartRateList);
        model.addAttribute("page",page);

        return "modules/heartrate/analyseRateChart";
    }


}
