package com.thinkgem.jeesite.modules.host.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/8.
 */
public interface HostService {
    /**
     * 删除主机
     * @param paramsList
     * @param dbKey
     * @throws Exception
     */
    public void deleteHost(List<Map<String,String>> paramsList, String dbKey) throws Exception;

    /**
     * 新增主机
     * @param param
     * @param dbKey
     * @throws Exception
     */
    public void insertHost(Map<String,String> param, String dbKey) throws Exception;

    /**
     * 查询主机
     * @param param
     * @param dbKey
     * @return
     * @throws Exception
     */
    public Map<String,String> queryHostInfo(Map<String,String> param, String dbKey) throws Exception;

    /**
     * 修改主机
     * @param param
     * @param dbKey
     * @throws Exception
     */
    public void updateHost(Map<String,String> param, String dbKey) throws Exception;

}
