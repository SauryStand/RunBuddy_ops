//用于action url
Globals.baseActionUrl = {
    //todo
    /**********************框架的核心方法  start *************************/
    //用于综合处理方法简单逻辑，如需发多条sql语句
    FRAME_MULTI_OPERATION_URL :Globals.ctx +"/core/multiOperation",
    //根据条件查询某个对象
    FRAME_QUERY_FOR_OBJECT_URL :Globals.ctx +"/core/queryForObject",
    //插入对象
    FRAME_INSERT_OBJECT_URL :Globals.ctx +"/core/insertObject",
    //批量插入对象
    FRAME_INSERT_BATCH_OBJECT_URL :Globals.ctx +"/core/insertBatchObject"
    //更新对象

    
};
//用于jsp url
Globals.baseJspUrl = {
    /**********************用户管理  start************************/
   //用户管理--增加和修改页面
   USER_JSP_ADD_EDIT_URL: Globals.ctx + "/jsp/setting/sysmanage/user/addEditUser",
    //详情
    USER_JSP_VIEW_EDIT_URL: Globals.ctx + "/jsp/setting/sysmanage/user/viewUser"
    //用户管理--增加和修改页面

};