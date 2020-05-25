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
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/modules/layer/default/layer.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/jquery-2.1.3.min.js"></script>

</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>添加项目组</legend>
</fieldset>

<form class="layui-form" action="" method="post">
    <div style="padding: 20px; background-color: #F2F2F2;">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md6">
                <div class="layui-card" style="position: relative; left: 40%">
                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                        <legend>基础效果</legend>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
    </div>
</form>

<script type="text/javascript" src="<%=contextPath%>/layui/layui.js"></script>
<script type="text/javascript" src="<%=contextPath%>/layui/lay/modules/layer.js"></script>
<script>
    layui.use(['transfer', 'layer', 'util'], function(){
        var $ = layui.$
            ,transfer = layui.transfer
            ,layer = layui.layer
            ,util = layui.util;

        //模拟数据
        var data1 = [
            {"value": "1", "title": "李白"}
            ,{"value": "2", "title": "杜甫"}
            ,{"value": "3", "title": "苏轼"}
            ,{"value": "4", "title": "李清照"}
            ,{"value": "5", "title": "鲁迅", "disabled": true}
            ,{"value": "6", "title": "巴金"}
            ,{"value": "7", "title": "冰心"}
            ,{"value": "8", "title": "矛盾"}
            ,{"value": "9", "title": "贤心"}
        ]

            ,data2 = [
            {"value": "1", "title": "瓦罐汤"}
            ,{"value": "2", "title": "油酥饼"}
            ,{"value": "3", "title": "炸酱面"}
            ,{"value": "4", "title": "串串香", "disabled": true}
            ,{"value": "5", "title": "豆腐脑"}
            ,{"value": "6", "title": "驴打滚"}
            ,{"value": "7", "title": "北京烤鸭"}
            ,{"value": "8", "title": "烤冷面"}
            ,{"value": "9", "title": "毛血旺", "disabled": true}
            ,{"value": "10", "title": "肉夹馍"}
            ,{"value": "11", "title": "臊子面"}
            ,{"value": "12", "title": "凉皮"}
            ,{"value": "13", "title": "羊肉泡馍"}
            ,{"value": "14", "title": "冰糖葫芦", "disabled": true}
            ,{"value": "15", "title": "狼牙土豆"}
        ]

        //基础效果
        transfer.render({
            elem: '#test1'
            ,data: data1
        })

        //定义标题及数据源
        transfer.render({
            elem: '#test2'
            ,title: ['候选文人', '获奖文人']  //自定义标题
            ,data: data1
            //,width: 150 //定义宽度
            ,height: 210 //定义高度
        })

        //初始右侧数据
        transfer.render({
            elem: '#test3'
            ,data: data2
            ,value: ["1", "3", "5", "7", "9", "11"]
        })

        //显示搜索框
        transfer.render({
            elem: '#test4'
            ,data: data1
            ,title: ['文本墨客', '获奖文人']
            ,showSearch: true
        })

        //数据格式解析
        transfer.render({
            elem: '#test5'
            ,parseData: function(res){
                return {
                    "value": res.id //数据值
                    ,"title": res.name //数据标题
                    ,"disabled": res.disabled  //是否禁用
                    ,"checked": res.checked //是否选中
                }
            }
            ,data: [
                {"id": "1", "name": "李白"}
                ,{"id": "2", "name": "杜甫"}
                ,{"id": "3", "name": "贤心"}
            ]
            ,height: 150
        })

        //穿梭时的回调
        transfer.render({
            elem: '#test6'
            ,data: data1
            ,onchange: function(obj, index){
                var arr = ['左边', '右边'];
                layer.alert('来自 <strong>'+ arr[index] + '</strong> 的数据：'+ JSON.stringify(obj)); //获得被穿梭时的数据
            }
        })

        //实例调用
        transfer.render({
            elem: '#test7'
            ,data: data1
            ,id: 'key123' //定义唯一索引
        })
        //批量办法定事件
        util.event('lay-demoTransferActive', {
            getData: function(othis){
                var getData = transfer.getData('key123'); //获取右侧数据
                layer.alert(JSON.stringify(getData));
            }
            ,reload:function(){
                //实例重载
                transfer.reload('key123', {
                    title: ['文人', '喜欢的文人']
                    ,value: ['2', '5', '9']
                    ,showSearch: true
                })
            }
        });

    });
</script>
<script>
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            $.ajax({
                url: '<%=contextPath%>/projectgroup/update',
                type: 'POST',
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                //请求成功时执行该函数
                success: function (result) {
                    if (result.code == '0') {
                        layer.msg('更新成功!', {time: 1 * 1000}, function () {
                            location.reload();
                        });
                    } else {
                        alert("更新失败!" + result.msg);
                    }
                },
                //请求失败时执行该函数
                error: function () {
                    alert("数据异常!");
                }
            });
            return false;
        });
    });

</script>
</body>
</html>
