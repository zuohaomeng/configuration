<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    response.setHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=contextPath%>/layui2.5.6/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=contextPath%>/layui2.5.6/css/modules/layer/default/layer.css">
    <script type="text/javascript" src="<%=contextPath%>/static/jquery-2.1.3.min.js"></script>

</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>项目组用户管理</legend>
</fieldset>


<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card" style="position: relative; left: 40%">
                <div class="layui-card-body">
                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                        <legend>项目组用户管理</legend>
                    </fieldset>
                    <div id="test1" class="demo-transfer"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/layui2.5.6/layui.js"></script>
<script type="text/javascript" src="<%=contextPath%>/layui2.5.6/lay/modules/layer.js"></script>
<script>
    layui.use(['transfer', 'layer', 'util'], function () {
        var $ = layui.$
            , transfer = layui.transfer
            , layer = layui.layer
            , util = layui.util;
        var data1 = [];
        var data2 = [];
        $.ajax({
            url: '<%=contextPath%>/projectgroup/getgroupuser?groupid=' +${groupid},
            type: 'GET',
            data: {},
            success: function (result) {
                data1 = result.data1;
                data2 = result.data2;
                //穿梭时的回调
                transfer.render({
                    elem: '#test1'
                    , title: ['非项目组用户', '项目组用户']
                    , data: data1
                    , value: data2
                    , onchange: function (obj, index) {
                        var arr = ['左边', '右边'];
                        // layer.alert('来自 <strong>' + arr[index] + '</strong> 的数据：' + JSON.stringify(obj)); //获得被穿梭时的数据
                        for (var i = 0; i < obj.length; i++) {
                            layer.alert(obj[i].value);
                            $.ajax({
                                url: "<%=contextPath%>/projectgroup/changegroupuser?groupid=${groupid}&index=" + index + "&userid=" + obj[i].value,
                                type: 'GET',
                                data: {},
                                success: function (result) {

                                },
                                error: function (errorMsg) {
                                    alert("数据异常！" + errorMsg);
                                },
                            });
                        }
                    }
                })
            },
            error: function (errorMsg) {
                alert("数据异常！" + errorMsg);
            },
        });


        // //批量办法定事件
        // util.event('lay-demoTransferActive', {
        //     getData: function (othis) {
        //         var getData = transfer.getData('key123'); //获取右侧数据
        //         layer.alert(JSON.stringify(getData));
        //     }
        //     , reload: function () {
        //         //实例重载
        //         transfer.reload('key123', {
        //             title: ['文人', '喜欢的文人']
        //             , value: ['2', '5', '9']
        //             , showSearch: true
        //         })
        //     }
        // });

    });
</script>
</body>
</html>
