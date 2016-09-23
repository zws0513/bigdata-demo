<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>统计每个省份的农产品市场总数</title>
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
        var marketChart = echarts.init(document.getElementById('psLine'));
        marketChart.clear();
        //marketChart.showLoading({text: '后台小二狂力奔跑中...'});

        var resultJson = ajaxCommon(getRootPath() + "/product/market/show");
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
                        }
                    }
                ]
            };
            option.title.text = resultJson.title;
            option.visualMap.min = resultJson.min;
            option.visualMap.max = resultJson.max;
            option.series[0].data = resultJson.data;
            marketChart.setOption(option);
        }
    }
    //载入图表
    loadMarketData();
</script>
</html>