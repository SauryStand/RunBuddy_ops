package com.thinkgem.jeesite.modules.runbuddy.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.runbuddy.dao.HeartRateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;

/**
 * 仿照SystemService写的RunBuddy模块service
 */
@Service
@Transactional(readOnly = true)
public class HeartService extends BaseService {

	
	@Autowired
	private HeartRateDao heartRateDao;
	
	@Transactional(readOnly = false)
	public void saveHeartRate(HeartRate heartRate){
		if(heartRate != null){
			heartRate.preInsert();
			heartRateDao.insert(heartRate);//插入心率数据
		}
	}
	


	
	
}
