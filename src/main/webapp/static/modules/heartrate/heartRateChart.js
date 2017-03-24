//定义变量， 通常是页面控件和参数
//var JsVar = new Object();

$(document).ready(function () {
    //mini.parse();
    dymanicHeartChart();//动态图
    //loadRateData();//这样的尝试
    // var testing = document.getElementById("real_realTimeRate").value();
    loadRateData();
});


//点击按钮返回数据
function getAllData(){
    //alert("viva la vida:"+data);
    loadRateData();
}

/**
 * 返回前台的心率数据啦
 * todo
 */
function loadRateData(){
    console.info(dataArray);
    //console.log(dataArray);
    console.info(dataArray3);
    //console.log(dataArray3);
    rateArr = new Array();//饼图的数组对象
    realLineArr = new Array();//柱形图的数组对象
    recordTimeArr = new Array();
    var unusedBit =[];//<45
    var lowerBit = [];//45-60
    var normalBit = [];//60-70心跳
    var highBit = [];//>70心跳,原本应该设置成80-100的
    var moreHighBit = [];//100-130
    var highestBit = [];//>130
    var j = 0,k = 0,l = 0,m = 0,n = 0, p = 0;
    for(var i = 0 ; i < dataArray3.length ; i++){
        if(dataArray3[i].status != 0){
            if(dataArray3[i].realRate > 45 && dataArray3[i].realRate <= 60){
                lowerBit[j++] = dataArray3[i].realRate;
            } else if(dataArray3[i].realRate > 60 && dataArray3[i].realRate <= 80){
                normalBit[k++] = dataArray3[i].realRate;
            }else if(dataArray3[i].realRate > 80 && dataArray3[i].realRate <= 100){
                highBit[l++] = dataArray3[i].realRate;
            }else if(dataArray3[i].realRate > 100 && dataArray3[i].realRate <= 130){
                moreHighBit[m++] = dataArray3[i].realRate;
            }else if(dataArray3[i].realRate > 130){
                highestBit[n++] = dataArray3[i].realRate;
            } else{
                //highBit[j++] = dataArray3[i].realRate;//暂时是这样，因为没有数据，等造出数据之后再把这个干掉
                unusedBit[p++] = dataArray3[i].realRate;;
            }
        }else{
            //alert("heartRate is unused because of the status is :"+ dataArray3[i].status);
        }

    }

    var timeline = [];
    var count = 0;
    //截取上传日期
    for(var i = 0 ; i < dataArray.length ; i++){
        if(dataArray[i].uploadTime != null){
            timeline[count] = dataArray[i].uploadTime;
        }
    }

    //这是给饼图用的
    rateArr.push({
        normalRateCount : normalBit.length,
        highRateCount : highBit.length
    });
    //线图
    realLineArr.push({
        lowerRateCount : lowerBit.length,
        normalRateCount : normalBit.length,
        highRateCount : highBit.length,
        higherRateCount : moreHighBit.length,
        highestRateCount : highestBit.length
    });
    //时间
    // recordTimeArr.push({
    //    record : timeline.valueOf()
    // });


    initFormatPieChart(rateArr);
    initHeartRateLineChart(realLineArr);//这个是心率的柱形图
}
/**
 * 字符串加工
 */
function processString(rateString){
    if(!rateString){
        alert("no data!");
    }
    var list = [];

}

/**
 * 心率统计的饼形图
 * @param inputData
 */
function initFormatPieChart(inputData) {
    if(!inputData || inputData.length < 1){
        return;
    }
    console.log(inputData);
    var pieChart = echarts.init(document.getElementById('heartRatePieChart'));
    option = {
        title : {
            text: '心率统计饼图v1.0',
            subtext: '近24小时'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        calculable : false,
        legend: {
        //,">130心率记录","50-60之间心率计数","<50心率计数"
            data:["100-130之间心率计数","80-100之间心率计数"],
            padding:[30,10,10,10],
            x:'center'
        },
        toolbox: {
            show : true,
            feature : {
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '50%',
                center: ['50%', '55%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position:'outer',
                            formatter:"{b} : {c}  ({d}%)"
                        },
                        labelLine: {
                            show: true,
                            length:1
                        },
                        color:
                            function(params){
                                var colors=["#32cd32","#87cefa","#6495ed","#ff69b4","#da70d6","#ba55d3","#ff7f50"];
                                return colors[params.dataIndex];
                            }
                    }
                },
                data:[
                    {value:inputData[0]["normalRateCount"], name:'100-130之间心率计数'},
                    {value:inputData[0].highRateCount, name:'80-100之间心率计数'}
                    // {value:inputData["ABNORMAL_RECORDS"], name:'>130心率记录'},
                    // {value:inputData["NOUSER_RECORDS"], name:'50-60之间心率计数'},
                    // {value:inputData["NOUSER_RECORDS"], name:'<50心率计数'}
                ]
            }
        ]
    };
    pieChart.setOption(option);
}


/**
 * 动态数据
 */
