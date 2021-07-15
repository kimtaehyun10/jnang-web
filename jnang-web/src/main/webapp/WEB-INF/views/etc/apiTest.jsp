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

 	var obj =  { 
 		"GTYPE": "1"
 		,"COMCD": "JUNGNANG01"
 		};
	$.post("/kiosk/dayItemList.do", obj , function(data) {
		$("#dp-data").text(data);
	});

}

function test2() {
 	var obj =  { 
 		"GTYPE": "2"
 		,"COMCD": "JUNGNANG01"
 		,"SPORTS_CD": "01"
		,"USE_TYPE": "01"
 		};
	$.post("/kiosk/dayItemList.do", obj , function(data) {
		$("#dp-data").text(data);
	});

}


function test3() {
	
	let today = new Date(); 
	let year = today.getFullYear(); // 년도
	let month = today.getMonth() + 1;  // 월
		month = (month > 9) ? month : "0"+ month;
	let day = today.getDate();  // 날짜
		day = (day > 9) ? day : "0"+ day;
	let hours = today.getHours(); // 시
		hours = (hours > 9) ? hours : "0"+ hours;
	let minutes = today.getMinutes();  // 분
		minutes = (minutes > 9) ? minutes : "0"+ minutes;
	let seconds = today.getSeconds();  // 초
		seconds = (seconds > 9) ? seconds : "0"+ seconds;
	
	var appDate = ''.concat(year,month,day, hours, minutes, seconds);
	var appNo	= ''.concat(hours, minutes, seconds);
	console.log(appDate);
	console.log(appNo);

	//결제 정보
 	var payList =  { 
 		"COMCD": "JUNGNANG01"	//사업장코드
 		,"USER_ID": "kiosk01" 	//kiosk1,2,3
		,"APP_DATE": appDate 	//승인일시분초 20210708202710
		,"APP_NO": "30002612"	//승인번호
		,"CARD_CD": "55"		//카드사코드
		,"CARD_NM": "우리카드"  	//카드사명		
		,"CARD_NO": "3445-****-**4254"	//카드번호
		,"HALBU_CNT": "0"		//할부
		,"REMARK": "kiosk01 결제"	//메모
 		};

	//상품 리스트
	var itemList = new Object();
	var aItemList = new Array();
	itemList.PART_CD = "01";		//
	itemList.SPORTS_CD = "01";		//대분류
	itemList.ITEM_CD = "A000236";	//상품코드
	itemList.SALE_AMT = "2701";		//판매금액
	itemList.SALE_NUM = "1";		//수량
	itemList.VAT_YN = "Y";			//vat 금액
	itemList.VAT_AMT = "11";		//vat 금액
	aItemList.push(itemList);
	
	itemList = new Object();
	itemList.PART_CD = "02";		//
	itemList.SPORTS_CD = "03";		//대분류
	itemList.ITEM_CD = "B000236";	//상품코드
	itemList.SALE_AMT = "2702";		//판매금액
	itemList.SALE_NUM = "1";		//수량
	itemList.VAT_YN = "Y";			//vat 금액
	itemList.VAT_AMT = "22";		//vat 금액
	aItemList.push(itemList);
	
	//var PAYLIST1 = JSON.stringify(payList);
	//var ITEMLIST1 = JSON.stringify(aItemList);
	var PAYLIST1 	= '{"COMCD":"JUNGNANG01","USER_ID":"kiosk01","APP_DATE": "20210708202710","APP_NO":"30002612","CARD_CD":"55","CARD_NM":"우리카드","CARD_NO":"3445-****-**4254","HALBU_CNT":"0","REMARK":"kiosk01 결제"}';	
	var ITEMLIST1 	= '[{"PART_CD":"01","SPORTS_CD":"01","ITEM_CD":"A000236","SALE_AMT":"2700","SALE_NUM":"1","VAT_YN":"Y","VAT_AMT":"11"},{"PART_CD":"02","SPORTS_CD":"03","ITEM_CD":"B000236","SALE_AMT":"2700","SALE_NUM":"1","VAT_YN":"Y","VAT_AMT":"22"}]';
	
 	var obj = {
 		"PAYLIST" : PAYLIST1//
 		,"ITEMLIST" : ITEMLIST1// 
 	};
	var jsonData = JSON.stringify(obj);
	$.post("/kiosk/dayInsert.do", obj , function(data) {
		$("#dp-data").text(data);
	});
}

function test4() {
 	var obj =  { 
 		"COMCD": "JUNGNANG01"
 		,"TID": "01"
		,"USE_TYPE": "01"
 		};
	$.post("/kiosk/dayCancel.do", obj , function(data) {
		$("#dp-data").text(data);
	});

}
</script>	
<a href="#none" onclick="test1();">일일대분류</a> 
&nbsp; <a href="#none" onclick="test2();">대분류별 상품리스트</a>
&nbsp; <a href="#none" onclick="test3();">상품 insert</a>
&nbsp; <a href="#none" onclick="test4();">상품취소</a>

<div id="dp-data"></div>
 </body>
</html>
