<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/findCommon.js"></script>
<div id="sub_find">
	<div>
		<div class="ipt_info margin_b50">
			<div class="inputbox margin_b50">
				<h3 class="fontsize_1dot25 margin_b20">비밀번호 변경</h3>
				<table class="tb_inputInfo">
					<colgroup>
						<col width="30%">
						<col width="70%">
					</colgroup>
					<tbody>
						<tr>
							<th>신규 비밀번호<span class="nece">*</span></th>
							<td><input type="password" id="pw" name="pw" onkeyup="addEvent.enterKey();" placeholder="비밀번호를 입력하세요."></td>
						</tr>
						<tr>
							<th>비밀번호 확인<span class="nece">*</span></th>
							<td><input type="password" id="pwCheck" name="pwCheck" onkeyup="addEvent.enterKey();" placeholder="비밀번호를 한번 더 입력하세요."></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="btnarea">
			<a id="confirm" class="green" onclick="find.proc();">비밀번호 변경</a>
		</div>
	</div>
</div>
