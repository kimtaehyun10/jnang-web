<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang='ko'>
	<head>
		<!-- Global site tag (gtag.js) - Google Analytics -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=G-VJ45JRH2MK"></script>
		<script>
			window.dataLayer = window.dataLayer || [];
			function gtag(){dataLayer.push(arguments);}
			gtag('js', new Date());
			gtag('config', 'G-VJ45JRH2MK');
		</script><!-- Global site tag (gtag.js) - Google Analytics End -->
		<META HTTP-EQUIV='Pragma' CONTENT='no-cache'>
		<META HTTP-EQUIV='Expires' CONTENT='-1'>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width,initial-scale=1.0,minimum-scale=0,maximum-scale=10,user-scalable=yes'>
		<meta name='HandheldFriendly' content='true'>
		<meta name='format-detection' content='telephone=no'>
		<meta http-equiv='imagetoolbar' content='no'>
		<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'>
		<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
    	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}" />
		<title>${cmsNm}</title>
		<link rel='shortcut icon' href='${pageContext.request.contextPath}/resource/images/common/favicon.ico'>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/reset.user.min.css?v=1" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/design.min.css?v=1" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.min.css?v=1" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.ajax.extends.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js?v=1"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/polyfill.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/modernizr.custom.70111.min.js"></script>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery.bxslider.min.css?v=1" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.bxslider.min.js?v=1"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.carouFredSel.6.0.4.min.js?v=1"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/dwict.common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/ui.min.js"></script>
	</head>
	<body>
		<tiles:insertAttribute name="header" />
		
<div class='fr_body'>
	<!-- 서브네비메뉴2 { -->
	<div id="sub_navi2" class='sub_navi2'></div>
	<!-- } 서브네비메뉴2 -->
	
	<!-- 서브본문영역1외부 { -->
	<div class='sub_cont1'>
		<div class='con_bx'>
		<!-- 서브본문영역1내부 { -->
		<tiles:insertAttribute name="body" />
		<!-- } 서브본문영역1내부 -->
		</div>
	</div>
	<!-- } 서브본문영역1외부 -->
</div>

		<tiles:insertAttribute name="footer" />
	</body>
</html>