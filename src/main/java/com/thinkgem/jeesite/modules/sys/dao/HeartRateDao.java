package com.thinkgem.jeesite.modules.sys.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.HeartRate;

import java.util.List;

/**
 * @description: 心率DAO接口
 * @Author: Johnny Chou
 * @Create: 2017-02-16-23:31
 **/

@MyBatisDao
public interface HeartRateDao extends CrudDao<HeartRate> {

	public List<HeartRate> insertSingleHeartDate(HeartRate heartRate);
	
    public List<HeartRate> getAllDate(JSONObject json);

    public List<HeartRate> getAllArrayDate(JSONArray jsonArray);

    int insertHeartDate(HeartRate heartRateDao);

}
