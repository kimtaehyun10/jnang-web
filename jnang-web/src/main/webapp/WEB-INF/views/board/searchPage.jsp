<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/dwict.board.const.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/dwict.board.search.js"></script>

<div id="subCon">
	<input type="hidden" id="searchKey" name="searchKey" value="ALL" />
	<input type="hidden" id="searchValue" name="searchValue" value="${keyword}" />
	<input type="hidden" id="h_cmsCd" name="h_cmsCd" value="" />	 
	<input type="hidden" id="h_mType" name="h_mType" value="" />	
</div>
<div id="boardCon">
	<input type="hidden" id="h_pageIndex" name="h_pageIndex" value="1"/>
	<input type="hidden" id="h_pageSize" name="h_pageSize" value="10"/>
	<input type="hidden" id="h_startRow" name="h_startRow" value="0"/>
</div>