<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>统计买车的男女比例</title>
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
    function loadGenderData() {
        //图表
        var genderChart = echarts.init(document.getElementById('psLine'));
        genderChart.clear();

        var resultJson = ajaxCommon(getRootPath() + "/car/gender-scale/show");
        if (resultJson.result) {
            var option = {
                title: {
                    text: '',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item'
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
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },
                        data: []
                    }
                ]
            };
            option.title.text = resultJson.title;
            option.series[0].data = resultJson.data;
            genderChart.setOption(option);
        }
    }
    //载入图表
    loadGenderData();
</script>
</html>