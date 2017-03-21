package com.thinkgem.jeesite.modules.runbuddy.service;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.runbuddy.dao.RateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import com.thinkgem.jeesite.modules.runbuddy.entity.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/3/21.
 */

@Service
@Transactional(readOnly = true)
public class RateService extends CrudService<RateDao,Rate>{

    @Autowired
    private RateDao rateDao;

    @Transactional(readOnly = false)
    public int saveRate(Rate rate){
        if(rate != null){
            rate.preInsert();
            rateDao.insert(rate);//插入心率数据
            return 1;
        }
        return 0;
    }


}
