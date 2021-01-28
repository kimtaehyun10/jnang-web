<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/datepicker.min.css">
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/i18n/datepicker.ko.js"></script>
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
	
	
	let cont = '';
	<c:forEach items="${centerGroup}" var="result" varStatus="status">
		cont += '<a href="/rent/write/?q=${result.PLACE_CD}"><li>${result.PLACE_NM}</li></a>';
	</c:forEach>
	$(".mbx1:eq(1)>ul").append(cont);	
	
	/* var clareCalendar = {maxDate: '+60d',minDate: '+7d' }
	$("#RENT_DATE").datepicker(clareCalendar); */

	//두개짜리 제어 연결된거 만들어주는 함수
	datePickerSet($("#datepicker1"), $("#datepicker2"), true); //다중은 시작하는 달력 먼저, 끝달력 2번째

	/*
	  * 달력 생성기
	  * @param sDate 파라미터만 넣으면 1개짜리 달력 생성
	  * @example   datePickerSet($("#datepicker"));
	  * 
	  * 
	  * @param sDate, 
	  * @param eDate 2개 넣으면 연결달력 생성되어 서로의 날짜를 넘어가지 않음
	  * @example   datePickerSet($("#datepicker1"), $("#datepicker2"));
	  */
	function datePickerSet(sDate, eDate, flag) {
	  //시작 ~ 종료 2개 짜리 달력 datepicker	
	  if (!isValidStr(sDate) && !isValidStr(eDate) && sDate.length > 0 && eDate.length > 0) {
	      var sDay = sDate.val();
	      var eDay = eDate.val();

	      if (flag && !isValidStr(sDay) && !isValidStr(eDay)) { //처음 입력 날짜 설정, update...			
	          var sdp = sDate.datepicker().data("datepicker");
	          sdp.selectDate(new Date(sDay.replace(/-/g, "/")));  //익스에서는 그냥 new Date하면 -을 인식못함 replace필요

	          var edp = eDate.datepicker().data("datepicker");
	          edp.selectDate(new Date(eDay.replace(/-/g, "/")));  //익스에서는 그냥 new Date하면 -을 인식못함 replace필요
	      }

	      //시작일자 세팅하기 날짜가 없는경우엔 제한을 걸지 않음
	      if (!isValidStr(eDay)) {
	          sDate.datepicker({
	              maxDate: new Date(eDay.replace(/-/g, "/"))
	          });
	      }
	      sDate.datepicker({
	          language: 'ko',
	          autoClose: true,
	          timepicker: true,
			  timeFormat: "hh:ii AA",
	          onSelect: function () {
	              datePickerSet(sDate, eDate);
	          }
	      });

	      //종료일자 세팅하기 날짜가 없는경우엔 제한을 걸지 않음
	      if (!isValidStr(sDay)) {
	          eDate.datepicker({
	              minDate: new Date(sDay.replace(/-/g, "/"))
	          });
	      }
	      eDate.datepicker({
	          language: 'ko',
	          autoClose: true,
			  timepicker: true,
			  timeFormat: "hh:ii AA",
	          onSelect: function () {
	              datePickerSet(sDate, eDate);
	          }
	      });

	      //한개짜리 달력 datepicker
	  } else if (!isValidStr(sDate)) {
	      var sDay = sDate.val();
	      if (flag && !isValidStr(sDay)) { //처음 입력 날짜 설정, update...			
	          var sdp = sDate.datepicker().data("datepicker");
	          sdp.selectDate(new Date(sDay.replace(/-/g, "/"))); //익스에서는 그냥 new Date하면 -을 인식못함 replace필요
	      }

	      sDate.datepicker({
	          language: 'ko',
	          autoClose: true
	      });
	  }


	  function isValidStr(str) {
	      if (str == null || str == undefined || str == "")
	          return true;
	      else
	          return false;
	  }
	}
	
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
			<input id="datepicker1" name="datepicker1" type="text" class="inputbox_01a"> -
        	<input id="datepicker2" name="datepicker2" type="text" class="inputbox_01a">	
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