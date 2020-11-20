<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
String today = transFormat.format(from);

%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery-ui.css?v=1" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/rent/rent.team.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/com/daumPostcode.min.js"></script>

	
<div class="sub_cont1">
<div class="con_bx">
	

<div class="sub_cleanreport">

	<!-- 
	<div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">센터별 대관안내 사항</div>
	<div class="padding_left_1dot0 fontsize_1dot15 margin_top_1dot0 lineheight_1dot8">
		<div class="catebox intro02">
			<ul>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0557&amp;pnum=7&amp;cnum=13" class="on">중랑구민체육센터</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0558&amp;pnum=7&amp;cnum=13">중랑문화체육관</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0559&amp;pnum=7&amp;cnum=13">중랑구립잔디운동장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0560&amp;pnum=7&amp;cnum=13">용마폭포공원축구장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0583&amp;pnum=7&amp;cnum=13">신내차량기지축구장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0619&amp;pnum=7&amp;cnum=13">중랑구립테니스장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0561&amp;pnum=7&amp;cnum=13">중랑구립정보도서관</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0562&amp;pnum=7&amp;cnum=13">중랑구민회관</a></li>
			</ul>
		</div>
	</div>
	<br>
	<br>
	-->
	
<form name="frm1" id="frm1" method="post" onsubmit="return send();"> 

	<div class="border_top_3_green"></div>
	
<table class="stbl_w3b border_top_0" summary="이 표는 제목/내용 등의 정보로 구성된 팀등록/수정 폼입니다.">
	<caption>
	팀 등록신청
	</caption>
	<colgroup>
		<col width="200px">
		<col width="*">
	</colgroup>
	<tbody>
	<tr>
    	<td colspan="4"><div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">팀 정보</div></td>
    </tr>
	<tr>
		<th>종목선택</th>
		<td>
			<select id="sp_type" name="sp_type" class="inputbox_01a" required>
			  <option value=''/> == 종목 == </option>
			  <!-- <option value='1'/> 체육관 </option> -->
			  <option value='2'/> 축구장/야구장 </option>
			  <!-- <option value='3'/> 야구 </option> -->
			  <option value='4'/> 테니스장 </option>
		    </select>
		 </td>
		<th>신청구분</th>
		<td><label><input type="radio" id="tm_type" name="tm_type" value="1" required> 관내팀 </label> &nbsp; &nbsp; <label><input type="radio" id="tm_type" name="tm_type" value="2">관외팀</label></td>
	</tr>
	<tr>
		<th>단체명</th>
		<td><input type="text" id="tm_nm" name="tm_nm" value="단체명" maxlength="" required class="inputbox_01a inputbox_01_s3"></td>
		<th>회원수</th>
		<td><div id="temMemCnt"> 0명 </div></td>
	</tr>
	<tr>
    	<td colspan="4">
    		<div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">대표자 정보</div> 
    		<a href='/mypage/modify' class="size_m2 btn_green1">회원정보 변경 바로가기</a>
    		</td>
    </tr>	
	  <th>대표자명</th>
	  <td><c:out value='${myData.MEM_NM}'/></td>
	  <th>아이디</th>
	  <td><c:out value='${myData.ID}'/></td>
	  </tr>
	<tr>
	  <th>연락처</th>
	  <td><c:out value='${myData.HOME_TEL}'/></td>
	  <th>휴대폰번호</th>
	  <td><c:out value='${myData.HP}'/></td>
	  </tr>
	<tr>
    	<td colspan="4">
    		<div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">회원 명단</div>
    		<a href='#none' onclick="mem_add();" class="size_m2 btn_green1">+ 추가</a>
    	</td>
    </tr>	
	<tr>
	  <td colspan="4" align="left">
	  	<input type="hidden" id="zip">
	  	<div id="mem_list"></div>
	  </td>
	</tr>
    </tbody>
</table>
<br>
<br>
	<div class="bx_btns_01a ali_c">
		<input type="hiddenx" id="mem_id" name="mem_id" value="<c:out value='${myData.ID}'/>">
		<input type="hiddenx" id="arryData" name="arryData">
		<input type="hiddenx" id="teamCnt" name="${rentCfg.teamCnt}">
		
		<input type="submit" class="size_m2 btn_green1" value="팀 신청">
		<!-- <input type="button" class="size_m2 btn_green1" value="대관신청" onClick="send();"> -->
	</div>

</form>


		<!-- } 서브본문영역1내부 -->
		</div>
	</div>
