<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="https://auth.logintalk.io/js/logintalk.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/modify.js"></script>
<script type="text/javascript" src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/com/daumPostcode.min.js"></script>
<a class='size_m2 btn_green1' href="/mypage/classStatus">수강신청현황</a>
<a class='size_m2 btn_green1' href="/mypage/lockerStatus">사물함신청현황</a>
<a class='size_m2 btn_green1' href="/mypage/rent">대관신청현황</a>
<a class='size_m2 btn_gray1' href="/mypage/modify">회원정보수정</a>
<div id="sub_join" class=' '>
	<div class="join_input">
		<div class="ipt_info margin_b50">			
			<div class="inputbox margin_b50">			
				<form id="inputInfo1">
					<table>
						<colgroup>
							<col width="30%"> <col width="70%">
						</colgroup>
						<tbody>
							<tr>
								<th>회원카드번호 </th>
								<td>
									<input type="text" id="cardNo" name="cardNo" class="disabled" value="${member.cardNo}" readonly="readonly" onfocus="this.blur();">									
								</td>									
							</tr>
							<tr>
								<th>이름 <span class="nece">*</span></th>
								<td>
									<input type="text" id="memNm" name="memNm" class="disabled" placeholder="이름을 입력하세요." value="${member.memNm}" readonly="readonly" onfocus="this.blur();">
									<span class="cap">이름, 휴대전화, 성별, 생년월일이 바뀌신 분은 센터로 연락 주세요.</span>
								</td>
							</tr>
							<tr>
								<th>아이디 <span class="nece">*</span></th>
								<td>
									<input type="text" id="id" name="id" value="${member.id}" readonly="readonly">
								</td>
							</tr>						
							<tr>
								<th>비밀번호 </th>
								<td class="ipt-btn">
									<a class="btn_form" onclick="modify.changePwdProc();" style="float: left;">비밀번호 변경</a>
								</td>
							</tr>						
							<tr>
								<th>휴대전화번호 <span class="nece">*</span></th>
								<td>
									<input type="text" id="hp" name="hp" class="disabled" value="${member.hp}" readonly="readonly" onfocus="this.blur();" maxlength="13">
								</td>
							</tr>
							<tr>
								<th>이메일 <span class="nece">*</span></th>
								<td><input type="text" id="email" name="email" value="${member.email}"></td>
							</tr>
							<tr>
								<th>보호자 휴대전화번호</th>
								<td>
									<input type="text" id="etcNo" name="etcNo" class="txt_s3 alnum" value="${member.etcNo}">
								</td>
							</tr>
							<%-- <tr>
								<th>예금주명 <span class="nece">*</span></th>
								<td>
									<input type="text" id="dpstrNm" name="dpstrNm" class="txt_s3 alnum" value="${member.dpstrNm}">
								</td>
							</tr>
							<tr>
								<th>환불계좌 은행명 <span class="nece">*</span></th>
								<td>
									<input type="text" id="bankNm" name="bankNm" class="txt_s3 alnum" value="${member.bankNm}">
								</td>
							</tr>
							<tr>
								<th>환불계좌번호 <span class="nece">*</span></th>
								<td>
									<input type="text" id="acountNum" name="acountNum" class="txt_s3 alnum" value="${member.acountNum}">
								</td>
							</tr> --%>
							<tr>
								<th>성별 <span class="nece">*</span></th>
								<td>
									<c:choose>
									<c:when test="${member.gender eq 'M'}">
									<input type="button" id="male" name="male" value="남성" onfocus="blur()" class="on" disabled="disabled">
									<input type="button" id="female" name="female" value="여성" onfocus="blur()" disabled="disabled">
									</c:when>
									<c:otherwise>
									<input type="button" id="male" name="male" value="남성" onfocus="blur()" disabled="disabled">
									<input type="button" id="female" name="female" value="여성" onfocus="blur()" class="on" disabled="disabled">
									</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>								
								<th>생년월일 <span class="nece">*</span></th>
								<td>
									<div>
										<c:choose>
											<c:when test="${member.birthSec eq 'S'}">
												<input type="radio" id="solar" name="birthSec" value="S" checked> <label for="solar"> 양력</label>  
												<input type="radio" id="lunar" name="birthSec" value="M" > <label for="lunar"> 음력</label>
											</c:when>
											<c:otherwise>
												<input type="radio" id="solar" name="birthSec" value="S" > <label for="solar"> 양력</label>  
												<input type="radio" id="lunar" name="birthSec" value="M" checked > <label for="lunar"> 음력</label> 
											</c:otherwise>
										</c:choose>										
									</div>
									<div class="margin-t10">
									<c:set var="now" value="<%=new java.util.Date()%>" />
									<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
										<select id="yyyy" name="yyyy" class="year selbox_01" disabled="disabled">
											<option value="${member.secBirthDate.substring(0,4)}">${member.secBirthDate.substring(0,4)}</option>
											<c:forEach var="i" begin="0" end="120" step="1" >
											<option value="${sysYear - i}">${sysYear - i}</option>
											</c:forEach>
										</select>
										<select id="mm" name="mm" class="month selbox_01" disabled="disabled">
											<option value="${member.secBirthDate.substring(4,6)}">${member.secBirthDate.substring(4,6)}</option>
											<c:forEach var="i" begin="1" end="12" step="1" >
											<option value="<fmt:formatNumber pattern="00" value="${i}" />"><fmt:formatNumber pattern="00" value="${i}" /></option>
											</c:forEach>
										</select>
										<select id="dd" name="dd" class="day selbox_01" disabled="disabled">
											<option value="${member.secBirthDate.substring(6,8)}">${member.secBirthDate.substring(6,8)}</option>
											<c:forEach var="i" begin="1" end="31" step="1" >
											<option value="<fmt:formatNumber pattern="00" value="${i}" />"><fmt:formatNumber pattern="00" value="${i}" /></option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th>주소 <span class="nece">*</span></th>
								<td>
									<div class="ipt-btn">
										<input type="text" id="homeZip" name="homeZip" class="disabled" readonly="readonly" onfocus="this.blur();" value="${member.homeZip}">
										<a class="btn_form" onclick="daumPostCode();">주소검색</a>
									</div>
									<div>
										<input type="text" id="homeAddr" name="homeAddr" value="${member.homeAddr}">
									</div>
									<span class="cap">주소검색 후 세부주소를 입력해 주세요.</span>
								</td>
							</tr>
							<c:if test="${not empty member.etcNo}">
								<tr>
									<th>부모님 휴대전화번호 <span class="nece">*</span></th>
									<td><input type="text" id="etcNo" name="etcNo" value="${member.hp}" class="disabled" readonly="readonly" onfocus="this.blur();" maxlength="13"></td>
								</tr>
							</c:if>													
						</tbody>
					</table>
				</form>
			</div>
			<!-- 부가정보 -->
			<h3 class="margin_b20">부가정보</h3>
			<div id="" class="inputbox">
				<form id="inputInfo2">
					<table>
						<colgroup>
							<col width="30%"> <col width="70%">
						</colgroup>
						<tbody>							
                            <tr>
								<th>자택전화번호</th>
								<td><input type="text" id="homeTel" name="homeTel" value="${member.homeTel}"></td>
							</tr>
							<tr>
								<th>메일 수신여부</th>
								<td>
									<c:if test="${member.emailYn eq 'N'}">
									<input type="radio" id="emailY" name="emailYn" value="Y"> <label for="emailY"> 동의</label>  
									<input type="radio" id="emailN" name="emailYn" value="N" checked> <label for="emailN"> 거부</label> 
									</c:if>
									<c:if test="${member.emailYn eq 'Y'}">
									<input type="radio" id="emailY" name="emailYn" value="Y" checked> <label for="emailY"> 동의</label>  
									<input type="radio" id="emailN" name="emailYn" value="N"> <label for="emailN"> 거부</label> 
									</c:if>
								</td>
							</tr>
							<tr>
								<th>SMS 수신여부</th>
								<td>
									<div>
										<c:if test="${member.smsYn eq 'N'}">
										<input type="radio" id="smsY" name="smsYn" value="Y"> <label for="smsY"> 동의</label>
										<input type="radio" id="smsN" name="smsYn" value="N" checked> <label for="smsN"> 거부</label>
										</c:if>
										<c:if test="${member.smsYn eq 'Y'}">
										<input type="radio" id="smsY" name="smsYn" value="Y" checked> <label for="smsY"> 동의</label>
										<input type="radio" id="smsN" name="smsYn" value="N"> <label for="smsN"> 거부</label>
										</c:if>
									</div>
									<span class="cap">SMS(단문메시지) 수신에 동의하시면 각종 알림정보(수강/대관/묻고답하기 등)를 무료로 받아보실 수 있습니다.</span>
								</td>
							</tr>
							<tr>
								<th>예금주명</th>
								<td>
									<input type="text" id="dpstrNm" name="dpstrNm" class="txt_s3 alnum" value="${member.dpstrNm}">
								</td>
							</tr>
							<tr>
								<th>환불계좌 은행명</th>
								<td>
									<input type="text" id="bankNm" name="bankNm" class="txt_s3 alnum" value="${member.bankNm}">
								</td>
							</tr>
							<tr>
								<th>환불계좌번호</th>
								<td>
									<input type="text" id="acountNum" name="acountNum" class="txt_s3 alnum" value="${member.acountNum}">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div class="btnarea">
			<a class="gray" onclick="modify.delete()">회원 탈퇴</a>
			<a class="green" onclick="modify.proc()">수정 완료</a>			 
		</div>
	</div>
</div>