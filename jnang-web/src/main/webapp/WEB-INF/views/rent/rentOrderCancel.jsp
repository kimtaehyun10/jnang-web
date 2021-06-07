<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery-ui.css?v=1" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/rent/rent.write.js"></script>
<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
String today = transFormat.format(from);
String param 	= request.getParameter("q");

%>
<script type="text/javascript">
/**
* @projectDescription rent.list.js
*
* @author 
* @version 1.21
*/
$(function(){
	setMenuTitle('bg_99', '대관 예약 취소신청', '대관 예약 취소 및 환불 신청');
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
	
});

function send() {
	
	//try {
		var frm = document.frm1;

		//이미 신청 중콕첵크
		
		if (confirm("\n 대관 취소요청  하시겠습니까?\n ")) {
		} else {
			return false;	
		}
		
		var aItemList = new Array();
			
			var formData=new FormData();
// 			var inputFile=$("#file");
// 			var files=inputFile[0].files;	
// 			var fileCheck = document.getElementById("file").value;
			var PTS_ARRAY = new Array();
			
			for(var i=0;i<$('#ptsSize').val();i++ ){
				PTS_ARRAY[i] = $('#PLACE_TIME_SEQ'+i).val();
			}
			
			formData.append("MEM_NO",$("#MEM_NO").val());
			formData.append("PLACE_CD",$("#PLACE_CD").val());
			formData.append("PAY_AMT",$("#PAY_AMT").val());
			formData.append("WRITE_DH",$("#WRITE_DH").val());
			formData.append("MEM_NM",$("#MEM_NM").val());
			formData.append("TEL",$("#TEL").val());
			formData.append("COMCD",$("#COMCD").val());		
			formData.append("CONCEPT",$("#CONCEPT").val());
			formData.append("OBJECT",$("#OBJECT").val());
			formData.append("TID",$("#TID").val());
			formData.append("RESERVE_DATE",$("#RESERVE_DATE").val());
			formData.append("PLACE_TIME_SEQ", PTS_ARRAY);
			
		    
// 			for(var i=0;i<files.length;i++){
// 				formData.append("files",files[i]);
// 			}		
			
			$.ajax({
		        type: "post",
		        enctype: 'multipart/form-data',
		        url:'/rent/cancelSave',
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
			
		return false;
};

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
			${myData.MEM_NM}
		</td>
	</tr>
	<tr>
		<th>연락처</th>
		<td>
			${myData.HP}
		</td>
	</tr>
	<tr>
		<th>예약 취소 요청 목록</th>
		<td>
			<c:forEach items="${rentList}" var="result" varStatus="status">
				${result.COMNM }&nbsp;${result.PLACE_NM }&nbsp;&nbsp;${result.RESERVE_DATE}&nbsp;&nbsp;&nbsp;${result.RDATE }<br>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<th>결제 한 금액</th>
		<td>
			<span style="color:red">관련 규정에 따라 환불금액이 없거나 적을 수 있습니다.</span>
			<br>
			${rentList[0].PAY_AMT }원
		</td>
	</tr>
	<tr>
		<th>취소명</th>
		<td>
			<input type="text" id="OBJECT" name="OBJECT" value="" maxlength="100" class="inputbox_01a inputbox_01_s3" required="" placeholder="취소신청합니다. ">
		</td>
	</tr>
	<tr>
		<th>취소사유</th>
		<td>
			<textarea name="CONCEPT" id="CONCEPT" maxlength="1000" style="height:200px;width:80%;" class="inputbox_01a" required="" placeholder="1,000자 이내로 작성하세요.">
						대관취소요청
문의내용
은행명 : 
예금주 : 
계좌번호 : 
취소사유 : (예 단순변심)
※ 환불금은 계좌로 환불됩니다.
</textarea>
		</td>
	</tr>
    </tbody>
</table>
※ 담당자가 확인 후 개별 연락 드립니다.
<br>
<br>
	<div class="bx_btns_01a ali_c">
		<%-- <input type="hidden" id="q" name="q" value="<%=param%>"> --%>
		<%-- <input type="hidden" id="COMCD" name="COMCD" value="${rentCfg.COMCD}"> --%>
		<input type="hidden" name="id" value="<c:out value='${myData.ID}'/>">
		<input type="hidden" id="MEM_NO" name="MEM_NO" value="<c:out value='${myData.MEM_NO}'/>">
		<input type="hidden" id="MEM_NM" name="MEM_NM" value="<c:out value='${myData.MEM_NM}'/>">
		<input type="hidden" id="TEL" name="TEL" value="<c:out value='${myData.HP}'/>">
		<%-- <input type="hidden" id="PLACE_CD" name="PLACE_CD" value="<%=PLACE_CD%>"> --%>
		<input type="hidden" id="TID" name="TID" value="<c:out value='${rentList[0].TID}'/>">
		<input type="hidden" id="COMCD" name="COMCD" value="<c:out value='${rentList[0].COMCD}'/>">
		<input type="hidden" id="PAY_AMT" name="PAY_AMT" value="<c:out value='${rentList[0].PAY_AMT}'/>">
		<input type="hidden" id="PLACE_CD" name="PLACE_CD" value="<c:out value='${rentList[0].PLACE_CD}'/>">
		<input type="hidden" id="WRITE_DH" name="WRITE_DH" value="<c:out value='${rentList[0].WRITE_DH}'/>">
		<input type="hidden" id="RESERVE_DATE" name="RESERVE_DATE" value="<c:out value='${rentList[0].RESERVE_DATE}'/>">
		<input type="hidden" id="ptsSize" value="${fn:length(rentList) }">
		<c:forEach items="${rentList}" var="result" varStatus="status">
			<input type="hidden" id="PLACE_TIME_SEQ${status.index}" name="PLACE_TIME_SEQ" value="<c:out value='${result.PLACE_TIME_SEQ}'/>">
		</c:forEach>
		<input type="hidden" id="arryData" name="arryData">
		<!-- <input type="button" class="size_m2 btn_green1" value="대관 문의 테스트" onClick="send();"> -->
		<input type="submit" class="size_m2 btn_green1" value="취소 요청">
		<a href="/mypage/rent" id=" " class="size_m2 btn_green1" class="gray2">뒤로가기</a>
		<!-- <input type="button" class="size_m2 btn_green1" value="대관신청" onClick="send();"> -->
	</div>

</form>


		<!-- } 서브본문영역1내부 -->
		</div>
	</div>