function dymanicHeartChart(){

    var lineChart = echarts.init(document.getElementById('heartRateLineChart'));
    var series1 = {};
    series1["name"]="文件积压量";
    series1["type"]='bar';
    series1["data"]=[];
    var series2 = {};
    series2["name"]="话单积压量";
    series2["type"]='bar';
    series2["data"]=[];
    var xAxisData = [];
    for(var i = 1 ; i <= 50 ; i++){
        if(i == 30){
            series1["data"].push(10 + Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1));//这里添加数据
            series2["data"].push(5 + Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1));
        }else{
            series1["data"].push(0);
            series2["data"].push(0);
        }

    }
    for(var i=0;i<50 ;i++){
        xAxisData.push((new Date()).getHours()+":"+(new Date()).getMinutes()+":"+(new Date()).getSeconds());
    }

    var option = {
        title : {
            text: '动态数据测试',
            padding : 0
            //subtext: '近一小时'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['文件积压量','话单积压量']
        },
        toolbox: {
            show : true,
//		        x: 'center',
//		        y: 'bottom',
            feature : {
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : true,
                data : xAxisData
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            series1,
            series2
        ]
    };

    lineChart.setOption(option);

    var timeTicket;
    var lastData = 11;
    var lastData2 = 12;
    var axisData;
    clearInterval(timeTicket);
    timeTicket = setInterval(function(){

        var preOverStock;
        var collOverStock;

        //动态获取数据库里面关于采集数据的信息
        //定时获取数据库里面的数据
        // getJsonDataByPost(Globals.baseActionUrl.FRAME_QUERY_FOR_OBJECT_URL,
        //     null, null, function success(result) {
        //         preOverStock = result.PRE_OVERSTOCK;
        //         collOverStock = result.COLL_OVERSTOCK;
        //         alert("data1:"+ pre_coll + "," + "data2:" + coll_file);
        //     }, "monitorMapper.queryAllFileAndTicket", "", false);

        // lastData = preOverStock;
        // lastData = lastData.toFixed(1) - 0;
        // lastData2 = collOverStock;
        // lastData2 = lastData2.toFixed(1) - 0;

        lastData += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
        lastData = lastData.toFixed(1) - 0;
        lastData2 += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
        lastData2 = lastData.toFixed(1) - 0;

        axisData = (new Date()).getHours()+":"+(new Date()).getMinutes()+":"+(new Date()).getSeconds();//.replace(/^\D*/, '');
        // 动态数据接口 addData
        lineChart.addData([ [ 0, // 系列索引
            lastData2, // 新增数据
            false, // 新增数据是否从队列头部插入
            false // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
        ], [ 1, // 系列索引
            lastData, // 新增数据
            false, // 新增数据是否从队列头部插入
            false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
            axisData // 坐标轴标签
        ] ]);
    }, 2100);
}


/**
* 动态折线图，大数据展示了这
 */
function dymanicLineChart(){

    function randomData() {
        now = new Date(+now + oneDay);
        value = value + Math.random() * 21 - 10;
        return {
            name: now.toString(),
            value: [
                [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                Math.round(value)
            ]
        }
    }

    var data = [];
    var now = +new Date(1997, 9, 3);
    var oneDay = 24 * 3600 * 1000;
    var value = Math.random() * 1000;
    for (var i = 0; i < 1000; i++) {
        data.push(randomData());
    }

    option = {
        title: {
            text: '动态数据 + 时间坐标轴'
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                params = params[0];
                var date = new Date(params.name);
                return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
            },
            axisPointer: {
                animation: false
            }
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        series: [{
            name: '模拟数据',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data
        }]
    };

    setInterval(function () {

        for (var i = 0; i < 5; i++) {
            data.shift();
            data.push(randomData());
        }

        myChart.setOption({
            series: [{
                data: data
            }]
        });
    }, 1000);
}


/**
 * 统计各个时段产生的心率数据
 */
function initHeartRateLineChart(rows){
    console.log(rows);
    var lineChart = echarts.init(document.getElementById('HistoryLineChart'));
    var legendData = [ '40-60心率',"60-80心率",'80-100心率','100-130心率','>130心率' ];
    var legendLength = 0;
    var series = [];
    var serie1 = new Object();
    serie1["name"] = "40-60心率";
    serie1["type"] = 'bar';
    serie1["data"] = [];
    var serie2 = new Object();
    serie2["name"] = "60-80心率";
    serie2["type"] = 'bar';
    serie2["data"] = [];
    var serie3 = new Object();
    serie3["name"] = "80-100心率";
    serie3["type"] = 'bar';
    serie3["data"] = [];
    var serie4 = new Object();
    serie4["name"] = "100-130心率";
    serie4["type"] = 'bar';
    serie4["data"] = [];
    var serie5 = new Object();
    serie5["name"] = ">130心率";
    serie5["type"] = 'bar';
    serie5["data"] = [];
    var xAxisData = [];
    // for (var i = 0; i < rows.length; i++) {
    // }
    serie1["data"].push(rows[0]["lowerRateCount"]);
    serie2["data"].push(rows[0]["normalRateCount"]);
    serie3["data"].push(rows[0]["highRateCount"]);
    serie4["data"].push(rows[0]["higherRateCount"]);
    serie5["data"].push(rows[0]["highestRateCount"]);
    xAxisData.push((new Date()).getHours()+":"+(new Date()).getMinutes()+":"+(new Date()).getSeconds());

    series.push(serie1);
    series.push(serie2);
    series.push(serie3);
    series.push(serie4);
    series.push(serie5);

    var option = {
        title : {
            text : '历史数据',
            padding : 0
            //subtext : '近一小时'
        },
        tooltip : {
            trigger : 'axis'
        },
        legend : {
            data : legendData,
            padding : 10,
            x : 'center'
        },
        toolbox: {
            show : false,
            feature : {
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [ {
            type : 'category',
            boundaryGap : true,
            data : xAxisData
        } ],
        yAxis : [ {
            type : 'value'
        } ],
        series : series
    };
    lineChart.setOption(option);

}

/**
 * 分析心率数据
 */
function initShowRateAnalyse(){

}