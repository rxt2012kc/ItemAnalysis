//词云
// 需要使用V3版本
var wordsInEnglish = 171476;
// var wordObj  = [{"text":"222", "size":50},{"text":"text", "size":60},{"text":"text", "size":50},{"text":"text", "size":60},{"text":"text", "size":50},{"text":"text", "size":60},{"text":"text", "size":50},{"text":"text", "size":60},{"text":"text", "size":60},{"text":"text", "size":60},{"text":"text", "size":60}];

for(var key in wordObj){
  wordObj[key].size = 30 + 3*wordObj[key].size;
}
var fill = d3.scale.category20();

var color = d3.scale.linear()
            .domain([0,1,2,3,4,5,6,10,15,20,100])
            .range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]); 


var layout = d3.layout.cloud()
    .size([300, 300])  // 宽高
    .words(wordObj)  // 数据
    .padding(5)  // 内间距
    .rotate(function() { return ~~(Math.random() * 2) * 90; })
    .font("Impact")
    .fontSize(function(d) { return d.size; })
    .on("end", draw);

layout.start();


// 渲染
function draw(words) {
  d3.select("#wordcloud_div").append("svg")
      .attr("width", layout.size()[0])
      .attr("height", layout.size()[1])
    .append("g")
      .attr("transform", "translate(" + layout.size()[0] / 2 + "," + layout.size()[1] / 2 + ")")
    .selectAll("text")
      .data(words)
    .enter().append("text")
      .style("font-size", function(d) { return d.size + "px"; })
      .style("font-family", "Impact")
      .style("fill", function(d, i) { return fill(i); })
      .attr("text-anchor", "middle")
      .attr("transform", function(d) {
        return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
      })
      .text(function(d) { return d.text; });
}