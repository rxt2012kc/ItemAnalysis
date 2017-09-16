<!--
 * CoreUI - Open Source Bootstrap Admin Template
 * @version v1.0.0-alpha.6
 * @link http://coreui.io
 * Copyright (c) 2017 creativeLabs Łukasz Holeczek
 * @license MIT
 -->
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description"
	content="CoreUI - Open Source Bootstrap Admin Template">
<meta name="author" content="Łukasz Holeczek">
<meta name="keyword"
	content="Bootstrap,Admin,Template,Open,Source,AngularJS,Angular,Angular2,Angular 2,Angular4,Angular 4,jQuery,CSS,HTML,RWD,Dashboard,React,React.js,Vue,Vue.js">
<link rel="shortcut icon" href="./img/favicon.png">
<title>FigBook</title>

<!-- Icons -->
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/simple-line-icons.css" rel="stylesheet">

<!-- Main styles for this application -->
<link href="css/style.css" rel="stylesheet">
<link href="css/feature.css" rel="stylesheet">
<!-- 数据 -->

<!-- 必须的一些库 -->
<script src="source/echarts.js"></script>
3
<script src="d3/d3.min.js"></script>
<script src="d3/d3.layout.cloud.js"></script>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="slider/jquery.immersive-slider.js"></script>

<link href='slider/immersive-slider.css' rel='stylesheet'
	type='text/css'>
<!-- 数据 -->
<script>
	// 销量走势
	var saled_time_data = [ [ "2000-06-05", 116 ], [ "2000-06-06", 129 ],
			[ "2000-06-07", 135 ], [ "2000-06-08", 86 ], [ "2000-06-09", 73 ],
			[ "2000-06-10", 85 ], [ "2000-06-11", 73 ], [ "2000-06-12", 68 ],
			[ "2000-06-13", 92 ], [ "2000-06-14", 130 ], [ "2000-06-15", 245 ],
			[ "2000-06-16", 139 ], [ "2000-06-17", 115 ],
			[ "2000-06-18", 111 ], [ "2000-06-19", 309 ],
			[ "2000-06-20", 206 ], [ "2000-06-21", 137 ],
			[ "2000-06-22", 128 ], [ "2000-06-23", 85 ], [ "2000-06-24", 94 ],
			[ "2000-06-25", 71 ], [ "2000-06-26", 106 ], [ "2000-06-27", 84 ],
			[ "2000-06-28", 93 ], [ "2000-06-29", 85 ], [ "2000-06-30", 73 ],
			[ "2000-07-01", 83 ], [ "2000-07-02", 125 ], [ "2000-07-03", 107 ],
			[ "2000-07-04", 82 ], [ "2000-07-05", 44 ], [ "2000-07-06", 72 ],
			[ "2000-07-07", 106 ], [ "2000-07-08", 107 ], [ "2000-07-09", 66 ],
			[ "2000-07-10", 91 ], [ "2000-07-11", 92 ], [ "2000-07-12", 113 ],
			[ "2000-07-13", 107 ], [ "2000-07-14", 131 ],
			[ "2000-07-15", 111 ], [ "2000-07-16", 64 ], [ "2000-07-17", 69 ],
			[ "2000-07-18", 88 ], [ "2000-07-19", 77 ], [ "2000-07-20", 83 ],
			[ "2000-07-21", 111 ], [ "2000-07-22", 100 ],
			[ "2000-07-23", 100 ], [ "2000-07-24", 200 ] ];
	//ebook and book
	var data_two_book = [ {
		value : 10000,
		name : '电子书'
	}, {
		value : 510,
		name : '纸质书'
	} ];
	//词云
	var wordObj = [];
	var num = 30;
	for (var i = 0; i < num; i++) {
		wordObj.push({
			"text" : i.toString(),
			"size" : i % 10
		});
	}
	//以上是自动生成wordObj的代码，在实际应用中应该删除

	//评价走势
	var timeLine = [ '2017-1-1', '2017-1-2', '2017-1-3', '2017-1-4',
			'2017-1-5', '2017-1-6', '2017-1-7', '2017-1-8', '2017-1-9',
			'2017-1-10', '2017-1-11', '2017-1-12' ];
	var best = [ 709, 1917, 2455, 2610, 1719, 1433, 1544, 3285, 5208, 3372,
			2484, 4078 ];
	var good = [ 327, 1776, 507, 1200, 800, 482, 204, 1390, 1001, 951, 381, 220 ];
	var bad = [ 327, 1776, 507, 1200, 800, 482, 204, 1390, 1001, 951, 381, 220 ];
	//评价饼图
	var comment_data = genData();
	function genData() {
		var legendData = [ '好评', '中评', '差评' ];
		var seriesData = [ {
			name : '好评',
			value : 30
		}, {
			name : '中评',
			value : 30
		}, {
			name : '差评',
			value : 10
		} ];
		return {
			legendData : legendData,
			seriesData : seriesData
		};
	}
	//相关数目推荐
	var link = [ 'www.bupt.edu.cn', 'www.bupt.edu.cn' ]
	var src = [ 'https://images-na.ssl-images-amazon.com/images/I/51Gsycdh-TL._SX430_BO1,204,203,200_.jpg' ];
	// 左栏信息
	var book_link = "https://images-na.ssl-images-amazon.com/images/I/51Gsycdh-TL._SX430_BO1,204,203,200_.jpg"
	var book_abstract = "这是摘要啊"
