<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/axicon/axicon.min.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/parallax_style.css"/>

<title>세션 만료</title>

<script language="javascript">
<%
	session.invalidate();
%>

function fn_MovePage(url) {
	document.location.href = url; 
}

</script>
</head>

<body>
<div id="Page" class="page tow">
    <div id="sectionFourth">
        <div class="section">
            <div class="left">
                <h2>세션 만료</h2>
                <p>미사용 시간 초과로 인해 세션이 끊어졌습니다.<br>로그인 페이지로 이동하신 후, 다시 이용해 주시기 바랍니다.</p>
            </div>
            
            <div class="link left">
                <div class="top">
                </div>
                <div class="bot">
                    <a class="one" href="javascript:fn_MovePage('/logout');">로그인 이동</a>
                </div>
            </div>
        </div>
    </div>
    
</div>
</body>
</html>

