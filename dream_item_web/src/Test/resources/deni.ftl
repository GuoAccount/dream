<html>
<head>
    <title>freemarker测试</title>
    <meta charset="UTF-8"/>
</head>
freemarker模板
<br>
msg:${msg}
TbItem:${tbItem.title}
<br>
     <#list items as item>
         ${item.title}
     </#list>
</html>