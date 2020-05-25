<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
    response.setHeader("P3P","CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>分布式配置中心</title>
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/modules/layer/default/layer.css" media="all">
    <script type="text/javascript" src="<%=contextPath%>/static/jquery-2.1.3.min.js"></script>

    <link rel="stylesheet" href="<%=contextPath%>/css/admin.css">
    <script>
        function chageCode() {
            document.getElementById("img").src = "<%=contextPath%>/imageCode?time="
                + new Date().getTime();
        }
    </script>
</head>
<body background="../../background.jpg">
<form class="layui-form" action="" method="post">
    <div class="login">

        <h1>配置中心登录</h1>

        </span>
        <div class="layui-form-item">
            <input type="text" name="username" lay-verify="required" autocomplete="off"
                   value="178183852@qq.com"
                   class="layui-input"  placeholder="请输入管理员账号">
        </div>
        <div class="layui-form-item">
            <input type="password" name="password" lay-verify="required" autocomplete="off"
                   value="123123"
                   class="layui-input"  placeholder="请输入管理员密码">
        </div>

        <button type="submit" lay-submit lay-filter="formDemo" class="layui-btn login_btn">
            登录
        </button>
    </div>
</form>
<script type="text/javascript" src="<%=contextPath%>/layui/layui.js"></script>
<script type="text/javascript" src="<%=contextPath%>/layui/lay/modules/layer.js"></script>
<script>
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            $.ajax({
                url: '<%=contextPath%>/login-in',
                type: 'POST',
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                //请求成功时执行该函数
                success: function (result) {
                    if (result.code == '0') {
                        window.location.href = "<%=contextPath%>/index";
                    } else {
                        layer.msg( result.msg );
                    }
                },
                //请求失败时执行该函数
                error: function (errorMsg) {
                    alert("数据异常!" + errorMsg.msg);
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
