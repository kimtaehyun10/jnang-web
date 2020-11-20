<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>중랑구 시설관리공단</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
	<style type='text/css'>
		/* 팝업레이어 */
		#popdiv {z-index:1000;position:relative;}
		.popups {position:absolute;border:1px solid #e9e9e9;background:#fff}
		.popups_con {}
		.popups_footer {padding:10px 0;background:#000;color:#fff;text-align:right}
		.popups_footer button {margin-right:5px;padding:5px 10px;border:0;background:#393939;color:#fff}
	</style>
</head>
<script>
 $(function() {
    $('.popups_reject').click(function(){
    	localStorage.setItem('popup14', new Date());
    	window.close();
    });
    $('.popups_close').click(function() {
        window.close();
    });
});
</script>
<body>
	<div id="popdiv">
	<div id="popups_14" class="popups">
		<div class="popups_con" style="cursor:pointer">
			<img src="${pageContext.request.contextPath}/resource/images/popup/popimg14.png" title="코로나바이러스 안내" alt="코로나바이러스 안내" usemap="#Map"/>
				<map name="Map">
					<!-- <area shape="rect" coords="70,145,585,187" onclick="javascript:window.close()" href="https://www.jungnang.go.kr/portal/main.do" target="_blank"/>  -->
					<area shape="rect" coords="65,168,538,212" onclick="javascript:window.close()" href="http://ncov.mohw.go.kr/shBoardView.do?brdId=3&brdGubun=32&ncvContSeq=520" target="_blank"/> 
					<area shape="rect" coords="65,223,538,266" onclick="javascript:window.close()" href="https://www.jungnang.go.kr/portal/bbs/view/B0000002/28234.do?searchCnd=&searchWrd=&gubun=&delCode=0&useAt=&replyAt=&menuNo=200473&sdate=&edate=&deptId=&deptName=&popupYn=&dept=&dong=&option1=&viewType=&searchCnd2=&pageIndex=1" target="_blank"/> 
					<area shape="rect" coords="65,275,538,318" onclick="javascript:window.close()" href="http://www.jungnangimc.or.kr/noticeView.do?sgrp=S01&siteCmsCd=CM0001&topCmsCd=CM0003&cmsCd=CM0035&pnum=2&cnum=3&ntNo=1172" target="_blank"/> 
					<area shape="rect" coords="67,328,540,372" onclick="javascript:window.close()" href="https://www.jungnang.go.kr/portal/bbs/list/B0000458.do?menuNo=201212" target="_blank"/> 
					<area shape="rect" coords="67,379,540,424" onclick="javascript:window.close()" href="http://www.mohw.go.kr/react/popup_200128.html" target="_blank"/> 
					<area shape="rect" coords="65,431,538,475" onclick="javascript:window.close()" href="http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=&brdGubun=&ncvContSeq=&contSeq=&board_id=&gubun=" target="_blank"/> 
					<area shape="rect" coords="66,482,539,525" onclick="javascript:window.close()" href="https://www.youtube.com/playlist?list=PL_-Emxqwow0B_ShiQiMP-qptrre2RWxBk" target="_blank"/> 
				</map>
				<br style="clear:both;" />
		</div>
		<div class="popups_footer">
			<button class="popups_reject popups_15">오늘은 다시 열람하지 않습니다.</button>
			<button class="popups_close popups_15">닫기</button>
		</div>
	</div>
</div>
</body>
</html>
