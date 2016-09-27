<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>统计车辆不同用途的数量分布</title>
</head>
<body>
<div style="padding:10px;clear: both;">
    <table>
        <tr>
            <td>
                <div id="main" style="width: 800px;height:600px;"></div>
            </td>
            <td>
                <div id="chart" style="width: 400px;height:600px;"></div>
            </td>
        </tr>
    </table>
    <div id="psLine" style="height:600px;"></div>
</div>
</body>
<script src="../../../resources/js/jquery/jquery-1.11.0.min.js"></script>
<script src="../../../resources/js/common/common.js"></script>
<script src="../../../resources/js/plugins/echarts.js"></script>
<script src="../../../resources/js/map/china.js"></script>
<script src="../../../resources/js/map/china/anhui.js"></script>
<script src="../../../resources/js/map/china/aomen.js"></script>
<script src="../../../resources/js/map/china/beijing.js"></script>
<script src="../../../resources/js/map/china/chongqing.js"></script>
<script src="../../../resources/js/map/china/fujian.js"></script>
<script src="../../../resources/js/map/china/gansu.js"></script>
<script src="../../../resources/js/map/china/guangdong.js"></script>
<script src="../../../resources/js/map/china/guangxi.js"></script>
<script src="../../../resources/js/map/china/guizhou.js"></script>
<script src="../../../resources/js/map/china/hainan.js"></script>
<script src="../../../resources/js/map/china/hebei.js"></script>
<script src="../../../resources/js/map/china/heilongjiang.js"></script>
<script src="../../../resources/js/map/china/henan.js"></script>
<script src="../../../resources/js/map/china/hubei.js"></script>
<script src="../../../resources/js/map/china/hunan.js"></script>
<script src="../../../resources/js/map/china/jiangsu.js"></script>
<script src="../../../resources/js/map/china/jiangxi.js"></script>
<script src="../../../resources/js/map/china/jilin.js"></script>
<script src="../../../resources/js/map/china/liaoning.js"></script>
<script src="../../../resources/js/map/china/neimenggu.js"></script>
<script src="../../../resources/js/map/china/ningxia.js"></script>
<script src="../../../resources/js/map/china/qinghai.js"></script>
<script src="../../../resources/js/map/china/shandong.js"></script>
<script src="../../../resources/js/map/china/shanghai.js"></script>
<script src="../../../resources/js/map/china/shanxi.js"></script>
<script src="../../../resources/js/map/china/shanxi1.js"></script>
<script src="../../../resources/js/map/china/sichuan.js"></script>
<script src="../../../resources/js/map/china/tianjin.js"></script>
<script src="../../../resources/js/map/china/xianggang.js"></script>
<script src="../../../resources/js/map/china/xinjiang.js"></script>
<script src="../../../resources/js/map/china/xizang.js"></script>
<script src="../../../resources/js/map/china/yunnan.js"></script>
<script src="../../../resources/js/map/china/zhejiang.js"></script>
<script type="text/javascript">
    function loadUsageData(province) {
        //图表
        var pie = echarts.init(document.getElementById('chart'));
        pie.clear();

        var resultJson = ajaxCommon(getRootPath() + "/car/usage/show");
        if (resultJson.result) {
            var option = {
                title: {
                    text: '',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:[]
                },
                visualMap: {
                    min: 0,
                    max: 10,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],
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
                        mapType: province,
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
            option.legend.data = resultJson.legendData;
            option.series = resultJson.data;
            pie.setOption(option);
        }
    }

    function loadAllUsageData() {
        var map = echarts.init(document.getElementById('main'));
        var option = {
            title: {
                text: '统计车辆不同用途的数量分布',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            series: [
                {
                    name: '号码分布',
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
        // 使用刚指定的配置项和数据显示图表。
        map.setOption(option);

        loadUsageData('北京');

        map.on('click', function (params) {
            province = params.name;
            loadUsageData(province);
        });
    }
    loadAllUsageData();

</script>
</html>