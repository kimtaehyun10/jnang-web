<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/findCommon.js"></script>
<div id="sub_join" class='join_s5'>
	<div id="join_comp">
		<c:choose>
		<c:when test="${exist eq true}">
		<h3 class="lineheight_1dot5 margin_t50 margin_b20">회원님의 아이디는 <span id="usedId">${member.id}</span> 입니다. <br> 로그인 후 중랑구시설관리공단의 서비스를 이용할 수 있습니다.</h3>
		<p>비밀번호 변경 대상이거나, 비밀번호를 잊으신 경우 아래에서 변경해주세요.</p>
		<div class="btnarea margin_t80">
			<a href="/mem/find/password" id="gotoChangePwd" class="gray">비밀번호 변경</a>
			<a href="/mem/login" id="gotoLogin" class="green">로그인 하러가기</a>
		</div>
		</c:when>
		<c:otherwise>
		<h3 class="lineheight_1dot5 margin_t50 margin_b20">회원가입 내역이 없습니다.</h3>
		<p>회원가입 후 중랑구시설관리공단의 서비스를 이용할 수 있습니다.</p>
		<div class="btnarea margin_t80">
			<a href="/" id="gotoMain" class="gray">메인으로</a>
			<a href="/mem/join1" id="gotoJoin" class="green">회원가입</a>
		</div>
		</c:otherwise>
		</c:choose>
	</div>
</div>