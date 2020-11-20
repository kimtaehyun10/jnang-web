<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/joinCommon.js"></script>
<div id="sub_join" class='join_s1'>
    <div class="join_step">
        <ul>
            <li>
                <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step01_on.png">
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
                <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step05.png">
                <span>가입 완료</span>
            </li>
        </ul>
        <!--<div class="line">&nbsp;</div>-->
    </div>

    <div class="join_cate">
        <div class="gene">
            <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_genemem.png">
            <span>일반 회원</span>
            <a class="btn" onclick="join.moreThan14();">14세 이상 가입하기</a>
        </div>
        <div class="child">
            <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_childmem.png">
            <span>어린이 회원</span>
            <a class="btn" onclick="join.lessThan14();">14세 미만 가입하기</a>
        </div>
    </div>
	
</div>