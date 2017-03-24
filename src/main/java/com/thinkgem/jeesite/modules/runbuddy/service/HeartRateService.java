package com.thinkgem.jeesite.modules.runbuddy.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.runbuddy.dao.HeartRateDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.HeartRate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 又是一个service层,为了 查询heartRate这个实体类而创建的，代码写得有点乱
 * Created by Administrator on 2017/3/24.
 */

@Service
@Transactional(readOnly = true)
public class HeartRateService extends CrudService<HeartRateDao,HeartRate> {

    public List<HeartRate> findAllHeartRateList(){
        return dao.findAllHeartRateList(new HeartRate());
    }


}
