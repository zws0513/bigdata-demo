<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>统计的车的所有权、车辆类型和品牌的分布</title>
</head>
<body>
<div style="padding:10px;clear: both;">
    <table style="border:0px">
        <tr align="center">
            <td>所有权</td>
            <td>车辆类型</td>
            <td>品牌</td>
            <td>销售数量</td>
        </tr>
    <#if all?exists>
        <#list all as data>
            <tr align="center">
                <td>${data.owner}</td>
                <td>${data.type}</td>
                <td>${data.brand}</td>
                <td>${data.amount}</td>
            </tr>
        </#list>
    </#if>
    </table>
</div>
</body>
</html>