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

    <form class="layui-form" action="" method="post">
        <div class="layui-card-body" style="position: relative; left: -70px ">
            <input type="text" name="projectId" value="${projectId}" style="display: none">
            <input type="text" name="env" value="${env}" style="display: none">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: 200px">Key</label>
                    <div class="layui-input-inline">
                        <input type="text" name="newKey" lay-verify="required" autocomplete="off"
                               placeholder="请输入Key"
                               class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: 200px">Value</label>
                    <div class="layui-input-inline">
                        <input type="text" name="newValue" lay-verify="required" autocomplete="off"
                               placeholder="请输入Value"
                               class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: 200px">备注：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="remark"
                               placeholder="请输入备注信息"
                               class="layui-input" style="width:200px; height:100px;"/>
                    </div>
                </div>
            </div>
            <button type="submit" class="layui-btn" lay-submit lay-filter="formDemo"
                    style="position: relative; left: 50% ">立即添加
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
                url: '<%=contextPath%>/item/add',
                type: 'POST',
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                //请求成功时执行该函数
                success: function (result) {
                    if (result.code == '0') {
                        layer.msg('' + result.msg, {time: 1 * 1000}, function () {
                            // location.reload();
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        });
                    } else {
                        alert("添加失败!" + result.msg);
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
