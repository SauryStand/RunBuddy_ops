/**
 * 显示遮罩层
 */
function showLoadMask(msg) {
	var html ='正在处理中,请稍后...';
	if(msg){
		html=msg;
	}
    mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: html
    });
}
/**
 * 隐藏遮罩层
 */
function hideLoadMask() {
    mini.unmask(document.body);
}
/**
 * 关闭窗口
 * @param data  参数， 自定义格式
 */
function closeWindow(data)
{
    if (window.CloseOwnerWindow){
         window.CloseOwnerWindow(data);
    }
    else{
        window.close();
    }
}
/**
 * 页面为修改操作，则调用该方法
 * @param title  弹出框的标题
 * @param width   宽度
 * @param height   高度
 * @param url     url
 * @param callback 回调函数
 * @param params   传递参数 自定义格式
 * @param opt    选项，自定义扩展
 */
function showEditDialog(title,width,height,url,callback,params,opt){
    var defaultOpt = {
        url:url,
        title:title,
        width:width,
        height:height,
        allowResize:false,
        showModal: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            iframe.contentWindow.onLoadComplete(systemVar.EDIT,params);
        },
        ondestroy: callback
    }
    //替换自定义属性
    $.extend(defaultOpt,opt);
    mini.open(defaultOpt);
}

/**
 * 页面为增加操作，则调用该方法
 * @param title  弹出框的标题
 * @param width   宽度
 * @param height   高度
 * @param url     url
 * @param callback 回调函数
 * @param params   传递参数 自定义格式
 * @param opt    选项，自定义扩展
 */
function showAddDialog(title,width,height,url,callback,params,opt){
    var defaultOpt = {
        url:url,
        title:title,
        width:width,
        height:height,
        allowResize:false,
        showModal: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            iframe.contentWindow.onLoadComplete(systemVar.ADD,params);
        },
        ondestroy: callback
    }
    //替换自定义属性
    $.extend(defaultOpt,opt);
    mini.open(defaultOpt);
}

/**
 * 页面不是增加或修改操作，则调用该方法
 * @param title  弹出框的标题
 * @param width   宽度
 * @param height   高度
 * @param url     url
 * @param callback 回调函数
 * @param params   传递参数 自定义格式
 * @param opt    选项，自定义扩展
 */
function showDialog(title,width,height,url,callback,params,opt){
    var defaultOpt = {
        url:url,
        title:title,
        width:width,
        height:height,
        allowResize:false,
        showModal: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            iframe.contentWindow.onLoadComplete(params);
        },
        ondestroy: callback
    }
    //替换自定义属性
    $.extend(defaultOpt,opt);
    mini.open(defaultOpt);
}
/**
 * 加载表格控件，用于表格不分页
 * @param widget 控件对象  必输项
 * @param params  参数  json 格式 必输项
 * @param execKey execKey 必输项
 * @param url 自定义URL,可选项
 * @param dbKey dbKey 可选项
 */
function datagridLoad(widget,params,execKey,url,dbKey){
    var tempUrl ='';
    var paramObj ='';
    if(url){
        tempUrl = url;
    }else{
        tempUrl = Globals.baseActionUrl.FRAME_QUERY_FOR_LIST_URL;
    }
    widget.setUrl(tempUrl);
    if(params){
        paramObj = {
            dbKey:dbKey,
            execKey:execKey,
            params:mini.encode(params)
        }
    }

    widget.load(paramObj,
      function success(e){ //成功返回数据
          var data = widget.getResultObject();
          if(data.timeout){
        	  showErrorMessageAlter("登录超时,请重新登陆",function(){
          		top.window.location.href =  data.timeout;
          		 return;
          	});
          }
          if(!e.data){
              showErrorMessageAlter("系统异常!")
          }

    },function fail(result){//抛出异常
            if(result.errorCode == 0){
                return;
            }
            showErrorMessageAlter("系统异常!")
    });
    var $widget = $('#'+widget.getId());//获取控件的jquery对象
    //如下代码处理情况是因为在  在表格控件中 datagrid   遇到有 “&” 特殊字符时， 显示会出现问题， 所以在单元格渲染时要进行处理
    //因为在此处已经处理了drawcell方法，所以如果自定义drawcell时会出现问题， 解决办法在 class 标签中多加一个标记（ownDrawCell），用于自己定义drawcell，
    if(!$widget.hasClass('ownDrawCell')) {//如果存在自已定义的 ondrawcell 方法 ，则不执行如下代码
        widget.un("drawcell", function () {
        }).on("drawcell", function (e) {
            //存在自定义的renderer方法时， 渲染时会有问题， 所以要过滤掉
            if (!e.column.hasOwnProperty("renderer")) {
                var evalue = e.value;
                if (evalue) {
                    //判断 有  “ & ” 的情况下， 进行特殊处理
                    if (evalue.toString().indexOf("&") > -1) {
                        e.cellHtml = evalue;
                    }
                }
            }
        });
    }
}
/**
 * 加载表格控件，用于表格分页
 * @param widget 控件对象 必输项
 * @param params  参数  json 格式 必输项
 * @param execKey execKey 必输项
 * @param url 自定义URL,可选项
 * @param dbKey dbKey 可选项
 */
