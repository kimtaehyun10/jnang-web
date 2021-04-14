<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery-ui.css?v=1" />

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
function send() {
	
	//try {
		var frm = document.frm1;

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
		<th>대관계약서 양식</th>
		<td>
			<a href="/data/file/제4호서식_대관 계약서.hwp" required="" class="btn_download_01_blue1a">대관 계약서 다운로드</a>
		</td>
	</tr>
		<tr>
			<th>파일 업로드</th>
			<td>
				<span style="color:red">작성하신 대관계약서를 첨부해주세요.</span>
				<br>
				<input type="file" id="file" name="file" required="" multiple >
				
			</td>
		</tr>
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