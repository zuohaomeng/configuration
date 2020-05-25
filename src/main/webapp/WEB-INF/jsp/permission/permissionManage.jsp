<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    response.setHeader("P3P","CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
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
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend style="text-align: center">权限角色管理</legend>
</fieldset>
<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">权限角色管理</div>
                <form class="layui-form layui-from-pane" action="" method="post">
                    <div class="layui-card-body">
                        <div class="layui-form-item">
                        </div>
                        <table id="demo" lay-filter="test"></table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="barDemo2">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="setPowerUser">设置</a>
</script>
<script type="text/html" id="barDemo3">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="setAdmin">设置</a>
</script>
<script src="<%=contextPath%>/layui/layui.js"></script>
<script>
    layui.use(['table', 'form'], function () {
        var table = layui.table;
        var form = layui.form;
        //第一个实例
        table.render({
            elem: '#demo',
            height: 312,
            url: '<%=contextPath%>/permission/role-relation-list', //数据接口
            page: true, //开启分页
            cols: [[ //表头
                {type: 'checkbox', fixed: 'left'},
                {field: 'id', title: '序号', width: 100},
                {field: 'username', title: '用户名', width: 200},
                {field: 'name', title: '姓名', width: 200},
                {field: 'roleName', title: '权限角色名', width: 200},
                {fixed: 'right', title: '普通管理员', toolbar: '#barDemo2', width: 100},
                {fixed: 'right', title: '高级管理员', toolbar: '#barDemo3', width: 100}
            ]]
        });
        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            if(obj.event === 'setUser'){
                layer.confirm("确定设置为普通用户权限吗？", function (index) {
                    $.ajax({
                        url: '<%=contextPath%>/permission/change-role?type=39',
                        type: 'GET',
                        data: {'id': data.id},
                        success: function (result) {
                            if (result.code == "0") {
                                obj.del();
                                layer.msg( result.msg, {icon: 6});
                                layer.close(index);
                            } else {
                                layer.msg(result.msg, {icon: 5});
                            }
                            location.reload();
                        },
                        error: function (errorMsg) {
                            alert("数据异常！" + errorMsg);
                            location.reload();
                        },
                    });
                });
            }else if(obj.event === 'setPowerUser'){
                layer.confirm("确定设置为普通管理员权限吗？", function (index) {
                    $.ajax({
                        url: '<%=contextPath%>/permission/change-role?type=38',
                        type: 'GET',
                        data: {'id': data.id},
                        success: function (result) {
                            if (result.code == "0") {
                                obj.del();
                                layer.msg( result.msg, {icon: 6});
                                layer.close(index);
                            } else {
                                layer.msg("删除失败!" + result.msg, {icon: 5});
                            }
                            location.reload();
                        },
                        error: function (errorMsg) {
                            alert("数据异常！" + errorMsg);
                            location.reload();
                        },
                    });
                });
            } else if (obj.event === 'setAdmin') {
                layer.confirm("确定设置为高级管理员权限吗？", function (index) {
                    $.ajax({
                        url: '<%=contextPath%>/permission/change-role?type=37',
                        type: 'GET',
                        data: {'id': data.id},
                        success: function (result) {
                            if (result.code == "0") {
                                obj.del();
                                layer.msg( result.msg, {icon: 6});
                                layer.close(index);
                            } else {
                                layer.msg("删除失败!" + result.msg, {icon: 5});
                            }
                            location.reload();
                        },
                        error: function (errorMsg) {
                            alert("数据异常！" + errorMsg);
                            location.reload();
                        },
                    });
                });
            }
        });

        //监听职位查询按钮
        form.on('submit(queryForm)', function (data) {
            console.log("----->" + data.field.deptname);
            table.render({
                elem: '#demo',
                height: 312,
                url: '<%=contextPath%>/project/search?portion=' + data.field.deptname,//数据接口
                page: true, //开启分页
                cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'},
                    {field: 'projectId', title: '项目标识', width: 200},
                    {field: 'projectName', title: '项目名', width: 200},
                    {field: 'leaderName', title: '负责人', width: 200},
                    {field: 'updateTime', title: '配置项更新时间', width: 200},
                    {fixed: 'right', title: '进入', toolbar: '#barDemo1', width: 100},
                    {fixed: 'right', title: '编辑', toolbar: '#barDemo2', width: 100},
                    {fixed: 'right', title: '删除', toolbar: '#barDemo3', width: 100}
                ]]
            });
            return false;
        });
    });
</script>
</body>
</html>