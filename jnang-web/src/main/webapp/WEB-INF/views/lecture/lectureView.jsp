<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/lecture/lecture.view1.js"></script>

<div class='sub_lecture_v01'>
	<table id='programTable' class='stbl_w3b' summary='이 표는 강좌명/대상/요일/시간/정원/수강료 등의 정보로 구성되어 있습니다.'></table>
	<table style="margin-top:50px;" id='programDetailTable' class='stbl_w3b' summary='이 표는 강좌소개/세부내용/기타 등의 정보로 구성되어 있습니다.'></table>

	<div style="margin-top:50px;" class='bx_btns_01a' id="insertLecture">
		<a class='size_m2 btn_gray2' href='/lecture/list'>목록</a>
		
		
	<sec:authorize access="isAnonymous()" >
	<!-- 	<input id="user" type='submit' class='size_m2 btn_gray2' value='신청' onclick="needAuthorize.user();">
	   <input type='submit' class='size_m2 btn_gray2' value='신청' onclick="stop();"> -->
		</sec:authorize>
		
		<sec:authorize access="isAuthenticated()" >
		<!-- 	<sec:authorize access="!hasAnyRole('ROLE_CARD')" >
				<input type='submit' class='size_m2 btn_gray2' value='신청' onclick="needAuthorize.card();">
			</sec:authorize> -->
			<sec:authorize access="hasAnyRole('ROLE_CARD')" >
	 			<input type='submit' class='size_m2 btn_green1' value='신청' onclick="addBasket();">
			</sec:authorize>
		</sec:authorize>
	</div>
</div>


