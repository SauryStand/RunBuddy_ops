<%@ page import="java.util.ArrayList" %>
<%@ page import="com.thinkgem.jeesite.modules.runbuddy.entity.RealTimeRate" %>
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
    <title>200 - 心率显示页面</title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>

    <script type="text/javascript">
        dataArray = new Array();
        <c:forEach  items="${page.list}" var="realdata2">
            dataArray.push({
                realTimeRate:"${realdata2.realTimeRate}",
                uploadTime:"${realdata2.uploadTime}"
                <%--realRate:"${realdata.realRate}",--%>
                <%--status:"${realdata.status}"--%>
            });
        </c:forEach>
        <%--dataArray = ${page.list};//赋值给js--%>
    </script>

    <%--<script type="text/javascript">--%>
        <%--dataArray3 = new Array();--%>
        <%--<c:forEach  items="${page.splitRealArr}" var="realdata3">--%>
            <%--dataArray3.push({--%>
                <%--realRate:"${realdata3.realRate}",--%>
                <%--status:"${realdata3.status}"--%>
            <%--});--%>
        <%--</c:forEach>--%>
        <%--&lt;%&ndash;dataArray = ${page.list};//赋值给js&ndash;%&gt;--%>
    <%--</script>--%>



    <script type="text/javascript" src="${ctxStatic}/modules/heartrate/heartRateChart.js"></script>
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
        <h1>当天数据显示</h1>
        <div title="预处理环节" style="height: 300px;width: 100%;">
            <div id="heartRatePieChart" style="width: 50%; height: 280px;float:left;margin-top: 20px;">
            </div>
            <div id="heartRateLineChart" style="width: 50%; height: 280px;float:left;margin-top: 20px;">
            </div>
        </div>
    </div>

</form:form>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>心率数据集</th>
        <th>上传时间</th>
        <th>记录时间</th>
    </tr>
    </thead>
    <tbody>
    <%
        request.setAttribute("strEnter", "\n");
        request.setAttribute("strTab", "\t");

    %>
    <!--这里传回来了page,list-->
    <c:forEach  items="${page.list}" var="realdata">
        <tr>
            <td id="real_realTimeRate"  >${realdata.realTimeRate}</td>
            <td id="real_uploadTime">${realdata.uploadTime}</td>
            <td id="real_recordTime">${realdata.recordTime}</td>
            <%--<td id="real_realTimeRate"><c:out value="${data.realTimeRate}" /></td>--%>
            <%--<td id="real_uploadTime"><c:out value="${data.uploadTime}" /></td>--%>
            <%--<td id="real_recordTime"><c:out value="${data.recordTime}" /></td>--%>
        </tr>
    </c:forEach>


    <a type="text" id="getList"  onclick="getAllData()">统计心率数据</a>



    </tbody>



</table>
<div class="pagination">${page}</div>


</body>
</html>
