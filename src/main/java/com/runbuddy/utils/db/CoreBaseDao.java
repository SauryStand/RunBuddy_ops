package com.runbuddy.utils.db;

import org.springframework.stereotype.Service;

/**
 * CoreBaseDao  基类
 * Created by Administrator on 2017/5/8.
 */
@Service("coreBaseDao")
public class CoreBaseDao {
//    /**
//     * 此处required = false
//     * 主要是为了，在spring-datasource.xml文件中，注入此对象时可以通过配置文件进行控制
//     * 如果不需要使用jdbcTemplate，则可以直接在配置文件中屏蔽掉
//     */
//    @Autowired(required = false)
//    private JdbcDaoSupport jdbcDaoSupport;
//
//    /**
//     * 此处required = false
//     * 主要是为了，在spring-datasource.xml文件中，注入此对象时可以通过配置文件进行控制
//     * 如果不需要使用mybatis时，则可以直接在配置文件中屏蔽掉
//     */
//    @Autowired(required = false)
//    private SqlSessionDaoSupport sqlSessionDaoSupport;
//
//
//    public JdbcDaoSupport getJdbcDaoSupport() {
//        return jdbcDaoSupport;
//    }
//
//    public SqlSessionDaoSupport getSqlSessionDaoSupport() {
//        return sqlSessionDaoSupport;
//    }
//
//    public void setJdbcDaoSupport(JdbcDaoSupport jdbcDaoSupport) {
//        this.jdbcDaoSupport = jdbcDaoSupport;
//    }
//
//    public void setSqlSessionDaoSupport(SqlSessionDaoSupport sqlSessionDaoSupport) {
//        this.sqlSessionDaoSupport = sqlSessionDaoSupport;
//    }
//
//    public JdbcTemplate getJdbcTemplate() {
//        return jdbcDaoSupport.getJdbcTemplate();
//    }
//
//    public SqlSession getSqlSession() {
//        return sqlSessionDaoSupport.getSqlSession();
//    }
//


}
