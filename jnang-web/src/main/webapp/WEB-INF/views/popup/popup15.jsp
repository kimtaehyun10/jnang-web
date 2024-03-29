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
    	localStorage.setItem('popup15', new Date());
    	window.close();
    });
    $('.popups_close').click(function() {
        window.close();
    });
	$('.popups_con').click(function() {
		window.opener.top.location.href='/board/05010100/8';
        window.close();
    });
});
</script>
<body>
	<div id="popdiv">
	<div id="popups_15" class="popups">
		<div class="popups_con" style="cursor:pointer">
			<img src="${pageContext.request.contextPath}/resource/images/popup/popimg15.png" title="코로나바이러스 휴관안내" alt="코로나바이러스 휴관안내" />
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
