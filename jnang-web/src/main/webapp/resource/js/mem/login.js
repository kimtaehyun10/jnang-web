/**
* @projectDescription login.js
*
* @author RGJ
* @version 1.12
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '회원관리', '로그인', true);
	var status = $('input[name=status]').val();
	var errorMsg = $('input[name=errorMsg]').val();
	if(status != null && status != ''){
		alert(errorMsg);
		if(status === '99'){
			if(confirm("비밀번호 변경 화면으로 이동하시겠습니까?")){
				login.logintalk.authentication(13);
			}
		}
	}
});

var login = {
	loginFromLogintalk: function(){
		this.logintalk.authentication(11);
	},
	phoneAuthentication: function(){
		this.logintalk.authentication(13);
	},
	logintalk: {
		authentication: function(serviceNo, procUrl){
			var options = {service: serviceNo};
			logintalk({key:'tZ3wZy9Z7', action:'/data/mem/login/logintalk/token', method:'get', verify:true, redirect:false});
			logintalk.main(options);
		}
	}
}