function datagridLoadPage(widget,params,execKey,url,dbKey){
    var tempUrl = '';
    var paramObj='';
    if(url){
        tempUrl = url;
    }else{
        tempUrl = Globals.baseActionUrl.FRAME_QUERY_PAGE_LIST;
    }
    widget.setUrl(tempUrl);
    if(params){
        var paramObj = {
            execKey:execKey,
            dbKey:dbKey,
            params:mini.encode(params)
        }
    }
    widget.load(paramObj,
        function success(e){ //成功返回数据
            var data = widget.getResultObject();
            if(data.timeout){
            	showErrorMessageAlter("登录超时,请重新登陆",function(){
            		top.window.location.href =  data.timeout;
            		 return;
            	});
            	return;
            }
            if(!e.data){
                showErrorMessageAlter("系统异常!")
            }

        },function fail(result){//抛出异常
            if(result.errorCode == 0){
                return;
            }
            showErrorMessageAlter("系统异常!")
        });

    var $widget = $('#'+widget.getId());//获取控件的jquery对象
    //如下代码处理情况是因为在  在表格控件中 datagrid   遇到有 “&” 特殊字符时， 显示会出现问题， 所以在单元格渲染时要进行处理
    //因为在此处已经处理了drawcell方法，所以如果自定义drawcell时会出现问题， 解决办法在 class 标签中多加一个标记（ownDrawCell），用于自己定义drawcell，
    if(!$widget.hasClass('ownDrawCell')) {//如果存在自已定义的 ondrawcell 方法 ，则不执行如下代码
        widget.un("drawcell", function () {
        }).on("drawcell", function (e) {
            //存在自定义的renderer方法时， 渲染时会有问题， 所以要过滤掉
            if (!e.column.hasOwnProperty("renderer")) {
                var evalue = e.value;
                if (evalue) {
                    //判断 有  “ & ” 的情况下， 进行特殊处理
                    if (evalue.toString().indexOf("&") > -1) {
                        e.cellHtml = evalue;
                    }
                }
            }
        });
    }
}

/**
 * 加载下拉框数据
 * @param widget 控件对象 必输项
 * @param params 参数  json 格式 必输项
 * @param execKey execKey 必输项
 * @param url 自定义URL,可选项
 * @param dbKey dbKey   可选项
 */
function comboxLoad(widget,params,execKey,url,dbKey,async){
    var tempParam='';
    var tempUrl='';
    if(params){
        tempParam = params;
    }
    if(url){
        tempUrl = url;
    }else{
        tempUrl = Globals.baseActionUrl.FRAME_QUERY_FOR_LIST_URL;
    }
    getJsonDataByPost(tempUrl,tempParam,null,
        function success(result){
            widget.setData(result);
        },execKey,dbKey,async);
}

/**
 * 加载树控件数据
 * @param widget 控件对象 必输项
 * @param params 参数  json 格式 必输项
 * @param execKey execKey 必输项
 * @param url 自定义URL,可选项
 * @param dbKey dbKey   可选项
 */
