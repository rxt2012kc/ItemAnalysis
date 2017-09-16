function loadXMLSync(url) {
  try {
    // Prefer XMLHttpRequest when available
    // var xhr = new XMLHttpRequest()
    // xhr.open('GET', url, false)
    // xhr.withCredentials = true;
    // xhr.setRequestHeader('Content-Type', 'text/xml')
    // xhr.setRequestHeader('Authorization', 'Basic droldan01@hotmail.com:password');
    // // xhr.setRequestHeader('Access-Control-Allow-Credentials','true')
    // xhr.send()
    
  }
  catch (e) {
    // XMLHttpRequest not available, fallback on ActiveXObject
    try {
      var activex = new ActiveXObject('Microsoft.XMLDOM')
      activex.async = false
      activex.load(url)

      return activex
    }
    catch (e) {
      // Neither XMLHttpRequest or ActiveXObject are available
      return undefined
    }
  }
}
function get(url){
  xmlHttp = createXMLHttpRequest();  
  xmlHttp.open("GET", url, true);// 异步处理返回   
  xmlHttp.onreadystatechange = callback;   
  xmlHttp.setRequestHeader("Content-Type",  "application/x-www-form-urlencoded;");  
  xmlHttp.send();  

  return xmlhttp.responseXML
}
loadXMLSync('http://47.93.18.242:8080/page?action=response.txt')