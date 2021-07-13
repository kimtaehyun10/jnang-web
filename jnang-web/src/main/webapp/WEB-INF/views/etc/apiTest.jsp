<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="/resource/js/jquery-3.5.1.min.js"></script>
<body>
<script>


function test1() {

 	var obj =  { "GTYPE": "1", "COMCD": "JUNGNANG01"};
	$.post("/kiosk/dayItemList.do", obj , function(data) {
		alert(data);
	});
	//JSON.stringify(aPayData)

	
	
	
	/*
 $.ajax({
     url : "/etc/dayItemList.do",
     type:'POST',
     dataType:'json',
     data: {
		GTYPE: "1",
		COMCD: "JUNGNANG01"
     },
     success : function(data, textStatus, jqXHR) {
    	alert("ok : " + JSON.stringify(jqXHR));
     },
     error : function(jqXHR, textStatus, errorThrown){
     	//Error시, 처리
     	//alert("error : " + JSON.stringify(jqXHR));
     }
	});  
*/
}
</script>	
<a href="#none" onclick="test1();">test</a>
 </body>
</html>
