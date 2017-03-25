<%@ page import="java.util.ArrayList" %>
<%@ page import="com.thinkgem.jeesite.modules.runbuddy.entity.Rate" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Jonney Chou
  Date: 2017/1/9
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>200 - 心率數據分析頁面</title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>

    <script type="text/javascript">
        dataArray = new Array();
        <c:forEach  items="${page.list}" var="realdata">
        dataArray.push({
            highestRate:"${realdata.highestRate}",
            lowestRate:"${realdata.lowestRate}",
            averageRate:"${realdata.averageRate}",
            motionState:"${realdata.motionState}",
            recommendState:"${realdata.recommendState}",
            execiseTime:"${realdata.execiseTime}",
            execiseLoad:"${realdata.execiseLoad}",
            recordDate:"${realdata.recordDate}"
        });
        </c:forEach>
        <%--dataArray = ${page.list};//赋值给js--%>
    </script>
    <script type="text/javascript" src="${ctxStatic}/modules/heartrate/analyseRateChart.js"></script>
</head>
<body>

<!--心率的form-->
<form:form id="heartForm" action="/function/heartrate" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <div>
        <label>心率：</label><input id="realTimeRate" name="realTimeRate" type="text" maxlength="50" class="input-mini" value="${RealTimeRate.realTimeRate}"/>
        <label>上传时间：</label><input id="uploadTime" name="uploadTime" type="text" maxlength="50" class="input-mini" value="${RealTimeRate.uploadTime}"/>
        <label>记录时间：</label><input id="recordTime" name="recordTime" type="text" maxlength="50" class="input-mini" value="${RealTimeRate.recordTime}"/>
        &nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
    </div>


    <div class="mini-tabs" activeIndex="0"
         style="width:100%;height:350px;margin-top: 20px; padding: 0px;" plain="false"
         tabAlign="left" tabPosition="top">
        <h1>分析数据显示</h1>
        <div title="数据显示" style="height: 300px;width: 100%;">
            <div id="heartRatePieChart" style="width: 50%; height: 280px;float:left;margin-top: 20px;">
            </div>
            <div id="heartRateLineChart" style="width: 50%; height: 280px;float:left;margin-top: 20px;">
            </div>
        </div>
    </div>




<h1>历史数据显示</h1>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>最高心率</th>
        <th>最低心率</th>
        <th>平均心率</th>
        <th>运动状态</th>
        <th>推荐运动模式</th>
        <th>运动时长</th>
        <th>负荷率</th>
        <th>记录时间</th>
    </tr>
    </thead>
    <tbody>
    <%
        request.setAttribute("strEnter", "\n");
        request.setAttribute("strTab", "\t");

    %>
    <!--这里传回来了page,list-->
    <c:forEach  items="${page.list}" var="rate">
        <tr>
            <td id="highestRate">${rate.highestRate}</td>
            <td id="lowestRate">${rate.lowestRate}</td>
            <td id="averageRate">${rate.averageRate}</td>
            <td id="motionState">${rate.motionState}</td>
            <td id="recommendState">${rate.recommendState}</td>
            <td id="execiseTime">${rate.execiseTime}</td>
            <td id="execiseLoad">${rate.execiseLoad}</td>
            <td id="recordDate">${rate.recordDate}</td>

        </tr>
    </c:forEach>


    </tbody>

</table>
<div>
    <a type="buttom" id="getList"  onclick="getAllData()">统计心率数据</a>
</div>
<div class="pagination">${page}</div>


</body>
</html>
