
var link = ['www.bupt.edu.cn','www.bupt.edu.cn']
var src = ['https://images-na.ssl-images-amazon.com/images/I/51Gsycdh-TL._SX430_BO1,204,203,200_.jpg'];
// ,
// 			'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505415783345&di=1aeede89c9f2a84de982d2ef8ae08fe4&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-07-03%2F200873111259355_2.jpg']

function set_slide(){
	var i=0;
	var slide = document.getElementsByClassName("slide");
	// slide[0].className = "hidden";
	for(i=src.length; i<slide.length;i+=1){
		slide[i].className = "hidden";
		i-=1;
	}
	for(i=0 ; i<src.length ; i+=1){
		slide[i].childNodes[1].childNodes[1].setAttribute("href",link[i]);
		slide[i].childNodes[1].childNodes[1].childNodes[1].setAttribute("src",src[i]);	
	}
}





// var data = ["img/slide1.jpg","img/slide2.jpg","img/slide3.jpg"]

// function begin_creat(){
// 	var immersive_slider = document.getElementById("immersive_slider");
// 	for(var i=0;i<data.length;i++){
// 		slider = create_slider(data[i]);
// 		// insert_first(immersive_slider,slider);
// 		immersive_slider.appendChild(slider);
// 	}
// 	var a = document.createElement("a");
// 	a.setAttribute("href","#");
// 	a.className = "is-prev";
// 	a.innerHTML = "&laquo;"
// 	immersive_slider.appendChild(a);
// 	var a2 = document.createElement("a");
// 	a2.setAttribute("href","#");
// 	a2.className = "is-next";
// 	a2.innerHTML = "&laquo;"
// 	immersive_slider.appendChild(a2);
// }

// function insert_first(target,thing){
// 	first = target.firstChild;
// 	target.appendChild(thing);
// 	target.insertBefore(first,thing);
// }

// function create_slider(ele){
	
// 	var slider = document.createElement("div");
// 	slider.className = "slider";

// 	// var content = document.createElement("div");
// 	// content.className = "content";
// 	// var h2 = document.createElement("h2");
// 	// h2.innerHTML = ele[0];
// 	// var p = document.createElement("p");
// 	// p.innerHTML = ele[1];
// 	// content.appendChild(h2);
// 	// content.appendChild(p);

// 	var image = document.createElement("image");

// 	var a = document.createElement("a");
// 	a.setAttribute("href",ele);
// 	a.setAttribute("target","_blank")

// 	var img = document.createElement("img")
// 	img.setAttribute("src",ele)
// 	img.setAttribute ("alt","Slider 1")
// 	a.appendChild(img);
// 	image.appendChild(a);
// 	slider.appendChild(image);

// 	return slider;
// }

