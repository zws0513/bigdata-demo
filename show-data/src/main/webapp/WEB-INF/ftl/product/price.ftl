<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>山西省的每种农产品的价格波动趋势</title>
</head>
<body>
<div style="padding:10px;clear: both;">
    <div id="psLine" style="height:600px;"></div>
</div>
</body>
<script src="../../../resources/js/jquery/jquery-1.11.0.min.js"></script>
<script src="../../../resources/js/common/common.js"></script>
<script src="../../../resources/js/plugins/echarts.js"></script>
<script src="../../../resources/js/map/china.js"></script>
<script type="text/javascript">
    //查询
    function loadMarketData() {
        //图表
        var kindChart = echarts.init(document.getElementById('psLine'));
        kindChart.clear();

        var resultJson = ajaxCommon(getRootPath() + "/product/price/show");
        if (resultJson.result) {
            var option = {
                title: {
                    text: '',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                visualMap: {
                    min: 0,
                    max: 10,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
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
                series: [
                    {
                        name: '',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:[]
                    }
                ]
            };
            option.title.text = resultJson.title;
            option.visualMap.min = resultJson.min;
            option.visualMap.max = resultJson.max;
            option.series[0].data = resultJason.data;
            marketChart.setOption(option);
        }
    }
    //载入图表
    loadMarketData();
</script>
</html>