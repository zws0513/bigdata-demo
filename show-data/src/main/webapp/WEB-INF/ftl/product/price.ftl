<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>山西省的每种农产品的价格波动趋势</title>
    <script src="../../../resources/js/common/common.js"></script>
</head>
<body>
<div style="padding:10px;clear: both;">
    <div id="psLine" style="height:600px;"></div>
</div>
</body>
<script src="../../../resources/js/jquery/jquery-1.11.0.min.js"></script>
<script src="../../../resources/js/common/common.js"></script>
<script src="../../../resources/js/plugins/echarts.js"></script>
<script type="text/javascript">
    //查询
    function loadPriceData() {
        //图表
        var priceChart = echarts.init(document.getElementById('psLine'));
        priceChart.clear();

        var resultJson = ajaxCommon(getRootPath() + "/product/price/show");
        if (resultJson.result) {
            var option = {
                title: {
                    text: '',
                    x: "center"
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: [],
                    y:"bottom"
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '5%',
                    containLabel: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        dataView: {readOnly: false},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: []
                },
                yAxis: {
                    type: 'value'
                },
                series: []
            };
            option.title.text = resultJson.title;
            option.legend.data = resultJson.legendData;
            option.xAxis.data = resultJson.xAxisData;
            option.series = resultJson.data;
            priceChart.setOption(option);
        }
    }
    //载入图表
    loadPriceData();
</script>
</html>