package com.thinkgem.jeesite.modules.runbuddy.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;

import java.util.List;

/**
 * @description: 心率DAO接口
 * @Author: Johnny Chou
 * @Create: 2017-02-16-23:31
 **/

@MyBatisDao
public interface HeartRateDao extends CrudDao<HeartRate> {

    //这里的接口都没用到
	public List<HeartRate> insertSingleHeartDate(HeartRate heartRate);
	
    public List<HeartRate> getAllDate(JSONObject json);

    public List<HeartRate> getAllArrayDate(JSONArray jsonArray);

    int insertHeartDate(HeartRate heartRateDao);

    public List<HeartRate> findAllHeartRateList(HeartRate heartRate);//定位到mapper.xml裡面找對應的方法


}
