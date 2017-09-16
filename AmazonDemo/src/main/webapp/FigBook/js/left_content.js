// // 左栏信息
// var book_link = "https://images-na.ssl-images-amazon.com/images/I/51Gsycdh-TL._SX430_BO1,204,203,200_.jpg"
// var book_abstract = "这是摘要啊"
var book = document.getElementsByClassName("book-picture")[0];
book.childNodes[1].setAttribute("src",book_link);
book.childNodes[3].innerHTML = book_abstract;
