<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    response.setHeader("P3P","CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/modules/layer/default/layer.css" media="all">
    <script type="text/javascript" src="<%=contextPath%>/static/jquery-2.1.3.min.js"></script>

</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>添加用户</legend>
</fieldset>

<form class="layui-form" action="" method="post">
    <div style="padding: 20px; background-color: #F2F2F2;">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md6">
                <div class="layui-card" style="position: relative; left: 40%">
                    <div class="layui-card-header">用户添加</div>
                    <div class="layui-card-body">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">账号：</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="username" lay-verify="required" autocomplete="off"
                                           placeholder="请输入用户账号"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">姓名：</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="name" lay-verify="required" autocomplete="off"
                                           placeholder="请输入用户姓名"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">权限：</label>
                                <div class="layui-input-inline">
                                    <select id="groupSelect" name="roleId" lay-filter="aihao" onchange="selectShow()">
                                        <option value="38" selected>高级用户</option>
                                        <option value="37">管理员</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">密码：</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="password" lay-verify="required" autocomplete="off"
                                           placeholder="请输入密码"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">确认密码：</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="passwordAgain" lay-verify="required" autocomplete="off"
                                           placeholder="请再次确认密码"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="layui-btn" lay-submit lay-filter="formDemo"
                                style="position: relative; left: 40% ">立即添加
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
                url: '<%=contextPath%>/permission/add',
                type: 'POST',
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                //请求成功时执行该函数
                success: function (result) {
                    if (result.code == '0') {
                        layer.msg(result.msg, {time: 1 * 1000}, function () {
                            location.reload();
                        });
                    } else {
                        alert(result.msg);
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
