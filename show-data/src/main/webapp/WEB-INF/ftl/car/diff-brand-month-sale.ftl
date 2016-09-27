<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>统计每个省份的农产品市场总数</title>
</head>
<body>
<select name="brandSelect" id="brandSelect" onchange="loadBrandData(this.options[this.options.selectedIndex].value)">
<#list brandAll as item>
    <option value="${item}" >${item}</option>
</#list>
</select>
<div style="padding:10px;clear: both;">
    <div id="psLine" style="height:600px;"></div>
</div>
</body>
<script src="../../../resources/js/jquery/jquery-1.11.0.min.js"></script>
<script src="../../../resources/js/common/common.js"></script>
<script src="../../../resources/js/plugins/echarts.js"></script>
<script type="text/javascript">
    //查询
    function loadBrandData(brand) {
        //图表
        var brandChart = echarts.init(document.getElementById('psLine'));
        brandChart.clear();

        var parameter = {
            brand : brand
        };

        var resultJson = ajaxCommon(getRootPath() + "/car/diff-brand-month-sale/show", parameter);
        if (resultJson.result) {
            var option = {
                title: {
                    text: '',
                    left: 'center'
                },
                color: ['#3398DB'],
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        data : [],
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'',
                        type:'bar',
                        barWidth: '60%',
                        data:[]
                    }
                ]
            };
            option.title.text = resultJson.title;
            option.xAxis[0].data = resultJson.xAxisData;
            option.series[0].name = resultJson.name;
            option.series[0].data = resultJson.data;
            brandChart.setOption(option);
        }
    }
</script>
</html>