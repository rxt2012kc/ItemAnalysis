////销量——纸质书pk电子书
//// 基于准备好的dom，初始化echarts实例
//        var myChart = echarts.init(document.getElementById('two_book'));
//
//        // 指定图表的配置项和数据
//        // var data_two_book = [
//        //                 {value:10000, name: '电子书'},
//        //                 {value:510, name: '纸质书'}
//        //             ];
//
//        var weatherIcons = {
//            'ebook': 'source/ebook.png',
//            'book': 'source/book.png',
//        };
//
//        var option = {
//            tooltip : {
//                trigger: 'item',
//                formatter: "{a} <br/>{b} : {c} ({d}%)"
//            },
//            color:['#FF99CC', '#99CCFF'],
//            legend: {
//                bottom: 10,
//                left: 'center',
//                data: ['电子书', '纸质书']
//            },
//            series : [
//                {
//                    type: 'pie',
//                    radius : '65%',
//                    // center: ['50%', '50%'],
//                    selectedMode: 'single',
//                    data:data_two_book,
//                    itemStyle: {
//                        emphasis: {
//                            shadowBlur: 10,
//                            shadowOffsetX: 0,
//                            shadowColor: 'rgba(0, 0, 0, 0.5)'
//                        }
//                    }
//                }
//            ]
//        };
//
//            
//
//        // 使用刚指定的配置项和数据显示图表。
//        myChart.setOption(option);