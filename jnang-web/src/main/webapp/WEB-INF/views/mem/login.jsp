<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
<%
final String strUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
if (strUrl.contains("localhost") || strUrl.contains("jnimc.dwict.kr")  ) {
	
} else {
	%>
	if (document.location.protocol == 'http:') {
	     top.location.href = document.location.href.replace('http:', 'https:');
	}
	<%
}
%>
</script>
<script src="https://auth.logintalk.io/js/logintalk.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mem/login.js"></script>
<div class='sub_login'>
	<div class='bx_login con_bx'>
		<div class='obx1'>
			<ul>
				<li>
					<form name='frm' method='post' action='/loginProcess'>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="hidden" name="status" value="${status}"/>
						<input type="hidden" name="errorMsg" value="${errorMsg}"/>
						<ul class='f1'>
							<li>
								<ul>
									<li><input type='text' name='userId' maxlength='20' placeholder='아이디' required></li>
									<li><input type='password' name='userPw' maxlength='20' placeholder='비밀번호' required></li>
								</ul>
							</li>
							<li><input type='submit' value='로그인'></li>
						</ul>
					</form>
				</li>
				<li>
					<a onclick="login.phoneAuthentication();">아이디ㆍ비밀번호 찾기</a>
					<a href='/mem/join1'>회원가입</a>
				</li>
				<div id="btn_sns">
                    <a id="naver">네이버로 로그인</a>
                    <a id="kakao">카카오톡으로 로그인</a>
                    <a id="face">페이스북으로 로그인</a>
                    <a id="talk" onclick="login.loginFromLogintalk();">로그인톡으로 로그인</a>
                </div>
			</ul>
		</div>
	</div>
</div>