package com.runbuddy.utils.db;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DaoSupport;

/**
 * Created by Administrator on 2017/3/25.
 */
public class SqlSessionDaoSupport extends DaoSupport implements InitializingBean{

    private static Logger logger = Logger.getLogger(SqlSessionDaoSupport.class);


    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {

    }
}
