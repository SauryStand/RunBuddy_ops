package com.runbuddy.service.Impl;

import com.runbuddy.dao.entity.HeartRate;
import com.runbuddy.service.HeartRateServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @Author: Johnny Chou
 * @Create: 2017-01-28-22:51
 **/
@Service("HeartRateServiceImpl")
public class HeartRateServiceImpl implements HeartRateServiceI{

    @Autowired
    private HeartRateServiceI heartRateServiceI;

    @Override
    public List<HeartRate> getAllDate() {
        heartRateServiceI.getAllDate();
        return null;
    }

    @Override
    public int insertHeartDate(HeartRate heartRate) {
        heartRateServiceI.insertHeartDate(heartRate);
        return 0;
    }
}
