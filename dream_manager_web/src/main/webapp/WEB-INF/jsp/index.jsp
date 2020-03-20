<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>梦商城后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="../../css/style.css" />
    <link rel="stylesheet" type="text/css" href="../../css/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../../css/icon.css" />
    <link rel="stylesheet" type="text/css" href="../../css/dream.css" />
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../../js/common.js"></script>

    <style type="text/css">
        .content {
            padding: 10px 10px 10px 10px;
        }
    </style>
</head>
<body class="easyui-layout">
<!-- begin of header -->
<div class="wu-header" data-options="region:'north',border:false,split:true">
    <div class="wu-header-left">
        <h1>梦商城后台管理系统</h1>
    </div>
    <div class="wu-header-right">
        <p><strong class="easyui-tooltip" title="2条未读消息">admin</strong>，欢迎您！</p>
        <p><a href="#">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>|<a href="#">安全退出</a></p>
    </div>
</div>
<div data-options="region:'west',title:'菜单',split:true" style="width:180px;">
    <ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
        <li>
            <span>商品管理</span>
            <ul>
                <li data-options="attributes:{'url':'item-add'}">新增商品</li>
                <li data-options="attributes:{'url':'item-list'}">查询商品</li>
                <li data-options="attributes:{'url':'item-param-list'}">规格参数</li>
            </ul>
        </li>
        <li>
            <span>网站内容管理</span>
            <ul>
                <li data-options="attributes:{'url':'content-category'}">内容分类管理</li>
                <li data-options="attributes:{'url':'content'}">内容管理</li>
            </ul>
        </li>
        <li>
            <span>
                网站索引管理
            </span>
            <ul>
                <li data-options="attributes:{'url':'index-import-search'}">导入索引库</li>
            </ul>
        </li>
    </ul>
</div>
<div data-options="region:'center',title:''">
    <div id="tabs" class="easyui-tabs">
        <div title="首页" style="padding:20px;">

        </div>
    </div>
</div>
<!-- begin of footer -->
<div class="wu-footer" data-options="region:'south',border:true,split:true">
    &copy; 2020 DREAM All Rights Reserved
</div>
<script type="text/javascript">
    $(function(){
        $('#menu').tree({
            onClick: function(node){
                if($('#menu').tree("isLeaf",node.target)){
                    var tabs = $("#tabs");
                    var tab = tabs.tabs("getTab",node.text);
                    if(tab){
                        tabs.tabs("select",node.text);
                    }else{
                        tabs.tabs('add',{
                            title:node.text,
                            href: node.attributes.url,
                            closable:true,
                            bodyCls:"content"
                        });
                    }
                }
            }
        });
    });
</script>
</body>
</html>
