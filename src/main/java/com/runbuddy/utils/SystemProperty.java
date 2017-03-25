package com.runbuddy.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;


/**
 * 读取多数据源的配置的
 * Created by Administrator on 2017/3/25.
 */
public class SystemProperty extends PropertyPlaceholderConfigurer {

    private static Logger logger = Logger.getLogger(SystemProperty.class);

    //所有配置文件的属性
    private static Properties properties = null;
    //数据源个数
    private String JDBC_DATA_SOURCE_COUNT = "jdbc.dataSourceCount";
    //url
    private String JDBC_URL = "jdbc.url";
    //数据源密码
    private String JDBC_PASSWORD = "jdbc.password";
    //数据源驱动
    private String JDBC_DRIVER = "jdbc.driverClassName";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)throws BeansException{
        logger.debug("initialize system properties!");
        String dataSourceCount = props.getProperty(JDBC_DATA_SOURCE_COUNT);
        boolean isModel = false;
        if(dataSourceCount != null){
            dataSourceCount = "1";
        }
        String isDriverModel = props.getProperty("isDriverModel");
        if(isDriverModel == null){
            isDriverModel = "jdbc";
        }
        if("log4jdbc".equals(isDriverModel)){
            //props.setProperty("jdbc.driverClassName", "net.sf.log4jdbc.DriverSpy");
            isModel = true;
        }
        int count = Integer.parseInt(dataSourceCount);
        for (int i = 1; i <= count; i++) {
            String srt = JDBC_PASSWORD;
            String url = JDBC_URL;
            String driver = JDBC_DRIVER;
            if (i != 1) {
                srt += "_" + i;
                url += "_" + i;
                driver += "_" + i;
            }
            String password_i = props.getProperty(srt);
            String url_i = props.getProperty(url);
            if(isModel){
                //将驱动名换成log4jdbc
                props.setProperty(url, url_i.replace("jdbc","jdbc:log4jdbc"));
                props.setProperty(driver, "net.sf.log4jdbc.DriverSpy");
            }
            //数据源密码解密,没用到
//            if (password_i != null) {
//                props.setProperty(srt, DesTool.dec(password_i));
//            }
        }
        properties=props;
        logger.debug("初始化properties文件内容为 ---> " + props);
        logger.debug("初始化加载properties完毕");
        super.processProperties(beanFactoryToProcess, props);

    }

    /**
     * 根据key获取属性值
     * @param key
     * @return
     */
    public static String getContextProperty(String key) {
        String value = properties.getProperty(key);
        if(BlankUtil.isBlank(value)){
            return "";
        }
        return value;
    }


}
