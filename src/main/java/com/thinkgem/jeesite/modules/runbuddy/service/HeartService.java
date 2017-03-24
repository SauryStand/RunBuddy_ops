package com.thinkgem.jeesite.modules.runbuddy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.runbuddy.dao.HeartRateDao;
import com.thinkgem.jeesite.modules.runbuddy.dao.RealTimeRateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import com.thinkgem.jeesite.modules.runbuddy.entity.RealTimeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 仿照SystemService写的RunBuddy模块service
 */
@Service
@Transactional(readOnly = true)
public class HeartService extends BaseService {

	//private static Logger logger = Logger.getLogger(HeartService.class);
	@Autowired
	private HeartRateDao heartRateDao;

	@Autowired
	private RealTimeRateDao realTimeRateDao;

	
	@Transactional(readOnly = false)
	public int saveHeartRate(HeartRate heartRate){
		if(heartRate != null){
			heartRate.preInsert();
			heartRateDao.insert(heartRate);//插入心率数据,这个是最先的成功例子
			return 1;
		}
		return 0;
	}


	//将事务设为false才能插入数据
	@Transactional(readOnly = false)
	public int saveArrHeartRate(JSONArray realRate,String recordTime){
		//logger.debug("-->>插入实时心率数组："+realRate.toJSONString());
		System.out.println("-->>进到这里了");
		RealTimeRate realTimeRate = new RealTimeRate();
		StringBuffer realTimeBuffer = new StringBuffer();
		Date date = new Date();
		DateFormat record = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Timestamp recorDate = Timestamp.valueOf(nowTime);
		if(realRate != null){

			for(Object realjson:realRate){
				JSONObject object = (JSONObject) realjson;
				realTimeBuffer.append(object.get("value")+":"+object.get("status"));
				//判断是否到最后一个
				if(realRate.indexOf(realjson)!= realRate.size()-1){
					realTimeBuffer.append("|");
				}
			}
			realTimeRate.setRealTimeRate(realTimeBuffer+"");//心率数据
			try {
				realTimeRate.setUploadTime(record.parse(recordTime));//将字符格式的日期转回Dateg格式
				realTimeRate.setRecordTime(new Date());//这个是记录插入所有数据的时间
			} catch (ParseException e) {
				logger.info("-->>记录产生心率数据的时间，不是上传到服务器的时间");
				e.printStackTrace();
			}
		}

		realTimeRate.preInsert();//预处理，乱框架自带，目前还搞不清楚
		realTimeRateDao.insert(realTimeRate);//插入数据,不知道有什么bug没

		return 0;
	}


	
}