function treeLoad(widget,execKey,params,url,dbKey){
    var tempUrl='';
    var tempDbkey='';
    var tempParams='';
    if(url){
        tempUrl = url+'?1=1';
    }else{
        tempUrl = Globals.baseActionUrl.FRAME_QUERY_FOR_LIST_URL+'?1=1';
    }
    if(tempUrl.indexOf("?")<0){
    	tempUrl+="?";
    }
    if(params){
    	tempParams=mini.encode(params);
   }
    if(dbKey){
        tempDbkey = "&dbKey="+dbKey;
    }
    //var path = tempUrl + "&execKey=" +execKey +tempDbkey+"&params="+tempParams;
    var path = tempUrl + "&execKey=" +execKey +tempDbkey+"&params="+encodeURIComponent(tempParams);
    widget.load(path);
//    var data = widget.getData();
//    if((data && data.length == 0)|| !data){
//        showErrorMessageAlter("系统异常,数据未加载成功！")
//    }
}

/**
 * 显示成功级别的消息弹出框
 * @param msg       消息内容 必输项
 * @param  callback 回调函数 可选项
 */
function showMessageAlter(msg,callback) {
    var miniObject=top.mini;
     if(miniObject==null){
         miniObject = mini;
     }
	 
    miniObject.showMessageBox({
        showHeader: false,
        minWidth: 250,
        title: "提示",
        buttons: ["ok"],
        message: msg,
        iconCls: "mini-messagebox-success",
        callback: callback
    });
}

/**
 * 显示确定提示的消息弹出框
 * @param msg       消息内容 必输项
 * @param  okcallback 确定回调函数 必输项
 * @param  cancelcallback 取消回调函数 可选项
 */
function showConfirmMessageAlter(msg,okcallback,cancelcallback) {
    var miniObject=top.mini;
     if(miniObject==null){
         miniObject = mini;
     }
	 
    miniObject.confirm(msg,"确定？",
        function (action) {
            if (action == "ok") {
                okcallback();
            } else {
                try{
                    cancelcallback();
                }catch(e){
                }
            }
        }
    );
}

/**
 * 显示error级别的消息弹出框
 * @param msg       消息内容 必输项
 * @param  callback 回调函数 可选项
 */
function showErrorMessageAlter(msg,callback) {
    var miniObject=top.mini;
     if(miniObject==null){
         miniObject = mini;
     }
	 
    miniObject.showMessageBox({
        showHeader: false,
        minWidth: 250,
        title: "错误",
        buttons: ["ok"],
        message: msg,
        iconCls: "mini-messagebox-error",
        callback: callback
    });
}
/**
 * 显示warning级别的消息弹出框
 * @param msg       消息内容 必输项
 * @param  callback 回调函数  可选项
 */
function showWarnMessageAlter(msg,callback) {
    /* top.mini.alert(msg, "警告");*/
	var miniObject=top.mini;
     if(miniObject==null){
         miniObject = mini;
     }
	 
    miniObject.showMessageBox({
        showHeader: false,
        /*width: 250,*/
        minWidth: 250,
        title: "警告",
        buttons: ["ok"],
        message: msg,
        iconCls: "mini-messagebox-warning",
        callback: callback
    });

}

/**
 * 将Json数据结果值反序列化至view详情表单
 * @param viewId
 * @param resultJson
 */
function deserializerJsonViewForm(viewId,resultJson){
    var view=$("#"+viewId);
    var viewUiElements=view.find("tr td div,label");
    $.each(viewUiElements,function (index){
        var id=$(this).attr("id");
        if(checkEffFormName(id,resultJson)){
            $("#"+id).html(getValueByElementsId(id,resultJson));
        }
    });
}
/**
 * 前提是要有数据显示，把数据清空view详情表单
 * @param viewId
 * @param resultJson
 */
function clearJsonViewForm(viewId){
    var view=$("#"+viewId);
    var viewUiElements=view.find("tr td div,label");
    $.each(viewUiElements,function (index){
        var id=$(this).attr("id");
        $("#"+id).html("");
    });
}
/**
 * 验证表单列name是否能在Json数据中查找到
 * @param elementId
 * @param resultJson
 * @returns {boolean}
 */
function checkEffFormName(elementId,resultJson){
    if(resultJson){
        for(var attr in resultJson){
            if(attr==elementId){
                return true;
            }
        }
        return false;
    }
}
/**
 * 根据表单内容id值查找json对象中的结果值
 * @param elementId
 * @param resultJson
 * @returns {*}
 */
function getValueByElementsId(elementId,resultJson){
    return resultJson[elementId]==undefined?"":resultJson[elementId];
}


