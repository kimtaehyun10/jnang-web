<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery-ui.css?v=1" />
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script> 


<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/rent/rent.write.js"></script>
<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
String today = transFormat.format(from);
String param 	= request.getParameter("q");
String [] arrParam = param.split("\\/"); 
String PLACE_CD = arrParam[0];
String YMD 		= request.getParameter("ymd");
YMD = (YMD == null || YMD == "") ? today : YMD;
%>
<STYLE type="text/css">
<!--
p.HStyle0
	{style-name:"바탕글"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle0
	{style-name:"바탕글"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle0
	{style-name:"바탕글"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle1
	{style-name:"본문"; margin-left:15.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle1
	{style-name:"본문"; margin-left:15.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle1
	{style-name:"본문"; margin-left:15.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle2
	{style-name:"개요 1"; margin-left:10.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle2
	{style-name:"개요 1"; margin-left:10.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle2
	{style-name:"개요 1"; margin-left:10.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle3
	{style-name:"개요 2"; margin-left:20.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle3
	{style-name:"개요 2"; margin-left:20.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle3
	{style-name:"개요 2"; margin-left:20.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle4
	{style-name:"개요 3"; margin-left:30.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle4
	{style-name:"개요 3"; margin-left:30.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle4
	{style-name:"개요 3"; margin-left:30.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle5
	{style-name:"개요 4"; margin-left:40.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle5
	{style-name:"개요 4"; margin-left:40.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle5
	{style-name:"개요 4"; margin-left:40.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle6
	{style-name:"개요 5"; margin-left:50.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle6
	{style-name:"개요 5"; margin-left:50.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle6
	{style-name:"개요 5"; margin-left:50.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle7
	{style-name:"개요 6"; margin-left:60.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle7
	{style-name:"개요 6"; margin-left:60.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle7
	{style-name:"개요 6"; margin-left:60.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle8
	{style-name:"개요 7"; margin-left:70.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle8
	{style-name:"개요 7"; margin-left:70.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle8
	{style-name:"개요 7"; margin-left:70.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle9
	{style-name:"쪽 번호"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:굴림; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle9
	{style-name:"쪽 번호"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:굴림; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle9
	{style-name:"쪽 번호"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:굴림; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle10
	{style-name:"머리말"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:150%; font-size:9.0pt; font-family:굴림; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle10
	{style-name:"머리말"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:150%; font-size:9.0pt; font-family:굴림; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle10
	{style-name:"머리말"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:150%; font-size:9.0pt; font-family:굴림; letter-spacing:0; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle11
	{style-name:"각주"; margin-left:13.1pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle11
	{style-name:"각주"; margin-left:13.1pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle11
	{style-name:"각주"; margin-left:13.1pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle12
	{style-name:"미주"; margin-left:13.1pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle12
	{style-name:"미주"; margin-left:13.1pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle12
	{style-name:"미주"; margin-left:13.1pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
p.HStyle13
	{style-name:"메모"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:굴림; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
li.HStyle13
	{style-name:"메모"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:굴림; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
div.HStyle13
	{style-name:"메모"; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:굴림; letter-spacing:-5%; font-weight:normal; font-style:normal; color:#000000;}
-->
</STYLE>
<style>

body {
    font-family: "맑은 고딕"
}

/*-- POPUP common style S ======================================================================================================================== --*/
#mask {
    position: absolute;
    left: 0;
    top: 0;
    z-index: 999;
    background-color: #000000;
    display: none; }

.layerpop {
    display: none;
    z-index: 1000;
    border: 2px solid #ccc;
    background: #fff;
    cursor: move; }

.layerpop_area .title {
    padding: 10px 10px 10px 10px;
    border: 0px solid #aaaaaa;
    background: #f1f1f1;
    color: #3eb0ce;
    font-size: 1.3em;
    font-weight: bold;
    line-height: 24px; }

.layerpop_area .layerpop_close {
    width: 25px;
    height: 25px;
    display: block;
    position: absolute;
    top: 10px;
    right: 10px;
    background: transparent url('btn_exit_off.png') no-repeat; }

.layerpop_area .layerpop_close:hover {
    background: transparent url('btn_exit_on.png') no-repeat;
    cursor: pointer; }

.layerpop_area .content {
    width: 96%;    
    margin: 2%;
    color: #828282; }
/*-- POPUP common style E --*/

</style>

<script type="text/javascript">
/**
* @projectDescription rent.list.js
*
* @author 
* @version 1.21
*/
$(function(){
	setMenuTitle('bg_99', '체육시설', '${rentCfg.COMNM} >  ${rentCfg.PLACE_NM}', true);
	$('.sub_navi1 .navi1 .mbx1 > a').on('click',function(){
		if($('.sub_navi1 .bg1').css('display')==='none'){
			var idx_n=$(this).parent().parent().index();
			$('.sub_navi1 .navi1 .dp1').each(function(){
				if(idx_n!=$(this).index()){
					$(this).find('ul').slideUp(100);
				}
			});
			$(this).next().slideToggle(150);
		}else{
			$(this).next().slideToggle(150);
		}
	});
	
	
	let cont = '';
	<c:forEach items="${centerGroup}" var="result" varStatus="status">
		cont += '<a href="/rent/write/?q=${result.PLACE_CD}"><li>${result.PLACE_NM}</li></a>';
	</c:forEach>
	$(".mbx1:eq(1)>ul").append(cont);	
	
	//당일 예약불가능 3일후 가능하도록 +3
	var clareCalendar = {maxDate: '+60d',minDate: '+2d' }
	$("#RENT_DATE").datepicker(clareCalendar);

	var sYMD = "<%=YMD%>";
	if (sYMD.length == 8) {
		sYMD = fn_convertDate(2,sYMD);
	}
	sYMD = fn_convertDate(1,sYMD)
	<%
	//선택된 날짜값없을경우 자동날짜  처리 오늘날짜 +3일 (당일 예약 불가능하도록)
	if (request.getParameter("ymd") == null || request.getParameter("ymd") == "") {
	%>
		sYMD = getAddDay(sYMD, 2);
	<%
	} else {
	%>
		sYMD = fn_convertDate(2,sYMD);
	<%
	}
	%>
	
	$("#RENT_DATE").val(sYMD);
	
	//선택 날짜 예약 로드
	setTimeout("getRent('',<%=PLACE_CD%>,0);",500);
	
});
var checked = "N";
function send() {
	
	//try {
		var frm = document.frm1;
		var contract = $("input[name=contractYn]:checked").val();
		if(checked != 'Y'){
			alert("대관계약서를 읽어주세요.");
			return false;	
		}else {
			if(contract != 'Y'){
				alert("대관계약서에 동의를 눌러주셔야 대관가능합니다.");
				return false;
			}
		}
		//이미 신청 중콕첵크
		getRent('',<%=PLACE_CD%>,9);
		var agree = $("input[name=agreeYn]:checked").val();
		if(agree != 'Y'){
			alert("개인정보 수집 동의서에 동의를 눌러주셔야 대관가능합니다.");
			return false;	
		}
		
		if (confirm("\n 대관 상담신청  하시겠습니까?\n ")) {
		} else {
			return false;	
		}
		
		var aItemList = new Array();
			
			var formData=new FormData();
			
			var checkedDesk = $('input:checkbox[id="deskBox"]').is(":checked");
			
			if(checkedDesk == true) {
				formData.append("DESK","0");
			}else {
				formData.append("DESK",$("#DESK").val());
			}
			
			var checkedMic = $('input:checkbox[id="micBox"]').is(":checked");
			if(checkedMic == true) {
				formData.append("MIC","0");
			}else {
				formData.append("MIC",$("#MIC").val());
			}
			
			var inputFile=$("#file");
			if(inputFile.length > 0) {
				var files=inputFile[0].files;	
				var fileCheck = document.getElementById("file").value;
				for(var i=0;i<files.length;i++){
					formData.append("files",files[i]);
				}		
			}
			
			if(!fileCheck){
		        alert("대관사용허가 신청서 , 대관 계약서를 첨부해주세요.");
		        return false;
		    }
			var tape = $("input[name=tapeYn]:checked").val();
			
			//formData.append("TAPE_YN",);
			formData.append("MEM_NO",$("#MEM_NO").val());
			formData.append("TAPEYN",tape);
			formData.append("GROUP_NM",$("#GROUP_NM").val());
			formData.append("HOME_ADDR",$("#HOME_ADDR").val());
			formData.append("PURPOSE",$("#PURPOSE").val());
			formData.append("PLACE_CD",$("#PLACE_CD").val());
			formData.append("MEM_NM",$("#MEM_NM").val());
			formData.append("TEL",$("#TEL").val());
			formData.append("COMCD",$("#COMCD").val());		
			formData.append("CONCEPT",$("#CONCEPT").val());
			formData.append("OBJECT",$("#OBJECT").val());
			formData.append("ETIME",$("#ETIME").val());
			formData.append("STIME",$("#STIME").val());
			formData.append("INWON",$("#INWON").val());
			formData.append("RENT_DATE",$("#RENT_DATE").val());
			formData.append("q",$("#q").val());
			
		    
		    
		    
		 /*    if(files.length < 2){
		    	alert("대관사용허가 신청서, 대관계약서를 모두 첨부해주세요.");
		    	return false;
		    } */
		    
		    
		
		    
			$.ajax({
		        type: "post",
		        enctype: 'multipart/form-data',
		        url:'/rent/writeSave',
		        data: formData,
		        processData: false,
		        contentType: false,       
		        success: function (data) {
		        	alert('신청이 완료 되었습니다.');
					top.location.href ='/mypage/rent';	        	
		        },        
		        error: function (jqXHR,textStatus,errorThrown) { 
		        	alert('접수 오류 !  다시 신청 하십시요!~');
		        }
		    });				
		
		
		
	/* 	$.post('/rent/writeSave',  $("#frm1").serialize() , function(data){
			
			if (data == "1") {
				alert('신청이 완료 되었습니다.');
				top.location.href ='/mypage/rent';
			} else {
				alert('접수 오류 !  다시 신청 하십시요!~');
			}
		},"json"); */
/* 	} catch (exception) 
	{
		console.log(exception);
		return false;
	} */
		return false;
	
};
$(document).ready(function(){
    $("#deskBox").change(function(){
        if($("#deskBox").is(":checked")){
            $("#DESK").val("");
            $('#DESK').attr('readonly', true);
        }else{
        	$('#DESK').attr('readonly', false);
        }
    });
    $("#micBox").change(function(){
    	if($("#micBox").is(":checked")){
        	$("#MIC").val("");
            $('#MIC').attr('readonly', true);
        }else {
        	$('#MIC').attr('readonly', false);
        }
    })
});

function openWindowPop(url, name){
	alert(url);
	alert(name);
	return false;
    var options = 'top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no';
    window.open(url, name, options);
}

function wrapWindowByMask() {
    //화면의 높이와 너비를 구한다.
    var maskHeight = $(document).height(); 
    var maskWidth = $(window).width();

    //문서영역의 크기 
    console.log( "document 사이즈:"+ $(document).width() + "*" + $(document).height()); 
    //브라우저에서 문서가 보여지는 영역의 크기
    console.log( "window 사이즈:"+ $(window).width() + "*" + $(window).height());        

    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
    $('#mask').css({
        'width' : maskWidth,
        'height' : maskHeight
    });

    //애니메이션 효과
    //$('#mask').fadeIn(1000);      
    $('#mask').fadeTo("slow", 0.5);
}

function popupOpen() {
    $('.layerpop').css("position", "absolute");
    //영역 가운에데 레이어를 뛰우기 위해 위치 계산 
    $('.layerpop').css("top",(($(window).height() - $('.layerpop').outerHeight()) / 2) + $(window).scrollTop());
    $('.layerpop').css("left",(($(window).width() - $('.layerpop').outerWidth()) / 2) + $(window).scrollLeft());
    $('.layerpop').draggable();
    $('#layerbox').show();
}

function popupClose() {
    $('#layerbox').hide();
    $('#mask').hide();
    checked = "Y";
}

function goDetail() {

    /*팝업 오픈전 별도의 작업이 있을경우 구현*/ 

    popupOpen(); //레이어 팝업창 오픈 
    wrapWindowByMask(); //화면 마스크 효과 
}


</script>
<div class="sub_cont1">
<div class="con_bx">
	

<div class="sub_cleanreport">
	
<form name="frm1" id="frm1" method="post" onsubmit="return send();" enctype="multipart/form-data"> 

	<div class="border_top_3_green"></div>
	
<table class="stbl_w3b border_top_0" summary="이 표는 제목/내용 등의 정보로 구성된 팀등록/수정 폼입니다.">
	<caption>
	대관문의
	</caption>
	<colgroup>
		<col width="200px">
		<col width="*">
	</colgroup>
	<tbody>
	<tr>
		<th>이름</th>
		<td>
			<input type="text" id="MEM_NM" name="MEM_NM" value="<c:out value='${myData.MEM_NM}'/>" maxlength="20" class="inputbox_01a" required="" placeholder="이름" readonly="readonly;">
		</td>
	</tr>
	<tr>
		<th>단체명</th>
		<td>
			<input type="text" id="GROUP_NM" name="GROUP_NM" value="" maxlength="20" class="inputbox_01a" required="" placeholder="단체명을 입력해주세요.">
		</td>
	</tr>
	<tr>
		<th>주소</th>
		<td>
			<input type="text" id="HOME_ADDR" name="HOME_ADDR" value="<c:out value='${myData.HOME_ADDR}'/>" maxlength="20" class="inputbox_01a inputbox_01_s3" required="" placeholder="" readonly="readonly;">
		</td>
	</tr>
	<tr>
		<th>연락처</th>
		<td>
			<input type="text" id="TEL" name="TEL" value="<c:out value='${myData.HP}'/>" maxlength="20" class="inputbox_01a" required="" placeholder="숫자만 입력해주세요." readonly="readonly;">
		</td>
	</tr>
	<tr>
		<th>행사명</th>
		<td>
			<input type="text" id="OBJECT" name="OBJECT" value="" maxlength="100" class="inputbox_01a inputbox_01_s3" required="" placeholder="행사명을 입력해주세요.">
		</td>
	</tr>
	<tr>
		<th>행사 참여 인원</th>
		<td>
			<input type="text" name="INWON" id="INWON" placeholder="숫자만 기입해주세요." maxlength="10" class="inputbox_01a" required=""
    			   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
		</td>
	</tr>
	<tr>
		<th>사용 목적</th>
		<td>
			<input type="text" id="PURPOSE" name="PURPOSE" value="" maxlength="20" class="inputbox_01a" required="" placeholder="사용목적을 입력해주세요.">
		</td>
	</tr>
	<tr>
		<th>예약현황</th>
		<td><div id="reservedList"></div></td>
	</tr>
	<tr>
		<th>대관일/시</th>
		<td>
			<input type="text" id="RENT_DATE" name="RENT_DATE" maxlength="10" value="" class="inputbox_01a" required="" onchange="getRent('',<%=PLACE_CD%>,9);">
			/ 
			<select id="STIME" name="STIME" class="inputbox_01a" required="" onchange="getRent('',<%=PLACE_CD%>,9);">
				<option value="">시작 시간</option>
				<c:forEach var="ii" begin="06" end="23" step="1">
				<option value="${ii}">${ii}:00</option>
				</c:forEach> 
			</select>
			 ~  
			<select id="ETIME" name="ETIME" class="inputbox_01a" required="" onchange="getRent('',<%=PLACE_CD%>,9);">
				<option value="">종료 시간</option>
				<c:forEach var="ii" begin="06" end="23" step="1">
				<option value="${ii}">${ii}:00</option>
				</c:forEach> 
			</select>
			 (종료시간 : 정리시간을 포함한 시간)
		</td>
	</tr>
	<tr>
		<th>철 책상</th>
		<td>
			<input type="text" id="DESK" name="DESK" value="" maxlength="20" class="inputbox_01a" required="" placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />개
			&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="deskBox" name="deskBox" value="0" style='width:30px; height:30px;'> 불필요
		</td>
	</tr>
	<tr>
		<th>마이크 및 단성</th>
		<td>
			<input type="text" id="MIC" name="MIC" value="" maxlength="20" class="inputbox_01a" required="" placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />개
			&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="micBox" name="micBox" value="0" style='width:30px; height:30px;'> 불필요
		</td>
	</tr>
	<tr>
		<th>국민의례 TAPE</th>
		<td>
			<input type="radio" name="tapeYn" value="Y" checked> 필요&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           	<input type="radio" name="tapeYn" value="N">불필요
		</td>
	</tr>
	<tr>
		<th>대관계약서 확인</th>
		<td>
			 <a href="javascript:goDetail('테스트');" required="" class="size_m2 btn_blue1">대관 계약서</a>
			 <input type="radio" name="contractYn" value="Y"> 동의&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <input type="radio" name="contractYn" value="N"> 미동의
			 <div id="mask"></div>
			 <div id="layerbox" class="layerpop"
			        style="width: 800px; height: 910px;">
			        <article class="layerpop_area">
			        <div class="title">대관계약서</div>
			        <a href="javascript:popupClose();" class="layerpop_close"id="layerbox_close">
			        	<img src='${pageContext.request.contextPath}/resource/images/common/btn_exit_off.png' alt=''>
			        </a> <br>
			        
			        <div class="content">
					      <P CLASS=HStyle0 STYLE='line-height:200%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:200%'>【별지 제4호 서식】</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-3%;line-height:200%'>〈개정 2013.03.21, </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-10%;line-height:200%'>2016.08.</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-3%;line-height:200%'>30.</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-10%;line-height:200%'>〉</SPAN></P>

						<P CLASS=HStyle0 STYLE='text-align:center;line-height:120%;'><SPAN STYLE='font-size:20.0pt;font-family:"휴먼명조";font-weight:bold;line-height:120%'>대관 계약서</SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:14.0pt;font-family:"휴먼명조";font-weight:bold;line-height:150%'><BR></SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:13.0pt;font-family:"휴먼명조";letter-spacing:-6%;line-height:150%'>중랑구시설관리공단</SPAN><SPAN STYLE='font-size:13.0pt;font-family:"휴먼명조";line-height:150%'> 체육시설을 이용함에 있어서 아래와 같은 </SPAN><SPAN STYLE='font-size:13.0pt;font-family:"휴먼명조";line-height:150%'>귀책사유별로 구분하되, </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-4%;line-height:150%'>회원의 귀책사유로 인한 계약해지는 개시일 이후 부터 취소일까지 이용일수에 해당하는 금액을 공제한 후 반환한다.</SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;text-align:center;line-height:150%;'><SPAN STYLE='font-size:14.0pt;font-family:"휴먼명조";line-height:150%'>- 준 수 사 항 -</SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'>
						<TABLE border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;border:none;'>
						<TR>
							<TD valign="middle" bgcolor="#ffffff"  style='width:654;height:155;border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
							<P CLASS=HStyle0 STYLE='margin-left:5.0pt;margin-right:5.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>1. 대관 사용신청 승인후 7일이내 대관료를 납부하여야 합니다.</SPAN></P>
							<P CLASS=HStyle0 STYLE='margin-left:5.0pt;margin-right:5.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>2. 대관 사용신청 승인후 7일이내 대관료를 납부하지 않을 시는 사용 허가를 취소할 수 있으며, 이에 대하여 신청인에게 통보합니다.</SPAN></P>
							<P CLASS=HStyle0 STYLE='margin-left:5.0pt;margin-right:5.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>3. 만일, 대관을 취소하고자 할 경우 행사일 당일 포함 7일전에 취소 통보를 하여야하며, 통보가 없을시, 대관료는 반환되지 아니함을 원칙으로 합니다. </SPAN></P>
							<P CLASS=HStyle0 STYLE='margin-left:5.0pt;margin-right:5.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>4. 정해진 시간이 초과 되었을 시에는 50%할증합니다.</SPAN></P>
							</TD>
						</TR>
						</TABLE></P>
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;text-align:center;line-height:150%;'><SPAN STYLE='font-size:14.0pt;font-family:"휴먼명조";line-height:150%'>- 유 의 사 항 - </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:14.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp; </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>1. 체육시설과 관련된 사항은 담당자의 지시에 따라야 한다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;2. 체육관 이용수칙을 준수하여야 한다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;3. 체육관 이용시 지정된 실내화(실내용 실내화)를 착용해야 한다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;4. 계약된 대관 시간은 엄수해야 한다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-left:27.2pt;margin-top:1.0pt;margin-bottom:1.0pt;text-indent:-27.2pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;5. </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-2%;line-height:150%'>계약된 대관 시간 중 안전사고에 대한 책임은 </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-2%;line-height:150%'>계약당사자간의 귀책사유에 따라</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'> 책임을 부담한다.</SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;6. </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-4%;line-height:150%'>지정된 장소이외의 운동시설에서는 음식물 반입을 금지한다. (자판기 커피포함) </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;7. </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-5%;line-height:150%'>대관은 허가된 시설에만 적용되므로 일체의 집기나 운동용품은 사전 허락 후 사용한다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;8. 지정된 대관 시설 외에는 사전허가 없이 출입 또는 사용을 금지한다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;&nbsp;9. 시</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-5%;line-height:150%'>설 등을 파손하였을 때에는 사용자의 과실로 배상해야함을 원칙으로 한다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:150%'>&nbsp;10. 기타 사항은 체육시설이용내규에 따른다. </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:100%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:100%'><BR></SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-left:6.0pt;margin-top:1.0pt;margin-bottom:1.0pt;text-indent:-6.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";letter-spacing:-3%;line-height:150%'> </SPAN><SPAN STYLE='font-size:13.0pt;font-family:"휴먼명조";letter-spacing:-3%;line-height:150%'>본인은 단체를 대표하여 위 내용을 준수할 것을 약속하며 시설대관을 신청합니다.</SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;line-height:100%;'><SPAN STYLE='font-size:14.0pt;font-family:"휴먼명조";line-height:100%'><BR></SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;text-align:center;line-height:180%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:180%'><BR></SPAN></P>
						
						<P CLASS=HStyle0 STYLE='margin-top:1.0pt;margin-bottom:1.0pt;text-align:center;line-height:200%;'><SPAN STYLE='font-size:12.0pt;font-family:"휴먼명조";line-height:200%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;신 청 인 :&nbsp;&nbsp;${myData.MEM_NM} </SPAN></P>
						
						<P CLASS=HStyle0 STYLE='line-height:130%;'><SPAN STYLE='font-size:16.0pt;font-family:"휴먼명조";font-weight:bold;line-height:130%'>중랑구시설관리공단이사장</SPAN><SPAN STYLE='font-family:"휴먼명조"'> </SPAN><SPAN STYLE='font-size:13.0pt;font-family:"휴먼명조";line-height:130%'>귀중</SPAN></P>
						<DIV STYLE=''>
							<P CLASS=HStyle0 STYLE='text-align:right;line-height:180%;'><SPAN STYLE='font-family:"양재 튼튼B,한컴돋움";letter-spacing:12%'><BR></SPAN></P>
						</DIV>
			        </div>
			        </article>
			    </div>
		</td>
	</tr>
		<!-- <tr>
			<th>파일 업로드</th>
			<td>
				<span style="color:red">작성하신 대관계약서를 첨부해주세요.</span>
				<br>
				<input type="file" id="file" name="file" required="" multiple >
				
			</td>
		</tr> -->
	<tr>
		<th>개인정보수집동의서</th>
		<td>
			<textarea name="CONCEPT" id="CONCEPT" maxlength="1000" style="height:200px;width:80%;" class="inputbox_01a" required="" readonly="readonly;">
▪  수집목적 : 개인정보보호법제15조 전자정부법제36조(행정정보의공동이용에따라감면서류확인)/서울특별시중랑구립
	      체육시설설치및운영에관한조례에의거구민체육센터 관련 업무에 이용됨에 동의합니다. 
▪  수집항목(필수) : 본인의 생년월일, 주소, 유선연락처, 휴대폰연락처, 성별, 은행계좌번호(환불신청시)
▪  기타수집 : 지방공기업경영평가, 감사, 고객만족도조사업무등에 이용됨에 동의합니다
▪  수집한 개인정보는 제3자에게 제공되지 않습니다.
▪  개인정보의(가입신청서) 보유 및 이용기간 : 보존기간 2년

</textarea>
			<br>	
			<input type="radio" name="agreeYn" style="margin-left:65%;" value="Y"> 동의&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           	<input type="radio" name="agreeYn" value="N">미동의
		</td>
	</tr>
    </tbody>
</table>
※ 대관시간 결정은 행사준비 시간, 반출, 청소시간을 포함하여 신청하시기 바랍니다.<br>
※ 신청이외의 출입시 추가비용을 별도로 내셔야합니다.<br>
※ 담당자가 (매일)일정확인 후 개별 연락 드립니다.
<br>
<br>
	<div class="bx_btns_01a ali_c">
		<input type="hidden" id="q" name="q" value="<%=param%>">
		<input type="hidden" id="COMCD" name="COMCD" value="${rentCfg.COMCD}">
		<input type="hidden" name="id" value="<c:out value='${myData.ID}'/>">
		<input type="hidden" id="MEM_NO" name="MEM_NO" value="<c:out value='${myData.MEM_NO}'/>">
		<input type="hidden" id="PLACE_CD" name="PLACE_CD" value="<%=PLACE_CD%>">
		<input type="hidden" id="arryData" name="arryData">
		<!-- <input type="button" class="size_m2 btn_green1" value="대관 문의 테스트" onClick="send();"> -->
		<input type="submit" class="size_m2 btn_green1" value="대관 문의">
		<a href="/rentlist" id=" " class="size_m2 btn_green1" class="gray2">취 소</a>
		<!-- <input type="button" class="size_m2 btn_green1" value="대관신청" onClick="send();"> -->
	</div>

</form>


		<!-- } 서브본문영역1내부 -->
		</div>
	</div>