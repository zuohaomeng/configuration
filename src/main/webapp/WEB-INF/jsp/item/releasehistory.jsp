<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/modules/layer/default/layer.css" media="all">
    <script type="text/javascript" src="<%=contextPath%>/static/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/layui/lay/modules/table.js"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">配置中心</div>
    </div>

    <div class="layui-side layui-bg-gray" style="width: 320px">
        <div class="layui-side-scroll" style="left: 10px;width: 300px">
            <br/>
            <h1 align="center" style="color: #0C0C0C">发布历史</h1>
            <br/>
            <table class="layui-table" lay-even width="100%"
                   style="table-layout:fixed;overflow: hidden">
                <thead>
                <tr>
                    <th width="" style="text-align: center;font-size: 18px">发布人</th>
                    <th width="120px" style="text-align: center;font-size: 18px">时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="t" items="${hlist}">
                    <tr onclick="watchDetail(${t.version})">
                        <td style="overflow: hidden;font-size: 15px">${t.name}</td>
                        <td style="overflow: hidden;font-size: 15px">${t.date}</td>
                    </tr>
                </c:forEach>
                </tbody>


            </table>
        </div>
    </div>

    <div class="layui-body" style="left: 350px;right: 20px">
        <br>
        <h1 align="center">配置更新详情</h1>
        <br/>
        <table class="layui-table" lay-even width="100%" style="text-align:center;vertical-align:middle;">
            <thead>
            <tr>
                <th width="10">类型</th>
                <th width="200">key</th>
                <th width="200">oldValue</th>
                <th width="200">newValue</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${detail}">
                <tr>
                    <td>${d.type}</td>
                    <td>${d.key}</td>
                    <td>${d.oldValue}</td>
                    <td>${d.newValue}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        <span style="margin-left: 500px">
              配置中心-毕业设计
        </span>

    </div>
</div>
<script src="<%=contextPath%>/layui/layui.js"></script>
<script>
    function watchDetail(version) {
        window.location.href = "<%=contextPath%>/history?projectId=${projectId}&env=${env}&version=" + version;
        // alert("hello")
    }

    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
</body>
</html>