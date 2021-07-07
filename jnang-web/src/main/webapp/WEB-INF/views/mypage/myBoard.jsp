<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/myBoard.js"></script>
<div id="subCon">		
	<input type="hidden" id="h_userId" name="h_userId" value="${member.id}" />
</div>
<a class='size_m2 btn_green1' href="/mypage/classStatus">수강신청현황</a>
<a class='size_m2 btn_green1' href="/mypage/lockerStatus">사물함신청현황</a>
<a class='size_m2 btn_green1' href="/mypage/rent">대관신청현황</a>
<a class='size_m2 btn_green1' href="/mypage/modify">회원정보수정</a>
<a class='size_m2 btn_gray1' href="/mypage/myBoard">마이게시판</a>
<div id="boardCon">
	<input type="hidden" id="h_pageIndex" name="h_pageIndex" value="1"/>
	<input type="hidden" id="h_pageSize" name="h_pageSize" value="10"/>
	<input type="hidden" id="h_startRow" name="h_startRow" value="0"/>
</div>