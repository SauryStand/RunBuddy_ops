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
    <script type="text/javascript" src="${ctxStatic}/modules/heartrate/heartRateChart.js"></script>
</head>
<body>

<!--心率的form-->
<form:form id="heartForm" action="/function/heartrate" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <%--<div>--%>
    <%--<label>心率：</label><input id="realTimeRate" name="title" type="text" maxlength="50" class="input-mini" value="${RealTimeRate.realTimeRate}"/>--%>
    <%--<label>上传时间：</label><input id="uploadTime" name="createBy.id" type="text" maxlength="50" class="input-mini" value="${RealTimeRate.uploadTime}"/>--%>
    <%--<label>记录时间：</label><input id="recordTime" name="requestUri" type="text" maxlength="50" class="input-mini" value="${RealTimeRate.recordTime}"/>--%>
    <%--</div>--%>

    <%--<div style="width: 100%; height: 600px;">--%>
    <%--<li class="active">--%>
    <%--<!-- 心率视图 -->--%>
    <%--<div id="heartRatePieChart" style="width: 50%;height:280px;"></div>--%>
    <%--</li>--%>
    <%--<li class="active">--%>
    <%--<!-- 柱状图-->--%>
    <%--<div id="heartRateLineChart" style="width: 50%;height:280px;"></div>--%>
    <%--</li>--%>
    <%--</div>--%>

    <div class="mini-tabs" activeIndex="0"
         style="width:100%;height:350px;margin-top: 20px; padding: 0px;" plain="false"
         tabAlign="left" tabPosition="top">
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
        <th>心率</th>
        <th>上传时间</th>
        <th>记录时间</th>

    </tr>
    </thead>
    <tbody><%
        request.setAttribute("strEnter", "\n");
        request.setAttribute("strTab", "\t");
    %>
    <!--这里传回来了list-->
    <c:forEach items="${page.list}" var="data">
        <tr>
            <td>${data.realTimeRate}</td>
            <td>${data.uploadTime}</td>
            <td>${data.recordTime}</td>
        </tr>

    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>


</body>
</html>
