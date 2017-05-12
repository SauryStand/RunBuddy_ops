//定义变量， 通常是页面控件和参数
var JsVar = new Object();
//初始化
$(document).ready(function () {
    mini.parse();
    JsVar["hostGrid"] = mini.get("hostGrid");//取得主机表格
    JsVar["queryFrom"] =  new mini.Form("#queryFrom");//取得查询表单
    //加载主机表格信息
    search();
});

//查询
function search() {
    var paramsObj = JsVar["queryFrom"].getData();
    load(paramsObj);
}
//重新加载表格
function refresh() {
    JsVar["queryFrom"].reset();
    load(null);
}
//加载表格
function load(param){
    datagridLoadPage(JsVar["hostGrid"],param,"host.queryHostList");
}

//渲染操作按钮
function onActionRenderer(e) {
	 var index = e.rowIndex;
	var HOST_STATE=e.record.HOST_STATE;
    var HOST_ID = e.record.HOST_ID;
    var RUN_STATE=e.record.RUN_STATE;
    var ENV_STATE = e.record.ENV_STATE;
    var html = '<a class="Delete_Button" href="javascript:edit(' + index + ')">修改</a>';
    html += '<a class="Delete_Button" href="javascript:test(' + index + ')">测试</a>';
    html += '<a class="Delete_Button" href="javascript:singleTermial(\'' + HOST_ID + '\')">终端操作</a>';
    return html;
}
//渲染主机名称单元格成超链接
function onRenderTopName(e){
	var index=e.rowIndex; 
	return '<a class="Delete_Button" href="javascript:hostDetail('+index+')">'+e.record.HOST_IP+'</a>';
}
//详细信息
function hostDetail(index){
	var row = JsVar["hostGrid"].getRow(index);
	showDialog("详细信息",600,400,Globals.baseJspUrl.HOST_JSP_DETAIL_URL,
	        function destroy(data){
	            
	    },row);
}
	
//新增主机
function add() {
    showAddDialog("主机管理-新增主机",780,470,Globals.baseJspUrl.HOST_JSP_ADD_EDIT_URL,
        function destroy(data){
            if (data == systemVar.SUCCESS) {
                JsVar["hostGrid"].reload();
                showMessageAlter("新增主机成功");
            }
    });
}
//修改用户
function edit(index) {
	var row;
    //单个操作时，index值不为空也不为undefined；批量操作时，index不传，值为undefined
    if(index!=undefined){
        //单个操作
		row = JsVar["hostGrid"].getRow(index);
	}else{
        //批量操作
		var rows = JsVar["hostGrid"].getSelecteds();
	    if (rows.length == 1) {
		    row = rows[0];
	    }
	    else {
	        showWarnMessageAlter("请选中一条记录!") ;
	        return;
	    }
	}
	showEditDialog("修改主机",780,470,Globals.baseJspUrl.HOST_JSP_ADD_EDIT_URL,
	        function destroy(data){
	            if (data == systemVar.SUCCESS) {
	                JsVar["hostGrid"].reload();
	                showMessageAlter("修改主机成功!");
	            }
	    },row);
}

//删除主机 
function del(index) {
  //创建一个集合,用于存放被勾选的ID
	var ids = new Array();
    //单个操作时，index值不为空也不为undefined；批量操作时，index不传，值为undefined
    if(index!=undefined){
        //单个操作
		var row = JsVar["hostGrid"].getRow(index);
		ids.push({HOST_ID:row["HOST_ID"]});
	}else{
		//得到被勾选的行对象
		var rows = JsVar["hostGrid"].getSelecteds();
	    if (rows.length > 0) {
	        for (var i = 0; i < rows.length; i++) {
	        	//此处缺少一个判断,集群是否能够删除
				ids.push({HOST_ID:rows[i]["HOST_ID"]});
	        }
	    }
	    else {
	        showWarnMessageAlter("请选中一条记录!") ;
	        return;
	    }
	    
	}
    var flag;
    //业务--查询时都有下挂程序
//    getJsonDataByPost(Globals.baseActionUrl.FRAME_QUERY_FOR_OBJECT_URL,{ids:ids},'主机管理--查询主机是否存在下挂程序',
//    		function(result){
//    	if(result["SUM"] >0){
//    		flag= true;
//    	}
//    },"deployTask.queryHostByAppCount","",false);
    
    //框架--查询是否存在下挂程序
	getJsonDataByPost(Globals.baseActionUrl.FRAME_QUERY_FOR_OBJECT_URL,{ids:ids},'主机管理--查询主机是否存在下挂程序',
		function(result){
		if(result["SUM"] >0){
			flag= true;
		}
	},"host.queryCountByHostIds","",false);
    if(flag){
    	showWarnMessageAlter("所选主机中已划分，不能进行删除操作!");
    	return;
    }
    
    showConfirmMessageAlter("确定删除记录？",function ok(){
    	getJsonDataByPost(Globals.baseActionUrl.HOST_ACTION_DELETE_URL,ids,"主机管理-删除主机",
                function(result){
                    JsVar["hostGrid"].reload();
                    showMessageAlter("删除主机成功!");
                });
    	 
    });
}

//测试主机能否登录sftp  
function test(index){
	var obj = {};
	var row = JsVar["hostGrid"].getRow(index);
	obj["HOST_IP"] = row["HOST_IP"];
	obj["SSH_PASSWD"] = row["SSH_PASSWD"];
	obj["SSH_PORT"] = row["SSH_PORT"];
	obj["SSH_USER"] = row["SSH_USER"];
	
	getJsonDataByPost(Globals.baseActionUrl.HOST_ACTION_LOGIN_TEST_URL,obj,"主机管理-主机登录测试",
		function (result){
			if(result["flag"] == 1){
				showMessageAlter("ssh连接测试成功!");
			}else{
				showWarnMessageAlter("ssh连接失败!");
			}
	});
}

/**
 * 多台主机终端显示
 */
function terminal(){
	var hostList = JsVar["hostGrid"].getSelecteds();
	if (hostList == null || hostList.length == 0) {
		showWarnMessageAlter("请选择需要进行终端操作的主机!");
		return;
	}
	
	var hostArray = [];
	for (var i=0; i<hostList.length; i++) {
		hostArray.push("'" + hostList[i]["HOST_ID"] + "'");
	}
	var hostStr = hostArray.join(",");
	$("#termialHost").val(hostStr);
	$("#termialForm").attr("action", Globals.baseActionUrl.HOST_ACTION_TERMINAL_URL);
	$("#termialForm").submit();
}

/**
 * 单个终端显示
 * @param hostId
 */
function singleTermial(hostId) {
	var hostStr = "'" + hostId + "'";
	$("#termialHost").val(hostStr);
	$("#termialForm").attr("action", Globals.baseActionUrl.HOST_ACTION_TERMINAL_URL);
	$("#termialForm").submit();
}


