//近期销量
// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('saled_time'));

        // 指定图表的配置项和数据
        
        
        var dateList = saled_time_data.map(function (item) {
            return item[0];
        });
        var valueList = saled_time_data.map(function (item) {
            return item[1];
        });

        var option = {

            // Make gradient line here
            visualMap: [ 
            {
                show: false,
                type: 'continuous',
                seriesIndex: 0,
                dimension: 0,
                min: 0,
                max: dateList.length - 1
            }],


            // title: [
            // {
            //     left: 'center',
            //     text: '近期销量'
            // }],
            tooltip: {
                trigger: 'axis'
            },
            xAxis: [
            {
                data: dateList,
            }],
            yAxis: [{
                splitLine: {show: false},
            }],
            series: [
            {
                type: 'line',
                showSymbol: false,
                data: valueList,
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);