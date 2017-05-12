package com.thinkgem.jeesite.modules.host.service.Impl;

import org.springframework.stereotype.Service;

/**
 * @description: 基础接口类
 * @Author: Johnny Chou
 * @Create: 2017-02-15-0:07
 **/
@Service("coreService")
public class CoreServiceImpl {
//    /**
//     * log4j日志对象
//     */
//    private static Logger log = Logger.getLogger(CoreServiceImpl.class);
//
//    /**
//     * 注入baseDao
//     */
//    @Autowired
//    protected CoreBaseDao coreBaseDao=null;
//
//    /**
//     * 是否需要往上抛RuntimeException原始信息
//     */
//    protected boolean throwOriginalException = SystemProperty.getContextProperty("throwOriginalException")==null?true:Boolean.parseBoolean(SystemProperty.getContextProperty("throwOriginalException"));
//
//    /**
//     * 核心service，查询多行记录
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public List<HashMap<String, String>> queryForList(String execKey,Map<String, String> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，查询多行记录开始");
//        log.debug("核心Service，执行sql语句Key值 ---> " + execKey);
//        log.debug("核心Service，查询参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        List resultData = null;
//        try{
//            resultData = coreBaseDao.getSqlSession().selectList(execKey, params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return resultData==null?new ArrayList<HashMap<String, String>>():resultData;
//    }
//
//    @Override
//    public List<HashMap<String, Object>> queryForList2New(String execKey, Map<String, Object> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，查询多行记录开始");
//        log.debug("核心Service，执行sql语句Key值 ---> " + execKey);
//        log.debug("核心Service，查询参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        List resultData = null;
//        try{
//            resultData = coreBaseDao.getSqlSession().selectList(execKey, params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return resultData==null?new ArrayList<HashMap<String, String>>():resultData;
//    }
//
//    /**
//     * 核心Service，查询单行记录
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public HashMap<String, String> queryForObject(String execKey,Map<String, String> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，查询单行记录开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，查询参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        HashMap resultdata = null;
//        try{
//            resultdata = (HashMap) coreBaseDao.getSqlSession().selectOne(execKey,params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return resultdata==null?new HashMap<String, String>():resultdata;
//    }
//
//    @Override
//    public HashMap<String, Object> queryForObject2New(String execKey, Map<String, Object> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，查询单行记录开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，查询参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        HashMap resultdata = null;
//        try{
//            resultdata = (HashMap) coreBaseDao.getSqlSession().selectOne(execKey,params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return resultdata==null?new HashMap<String, String>():resultdata;
//    }
//
//    public Object commonQryObject(String execKey, Map<String, Object> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，查询单行记录开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，查询参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        Object resultdata = null;
//        try{
//            resultdata = coreBaseDao.getSqlSession().selectOne(execKey,params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return resultdata==null?new Object():resultdata;
//    }
//
//    /**
//     * 核心Service，更新对象
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public int updateObject(String execKey, List<Map<String, String>> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，更新对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        for (int i = 0; i < params.size(); i++) {
//            Map paramsObject = (Map) params.get(i);
//            try{
//                affectedRow += coreBaseDao.getSqlSession().update(execKey, paramsObject);
//            } catch (RuntimeException e){
//                throwException(e,execKey,paramsObject,dbKey);
//            }
//        }
//        return affectedRow;
//    }
//    /**
//     * 核心Service，更新对象
//     * @param execKey
//     * @param dbKey
//     * @return
//     */
//    public int updateObject(String execKey, Map<String, String> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，更新对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> " + dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        try{
//            affectedRow = coreBaseDao.getSqlSession().update(execKey, params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return affectedRow;
//
//    }
//    /**
//     * 核心Service，批量修改对象并支持分批处理，当数据大于配置的数据量， 则分批处理。
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public int updateBatchObject(String execKey, List<Map<String, String>> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，批量插入对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> " + dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//
//        int count = Integer.parseInt(
//                (SystemProperty.getContextProperty("db_commit_count")==null||SystemProperty.getContextProperty("db_commit_count").equals(""))
//                        ?"100":SystemProperty.getContextProperty("db_commit_count")
//        );
//        if(params.size()<= count){//如果数据小于配置的数据， 则无需分批插入
//            try{
//                affectedRow = coreBaseDao.getSqlSession().update(execKey, params);
//            } catch (RuntimeException e){
//                throwException(e,execKey,params,dbKey);
//            }
//        }else{
//            while(true){//一直循环处理，当数组里的数据全部取完，则才会退出
//                List tempList = new ArrayList();
//                boolean isCommit = false;
//                if(params.size()<1){
//                    break;
//                }
//                for(int i = 0 ; i < count && params.size()>0; i ++){
//                    tempList.add(params.get(0));//每次取第一个
//                    params.remove(0);//插入之后立刻删掉，必免重复
//                    isCommit = true;//如果有数据，则需要插入数据库的标识
//                }
//                if(isCommit){
//                    try{
//                        affectedRow = coreBaseDao.getSqlSession().update(execKey, tempList);
//                    } catch (RuntimeException e){
//                        throwException(e,execKey,tempList,dbKey);
//                    }
//                }
//            }
//        }
//        return affectedRow;
//    }
//
//    /**
//     * 核心Service，插入对象
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public int insertObject(String execKey, List<Map<String, String>> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，插入对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> " + execKey);
//        log.debug("核心Service，执行参数 ---> " +CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> " + dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        for (int i = 0; i < params.size(); i++) {
//            Map paramsObject = (Map) params.get(i);
//            try{
//                affectedRow += coreBaseDao.getSqlSession().insert(execKey, paramsObject);
//            } catch (RuntimeException e){
//                throwException(e,execKey,paramsObject,dbKey);
//            }
//        }
//        return affectedRow;
//    }
//    /**
//     * 核心Service，插入对象
//     * @param execKey
//     * @param dbKey
//     * @return
//     */
//    public int insertObject(String execKey, Map<String, String> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，插入对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> " + execKey);
//        log.debug("核心Service，执行参数 ---> " +CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> " + dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        try{
//            affectedRow = coreBaseDao.getSqlSession().insert(execKey, params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return affectedRow;
//
//    }
//
//    /**
//     * 核心Service，批量插入对象并支持分批处理，当数据大于配置的数据量， 则分批处理。
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public int insertBatchObject(String execKey, List<Map<String, String>> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，批量插入对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> " + dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        int count = Integer.parseInt(
//                (SystemProperty.getContextProperty("db_commit_count")==null||SystemProperty.getContextProperty("db_commit_count").equals(""))
//                        ?"100":SystemProperty.getContextProperty("db_commit_count")
//        );
//        if(params.size()<= count){//如果数据小于配置的数据， 则无需分批插入
//            try{
//                affectedRow = coreBaseDao.getSqlSession().insert(execKey, params);
//            } catch (RuntimeException e){
//                throwException(e,execKey,params,dbKey);
//            }
//        }else{
//            while(true){//一直循环处理，当数组里的数据全部取完，则才会退出
//                List tempList = new ArrayList();
//                boolean isCommit = false;
//                if(params.size()<1){
//                    break;
//                }
//                for(int i = 0 ; i < count && params.size()>0; i ++){
//                    tempList.add(params.get(0));//每次取第一个
//                    params.remove(0);//插入之后立刻删掉，必免重复
//                    isCommit = true;//如果有数据，则需要插入数据库的标识
//                }
//                if(isCommit){
//                    try{
//                        affectedRow = coreBaseDao.getSqlSession().insert(execKey, tempList);
//                    } catch (RuntimeException e){
//                        throwException(e,execKey,tempList,dbKey);
//                    }
//                }
//            }
//        }
//        return affectedRow;
//    }
//
//    /**
//     * 核心Service，插入对象[返回主键]
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public List<String> insertObjectReturnKey(String execKey,List<Map<String, String>> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，插入对象[返回主键]开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        List keyList = new ArrayList();
//        for (int i = 0; i < params.size(); i++) {
//            Map paramsObject = (Map) params.get(i);
//            int returnKey = 0;
//            try{
//                returnKey = coreBaseDao.getSqlSession().insert(execKey, paramsObject);
//            } catch (RuntimeException e){
//                throwException(e,execKey,paramsObject,dbKey);
//            }
//            keyList.add(String.valueOf(returnKey));
//        }
//        return keyList;
//    }
//
//    /**
//     * 核心Service，删除对象
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public int deleteObject(String execKey, List<Map<String, String>> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，删除对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        for (int i = 0; i < params.size(); i++) {
//            Map paramsObject = (Map) params.get(i);
//            try{
//                affectedRow += coreBaseDao.getSqlSession().delete(execKey, paramsObject);
//            } catch (RuntimeException e){
//                throwException(e,execKey,paramsObject,dbKey);
//            }
//        }
//        return affectedRow;
//    }
//    /**
//     * 核心Service，删除对象
//     * @param execKey
//     * @param dbKey
//     * @return
//     */
//    public int deleteObject(String execKey, Map<String, String> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，删除对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> " + dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        try{
//            affectedRow = coreBaseDao.getSqlSession().delete(execKey, params);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        return affectedRow;
//
//    }
//    /**
//     * 核心Service，批量删除对象
//     * @param execKey
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public int deleteBatchObject(String execKey, List<Map<String, String>> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，批量删除对象开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int affectedRow = 0;
//        int count = Integer.parseInt(
//                (SystemProperty.getContextProperty("db_commit_count")==null||SystemProperty.getContextProperty("db_commit_count").equals(""))
//                        ?"100":SystemProperty.getContextProperty("db_commit_count")
//        );
//        if(params.size()<= count){//如果数据小于配置的数据， 则无需分批插入
//            try{
//                affectedRow = coreBaseDao.getSqlSession().delete(execKey, params);
//            } catch (RuntimeException e){
//                throwException(e,execKey,params,dbKey);
//            }
//        }else{
//            while(true){//一直循环处理，当数组里的数据全部取完，则才会退出
//                List tempList = new ArrayList();
//                boolean isCommit = false;
//                if(params.size()<1){
//                    break;
//                }
//                for(int i = 0 ; i < count && params.size()>0; i ++){
//                    tempList.add(params.get(0));//每次取第一个
//                    params.remove(0);//插入之后立刻删掉，必免重复
//                    isCommit = true;//如果有数据，则需要插入数据库的标识
//                }
//                if(isCommit){
//                    try{
//                        affectedRow = coreBaseDao.getSqlSession().delete(execKey, tempList);
//                    } catch (RuntimeException e){
//                        throwException(e,execKey,tempList,dbKey);
//                    }
//                }
//            }
//        }
//        return affectedRow;
//    }
//
//
//    /**
//     * 核心Service，综合操作
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public HashMap<String, Object> multiOperation(List<Map<String, List<Map<String, String>>>> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，综合操作开始");
//        log.debug("核心Service，执行参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        HashMap resultObject = new HashMap();
//        for (int i = 0; i < params.size(); i++) {
//            Map operationMap = (Map) params.get(i);
//            Set set = operationMap.entrySet();
//            for (Iterator it = set.iterator(); it.hasNext(); ) {
//                Map.Entry entry = (Map.Entry) it.next();
//                String mapKey = (String) entry.getKey();
//                List paramsList = (List) entry.getValue();
//                String[] keyArray = mapKey.split("\\|");
//                String operationType = keyArray[0];
//                String execKey = keyArray[1];
//                if (operationType.equals("select")) {
//                    Map paramsMap = (Map) paramsList.get(0);
//                    log.debug("核心Service，综合操作，查询操作");
//                    log.debug("核心Service，综合操作，查询参数 ---> "+paramsMap);
//                    log.debug("核心Service，综合操作，数据源Key值为 ---> "+dbKey);
//                    resultObject.put(mapKey,queryForList(execKey, paramsMap, dbKey));
//                } else if (operationType.equals("update")) {
//                    log.debug("核心Service，综合操作，更新操作");
//                    log.debug("核心Service，综合操作，更新参数 ---> "+paramsList);
//                    log.debug("核心Service，综合操作，数据源Key值为 ---> "+dbKey);
//                    resultObject.put(mapKey, Integer.valueOf(updateObject(execKey, paramsList, dbKey)));
//                } else if (operationType.equals("insert")) {
//                    log.debug("核心Service，综合操作，插入操作");
//                    log.debug("核心Service，综合操作，插入参数 ---> "+paramsList);
//                    log.debug("核心Service，综合操作，数据源Key值为 ---> "+dbKey);
//                    resultObject.put(mapKey, Integer.valueOf(insertObject(execKey, paramsList, dbKey)));
//                } else if (operationType.equals("delete")) {
//                    log.debug("核心Service，综合操作，删除操作");
//                    log.debug("核心Service，综合操作，删除参数 ---> "+paramsList);
//                    log.debug("核心Service，综合操作，数据源Key值为 ---> "+dbKey);
//                    resultObject.put(mapKey, Integer.valueOf(deleteObject(execKey, paramsList, dbKey)));
//                } else {
//                    throw new RuntimeException("核心Service，综合操作，找不到相应的处理类型");
//                }
//            }
//        }
//        return resultObject;
//    }
//
//    public int getMaxValue(Map<String, String> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，获取最大值开始");
//        log.debug("核心Service，执行参数 ---> "+params);
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int sequence = 0;
//        try{
//            sequence = ((Integer) coreBaseDao.getSqlSession().selectOne(FrameConfigKey.QUERY_MAXVALUE_EXECKEY, params)).intValue();
//        } catch (RuntimeException e){
//            throwException(e,FrameConfigKey.QUERY_MAXVALUE_EXECKEY,params,dbKey);
//        }
//        return sequence;
//    }
//
//    /**
//     * 核心Service，查询序列
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public int getSequence(Map<String, String> params, String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，查询序列开始");
//        log.debug("核心Service，执行参数 ---> "+params);
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        DbContextHolder.setDbType(dbKey);
//        int sequence = 0;
//        try{
//            sequence = ((Integer) coreBaseDao.getSqlSession().selectOne(FrameConfigKey.QUERY_SEQUENCE_EXEC_KEY, params)).intValue();
//        } catch (RuntimeException e){
//            throwException(e,FrameConfigKey.QUERY_SEQUENCE_EXEC_KEY,params,dbKey);
//        }
//        return sequence;
//    }
//    /**
//     * 核心Service，分页查询
//     * @param execKey
//     * @param pageSize
//     * @param pageIndex
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public Map<String, Object> queryPageList(String execKey,int pageSize, int pageIndex, Map<String, String> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，分页查询开始");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，查询参数 ---> "+CommonTool.printParams(params));
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        log.debug("核心Service，分页页码 ---> "+pageIndex);
//        log.debug("核心Service，分页大小 ---> "+pageSize);
//        DbContextHolder.setDbType(dbKey);
//        int offset = pageIndex * pageSize;
//        RowBounds rb = new RowBounds(offset, pageSize);
//        List<Map<String,Object>> resultData = null;
//        try{
//            resultData = coreBaseDao.getSqlSession().selectList(execKey, params,rb);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        PageInfo<Map<String,Object>> page = new PageInfo(resultData);
//        Map resultMap = new HashMap();
//        resultMap.put(FrameParamsDefKey.PAGE_INDEX, page.getPageNum());
//        resultMap.put(FrameParamsDefKey.PAGE_SIZE, page.getPageSize());
//        resultMap.put(FrameParamsDefKey.TOTAL, page.getTotal());
//        resultMap.put(FrameParamsDefKey.DATA, page.getList());
//        return resultMap;
//    }
//
//
//    /**
//     * 核心Service，分页查询[oracle]
//     * @param execKey
//     * @param pageSize
//     * @param pageIndex
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public Map<String, Object> queryPageListForOrcl(String execKey,int pageSize, int pageIndex, Map<String, String> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，分页查询开始[oracle]");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，查询参数 ---> "+params);
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        log.debug("核心Service，分页页码 ---> "+pageIndex);
//        log.debug("核心Service，分页大小 ---> "+pageSize);
//        DbContextHolder.setDbType(dbKey);
//        int offset = pageIndex * pageSize;
//        RowBounds rb = new RowBounds(offset, pageSize);
//        List<Map<String,Object>> resultData = null;
//        try{
//            resultData = coreBaseDao.getSqlSession().selectList(execKey, params,rb);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        PageInfo<Map<String,Object>> page = new PageInfo(resultData);
//        Map resultMap = new HashMap();
//        resultMap.put(FrameParamsDefKey.PAGE_INDEX, page.getPageNum());
//        resultMap.put(FrameParamsDefKey.PAGE_SIZE, page.getPageSize());
//        resultMap.put(FrameParamsDefKey.TOTAL, page.getTotal());
//        resultMap.put(FrameParamsDefKey.DATA, page.getList());
//        return resultMap;
//    }
//
//    /**
//     * 核心Service，分页查询[mysql]
//     * @param execKey
//     * @param pageSize
//     * @param pageIndex
//     * @param params
//     * @param dbKey
//     * @return
//     */
//    public Map<String, Object> queryPageListForMySql(String execKey,int pageSize, int pageIndex, Map<String, String> params,String dbKey) {
//        DbContextHolder.clearDbType();
//        log.debug("核心Service，分页查询开始[mysql]");
//        log.debug("核心Service，执行sql语句Key值 ---> "+execKey);
//        log.debug("核心Service，查询参数 ---> "+params);
//        log.debug("核心Service，数据源Key值为 ---> "+dbKey);
//        log.debug("核心Service，分页页码 ---> "+pageIndex);
//        log.debug("核心Service，分页大小 ---> "+pageSize);
//        DbContextHolder.setDbType(dbKey);
//        int offset = pageIndex * pageSize;
//        RowBounds rb = new RowBounds(offset, pageSize);
//        List<Map<String,Object>> resultData = null;
//        try{
//            resultData = coreBaseDao.getSqlSession().selectList(execKey, params,rb);
//        } catch (RuntimeException e){
//            throwException(e,execKey,params,dbKey);
//        }
//        PageInfo<Map<String,Object>> page = new PageInfo(resultData);
//        Map resultMap = new HashMap();
//        resultMap.put(FrameParamsDefKey.PAGE_INDEX, page.getPageNum());
//        resultMap.put(FrameParamsDefKey.PAGE_SIZE, page.getPageSize());
//        resultMap.put(FrameParamsDefKey.TOTAL, page.getTotal());
//        resultMap.put(FrameParamsDefKey.DATA, page.getList());
//        return resultMap;
//    }
//
//    /**
//     * 捕获的异常处理
//     * @Title: throwException
//     * @return: void
//     * @author: tianjc
//     * @date: 2016年9月19日 下午3:47:37
//     * @editAuthor:
//     * @editDate:
//     * @editReason:
//     */
//    public void throwException(RuntimeException e,String execKey,Map<String, ?> params, String dbKey){
//        if (throwOriginalException) {
//            //将原始RuntimeException往上抛
//            throw e;
//        } else {
//            log.error(e.getMessage(),e);
//            //去除数据库异常敏感信息
//            //去除数据库异常敏感信息,IBM appscan扫描，前台显示params会反馈《参数系统调用代码注入》,《文件参数 Shell 命令注入》
//            throw new PersistenceException("数据库查询异常    execKey:"+ execKey);
//        }
//    }
//
//    /**
//     * 捕获的异常处理
//     * @Title: throwException
//     * @return: void
//     * @author: tianjc
//     * @date: 2016年9月19日 下午4:01:40
//     * @editAuthor:
//     * @editDate:
//     * @editReason:
//     */
//    public void throwException(RuntimeException e,String execKey,List<?> params, String dbKey){
//        if (throwOriginalException) {
//            //将原始RuntimeException往上抛
//            throw e;
//        } else {
//            log.error(e.getMessage(),e);
//            //去除数据库异常敏感信息,IBM appscan扫描，前台显示params会反馈《参数系统调用代码注入》,《文件参数 Shell 命令注入》
//            throw new PersistenceException("数据库查询异常    execKey:"+ execKey);
//        }
//    }
//
//    public static void main(String[] arg){
//        List<Map<String,String>> params = new ArrayList<Map<String, String>>();
//        for(int i = 0 ; i < 20 ; i ++){
//            Map map = new HashMap();
//            map.put("a"+i,"a"+i);
//            params.add(map);
//        }
//        while(true){
//            List tempList = new ArrayList();
//            boolean isCommit = false;
//            if(params.size()<1){
//                break;
//            }
//            for(int i = 0 ; i <19 && params.size()>0; i ++){
//                tempList.add(params.get(0));
//                params.remove(0);
//                isCommit = true;
//            }
//            if(isCommit){
//                System.out.println(tempList);
//            }
//        }
//    }


}
