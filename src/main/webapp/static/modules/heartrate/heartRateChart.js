//定义变量， 通常是页面控件和参数
//var JsVar = new Object();

$(document).ready(function () {
    //mini.parse();
    initFormatPieChart();
    dymanicHeartChart();//动态图
    //loadRateData();//这样的尝试
    // var testing = document.getElementById("real_realTimeRate").value();
    // alert(testing);
});



function getAllData(data){
    //alert("viva la vida:"+data);
    loadRateData();
}

/**
 * 返回前台的心率数据啦
 * todo
 */
function loadRateData(){
    console.info(dataArray);
    console.log(dataArray);
    // console.info(dataArray3);
    // console.log(dataArray3);
    //var myArr = new Array();
    for(var i = 0 ; i < dataArray.length ; i++){
        alert("-->>" + dataArray[i].realTimeRate+"####"+dataArray[i].uploadTime);
    }
    // for(var i = 0 ; i < dataArray3.length ; i++){
    //     alert("-->>" + dataArray3[i].realRate+"####"+dataArray3[i].status);
    // }

}


function initFormatPieChart() {

    var myChart = echarts.init(document.getElementById('heartRatePieChart'));
    var option = {
        // backgroundColor: '#2c343c',
        title: {
            text: '心率统计饼图v1.0',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#ccc'
            }
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
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
                colorLightness: [0, 1]
            }
        },
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:[
                    {value:735, name:'100-130之间心率计数'},
                    {value:310, name:'80-100之间心率计数'},
                    {value:274, name:'>130心率记录'},
                    {value:235, name:'50-60之间心率计数'},
                    {value:400, name:'<50心率计数'}
                ].sort(function (a, b) { return a.value - b.value}),
                roseType: 'angle',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },

                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                    return Math.random() * 200;
                }
            }
        ]
    };
    myChart.setOption(option);
}

/**
 * unused
 */
function dymanicHeartChartdgs(){
    var myChart = echarts.init(document.getElementById('heartRateLineChart'));
    var option = {
        title : {
            text: '动态数据',
            subtext: '纯属虚构'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['最新成交价', '预购队列']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        dataZoom : {
            show : false,
            start : 0,
            end : 100
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : true,
                data : (function (){
                    var now = new Date();
                    var res = [];
                    var len = 10;
                    while (len--) {
                        res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
                        now = new Date(now - 2000);
                    }
                    return res;
                })()
            },
            {
                type : 'category',
                boundaryGap : true,
                data : (function (){
                    var res = [];
                    var len = 10;
                    while (len--) {
                        res.push(len + 1);
                    }
                    return res;
                })()
            }
        ],
        yAxis : [
            {
                type : 'value',
                scale: true,
                name : '价格',
                boundaryGap: [0.2, 0.2]
            },
            {
                type : 'value',
                scale: true,
                name : '预购量',
                boundaryGap: [0.2, 0.2]
            }
        ],
        series : [
            {
                name:'预购队列',
                type:'bar',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data:(function (){
                    var res = [];
                    var len = 10;
                    while (len--) {
                        res.push(Math.round(Math.random() * 1000));
                    }
                    return res;
                })()
            },
            {
                name:'最新成交价',
                type:'line',
                data:(function (){
                    var res = [];
                    var len = 10;
                    while (len--) {
                        res.push((Math.random()*10 + 5).toFixed(1) - 0);
                    }
                    return res;
                })()
            }
        ]
    };
    myChart.setOption(option);


    var timeTicket;
    var lastData = 11;
    var axisData;
    clearInterval(timeTicket);
    timeTicket = setInterval(function (){
        lastData += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
        lastData = lastData.toFixed(1) - 0;
        axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');

        // 动态数据接口 addData
        myChart.addData([
            [
                0,        // 系列索引
                Math.round(Math.random() * 1000), // 新增数据
                false,     // 新增数据是否从队列头部插入
                false     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
            ],
            [
                1,        // 系列索引
                lastData, // 新增数据
                false,    // 新增数据是否从队列头部插入
                false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                axisData  // 坐标轴标签
            ]
        ]);
    }, 2100);
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
            text: '文件与话单积压',
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