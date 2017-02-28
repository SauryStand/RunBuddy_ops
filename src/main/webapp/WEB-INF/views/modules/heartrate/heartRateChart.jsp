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
<div class="search" id="queryForm" style=" padding: 0px;margin-bottom: 5px;height: 50px;">
    <table style="width: 100%;height: 100%;">
        <tr>
            <td style="width: 300px;padding-left: 10px;">
                <a class="mini-button opBtn" onclick="delUser()" plain="false" name="user-option" id="del-user" data-options='{"pId":"del-user"}'>删除</a>
                <a class="mini-button opBtn" onclick="addUser()" plain="false" name="user-option" id="add-user" data-options='{"pId":"add-user"}'>新增</a>
            </td>
            <td>
                <span style="margin-left: 5px;">用户名/工号：</span>
                <input id="EMPEE_NAME" name="EMPEE_NAME" class="mini-textbox" style="width:180px;margin-left: 5px;" />
                <a class="mini-button" onclick="search()" style="margin-left: 5px;">查询</a>
            </td>
        </tr>
    </table>
</div>


<div style="width: 100%; height: 180px;">
    <!-- 心率视图 -->
    <div id="heartRatePieChart" style="width: 600px;height:280px;"></div>
    <!-- 柱状图-->
    <div id="heartRateLineChart" style="width: 600px;height:280px;"></div>

</div>

</body>
</html>
