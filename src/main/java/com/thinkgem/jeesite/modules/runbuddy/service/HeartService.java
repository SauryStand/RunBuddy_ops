package com.thinkgem.jeesite.modules.runbuddy.service;

import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;

import java.util.List;

public interface HeartService {
    List<HeartRate> getAllDate();
    int insertHeartDate(HeartRate heartRateDao);
}
