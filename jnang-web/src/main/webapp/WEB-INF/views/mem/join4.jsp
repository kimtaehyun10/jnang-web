<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/joinCommon.js"></script>
<script type="text/javascript" src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/com/daumPostcode.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/join4.js"></script>
<div id="sub_join" class='join_s4'>
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
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step03.png">
				<span>본인 인증</span>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step04_on.png">
				<span>정보 입력</span>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/resource/images/sub/icon_join_step05.png">
				<span>가입 완료</span>
			</li>
		</ul>
		<!--<div class="line">&nbsp;</div>-->
	</div>
	<div class="join_input">
		<div class="ipt_info margin_b50">
			<h3 class="margin_b20">회원정보 입력</h3>
			<div class="inputbox margin_b50">
				<table id="masterTable">
					<colgroup>
						<col width="30%">
						<col width="70%">
					</colgroup>
					<tbody>
					<input type="hidden" id="h_adult" name="h_adult" value="${adult}">
					<input type="hidden" id="h_exist" name="h_exist" value="${exist}">
					<input type="hidden" id="h_secBirthDate" name="h_secBirthDate" value="${member.secBirthDate}">
					<c:if test="${adult eq true}">
					<c:choose>
					<c:when test="${exist eq true}">
						<tr>
							<th>회원카드번호 </th>
							<td><input type="text" id="cardNo" name="cardNo" class="disabled" value="${member.cardNo}" readonly="readonly" onfocus="this.blur();"></td>
						</tr>
						<tr>
							<th>이름 <span class="nece">*</span></th>
							<td><input type="text" id="memNm" name="memNm" class="disabled" placeholder="이름을 입력하세요." value="${member.memNm}" readonly="readonly" onfocus="this.blur();"></td>
						</tr>
						<tr>
							<th>아이디 <span class="nece">*</span></th>
							<td>
								<input type="text" id="id" name="id" placeholder="아이디를 입력하세요.">
							</td>
						</tr>
						<!-- <tr>
							<th>닉네임 <span class="nece">*</span></th>
							<td class="ipt-btn">
								<input type="text" id=" " name=" " placeholder="닉네임을 입력하세요.">
								<a href="#" class="btn_form">중복확인</a>
							</td>
						</tr> -->
						<tr>
							<th>비밀번호 <span class="nece">*</span></th>
							<td>
								<input type="password" id="pw" name="pw" placeholder="비밀번호를 입력하세요.">
								<span class="cap">특수문자를 포함한 영문자, 숫자 9 ~ 16자리</span>
							</td>
						</tr>
						<tr>
							<th>비밀번호 확인 <span class="nece">*</span></th>
							<td><input type="password" id="pwCheck" name="pwCheck" placeholder="비밀번호를 한번 더 입력하세요."></td>
						</tr>
						<tr>
							<th>휴대전화번호 <span class="nece">*</span></th>
							<td>
								<c:choose>
								<c:when test="${member.hp ne null and member.hp ne ''}">
								<input type="text" id="hp" name="hp" class="disabled" value="${member.hp}" readonly="readonly" onfocus="this.blur();" maxlength="13">
								</c:when>
								<c:otherwise>
								<input type="text" id="hp" name="hp" placeholder="ex. 010-1234-5678" maxlength="13">
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>이메일 <span class="nece">*</span></th>
							<td><input type="text" id="email" name="email" value="${member.email}"></td>
						</tr>
						<tr>
							<th>성별 <span class="nece">*</span></th>
							<td>
								<c:choose>
								<c:when test="${member.gender eq 'M'}">
								<input type="button" id="male" name="male" value="남성" onfocus="blur()" class="on">
								<input type="button" id="female" name="female" value="여성" onfocus="blur()">
								</c:when>
								<c:otherwise>
								<input type="button" id="male" name="male" value="남성" onfocus="blur()">
								<input type="button" id="female" name="female" value="여성" onfocus="blur()" class="on">
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>생년월일 <span class="nece">*</span></th>
							<td>
								<div>
									<input type="radio" id="solar" name="birthSec" value="S" checked> <label for="solar"> 양력</label>  
									<input type="radio" id="lunar" name="birthSec" value="M"> <label for="lunar"> 음력</label> 
								</div>
								<div class="margin-t10">
								<c:set var="now" value="<%=new java.util.Date()%>" />
								<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
									<select id="yyyy" name="yyyy" class="year selbox_01">
										<option value="">선택</option>
										<c:forEach var="i" begin="0" end="120" step="1" >
										<option value="${sysYear - i}">${sysYear - i}</option>
										</c:forEach>
									</select>
									<select id="mm" name="mm" class="month selbox_01">
										<option value="">선택</option>
										<c:forEach var="i" begin="1" end="12" step="1" >
										<option value="<fmt:formatNumber pattern="00" value="${i}" />"><fmt:formatNumber pattern="00" value="${i}" /></option>
										</c:forEach>
									</select>
									<select id="dd" name="dd" class="day selbox_01">
										<option value="">선택</option>
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
					</c:when>
					<c:otherwise>
						<tr>
							<th>회원카드번호 </th>
							<td><input type="text" id="cardNo" name="cardNo" class="disabled" readonly="readonly" onfocus="this.blur();"></td>
						</tr>
						<tr>
							<th>이름 <span class="nece">*</span></th>
							<td><input type="text" id="memNm" name="memNm" class="disabled" placeholder="이름을 입력하세요." value="${member.memNm}" readonly="readonly" onfocus="this.blur();"></td>
						</tr>
						<tr>
							<th>아이디 <span class="nece">*</span></th>
							<td>
								<input type="text" id="id" name="id" placeholder="아이디를 입력하세요.">
							</td>
						</tr>
						<!-- <tr>
							<th>닉네임 <span class="nece">*</span></th>
							<td class="ipt-btn">
								<input type="text" id="nickname" name="nickname" placeholder="닉네임을 입력하세요.">
								<a href="#" class="btn_form">중복확인</a>
							</td>
						</tr> -->
						<tr>
							<th>비밀번호 <span class="nece">*</span></th>
							<td>
								<input type="password" id="pw" name="pw" placeholder="비밀번호를 입력하세요.">
								<span class="cap">특수문자를 포함한 영문자, 숫자 9 ~ 16자리</span>
							</td>
						</tr>
						<tr>
							<th>비밀번호 확인 <span class="nece">*</span></th>
							<td><input type="password" id="pwCheck" name="pwCheck" placeholder="비밀번호를 한번 더 입력하세요."></td>
						</tr>
						<tr>
							<th>휴대전화번호 <span class="nece">*</span></th>
							<td>
								<c:choose>
								<c:when test="${member.hp ne null and member.hp ne ''}">
								<input type="text" id="hp" name="hp" class="disabled" value="${member.hp}" readonly="readonly" onfocus="this.blur();" maxlength="13">
								</c:when>
								<c:otherwise>
								<input type="text" id="hp" name="hp" placeholder="ex. 010-1234-5678" maxlength="13">
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>이메일 <span class="nece">*</span></th>
							<td><input type="text" id="email" name="email" placeholder="ex. abcd@naver.com"></td>
						</tr>
						<tr>
							<th>성별 <span class="nece">*</span></th>
							<td>
								<c:choose>
								<c:when test="${member.gender eq 'M'}">
								<input type="button" id="male" name="male" value="남성" onfocus="blur()" class="on">
								<input type="button" id="female" name="female" value="여성" onfocus="blur()">
								</c:when>
								<c:otherwise>
								<input type="button" id="male" name="male" value="남성" onfocus="blur()">
								<input type="button" id="female" name="female" value="여성" onfocus="blur()" class="on">
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>생년월일 <span class="nece">*</span></th>
							<td>
								<div>
									<input type="radio" id="solar" name="birthSec" value="S" checked> <label for="solar"> 양력</label>  
									<input type="radio" id="lunar" name="birthSec" value="M"> <label for="lunar"> 음력</label> 
								</div>
								<div class="margin-t10">
								<c:set var="now" value="<%=new java.util.Date()%>" />
								<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
									<select id="yyyy" name="yyyy" class="year selbox_01">
										<option value="">선택</option>
										<c:forEach var="i" begin="0" end="120" step="1" >
										<option value="${sysYear - i}">${sysYear - i}</option>
										</c:forEach>
									</select>
									<select id="mm" name="mm" class="month selbox_01">
										<option value="">선택</option>
										<c:forEach var="i" begin="1" end="12" step="1" >
										<option value="<fmt:formatNumber pattern="00" value="${i}" />"><fmt:formatNumber pattern="00" value="${i}" /></option>
										</c:forEach>
									</select>
									<select id="dd" name="dd" class="day selbox_01">
										<option value="">선택</option>
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
									<input type="text" id="homeZip" name="homeZip" class="disabled" readonly="readonly" onfocus="this.blur();">
									<a class="btn_form" onclick="daumPostCode();">주소검색</a>
								</div>
								<div>
									<input type="text" id="homeAddr" name="homeAddr">
								</div>
								<span class="cap">주소검색 후 세부주소를 입력해 주세요.</span>
							</td>
						</tr>
					</c:otherwise>
					</c:choose>
					</c:if>
					<c:if test="${adult ne true}">
						<tr>
							<th>회원카드번호 </th>
							<td><input type="text" id="cardNo" name="cardNo" class="disabled" readonly="readonly" onfocus="this.blur();"></td>
						</tr>
						<tr>
							<th>이름 <span class="nece">*</span></th>
							<td><input type="text" id="memNm" name="memNm" placeholder="자녀 이름을 입력하세요."></td>
						</tr>
						<tr>
							<th>아이디 <span class="nece">*</span></th>
							<td>
								<input type="text" id="id" name="id" placeholder="아이디를 입력하세요.">
							</td>
						</tr>
						<!-- <tr>
							<th>닉네임 <span class="nece">*</span></th>
							<td class="ipt-btn">
								<input type="text" id="nickname" name="nickname" placeholder="닉네임을 입력하세요.">
								<a href="#" class="btn_form">중복확인</a>
							</td>
						</tr> -->
						<tr>
							<th>비밀번호 <span class="nece">*</span></th>
							<td>
								<input type="password" id="pw" name="pw" placeholder="비밀번호를 입력하세요.">
								<span class="cap">특수문자를 포함한 영문자, 숫자 9 ~ 16자리</span>
							</td>
						</tr>
						<tr>
							<th>비밀번호 확인 <span class="nece">*</span></th>
							<td><input type="password" id="pwCheck" name="pwCheck" placeholder="비밀번호를 한번 더 입력하세요."></td>
						</tr>
						<tr>
							<th>휴대전화번호 <span class="nece">*</span></th>
							<td>
								<input type="text" id="hp" name="hp" placeholder="ex. 010-1234-5678" maxlength="13">
							</td>
						</tr>
						<tr>
							<th>이메일 <span class="nece">*</span></th>
							<td><input type="text" id="email" name="email" placeholder="ex. abcd@naver.com"></td>
						</tr>
						<tr>
							<th>성별 <span class="nece">*</span></th>
							<td>
								<input type="button" id="male" name="male" value="남성" onfocus="blur()" class="on">
								<input type="button" id="female" name="female" value="여성" onfocus="blur()">
							</td>
						</tr>
						<tr>
							<th>생년월일 <span class="nece">*</span></th>
							<td>
								<div>
									<input type="radio" id="solar" name="birthSec" value="S" checked> <label for="solar"> 양력</label>  
									<input type="radio" id="lunar" name="birthSec" value="M"> <label for="lunar"> 음력</label> 
								</div>
								<div class="margin-t10">
								<c:set var="now" value="<%=new java.util.Date()%>" />
								<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
									<select id="yyyy" name="yyyy" class="year selbox_01">
										<option value="">선택</option>
										<c:forEach var="i" begin="0" end="120" step="1" >
										<option value="${sysYear - i}">${sysYear - i}</option>
										</c:forEach>
									</select>
									<select id="mm" name="mm" class="month selbox_01">
										<option value="">선택</option>
										<c:forEach var="i" begin="1" end="12" step="1" >
										<option value="<fmt:formatNumber pattern="00" value="${i}" />"><fmt:formatNumber pattern="00" value="${i}" /></option>
										</c:forEach>
									</select>
									<select id="dd" name="dd" class="day selbox_01">
										<option value="">선택</option>
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
									<input type="text" id="homeZip" name="homeZip" class="disabled" readonly="readonly" onfocus="this.blur();">
									<a class="btn_form" onclick="daumPostCode();">주소검색</a>
								</div>
								<div>
									<input type="text" id="homeAddr" name="homeAddr">
								</div>
								<span class="cap">주소검색 후 세부주소를 입력해 주세요.</span>
							</td>
						</tr>
						<tr>
							<th>부모님 휴대전화번호 <span class="nece">*</span></th>
							<td><input type="text" id="etcNo" name="etcNo" value="${member.hp}" class="disabled" readonly="readonly" onfocus="this.blur();" maxlength="13"></td>
						</tr>
					</c:if>
					</tbody>
				</table>
			</div>

			<!-- 부가정보 -->
			<h3 class="margin_b20">부가정보</h3>
			<div id="" class="inputbox">
				<table>
					<colgroup>
						<col width="30%">
						<col width="70%">
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
								<c:if test="${member.emailYn ne 'N'}">
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
									<c:if test="${member.smsYn ne 'N'}">
									<input type="radio" id="smsY" name="smsYn" value="Y" checked> <label for="smsY"> 동의</label>
									<input type="radio" id="smsN" name="smsYn" value="N"> <label for="smsN"> 거부</label>
									</c:if>
								</div>
								<span class="cap">SMS(단문메시지) 수신에 동의하시면 각종 알림정보(수강/대관/묻고답하기 등)를 무료로 받아보실 수 있습니다.</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="btnarea">
			<a id="confirm" class="green" onclick="join.joinProc();">회원가입</a>
		</div>
	</div>
</div>