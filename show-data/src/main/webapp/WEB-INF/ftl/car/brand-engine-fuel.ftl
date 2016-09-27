<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>通过不同类型(品牌)车销售情况,来统计发动机型号和燃料种类</title>
</head>
<body>
<div style="padding:10px;clear: both;">
    <table style="border:0px">
        <tr align="center">
            <td>品牌</td>
            <td>发动机型号</td>
            <td>燃料种类</td>
            <td>销售数量</td>
        </tr>
    <#if all?exists>
        <#list all as data>
            <tr align="center">
                <td>${data.brand}</td>
                <td>${data.engine}</td>
                <td>${data.fuel}</td>
                <td>${data.amount}</td>
            </tr>
        </#list>
    </#if>
    </table>
</div>
</body>
</html>