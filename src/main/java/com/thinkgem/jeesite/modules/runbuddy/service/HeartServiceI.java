package com.thinkgem.jeesite.modules.runbuddy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;

import java.util.List;

public interface HeartService {

    List<HeartRate> getAllDate(JSONObject json);

    List<HeartRate> getAllArrayDate(JSONArray jsonArray);

    int insertHeartDate(HeartRate heartRateDao);
}
