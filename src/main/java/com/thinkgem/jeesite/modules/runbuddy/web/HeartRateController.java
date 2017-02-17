package com.thinkgem.jeesite.modules.runbuddy.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.runbuddy.common.ReplyCollection;
import com.thinkgem.jeesite.modules.runbuddy.dao.HeartRateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import com.thinkgem.jeesite.modules.runbuddy.service.HeartService;
import com.thinkgem.jeesite.modules.runbuddy.service.Impl.HeartRateServiceImpl;

@Controller
public class HeartRateController {

	private static Logger logger = Logger.getLogger(HeartRateController.class);


	//@Autowired
	//private HeartRateDao heartRateDao;
	
	@Autowired
	private HeartService heartService;
	

	@RequestMapping(value = "/hearts")
	@ResponseBody
	public String upLoadHeartRate(String json) {
		logger.debug("-->>RunBuddy_ops拦截，测试输出");
		HeartRate heartRate = new HeartRate();
		heartRate.setHighest_rate("126");
		heartRate.setLowest_rate("56");
		heartRate.setAverage_rate("78");
		heartRate.setMotion_state(2);
		heartRate.setRecommend_state(1);
		heartRate.setExecise_time(26);
		heartRate.setExecise_load(18);
		Date date = new Date();
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(date);
		Timestamp recorDate = Timestamp.valueOf(nowTime);
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		heartRate.setRecord_date(recorDate);
		logger.debug("-->>测试样例数据：" + heartRate.toString());
		heartService.saveHeartRate(heartRate);
		return "";
	}

	@RequestMapping(value = "/heartrate")
	@ResponseBody
	public ReplyCollection upLoadHeartRate(@RequestBody JSONObject json) {

		ReplyCollection reply = new ReplyCollection();
		logger.debug("-->>拦截测试输出");
		System.out
				.println("------>>look here:" + json.toJSONString() + "测试~~~");
		reply.setCode("8888");
		reply.setMessage("-->>upload successful");
		// todo
		return reply;
	}
}
