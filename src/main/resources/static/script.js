
    var data = /*[[${chartData}]]*/ [];

    // Prepare the series data
    var seriesData = [{
    name: 'Average Answer',
    data: data.map(function(item) {
    return item.average_answer;
})
}];

    // Create the chart
    Highcharts.chart('chartContainer', {
    chart: {
    polar: true,
    type: 'area'
},
    title: {
    text: 'Average Answers by Category'
},
    xAxis: {
    categories: data.map(function(item) {
    return item.category;
}),
    tickmarkPlacement: 'on',
    lineWidth: 0
},
    yAxis: {
    gridLineInterpolation: 'polygon',
    lineWidth: 0,
    min: 0
},
    series: seriesData
});
