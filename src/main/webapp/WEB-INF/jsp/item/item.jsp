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
    <legend style="text-align: center">配置信息界面</legend>
</fieldset>
<div style="padding: 0px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card" style="height: 550px">
                <div class="layui-card-header" >
                    <button id="button1" class="layui-btn">
                        发布历史
                    </button>
                    <button id="button2" class="layui-btn">
                        回滚
                    </button >
                    <button id="button3" class="layui-btn layui-btn-danger">
                        发布
                    </button>
                    <button id="button4" onclick="add();" class="layui-btn" style="float:right">
                        新增
                    </button>
                </div>
                <form class="layui-form layui-from-pane" action="" method="post">
                    <div class="layui-card-body">

                        <table id="demo" lay-filter="test"></table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="barDemo1">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="watch">进入</a>
</script>
<script type="text/html" id="barDemo2">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="update">编辑</a>
</script>
<script type="text/html" id="barDemo3">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script src="<%=contextPath%>/layui/layui.js"></script>
<script>
    function add(){
        layer.open({
            type: 2,
            title: "添加",
            area: ['500px', '400px'],
            content: '<%=contextPath%>/item/to-add?projectId=${projectId}', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://www.baidu.com', 'no']
            end: function () {
                location.reload();
            }
        });
    }

    layui.use(['table', 'form'], function () {
        var table = layui.table;
        var form = layui.form;
        //第一个实例
        table.render({
            elem: '#demo',
            height: 466,
            url: '<%=contextPath%>/item/list?projectId=${projectId}', //数据接口
            page: true,  //开启分页
            cols: [[ //表头
                {field: 'key', title: 'Key', width: 200},
                {field: 'value', title: 'Value', width: 200},
                {field: 'remark', title: '备注', width: 200},
                {field: 'status', title: '状态', width: 100},
                {field: 'updateName', title: '最新修改人', width: 100},
                {field: 'updateTime', title: '最新修改时间', width: 200},
                {fixed: 'right', title: '编辑', toolbar: '#barDemo2', width: 70},
                {fixed: 'right', title: '删除', toolbar: '#barDemo3', width: 70}
            ]]
        });
        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            if(obj.event === 'update'){
                layer.open({
                    type: 2,
                    title: "添加",
                    area: ['500px', '400px'],
                    content: '<%=contextPath%>/item/to-update?id='+data.id,
                    end: function () {
                        location.reload();
                    }
                });
            } else if (obj.event === 'del') {
                layer.confirm('真的删除\t' + data.deptname + "\t项目吗！", function (index) {
                    $.ajax({
                        url: '<%=contextPath%>/item/delete',
                        type: 'GET',
                        data: {'id': data.id},
                        success: function (result) {
                            if (result.code == "0") {
                                obj.del();
                                layer.msg("删除成功!" + result, {icon: 6});
                                layer.close(index);
                            } else {
                                layer.msg("删除失败!" + result, {icon: 5});
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
    });
</script>
</body>
</html>