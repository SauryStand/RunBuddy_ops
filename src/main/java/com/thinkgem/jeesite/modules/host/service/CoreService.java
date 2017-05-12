package com.thinkgem.jeesite.modules.host.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:接口层、暴露给Action进行调用，完成数据库的增、删、改、查操作
 * @Author: tangdl
 * @Create: 2017-02-15-0:07
 **/
public interface CoreService {
    /**
     * 查询(多值)
     */
    @Deprecated
    public List<HashMap<String, String>> queryForList(String execKey, Map<String, String> params, String dbKey);


    /**
     * 查询多值，参数与返回值都为Object类型
     * 建议使用此方法
     * Edit Author：tangdl
     * Edit Date：2016-06-21
     * @param execKey
     * @param params
     * @param dbKey
     * @return
     */
    public List<HashMap<String, Object>> queryForList2New(String execKey,Map<String, Object> params,String dbKey);


    /**
     * 查询(单值)
     */
    @Deprecated
    public HashMap<String, String> queryForObject(String execKey,Map<String, String> params,String dbKey);


    /**
     * 查询(单值)，参数与返回值都为Object类型
     * 建议使用此方法
     * Edit Author：tangdl
     * Edit Date：2016-06-21
     * @param execKey
     * @param params
     * @param dbKey
     * @return
     */
    public HashMap<String, Object> queryForObject2New(String execKey,Map<String, Object> params,String dbKey);


    /**
     * 更新
     * 采取循环更新，控制事务，大数据量，效率偏低
     */
    public int updateObject(String execKey,List<Map<String, String>> params,String dbKey);


    /**
     * 单个对象更新
     *
     */
    public int updateObject(String execKey,Map<String, String> param,String dbKey);


    /**
     * 核心Service，批量修改对象并支持分批处理，当数据大于配置的数据量， 则分批处理。
     * @param execKey
     * @param params
     * @param dbKey
     * @return
     */
    public int updateBatchObject(String execKey, List<Map<String, String>> params, String dbKey);


    /**
     * 插入
     * 采取循环插入，控制事务，大数据量，效率偏低
     */
    public int insertObject(String execKey,List<Map<String, String>> params,String dbKey);


    /**
     * 单个插入
     */
    public int insertObject(String execKey,Map<String, String> param,String dbKey);


    /**
     * 批量插入
     * 采取批量插入，控制事务，大数据量效率高
     */
    public int insertBatchObject(String execKey, List<Map<String, String>> params, String dbKey);


    /**
     * 插入(返回SelectKey)
     * 大部分返回的SelectKey为主键，需要在配置sql语句时配置selectKey
     * 采取循环插入，控制事务，大数据量，效率偏低
     */
    public List<String> insertObjectReturnKey(String execKey,List<Map<String, String>> params,String dbKey);


    /**
     * 删除
     * 采取循环删除，控制事务，大数据量，效率偏低
     */
    public int deleteObject(String execKey,List<Map<String, String>> params,String dbKey);


    /**
     * 单个删除
     */
    public int deleteObject(String execKey,Map<String, String> param,String dbKey);


    /**
     * 核心Service，批量删除对象,大数据量 使用该方法
     * @param execKey
     * @param params
     * @param dbKey
     * @return
     */
    public int deleteBatchObject(String execKey, List<Map<String, String>> params,String dbKey);


    /**
     * 综合操作
     * 增、删、改、查可以一次操作
     * 返回结果通过参数中的Map中的execKey值以Map形式返回
     * 循环操作，大数据量，效率偏低
     */
    public HashMap<String, Object> multiOperation(List<Map<String, List<Map<String, String>>>> params,String dbKey);


    /**
     * 查询当前表的最大列值
     * 根据表明与字段名称，进行max操作，然后返回结果值
     */
    public int getMaxValue(Map<String, String> params,String dbKey);


    /**
     * 查询(分页)
     */
    public Map<String, Object> queryPageList(String execKey, int pageSize, int pageIndex, Map<String, String> params, String dbKey);


    /**
     * 获取序列
     */
    public int getSequence(Map<String, String> sequenceName, String dbKey);

    /**
     * 对象查询，例如单值查询，count查询
     */
    public Object commonQryObject(String execKey, Map<String, Object> params, String dbKey);







}
