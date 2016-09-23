<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>统计排名前 3 的省份共同拥有的农产品类型</title>
</head>
<body>
<table border="1">
    <tr>
        <th>种类</th>
    </tr>
<#list all as label>
    <tr>
        <td>${label.kind}</td>
    </tr>
</#list>
</table>
</body>
</html>