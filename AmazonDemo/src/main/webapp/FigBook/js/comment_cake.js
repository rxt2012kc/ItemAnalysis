//评价分布
// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('comment_cake'));

        // 指定图表的配置项和数据
        // var link = ['www.bupt.edu.cn','www.bupt.edu.cn']
        // var src = ['https://images-na.ssl-images-amazon.com/images/I/51Gsycdh-TL._SX430_BO1,204,203,200_.jpg'];
        

        var option = {
            // title : {
            //     text: '评价分布',
            //     x:'center'
            // },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                type: 'scroll',
                orient: 'vertical',
                right: 20,
                top: 20,
                bottom: 20,
                data: comment_data.legendData
            },
            series : [
                {
                    name: '姓名',
                    type: 'pie',
                    radius : '65%',
                    // center: ['40%', '50%'],
                    data: comment_data.seriesData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };




        // function genData() {
        //     var legendData = ['好评','中评','差评'];
        //     var seriesData = [{name:'好评',value:30},
        //                       {name:'中评',value:30},
        //                       {name:'差评',value:10}];
        //     return {
        //         legendData: legendData,
        //         seriesData: seriesData
        //     };
        // }


                    

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);