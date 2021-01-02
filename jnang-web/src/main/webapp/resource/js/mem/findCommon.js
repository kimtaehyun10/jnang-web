/**
* @projectDescription findCommon.js
*
* @author RGJ
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '회원관리', '아이디·비밀번호 찾기', true);
});

var find = {
	proc: function(){
		if(this.validate()){
			this.changePassword();
		}
	},
	validate: function(){
		if($("#pw").val() === ''){
			alert('비밀번호를 입력해 주세요.');
			$("#pw").focus();
			return false;
		};
		if(!/^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{9,16}$/.test($("#pw").val())){
			alert("비밀번호는 특수문자를 포함한 [ 영문자, 숫자 ] 9~16자리로 입력해주세요.");
			return false;
		};
		if($("#pwCheck").val() === ''){
			alert('비밀번호를 다시 한 번 입력해 주세요.');
			$("#pwCheck").focus();
			return false;
		};
		if($("#pw").val() != $("#pwCheck").val()){
			alert('비밀번호가 일치하지 않습니다.');
			$("#pwCheck").focus();
			return false;
		};
		return true;
	},
	changePassword: function(){
		var param = makeParam.input('pw');
		$.post('/data/mem/password', param, function(data){
			if(data === 1){
				location.href='/mem/find/password/complete';
			}else{
				alert('일시적으로 오류가 발생하였습니다. 지속적으로 문제 발생 시 관리자에게 연락 바랍니다.');
			}
		});
	}
}

var addEvent = {
	enterKey: function(){
		if(window.event.keyCode===13){
			find.proc();
		}
	}
}


