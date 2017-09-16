// 近期评价走向
// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('comment_time'));
        // var timeLine = ['2017-1-1','2017-1-2','2017-1-3','2017-1-4','2017-1-5','2017-1-6','2017-1-7','2017-1-8','2017-1-9','2017-1-10','2017-1-11','2017-1-12'];
        // var best =  [709,1917,2455,2610,1719,1433,1544,3285,5208,3372,2484,4078];
        // var good = [327,1776,507,1200,800,482,204,1390,1001,951,381,220];
        // var bad = [327,1776,507,1200,800,482,204,1390,1001,951,381,220];

        var sum = [];

        for(var i=0;i<good.length;i++){
            try{
                sum_temp = good[i] + medium[i] + bad[i];
                sum.push(sum_temp);
            }
            catch(err){
                continue;
            }
            
        }

        var option = {
            backgroundColor: "#fff",
            "title": {
                x: "4%",

                textStyle: {
                    color: '#fff',
                    fontSize: '22'
                },
                subtextStyle: {
                    color: '#90979c',
                    fontSize: '16',

                },
            },
            "tooltip": {
                "trigger": "axis",
                "axisPointer": {
                    "type": "shadow",
                    textStyle: {
                        color: "#fff"
                    }

                },
            },
            "grid": {
                "borderWidth": 0,
                "top": 110,
                "bottom": 95,
                textStyle: {
                    color: "#fff"
                }
            },
            "legend": {
                x: '4%',
                top: '11%',
                textStyle: {
                    color: '#90979c',
                },
                "data": ['差评', '中评', '好评']
            },
             

            "calculable": true,
            "xAxis": [{
                "type": "category",
                "axisLine": {
                    lineStyle: {
                        color: '#90979c'
                    }
                },
                "splitLine": {
                    "show": false
                },
                "axisTick": {
                    "show": false
                },
                "splitArea": {
                    "show": false
                },
                "axisLabel": {
                    "interval": 0,

                },
                "data": timeLine,
            }],
            "yAxis": [{
                "type": "value",
                "splitLine": {
                    "show": false
                },
                "axisLine": {
                    lineStyle: {
                        color: '#90979c'
                    }
                },
                "axisTick": {
                    "show": false
                },
                "axisLabel": {
                    "interval": 0,

                },
                "splitArea": {
                    "show": false
                },

            }],
            "dataZoom": [
                {
                "show": true,
                "height": 30,
                "xAxisIndex": [
                    0
                ],
                bottom: 30,
                "start": 30,
                "end": 100,
                handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
                handleSize: '110%',
                handleStyle:{
                    color:"#ddd",
                    
                },
                   textStyle:{
                    color:"#fff"},
                   borderColor:"#eee"
                
                
            }
            , {
                "type": "inside",
                "show": true,
                "height": 15,
                "start": 1,
                "end": 35
            }
            ],
            "series": [{
                    "name": "差评",
                    "type": "bar",
                    "stack": "总量",
                    "barMaxWidth": 35,
                    "barGap": "10%",
                    "itemStyle": {
                        "normal": {
                            "color": "#333366",
                            "label": {
                                "show": false,
                                "textStyle": {
                                    "color": "#fff"
                                },
                                "position": "insideTop",
                                formatter: function(p) {
                                    return p.value > 0 ? (p.value) : '';
                                }
                            }
                        }
                    },
                    "data":bad,
                },
                {
                    "name": "中评",
                    "type": "bar",
                    "stack": "总量",
                    "itemStyle": {
                        "normal": {
                            "color": "#99CCFF",
                            "barBorderRadius": 0,
                            "label": {
                                "show": false,
                                "position": "top",
                                formatter: function(p) {
                                    return p.value > 0 ? (p.value) : '';
                                }
                            }
                        }
                    },
                    "data": medium
                },
                {
                    "name": "好评",
                    "type": "bar",
                    "stack": "总量",
                    "itemStyle": {
                        "normal": {
                            "color": "#FF99CC",
                            "barBorderRadius": 0,
                            "label": {
                                "show": false,
                                "position": "top",
                                formatter: function(p) {
                                    return p.value > 0 ? (p.value) : '';
                                }
                            }
                        }
                    },
                    "data": good
                },
                 {
                    "name": "总数",
                    "type": "line",
                    "stack": "总量",
                    symbolSize:10,
                    symbol:'circle',
                    "itemStyle": {
                        "normal": {
                            "color": "#000000",
                            "barBorderRadius": 0,
                            "label": {
                                "show": true,
                                "position": "top",
                                formatter: function(p) {
                                    return p.value > 0 ? (p.value) : '';
                                }
                            }
                        }
                    },
                    "data": sum
                },
            ]
        }                 

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);