<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/contract/dwict.board.contract.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/contract/dwict.board.price.js"></script>

<div id="subCon">	
	<input type="hidden" id="h_cmsCd" name="h_cmsCd" value="${cmsCd}" />	 		
</div>
<div id="boardCon">
	<input type="hidden" id="h_pageIndex" name="h_pageIndex" value="1"/>
	<input type="hidden" id="h_pageSize" name="h_pageSize" value="10"/>
	<input type="hidden" id="h_startRow" name="h_startRow" value="0"/>
</div>