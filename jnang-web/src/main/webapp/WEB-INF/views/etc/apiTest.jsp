<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>Document</title>
 </head>
 <body>
  <form name="frm" method="post" action="http://14.36.179.143:18080/member/joinMember.do">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="text" name="request" value='{"request":{"conninfo":"269A96546C0E7D7EB6957405281063B4269A96546C0E7D7EB6957405281063B4","name":"이름2","birthday":"1978-12-19","gender":"male","password":"pwd1234","id":"powerjyc9","mobile":"010-4291-4292","email":"powerjyc@email","postCode":"11111","address":"주소주소주소주소ㅜㅈ소ㅜ 1","smsReceive":"Y","emailReceive":"Y","legalRepresenter":"대리인 이름","agreePrivacy":"Y","agreeOffer3Party":"Y"}}'>
	<!--
	<input type="text" name="conninfo" value="269A96546C0E7D7EB6957405281063B4269A96546C0E7D7EB6957405281063B4">
	<input type="text" name="name" value="이름2">
	<input type="text" name="birthday" value="1978-12-19">
	<input type="text" name="gender" value="male">
	<input type="text" name="password" value="pwd1234">
	<input type="text" name="id" value="powerjyc9">
	<input type="text" name="mobile" value="010-4291-4292">
	<input type="text" name="email" value="powerjyc@email">
	<input type="text" name="postCode" value="11111">
	<input type="text" name="address" value="주소주소주소주소ㅜㅈ소ㅜ 1">
	<input type="text" name="smsReceive" value="Y">
	<input type="text" name="emailReceive" value="Y">
	<input type="text" name="legalRepresenter" value="대리인 이름">
	<input type="text" name="agreePrivacy" value="Y">
	<input type="text" name="agreeOffer3Party" value="Y">
	-->
  <input type="submit" name="submit">
  </form>
 </body>
</html>

{"response":{"conninfo":"269A96546C0E7D7EB6957405281063B4269A96546C0E7D7EB6957405281063B4","name":"이름2","birthday":"1978-12-19","gender":"male","password":"pwd1234","id":"powerjyc9","mobile":"010-4291-4292","email":"powerjyc@email","postCode":"11111","address":"주소주소주소주소ㅜㅈ소ㅜ 1","smsReceive":"Y","emailReceive":"Y","legalRepresenter":"대리인 이름","agreePrivacy":"Y","agreeOffer3Party":"Y"}}

{
	"response":{
		"conninfo":"269A96546C0E7D7EB6957405281063B4269A96546C0E7D7EB6957405281063B4",
		"name":"이름2",
		"birthday":"1978-12-19",
		"gender":"male",
		"password":"pwd1234",
		"id":"powerjyc9",
		"mobile":"010-4291-4292",
		"email":"powerjyc@email",
		"postCode":"11111",
		"address":"주소주소주소주소ㅜㅈ소ㅜ 1",
		"smsReceive":"Y",
		"emailReceive":"Y",
		"legalRepresenter":"대리인 이름",
		"agreePrivacy":"Y",
		"agreeOffer3Party":"Y"
	}
}
</body>
</html>