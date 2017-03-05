package com.thinkgem.jeesite.modules.runbuddy.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.RealTimeRate;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @Author: Johnny Chou
 * @Create: 2017-02-19-13:43
 **/

@MyBatisDao
public interface RealTimeRateDao extends CrudDao<RealTimeRate> {

    public List<RealTimeRate> getUsefulHeartRate(Date recordTime);//查询什么时候上传的数据

    public List<RealTimeRate> findAllRearRateList(RealTimeRate realTimeRate);//

}
