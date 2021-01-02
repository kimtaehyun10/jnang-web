<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/declaration/dwict.board.declarationCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/declaration/dwict.board.decCommentPage.js"></script>
<style>
	div{
font-size:18px;
}


</style>
<div id="subCon">	
	<input type="hidden" id="h_cmsCd" name="h_cmsCd" value="${cmsCd}" />	 		
</div>
<div id="boardCon">
	<div class='sub_cleanreport'>		
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>신고서 처리결과 확인</div>	
		<br>
		<br>	
		<div class='margin_b10 lineheight_1dot8'>신고 및 질의 시 발생된 고유번호와 비밀번호를 입력 후 확인 버튼을 클릭하세요.</div>	
		<div class='border_top_3_green'></div>
		<table class='stbl_w3b border_top_0' summary='이 표는 제목/내용 등의 정보로 구성된 익명신고 등록/수정 폼입니다.'>
		<caption>익명신고 등록/수정 폼</caption>
		<colgroup>
			<col width='100px'/>
			<col width='*'/>
		</colgroup>
		<tr>
			<th>고유번호</th>
			<td><input type='text' id='dCode' name='dCode' value='' maxlength='' required class='inputbox_01a inputbox_01_s2'></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type='password' id='dPass' name='dPass' value='' maxlength='' required class='inputbox_01a inputbox_01_s2'></td>
		</tr>
		</table>
	
		<div class='bx_btns_01a ali_c'>
			<input type="button" class='size_m2 btn_green1' value='확인하기' onclick="find.proc()">
		</div>			
	</div>
</div>