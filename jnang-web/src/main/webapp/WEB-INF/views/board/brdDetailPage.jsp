<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/dwict.board.const.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/dwict.board.brdDetailPage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/ckeditor/ckeditor.js"></script>

<div id="subCon">
	<input type="hidden" id="h_cmsCd" name="h_cmsCd" value="${cmsCd}" />
	<input type="hidden" id="h_cmsNm" name="h_cmsNm" value="${cmsNm}" />
	<input type="hidden" id="h_brdNo" name="h_brdNo" value="${brdNo}" />
	<input type="hidden" id="h_userId" name="h_userId" value="${userId}" />
	<input type="hidden" id="h_mType" name="h_mType" value="${mType}" />
	<input type="hidden" id="h_pType" name="h_pType" value="${pType}" />
</div>
<div id="boardCon">
	
</div>