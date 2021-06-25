<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/myBoard.js"></script>
<div id="subCon">		
	<input type="hidden" id="h_userId" name="h_userId" value="${member.id}" />
</div>
<div id="boardCon">
	<input type="hidden" id="h_pageIndex" name="h_pageIndex" value="1"/>
	<input type="hidden" id="h_pageSize" name="h_pageSize" value="10"/>
	<input type="hidden" id="h_startRow" name="h_startRow" value="0"/>
</div>