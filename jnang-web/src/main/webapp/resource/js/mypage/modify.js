/**
* @projectDescription modify.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '마이페이지', '회원정보수정', true);
});

var daumPostCode = function(){
	var homeZip = document.getElementById("homeZip");
	var homeAddr = document.getElementById("homeAddr");
	execDaumPostcode(homeZip, homeAddr);
}

var modify = {
	changePwdProc: function(){
		if(confirm("입력하신 내용이 초기화됩니다. 진행하시겠습니까?")){	
			this.phoneAuthentication();
		};
	},
	phoneAuthentication: function(){
		this.logintalk.authentication(13);
	},
	logintalk: {
		authentication: function(serviceNo){
			var options = {service: serviceNo};
			logintalk({key:'tZ3wZy9Z7', action:'/data/mypage/logintalk/token', method:'get', verify:true, redirect:false});
			logintalk.main(options);
		}
	},
	proc: function(){
		if(this.validate()){
			this.update();
		}
	},
	validate: function(){		
		if($("#email").val() === ''){
			alert('이메일 주소를 입력해 주세요.');
			$("#email").focus();
			return false;
		};
		if(!/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test($("#email").val())){
			alert("이메일 형식이 맞지 않습니다.");
			$("#email").focus();
			return false;
		};		
		if($("#homeZip").val() === ''){
			alert('주소를 검색해 주세요.');
			daumPostCode();
			return false;
		};
		if($("#homeAddr").val() === ''){
			alert('주소를 입력해 주세요.');
			$("#homeAddr").focus();
			return false;
		};
		return true;
	},
	update: function(){		
		var paramInput = makeParam.input('id', 'email', 'homeZip', 'homeAddr', 'homeTel', 'etcNo','dpstrNm', 'bankNm', 'acountNum');
		var paramRadio = makeParam.radio('birthSec', 'emailYn', 'smsYn');
		var paramSelect = makeParam.concat('secBirthDate', makeParam.select('yyyy', 'mm', 'dd'));
		var paramCustom = {gender:$('#male').hasClass('on')?'M':'F'};
		var param = Object.assign(paramInput, paramRadio, paramSelect, paramCustom);		
		$.put('/data/mypage/modify/'+$('#id').val(), param, function(data){
			alert(data.msg);
			window.location.href='/mypage/classStatus';
		});
	},
	delete: function(){		
		if(confirm('정말로 탈퇴하시겠습니까?')){
			$.delete('/data/mypage/modify/'+$('#id').val(), {}, function(data){
				alert(data.msg);
				window.location.href='/mem/logout';				
			});						 			 
		}		
	}
}