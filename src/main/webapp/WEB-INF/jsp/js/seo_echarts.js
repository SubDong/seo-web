setTimeout(function () {
    //关键词排行
    var myChart = echarts.init(document.getElementById('KeywordPaiming'));
    var option = {
        title: {
            text: '数据为展示信息',
            subtext: '下个版本将投放真实数据',
            x: 'left',
            subtextStyle: {color: '#386e9e'}
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['出现频率', '2%≦密度≦8%', '百度指数', '百度排名', '排名变动', '预计百度流量(IP)']
        },
        toolbox: {
            show: true,
            feature: {
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: ['NBA', 'QQ', '新闻', '腾讯', '视频', '腾讯网', '财经', '房产', '科技', 'Tencent', '资讯']
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '出现频率',
                type: 'line',
                stack: '总量',
                data: [120, 132, 101, 134, 90, 230, 210, 100, 10, 200, 300]
            },
            {
                name: '2%≦密度≦8%',
                type: 'line',
                stack: '总量',
                data: [220, 182, 191, 234, 290, 330, 310, 400, 200, 111, 10]
            },
            {
                name: '百度指数',
                type: 'line',
                stack: '总量',
                data: [150, 232, 201, 154, 190, 330, 410, 200, 100, 80, 120]
            },
            {
                name: '百度排名',
                type: 'line',
                stack: '总量',
                data: [320, 332, 301, 334, 390, 330, 320, 150, 232, 201, 154]
            },
            {
                name: '排名变动',
                type: 'line',
                stack: '总量',
                data: [820, 932, 901, 934, 1290, 1330, 1320, 320, 332, 301, 334]
            },
            {
                name: '预计百度流量(IP)',
                type: 'line',
                stack: '总量',
                data: [820, 932, 901, 934, 1290, 1330, 1320, 901, 934, 1290, 1330]
            }
        ]
    };

    myChart.setOption(option);

    //综合排名
    var myChart = echarts.init(document.getElementById('paiming'));
    var option = {
        title: {
            text: '数据为展示信息',
            subtext: '下个版本将投放真实数据',
            x: 'left',
            subtextStyle: {color: '#386e9e'}
        },
        tooltip: {
            trigger: 'axis'
        },
        /*legend: {
         data:['综合排名']
         },*/
        toolbox: {
            show: true,
            feature: {

                dataView: {readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        dataZoom: {
            show: true,
            realtime: true,
            start: 40,
            end: 60
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: true,
                data: function () {
                    var list = [];
                    for (var i = 1; i <= 30; i++) {
                        list.push('2013-03-' + i);
                    }
                    return list;
                }()
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '综合排名',
                type: 'line',
                data: function () {
                    var list = [];
                    for (var i = 1; i <= 30; i++) {
                        list.push(Math.round(Math.random() * 30) + 30);
                    }
                    return list;
                }()
            }

        ]
    };
    myChart.setOption(option);

// 为echarts对象加载数据
    //历史收录
    myChart.setOption(option);
    var myChart = echarts.init(document.getElementById('lishishoulu'));
    var option = {
        /*title: {
         text: '历史收录'
         },*/
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['百度收录', '百度反链']
        },
        toolbox: {
            show: true,
            orient: 'horizontal',      // 布局方式，默认为水平布局，可选为：
                                       // 'horizontal' ¦ 'vertical'
            x: 'right',                // 水平安放位置，默认为全图右对齐，可选为：
                                       // 'center' ¦ 'left' ¦ 'right'
                                       // ¦ {number}（x坐标，单位px）
            y: 'top',                  // 垂直安放位置，默认为全图顶端，可选为：
                                       // 'top' ¦ 'bottom' ¦ 'center'
                                       // ¦ {number}（y坐标，单位px）
            color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
            backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
            borderColor: '#ccc',       // 工具箱边框颜色
            borderWidth: 0,            // 工具箱边框线宽，单位px，默认为0（无边框）
            padding: 5,                // 工具箱内边距，单位px，默认各方向内边距为5，
            showTitle: true,
            feature: {
                dataView: {
                    show: true,
                    title: '数据视图',
                    readOnly: false,
                    lang: ['数据视图', '关闭', '刷新']
                },
                magicType: {
                    show: true,
                    title: {
                        line: '动态类型切换-折线图',
                        bar: '动态类型切换-柱形图',
                        stack: '动态类型切换-堆积',
                        tiled: '动态类型切换-平铺'
                    },
                    type: ['line', 'bar', 'stack', 'tiled']
                },
                restore: {
                    show: true,
                    title: '还原',
                    color: 'black'
                },
                saveAsImage: {
                    show: true,
                    title: '保存为图片',
                    type: 'jpeg',
                    lang: ['点击本地保存']
                },
                myTool: {
                    show: true,
                    title: '自定义扩展方法',
                    icon: 'image://../asset/ico/favicon.png',
                    onclick: function () {
                        alert('myToolHandler')
                    }
                }
            }
        },
        calculable: true,
        dataZoom: {
            show: true,
            realtime: true,
            start: 20,
            end: 80
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: ['2015-01-03', '2015-01-02', '2015-01-01', '2014-12-31', '2014-12-30', '2014-12-29', '2014-12-28', '2014-12-27'],
                type: 'category',
                boundaryGap: false
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '百度收录',
                type: 'line',
                data: [5, 8, 9, 0, 10, 2, 7, 1]
            },
            {
                name: '百度反链',
                type: 'line',
                data: [2, 5, 4, 8, 3, 7, 0, 2]
            }
        ]
    };
    myChart.setOption(option);
}, 700);