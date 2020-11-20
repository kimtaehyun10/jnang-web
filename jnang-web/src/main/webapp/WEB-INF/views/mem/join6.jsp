<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/joinCommon.js"></script>
<div id="sub_join" class='join_s5'>
	<!--<div class="join_step">
		<ul>
			<li>
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step01.png">
				<span>회원 선택</span>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step02.png">
				<span>약관 동의</span>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step03.png">
				<span>본인 인증</span>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step04.png">
				<span>정보 입력</span>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step05_on.png">
				<span>가입 완료</span>
			</li>
		</ul>
	</div>-->
	<div id="join_comp">
		<%-- <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_comp.png"> --%>
		<h3 class="lineheight_1dot5 margin_t50 margin_b20">이미 회원입니다. <br> 회원님의 아이디는 <span id="usedId">${member.id}</span> 입니다.</h3>
		<p>로그인 후 중랑구시설관리공단의 서비스를 이용할 수 있습니다.</p>
		<div class="btnarea margin_t80">
			<a href="/" id="gotoMain" class="gray">메인으로</a>
			<a href="/mem/login" id="gotologin" class="green">로그인 하러가기</a>
		</div>
	</div>
</div>