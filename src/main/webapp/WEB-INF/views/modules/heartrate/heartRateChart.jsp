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
    <title>HeartRate Chart example</title>
    <script language="javascript" type="text/javascript" src="/static/echarts/echarts.common.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/modules/heartrate/heartRateChart.js"></script>

</head>
<body>
<!-- 心率视图 -->
<div id="heartRateChart" style="width: 600px;height:400px;"></div>

<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/role/">角色列表</a></li>
    <shiro:hasPermission name="sys:role:edit">
        <li><a href="${ctx}/sys/role/form">角色添加</a></li>
    </shiro:hasPermission>
</ul>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>角色名称</th>
        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission></tr>
    <c:forEach items="${list}" var="role">
        <tr>
            <td><a href="form?id=${role.id}">${role.name}</a></td>
            <shiro:hasPermission name="sys:role:edit">
                <td>
                    <a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>
                    <c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
                        <a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
                    </c:if>
                    <a href="${ctx}/sys/role/delete?id=${role.id}"
                       onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
</table>

</body>
</html>
