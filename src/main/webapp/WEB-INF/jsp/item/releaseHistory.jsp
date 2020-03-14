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
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 18px;">
    <legend style="text-align: center">发布历史</legend>
</fieldset>
<div class="layui-row layui-col-space10">
    <div class="layui-col-md4">
        1/3
    </div>
    <div class="layui-col-md4">
        1/3
    </div>
    <div class="layui-col-md4">
        1/3
    </div>
</div>

<script src="<%=contextPath%>/layui/layui.js"></script>
</body>
</html>