package com.runbuddy.service;

import com.runbuddy.dao.entity.HeartRate;

import java.util.List;

/**
 * @description:接口类
 * @Author: Johnny Chou
 * @Create: 2017-01-28-22:52
 **/

public interface HeartRateServiceI {
    List<HeartRate> getAllDate();
    int insertHeartDate(HeartRate heartRateDao);

}