</script>
</head>

<body
	class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
	<!-- 上边栏 -->
	<header class="app-header navbar">
		<a class="navbar-brand" href="#"></a>
	</header>
	<!-- 左边栏 -->
	<div class="app-body">
		<div class="sidebar">
			<nav class="sidebar-nav">
				<div class="book-picture">
					<img class="book-img" src="#" alt="">
					<p class="abstract">摘要</p>
				</div>
				<div class="info-footer">
					<h1>FigBook</h1>
					</br> </br> 网研交换队 荣誉出品 </br> </br> 任鑫涛、张思遥、孙陈娜、贾已
				</div>
			</nav>
		</div>
		<!-- Main content -->
		<main class="main">
		<div class="container-fluid">
			<div class="animated fadeIn">
				<div class="row">
					<!-- 词云 -->
					<div class="col-sm-6 col-lg-6">
						<div class="card text-white bg-primary">
							<div class="card-body pb-0">
								<div class="btn-group float-right"></div>
								<h4 class="mb-0">用户评价词云</h4>
							</div>
							<div class="chart-wrapper px-3" style="height: 300px;">
								<div id="wordcloud_div" class="wordcloud_div"
									style="width: 100%; height: 100%; z-index: 100;"></div>
							</div>
						</div>
					</div>
					<!--/.col-->
					<!-- 近期销量 -->
					<div class="col-sm-6 col-lg-6">
						<div class="card text-white bg-info">
							<div class="card-body pb-0">
								<h4 class="mb-0">近期销量</h4>

							</div>

							<div class="chart-wrapper px-3" style="height: 300px;">
								<div id="saled_time" style="width: 100%; height: 100%;"></div>
							</div>
						</div>
					</div>
					<!--/.col-->
					<!-- 销量vs -->
					<div class="col-sm-6 col-lg-6">
						<div class="card text-white bg-warning">
							<div class="card-body pb-0">
								<div class="btn-group float-right"></div>
								<h4 class="mb-0">销量——纸质书vs电子书</h4>
							</div>
							<div class="chart-wrapper" style="height: 300px;">
								<div id="two_book" style="width: 100%; height: 100%;"></div>
							</div>
						</div>
					</div>
					<!--/.col-->
					<!-- 用户评价分布 -->
					<div class="col-sm-6 col-lg-6">
						<div class="card text-white bg-danger">
							<div class="card-body pb-0">
								<div class="btn-group float-right"></div>
								<h4 class="mb-0">用户评价分布</h4>
							</div>
							<div class="chart-wrapper px-3" style="height: 300px;">
								<div id="comment_cake" style="width: 100%; height: 100%;"></div>
							</div>
						</div>
					</div>
					<!--/.col-->
				</div>
				<!--/.row-->

			</div>
			<!-- 用户情感走向 -->
			<div class="card">
				<div class="card-body">
					<div class="row">
						<div class="col-sm-5">
							<h4 class="card-title mb-0">用户情感走向</h4>
						</div>
					</div>
					<!--/.row-->
					<div class="chart-wrapper" style="height: 400px;">
						<div id="comment_time" style="width: 100%; height: 100%;"></div>
					</div>
				</div>
			</div>
			<!-- 相似书籍推荐 -->
			<div class="card">
				<div class="card-body">
					<div class="row">
						<div class="col-sm-5">
							<h4 class="card-title mb-0">相似书籍推荐</h4>
						</div>

					</div>
					<!--/.row-->
					<div class="wrapper" style="margin-top: 20px">
						<div class="main_slide">
							<div class="page_container">
								<div id="immersive_slider">
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"> <img src="img/logo.png"
												alt="Slider 1">
											</a>
										</div>
									</div>
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"> <img src="./img/logo.png"
												alt="Slider 1"></a>
										</div>
									</div>
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"><img src="./img/logo.png"
												alt="Slider 1"></a>
										</div>
									</div>
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"><img src="./img/logo.png"
												alt="Slider 1"></a>
										</div>
									</div>
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"><img src="./img/logo.png"
												alt="Slider 1"></a>
										</div>
									</div>
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"><img src="./img/logo.png"
												alt="Slider 1"></a>
										</div>
									</div>
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"><img src="./img/logo.png"
												alt="Slider 1"></a>
										</div>
									</div>
									<div class="slide">
										<div class="image">
											<a href="#" target="_blank"><img src="./img/logo.png"
												alt="Slider 1"></a>
										</div>
									</div>

									<a href="#" class="is-prev">&laquo;</a> <a href="#"
										class="is-next">&raquo;</a>
								</div>
							</div>
							<script>
								function GetQueryString(name) {
									var reg = new RegExp("(^|&)" + name
											+ "=([^&]*)(&|$)");
									var r = window.location.search.substr(1)
											.match(reg);
									if (r != null)
										return unescape(r[2]);
									return null;
								}
								ANSI = GetQueryString("ANSI");
								if (ANSI != null && ANSI.toString().length > 1) {
									alert(GetQueryString("ANSI"));
								}
								comment_data = {};
								saled_time_data = []
								wordObj = [];
								timeLine = [];
								good = [];
								medium = [];
								bad = [];
								sell_date = [];
								sell_num = [];
								data_two_book = [];
								words = [];
								weights = [];
								$(document)
										.ready(
												function() {
															itemSearchUrl = '/amazonDemo/amazonSearch/searchItem.do',
															$
																	.ajax({
																		url : itemSearchUrl,
																		data : {
																			"ANSI" : ANSI
																		},
																		type : 'GET',
																		dataType : 'json',
																		cache : true,
																		async : false,
																		success : function(
																				data) {
																			console
																					.log(data)
																			alert(data)
																			if (data) {
																				// review distribution
																				comment_data.legendData = [
																						'好评',
																						'中评',
																						'差评' ]
																				comment_data.seriesData = [
																						{
																							name : '好评',
																							value : data.reviewDistribution.good
																						},
																						{
																							name : '中评',
																							value : data.reviewDistribution.medium
																						},
																						{
																							name : '差评',
																							value : data.reviewDistribution.bad
																						} ]
																				timeLine = data.timeLine
																				good = data.good
																				medium = data.medium
																				bad = data.bad
																				sell_date = data.sellDate
																				sell_num = data.sellNum
																				/* data_two_book = [{value:data.kindle, name: '电子书'},
																				                 {value:data.paper, name: '纸质书'}]; */
																				words = data.words
																				weights = data.weights
																				for (var i = 0; i < sell_date.length; i++) {
																					saled_time_data
																							.push([
																									sell_date[i],
																									sell_num[i] ])
																				}
																				//词云
																				var num = 30;
																				for (var i = 0; i < words.length; i++) {
																					wordObj
																							.push({
																								"text" : words[i],
																								"size" : weights[i]
																							});
																				}
																			} else {

																			}
																		}
																	})
												});
							</script>

							<script>
								//相关数目推荐
								var link = [ 'www.bupt.edu.cn',
										'www.bupt.edu.cn' ]
								var src = [ 'https://images-na.ssl-images-amazon.com/images/I/51Gsycdh-TL._SX430_BO1,204,203,200_.jpg' ];
								// 左栏信息
								var book_link = "https://images-na.ssl-images-amazon.com/images/I/51Gsycdh-TL._SX430_BO1,204,203,200_.jpg"
								var book_abstract = "这是摘要啊"
							</script>


						</div>
					</div>
				</div>

			</div>
			<!--/.card-->
		</main>
	</div>

	<script src="./js/app.js"></script>


	<!-- Plugins and scripts required by this views -->

	<script>
		
	</script>

	<!-- Custom scripts required by this view -->
	<script src="./js/views/main.js"></script>
	<script src="./js/comment_cake.js"></script>
	<script src="./js/comment_time.js"></script>
	<script src="./js/saled_time.js"></script>
	<script src="./js/two_book.js"></script>
	<script src="./js/wordcloud_self.js"></script>
	<script src="./js/left_content.js"></script>
	<script src="slider/slider_content.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			set_slide();
			$("#immersive_slider").immersive_slider({
				container : ".main"
			});
		});
	</script>
	<script>
		// 修正不知道哪里来的3
		document.getElementsByTagName("body")[0].childNodes[0].nodeValue = "";
	</script>

</body>

</html>