<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery-ui.css?v=1" />
<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
String today = transFormat.format(from);

String param = request.getParameter("q");

%>
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
	
	var clareCalendar = {maxDate: '+60d',minDate: '+7d' }
	$("#RENT_DATE").datepicker(clareCalendar);
	
	
});


function send() {
	try {
		var frm = document.frm1;
	
		if (confirm("\n 대관 상담신청  하시겠습니까?\n ")) {
		} else {
			return false;	
		}
	
		var aItemList = new Array();
		$.post('/rent/writeSave',  $("#frm1").serialize() , function(data){
			
			if (data == "1") {
				alert('신청이 완료 되었습니다.');
				top.location.href ='/mypage/rent';
			} else {
				alert('접수 오류 !  다시 신청 하십시요!~');
			}
		},"json");
	} catch (exception) 
	{
		console.log(exception);
		return false;
	}
		return false;
	
};




</script>
<div class="sub_cont1">
<div class="con_bx">
	

<div class="sub_cleanreport">
	
<form name="frm1" id="frm1" method="post" onsubmit="return send();"> 

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
			<input type="text" name="MEM_NM" value="<c:out value='${myData.MEM_NM}'/>" maxlength="20" class="inputbox_01a" required="" placeholder="이름">
		</td>
	</tr>
	<tr>
		<th>연락처</th>
		<td>
			<input type="text" name="TEL" value="<c:out value='${myData.HP}'/>" maxlength="20" class="inputbox_01a" required="" placeholder="휴대폰">
		</td>
	</tr>
	<tr>
		<th>대관일/시</th>
		<td>
			<input type="text" id="RENT_DATE" name="RENT_DATE" maxlength="10" class="inputbox_01a" required="">
			/ 
			<select name="STIME" class="inputbox_01a" required="">
				<option value="">시작 시간</option>
				<c:forEach var="ii" begin="06" end="23" step="1">
				<option value="${ii}">${ii}:00</option>
				</c:forEach> 
			</select>
			 ~  
			<select name="ETIME" class="inputbox_01a" required="">
				<option value="">종료 시간</option>
				<c:forEach var="ii" begin="06" end="23" step="1">
				<option value="${ii}">${ii}:00</option>
				</c:forEach> 
			</select>
		</td>
	</tr>
	<tr>
		<th>대관 용도</th>
		<td>
			<input type="text" name="OBJECT" value="" maxlength="100" class="inputbox_01a inputbox_01_s3" required="" placeholder="제목">
		</td>
	</tr>
	<tr>
		<th>문의내용</th>
		<td>
			<textarea name="CONCEPT" maxlength="1000" style="height:200px;width:80%;" class="inputbox_01a" required="" placeholder="1,000자 이내로 작성하세요.">
사용인원 : (예:최대 00명 )
장비대여 : (예:마이크/조명시설/축구공)
문의 내용 :  

</textarea>
		</td>
	</tr>
    </tbody>
</table>
※ 담당자가 (매일)일정확인 후 개별 연락 드립니다.
<br>
<br>
	<div class="bx_btns_01a ali_c">
		<input type="hidden" name="q" value="<%=param%>/${rentCfg.COMCD}">
		<input type="hidden" name="id" value="<c:out value='${myData.ID}'/>">
		<input type="hidden" name="MEM_NO" value="<c:out value='${myData.MEM_NO}'/>">
		<input type="submit" class="size_m2 btn_green1" value="대관 문의">
		<a href="/rentlist" id=" " class="size_m2 btn_green1" class="gray2">취 소</a>
		<!-- <input type="button" class="size_m2 btn_green1" value="대관신청" onClick="send();"> -->
	</div>

</form>


		<!-- } 서브본문영역1내부 -->
		</div>
	</div>
