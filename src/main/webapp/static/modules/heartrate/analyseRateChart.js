//定义变量， 通常是页面控件和参数
//var JsVar = new Object();

$(document).ready(function () {
    //mini.parse();
    loadRateData();
});

function loadRateData(){

    initAnalyseData();
    initAnalysePieData();
}

/**
 * 加载饼形图数据
 */
function initAnalysePieData(){
    var pieChart = echarts.init(document.getElementById('heartRatePieChart'));
    option = {
        title : {
            text: '心率数据分析统计v1.0',
            subtext: '历史记录'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        calculable : false,
        legend: {
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
                ]
            }
        ]
    };
    pieChart.setOption(option);
}

/**
 * 加載柱形图数据
 */
function initAnalyseData(){
    console.log(dataArray);
    var lineChart = echarts.init(document.getElementById('heartRateLineChart'));
    var legendData = [ '最高心率','最低心率','平均心率'];
    var legendLength = 0;
    var series = [];
    var serie1 = new Object();
    serie1["name"] = "最高心率";
    serie1["type"] = 'bar';
    serie1["data"] = [];
    var serie2 = new Object();
    serie2["name"] = "最低心率";
    serie2["type"] = 'bar';
    serie2["data"] = [];
    var serie3 = new Object();
    serie3["name"] = "平均心率";
    serie3["type"] = 'bar';
    serie3["data"] = [];
    var xAxisData = [];

    for (var i = 0; i < dataArray.length; i++) {
        serie1["data"].push(dataArray[i]["highestRate"]);
        serie2["data"].push(dataArray[i]["lowestRate"]);
        serie3["data"].push(dataArray[i]["averageRate"]);
        xAxisData.push(dataArray[i]["recordDate"]);
    }
    series.push(serie1);
    series.push(serie2);
    series.push(serie3);
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

