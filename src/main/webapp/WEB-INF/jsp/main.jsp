<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>配置中心</title>
    <link rel="stylesheet" href="<%=contextPath%>/layui/css/layui.css">
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">配置中心</div>

        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" target="iframe">基本资料</a></dd>
                    <dd><a href="javascript:;" target="iframe">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="<%=contextPath%>/loginOut">退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="javascript:;">项目管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=contextPath%>/project" target="iframe">项目详情</a></dd>
                        <dd><a href="<%=contextPath%>/project/to-project-add" target="iframe">添加项目</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">项目组管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=contextPath%>/projectgroup" target="iframe">项目组详情</a></dd>
                        <dd><a href="<%=contextPath%>/projectgroup/to-add" target="iframe">添加项目组</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item ">
                    <a href="javascript:;">权限管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=contextPath%>/permission/to-add" target="iframe">添加用户</a></dd>
                        <dd><a href="<%=contextPath%>/permission/to-permissionManage" target="iframe">权限设置</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item ">
                    <a href="javascript:;">个人管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=contextPath%>/information/to-update" target="iframe">个人管理</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 2px;">
            <iframe name="iframe" width="100%" height="600px" frameborder="0"
                    class="xiframe" scrolling="no" ></iframe>
        </div>

    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        <span style="margin-left: 500px">
              配置中心-毕业设计
        </span>

    </div>
</div>
<script src="<%=contextPath%>/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
</body>
</html>