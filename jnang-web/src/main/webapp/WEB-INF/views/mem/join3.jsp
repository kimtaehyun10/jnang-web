<%@page import="org.springframework.beans.factory.annotation.Value"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="Kisinfo.Check.IPINClient" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="https://auth.logintalk.io/js/logintalk.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/joinCommon.js"></script>
<c:set var="sSiteCode">
	<spring:eval expression="@appConfig['nice.ipin.siteCode']"/>
</c:set>
<c:set var="sSitePwd">
	<spring:eval expression="@appConfig['nice.ipin.sitePwd']"/>
</c:set>
<c:set var="action">
	<spring:eval expression="@appConfig['nice.ipin.returnUrl']"/>
</c:set>
<%
	final String sSiteCode = (String) pageContext.getAttribute("sSiteCode");
	final String sSitePwd = (String) pageContext.getAttribute("sSitePwd");
	final String sReturnURL = (String) pageContext.getAttribute("action");
	
	final IPINClient pClient = new IPINClient();
	final String sCPRequest = pClient.getRequestNO(sSiteCode);
	
	final int iRtn = pClient.fnRequest(sSiteCode, sSitePwd, sCPRequest, sReturnURL);
	String sEncData = null;
	String sRtnMsg = null;
	if (iRtn == 0) {
		sEncData = pClient.getCipherData();
		sRtnMsg = "정상 처리되었습니다.";
	} else if (iRtn == -1) {
		sRtnMsg = "암호화 시스템 오류 : 귀사 서버 환경에 맞는 모듈을 이용해주십시오.<br>오류가 지속되는 경우 iRtn 값, 서버 환경정보, 사이트코드를 기재해 문의주시기 바랍니다.";
	} else if (iRtn == -2) {
		sRtnMsg = "암호화 처리 오류 : 최신 모듈을 이용해주십시오. 오류가 지속되는 경우 iRtn 값, 서버 환경정보, 사이트코드를 기재해 문의주시기 바랍니다.";
	} else if (iRtn == -9) {
		sRtnMsg = "입력 정보 오류 : 암호화 함수에 입력된 파라미터 값을 확인해주십시오.<br>오류가 지속되는 경우, 함수 실행 직전 각 파라미터 값을 로그로 출력해 발송해주시기 바랍니다.";
	} else {
		sRtnMsg = "기타 오류: iRtn 값과 적용한 샘플소스를 발송해주시기 바랍니다.";
	}
%>
<div id="sub_join" class='join_s3'>
    <div class="join_step">
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
                <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step03_on.png">
                <span>본인 인증</span>
            </li>
            <li>
                <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step04.png">
                <span>정보 입력</span>
            </li>
            <li>
                <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step05.png">
                <span>가입 완료</span>
            </li>
        </ul>
    </div>

    <div class="join_certf">
    	<c:if test="${adult ne true}">
    	<p id="notice14" class="margin_b40">14세 미만은 <span>부모님 명의</span>로 인증하셔야 합니다.</p>
    	</c:if>
        <div class="phone">
            <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_certf_p.png">
            <h5 class="margin_b20">휴대폰 본인인증</h5>
            <span class="margin_b30">본인명의로 등록된 휴대폰을 통해<br/>본인확인을 받을 수 있는 서비스 입니다.</span>
            <a class="btn" onclick="join.phoneAuthentication();">휴대폰 인증하기</a>
        </div>
        <div class="ipin">
            <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_certf_i.png">
            <h5 class="margin_b20">아이핀(i-PIN)인증</h5>
            <span class="margin_b30">개인식별 번호를 발급받아 인터넷상에서<br/>본인확인을 받을 수 있는 서비스 입니다.</span>
            <a class="btn" onclick="join.iPinAuthentication();">아이핀(i-PIN) 인증하기</a>
        </div>
	</div>
	
	<!-- 아이핀 가상주민번호 서비스 팝업 호출 form -->
	<form name="form_ipin" method="post">
		<sec:csrfInput></sec:csrfInput>
		<!-- 요청모드 (필수 데이터) -->
		<input type="hidden" name="m" value="pubmain">	
		<!-- 인증요청 암호화 데이터 -->
		<input type="hidden" name="enc_data" value="<%= sEncData %>">		
	</form>
	
	<!-- 아이핀 가상주민번호 서비스 팝업 인증결과 전달 form -->
	<form name="vnoform" method="post">
		<sec:csrfInput></sec:csrfInput>
		<!-- 인증결과 암호화 데이터 -->
		<input type="hidden" name="enc_data">								
	</form>
</div>