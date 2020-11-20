<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/joinCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/join2.js"></script>
<div id="sub_join" class='join_s2'>
    <div class="join_step">
        <ul>
            <li>
                <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step01.png">
                <span>회원 선택</span>
            </li>
            <li>
                <img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step02_on.png">
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
	<input type="hidden" id="adult" name="adult" value="${adult}" />
    <div class="join_terms">
        <div class="term">
            <h3 class="margin_b20">이용약관</h3>
            <div class="box margin_b10" style="overflow-y:scroll;">
				<jsp:include page="info/info1.jsp"></jsp:include>            
            </div>
			<div class="agree">
				<input type="checkbox" id="agree1" name="agree1">
				<label for="agree1">&nbsp; 위 내용을 읽었으며, "이용약관"에 동의합니다.</label>
			</div>   
		</div>
		<div class="term">
            <h3 class="margin_b20">개인정보 수집 및 이용동의</h3>
            <div class="box margin_b10" style="overflow-y:scroll;">
				<jsp:include page="info/info2.jsp"></jsp:include>            
            </div>
			<div class="agree">
				<input type="checkbox" id="agree2" name="agree2">
				<label for="agree2">&nbsp; 위 내용을 읽었으며, "개인정보 수집"에 동의합니다.</label>
			</div>   
		</div>
		<%-- <div class="ipt_info margin_b50">
            <h3 class="margin_b20">본인확인 정보 입력 (<span class="nece">*</span> 필수입력)</h3>
            <div class="infobox">
				<form id="identi">
					<table>
						<colgroup>
							<col width="30%">
							<col width="70%">
						</colgroup>
						<tbody>
							<tr>
								<th>이름<span class="nece">*</span></th>
								<td><input type="text" id="memNm" name="memNm" placeholder="이름을 입력하세요."></td>
							</tr>
							<tr>
								<th>생년월일<span class="nece">*</span></th>
								<td>
									<select id="yyyy" class="year selbox_01"></select>
									<select id="mm" class="month selbox_01"></select>
									<select id="dd" class="day selbox_01"></select>
								</td>
							</tr>
							<tr>
								<th>성별<span class="nece">*</span></th>
								<td>
									<input type="button" id="male" name="gender" value="남성" onfocus="blur()" class="on">
									<input type="button" id="female" name="gender" value="여성" onfocus="blur()">
								</td>
							</tr>
							<tr>
								<th>휴대전화번호<span class="nece">*</span></th>
								<td><input type="text" id="hp" name="hp" placeholder="휴대전화번호를 입력하세요."></td>
							</tr>
							<c:if test="${adult eq 'N'}">
							<tr>
								<th>부모님 휴대전화번호<span class="nece">*</span></th>
								<td><input type="text" id="parentPhone" name="parentPhone" placeholder="부모님의 휴대전화번호를 입력하세요."></td>
							</tr>
							</c:if>
						</tbody>
					</table>
				</form>
            </div>
		</div> --%>
		<div class="btnarea">
			<a onclick="join.authentication();" class="green">본인인증</a>
			<a onclick="join.cancel();" class="gray">취소</a>
		</div>
    </div>
</div>