<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>FigBook</title>

<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"
	href="./FigBook/css/bootstrap.css">
<!--Main styles-->
<link rel="stylesheet" type="text/css" href="./FigBook/css/main.css">
<!--Adaptive styles-->
<link rel="stylesheet" type="text/css" href="./FigBook/css/adaptive.css">
<!--Swipe menu-->
<link rel="stylesheet" type="text/css" href="./FigBook/css/pushy.css">
<!--fonts-->
<link rel="stylesheet" type="text/css"
	href="./FigBook/css/font-awesome.css">
<!--animation css-->
<link rel="stylesheet" type="text/css" href="./FigBook/css/animate.css">
<!-- Slider Revolution -->
<link rel="stylesheet" type="text/css" href="rs-plugin/css/settings.css"
	media="screen" />
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script',
			'https://www.google-analytics.com/analytics.js', 'ga');

	ga('create', 'UA-78930914-1', 'auto');
	ga('send', 'pageview');
</script>
</head>

<body onload="initialize()" class="promo">
	<!--autorization-->
	<div class="add_place none" id="autorized">
		<div class="place_form login_form">
			<i class="fa fa-times close_window" id="closeau"></i>
			<h3>
				Autorization<span></span>
			</h3>
			<form>
				<label>Login:<input type="text"></label> <label>Password:<input
					type="text"></label> <a href="#" class="btn btn-success">Log in</a>
				<a href="#" class="btn btn-primary"><i class="icon-facebook"></i>Log
					in with Facebook</a>
			</form>
		</div>
	</div>

	<!-- Site Overlay -->
	<div class="site-overlay"></div>
	<div id="container">
		<div class="top_promo_block" id="promo_head">
			<div class="start_descrition">
				<h1>
					welcome to FigBook!<span></span>
				</h1>
				<span>Search the things you are interested in and get
					analyze. Simply.</span>
				<div class="search_promo">
					<div class="input-group">
						<input type="text" class="form-control" id="url" aria-label="...">
						<div class="input-group-btn btn_promo_search">
							<button type="button" class="btn btn-success" id="searchButton">Search</button>
						</div>
					</div>
				</div>
			</div>
			<div class="scroll_block">
				<img src="./FigBook/img/scroll.png" class="animated infinite bounce"
					alt="#">
			</div>
		</div>

		<!--
#################################
- SCRIPT FILES -
#################################
-->
		<!--Google maps API linl-->
		<!--<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCsbzuJDUEOoq-jS1HO-LUXW4qo0gW9FNs"></script>-->
		<script type="text/javascript" src="./FigBook/js/maps/api.js"></script>

		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="./FigBook/js/jquery.min.js"></script>
		<!--scroll animate block-->
		<script src="./FigBook/js/wow.min.js"></script>
		<!--Other main scripts-->
		<script src="./FigBook/js/all_scr.js"></script>
		<!--Bootstrap js-->
		<script src="./FigBook/js/bootstrap.min.js"></script>
		<!--Map js-->
		<script type="text/javascript" src="./FigBook/js/map_index.js"></script>
		<script type="text/javascript" src="./FigBook/js/infobox.js"></script>
		<!--Slider Revolution-->
		<script type="text/javascript"
			src="rs-plugin/js/jquery.themepunch.tools.min.js"></script>
		<script type="text/javascript"
			src="rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
		<!--Parallax-->
		<script type="text/javascript"
			src="./FigBook/js/jquery.parallax-0.2-min.js"></script>

		<!--scroll animation-->
		<script>
			new WOW().init();
		</script>

		<!--Slider revolution settings-->
		<script type="text/javascript">
			jQuery(document).ready(function() {
				"use strict";
				jQuery('.tp-banner').show().revolution({
					dottedOverlay : "none",
					delay : 16000,
					startwidth : 1170,
					startheight : 700,
					hideThumbs : 200,
					thumbWidth : 100,
					thumbHeight : 50,
					thumbAmount : 5,
					navigationType : "bullet",
					navigationArrows : "solo",
					navigationStyle : "preview4",
					touchenabled : "on",
					onHoverStop : "on",
					swipe_velocity : 0.7,
					swipe_min_touches : 1,
					swipe_max_touches : 1,
					drag_block_vertical : false,
					parallax : "scroll",
					parallaxBgFreeze : "on",
					parallaxLevels : [ 10, 7, 4, 3, 2, 5, 4, 3, 2, 1 ],
					keyboardNavigation : "off",
					navigationHAlign : "center",
					navigationVAlign : "bottom",
					navigationHOffset : 0,
					navigationVOffset : 20,
					soloArrowLeftHalign : "left",
					soloArrowLeftValign : "center",
					soloArrowLeftHOffset : 20,
					soloArrowLeftVOffset : 0,
					soloArrowRightHalign : "right",
					soloArrowRightValign : "center",
					soloArrowRightHOffset : 20,
					soloArrowRightVOffset : 0,
					shadow : 0,
					fullWidth : "on",
					fullScreen : "off",
					spinner : "spinner4",
					stopLoop : "off",
					stopAfterLoops : -1,
					stopAtSlide : -1,
					shuffle : "off",
					autoHeight : "off",
					forceFullWidth : "off",
					hideThumbsOnMobile : "off",
					hideNavDelayOnMobile : 1500,
					hideBulletsOnMobile : "off",
					hideArrowsOnMobile : "off",
					hideThumbsUnderResolution : 0,
					hideSliderAtLimit : 0,
					hideCaptionAtLimit : 0,
					hideAllCaptionAtLilmit : 0,
					startWithSlide : 0,
					videoJsPath : "rs-plugin/videojs/",
					fullScreenOffsetContainer : ""
				});
			}); //ready
		</script>

		<!--Parallax house-->
		<script type="text/javascript">
			"use strict";
			$('.categori_block').parallax({

				'elements' : [ {
					'selector' : '.categori_block',
					'properties' : {
						'x' : {
							'background-position-x' : {
								'initial' : 0,
								'multiplier' : 0.1,
								'invert' : true
							}
						},
						'y' : {
							'background-position-y' : {
								'initial' : -780,
								'multiplier' : 0.1,
								'invert' : true
							}
						}
					}
				} ]
			});
		</script>
		<script>
			$("#searchButton").click(function() {
				url = $("#url").val();
				window.location.href = "./FigBook/analyser.jsp?ANSI=" + url;
			});
		</script>
		<script type="text/javascript">
			"use strict";
			setHeiHeight();
			$(window).resize(setHeiHeight);
		</script>
</body>
</html>