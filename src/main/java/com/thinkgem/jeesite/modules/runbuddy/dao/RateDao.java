package com.thinkgem.jeesite.modules.runbuddy.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.runbuddy.entity.Rate;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
@MyBatisDao
public interface RateDao extends CrudDao<Rate> {

    public List<Rate> findAllRateData();

}
