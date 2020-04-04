<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>分布式配置中心</title>
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/modules/layer/default/layer.css" media="all">
    <link rel="stylesheet" href="<%=contextPath%>/css/admin.css">
    <script type="text/javascript" src="<%=contextPath%>/static/jquery-2.1.3.min.js"></script>
    <script src="<%=contextPath%>/layui/layui.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/layui/lay/modules/layer.js"></script>
    <script>
        function chageCode() {
            document.getElementById("img").src = "<%=contextPath%>/imageCode?time="
                + new Date().getTime();
        }
    </script>
</head>
<body>
<form class="layui-form" action="<%=contextPath%>/user/login-in" method="post">
    <div class="login">

        <h1>配置中心登录fds</h1>

        </span>
        <div class="layui-form-item">
            <input class="layui-input" name="username" placeholder="用户名" lay-verify="required" type="text"
                   id="username" value="admin"
                   autocomplete="off">
        </div>
        <div class="layui-form-item">
            <input class="layui-input" name="password" id="password" placeholder="密码" lay-verify="required"
                   type="password"
                   autocomplete="off" value="admin123">
        </div>
        <button class="layui-btn login_btn" id="login" lay-submit lay-filter="formBtn">登录</button>
    </div>
</form>

<script>
    <c:choose>
    <c:when test="${responseModel.code != '0' }">
        layer.msg('${responseModel.msg}');
    </c:when>
    </c:choose>

    layui.use('form', function () {
        var form = layui.form;
        //渲染控件
        form.render();
    });
</script>
</body>
</html>
