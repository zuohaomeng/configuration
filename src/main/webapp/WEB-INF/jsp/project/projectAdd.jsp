<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
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
    <legend>添加项目</legend>
</fieldset>

<form class="layui-form" action="" method="post">
    <div style="padding: 20px; background-color: #F2F2F2;">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md6">
                <div class="layui-card" style="position: relative; left: 40%">
                    <div class="layui-card-header">项目添加</div>
                    <div class="layui-card-body">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">项目唯一标识：</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="projectId" lay-verify="required" autocomplete="off"
                                           placeholder="请输入项目唯一标识"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">项目名：</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="projectName" lay-verify="required" autocomplete="off"
                                           placeholder="请输入项目名"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">项目组：</label>
                                <div class="layui-input-inline">
                                    <select id="groupSelect" name="groupId" lay-filter="aihao" onchange="selectShow()">
                                        <option value="-1" selected=""></option>
                                        <c:forEach items="${pgs}" var="pg">
                                            <option value="${pg.id}">${pg.groupName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">负责人：</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="leaderName" lay-verify="required" autocomplete="off"
                                           placeholder="请输入项目负责人"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">邮箱：</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="email" lay-verify="required" autocomplete="off"
                                           placeholder="请输入负责人邮箱"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 200px">备注：</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="remark"  autocomplete="off"
                                           placeholder="请输入备注信息"
                                           class="layui-input"  style="width:300px; height:100px;"/>
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
                url: '<%=contextPath%>/project/add',
                type: 'POST',
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                //请求成功时执行该函数
                success: function (result) {
                    if (result.code == '0') {
                        layer.msg('添加成功!'+result.msg, {time: 1 * 1000}, function () {
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
