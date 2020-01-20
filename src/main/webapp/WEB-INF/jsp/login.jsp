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
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/layui.css">
    <link rel="stylesheet" href="<%=contextPath%>/css/admin.css">
    <script src="<%=contextPath%>/layui/layui.js"></script>

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

        <h1>配置中心登录</h1>
        <c:choose>
            <c:when test="${responseModel.code != '0' }">
                ${responseModel.msg}
            </c:when>
        </c:choose>
        </span>
        <div class="layui-form-item">
            <input class="layui-input" name="usernumber" placeholder="用户名" lay-verify="required" type="text"
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
    layui.use('form', function () {
        var form = layui.form;
        //渲染控件
        form.render();
    });
</script>
</body>
</html>
