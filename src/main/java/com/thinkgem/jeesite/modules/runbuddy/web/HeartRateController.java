package com.thinkgem.jeesite.modules.runbuddy.web;

import com.alibaba.fastjson.JSONObject;
import com.runbuddy.common.ReplyCollection;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import com.thinkgem.jeesite.modules.runbuddy.entity.Rate;
import com.thinkgem.jeesite.modules.runbuddy.service.HeartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class HeartRateController {

    private static Logger logger = Logger.getLogger(HeartRateController.class);


    //@Autowired
    //private HeartRateDao heartRateDao;

    @Autowired
    private HeartService heartService;


    @RequestMapping(value = "/hearts")
    //@RequestMapping(value = "/heartrate")
    @ResponseBody
    public ReplyCollection upLoadHeartRate(String json) {
        ReplyCollection reply = new ReplyCollection();
        logger.debug("-->>RunBuddy_ops拦截，测试输出");
        logger.debug("-->>不考虑性能，随便new 随便static");
        HeartRate heartRate = new HeartRate();
        Random random = new Random();
        int maxInt = random.nextInt(135) % 74 + 60;//random.nextInt(max)%(max-min+1) + min;[60,135]之间的心率数
        int minInt = random.nextInt(80) % 31 + 50;
        int average = random.nextInt(80) % 16 + 65;
        //int hight = (int) (Math.random() * 100);
        heartRate.setHighestRate(maxInt + "");
        heartRate.setLowestRate(minInt + "");
        heartRate.setAverageRate(average + "");
        heartRate.setMotionState(2);
        heartRate.setRecommendState(1);
        heartRate.setExeciseTime(23);
        heartRate.setExeciseLoad(16);
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp recorDate = Timestamp.valueOf(nowTime);
        // SimpleDateFormat dateFormat = new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        heartRate.setRecordDate(recorDate);

        int feedback = heartService.saveHeartRate(heartRate);
        if(feedback == 1){
            logger.debug("-->>测试样例数据：" + heartRate.toString());
            logger.debug("-->>测试样例插入成功");
        }else{
            logger.debug("-->>测试样例插入失败");
        }
        if(feedback == 1){
            reply.setCode("8888");
            reply.setMessage("-->>上传成功！");
        }else{
            reply.setCode("4444");
            reply.setMessage("-->>上传失败！");
        }
        return reply;
    }

    @RequestMapping(value = "/heartrate")
    @ResponseBody
    public ReplyCollection upLoadRealHeartRate(@RequestBody JSONObject jsonObj) {
        int statusUploadFlag = 0;
        ReplyCollection reply = new ReplyCollection();
        logger.debug("-->>json拦截测试输出:" + jsonObj.toJSONString());
        //int realRate = Integer.parseInt(jsonObj.getString("realTimeRate"));
        //int status = Integer.parseInt(jsonObj.getString("status"));
        String uploadTime = jsonObj.getString("uploadTime");
        logger.debug("-->>json拦截测试输出时间:" + uploadTime);
        //logger.debug("-->>json拦截测试输出List数组:" + jsonObj.getJSONArray("realTimeRate").toJSONString());
        //String recordTime = jsonObj.getString("recordTime");
        //JSONArray tempArr = new JSONArray();
        heartService.saveArrHeartRate(jsonObj.getJSONArray("realTimeRate"),uploadTime);//这里保存的心率数据
        Rate rate = new Rate();
        for(Object unit : jsonObj.getJSONArray("realTimeRate")){
            JSONObject object = (JSONObject) unit;
            rate.setRealRate(object.get("realRate")+"");
            rate.setStatus(object.get("status")+"");
            logger.debug("look here:" + rate.toString());
        }

        /**
         * 那问题应该出在这里了
         * jsonObj.getJSONArray("realTimeRate")
         */

        if(statusUploadFlag == 1){
            reply.setCode("8888");
            reply.setMessage("-->>upload successful");
        }else{
            reply.setCode("4444");
            reply.setMessage("-->>upload failured！");
        }
        return reply;
    }

    /**
     * 另一个接口
     * @return
     */
    @RequestMapping(value = "/analyse")
    @ResponseBody
    public ReplyCollection uploadAnalyseHeartRate(@RequestBody JSONObject jsonObj){

        ReplyCollection reply = new ReplyCollection();
        //logger.debug("-->>【心率分析】json拦截测试输出:" + jsonObj.toJSONString());

        return reply;
    }











}
