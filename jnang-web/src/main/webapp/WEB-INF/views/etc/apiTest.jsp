<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <form name="frm" method="post" action="http://14.36.179.143:18080/member/joinMember.do">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	conninfo : <input type="text" name="conninfo" value="269A96546C0E7D7EB6957405281063B4269A96546C0E7D7EB6957405281063B4"> <BR>
	name : <input type="text" name="name" value="이름2"> <BR>
	birthday : <input type="text" name="birthday" value="1978-12-19"> <BR>
	gender :<input type="text" name="gender" value="male"> <BR>
	password :<input type="text" name="password" value="pwd1234"> <BR>
	id :<input type="text" name="id" value="powerjyc9"> <BR>
	mobile :<input type="text" name="mobile" value="010-4291-4292"> <BR>
	email :<input type="text" name="email" value="powerjyc@email"> <BR>
	postCode :<input type="text" name="postCode" value="11111"> <BR>
	address :<input type="text" name="address" value="주소주소주소주소ㅜㅈ소ㅜ 1"> <BR>
	smsReceive :<input type="text" name="smsReceive" value="Y"> <BR>
	emailReceive :<input type="text" name="emailReceive" value="Y"> <BR>
	legalRepresenter :<input type="text" name="legalRepresenter" value="대리인 이름"> <BR>
	agreePrivacy :<input type="text" name="agreePrivacy" value="Y"> <BR>
	agreeOffer3Party :<input type="text" name="agreeOffer3Party" value="Y"> <BR>
  <input type="submit" name="submit" value="전송"> <BR>
  </form>
 </body>
</html>
