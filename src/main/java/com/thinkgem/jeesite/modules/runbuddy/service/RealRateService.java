package com.thinkgem.jeesite.modules.runbuddy.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.runbuddy.dao.RealTimeRateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.RealTimeRate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @Author: Johnny Chou
 * @Create: 2017-03-05-18:25
 **/

@Service
@Transactional(readOnly = true)
public class RealRateService extends CrudService<RealTimeRateDao,RealTimeRate> {


    /**
     * 页面显示的查询出来的list数组
     * @return
     */
    public List<RealTimeRate> findAllRearRateList(){
        return dao.findAllRearRateList(new RealTimeRate());
    }



}
