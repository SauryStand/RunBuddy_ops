package com.thinkgem.jeesite.modules.runbuddy.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.runbuddy.dao.HeartRateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import com.thinkgem.jeesite.modules.runbuddy.service.HeartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HeartRateServiceImpl extends CrudService<HeartRateDao,HeartRate> implements HeartService {

    private static Logger LOGGER = LoggerFactory.getLogger(HeartRateServiceImpl.class);

    @Override
    public List<HeartRate> getAllDate(JSONObject json) {
        return null;
    }

    @Override
    public List<HeartRate> getAllArrayDate(JSONArray jsonArray) {
        StringBuffer heartRateBuffer = new StringBuffer();

        if(jsonArray != null){

        }


        for(Object jsonArray : heartRateBuffer){
            JSONObject jsonObject = (JSONObject)json;
        }

        return null;
    }

    @Override
    public int insertHeartDate(HeartRate heartRateDao) {
        return 0;
    }
}
