package com.thinkgem.jeesite.modules.runbuddy.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.runbuddy.dao.HeartRateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import com.thinkgem.jeesite.modules.runbuddy.service.HeartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 我这里继承了jeesite框架的基类Service，同时也实现了自己写的接口HeartService
 * 若有疑问，请不必惊慌
 */

//unused
//@Service("HeartRateServiceImpl")
public class HeartRateServiceImpl extends CrudService<HeartRateDao,HeartRate>{

    private static Logger LOGGER = LoggerFactory.getLogger(HeartRateServiceImpl.class);



    //插入单一数据
    public List<HeartRate> insertSingleHeartDate(HeartRate heartRate) {
    	logger.debug("-->>现在准备插入一条数据："+heartRate.toString());
        if(heartRate != null){
            save(heartRate);
        }
        
        return null;
    }

    public List<HeartRate> getAllDate(JSONObject json) {
        return null;
    }

    public List<HeartRate> getAllArrayDate(JSONArray jsonArray) {


        if(jsonArray != null){
            StringBuffer heartRateBuffer = new StringBuffer();

            for(Object jsonArr : jsonArray){
                JSONObject jsonObject = (JSONObject) jsonArr;
                HeartRate heartRate = new HeartRate();
                heartRateBuffer.append(jsonObject.get("HIGHEST_RATE")+ ":" + jsonObject.get("LOWEST_RATE"));
                String highestRate =  jsonObject.get("HIGHEST_RATE").toString();
                String lowestRate =  jsonObject.get("LOWEST_RATE").toString();
                heartRate.setHighest_rate(highestRate);
                heartRate.setLowest_rate(lowestRate);
                save(heartRate);//保存数据
            }
        }

        return null;
    }

    @Transactional(readOnly = false)
    public void save(HeartRate heartRate) {
        super.save(heartRate);
        //CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
    }


    public int insertHeartDate(HeartRate heartRateDao) {
        return 0;
    }
}
