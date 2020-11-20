/**
* @projectDescription join4.js
*
* @author RGJ
* @version 1.2
*/
'use strict'
$(function(){
	var secBirthDate = $('#h_secBirthDate').val();
	addEvent.phone('hp');
	if($('#h_adult').val() != 'true'){
		addEvent.gender();
	}else{
		$('#yyyy').val(dateUtil.getYearFromStr(secBirthDate)).prop('disabled', 'disabled');
		$('#mm').val(dateUtil.getMonthFromStr(secBirthDate)).prop('disabled', 'disabled');
		$('#dd').val(dateUtil.getDayFromStr(secBirthDate)).prop('disabled', 'disabled');
	}
});

var addEvent = {
	gender: function(){
		$('#male').click(function(e){
			$('#male').addClass('on');
			$('#female').removeClass('on');
		});
		$('#female').click(function(e){
			$('#male').removeClass('on');
			$('#female').addClass('on');
		});
	},
	phone: function(domId){
		$('#'+domId).keyup(function(e){
			var number = $('#hp').val().replace(/[^0-9]/g, '');
			var phone = '';
			if(number.length < 4) {
		        return number;
		    } else if(number.length < 7) {
		        phone += number.substr(0, 3);
		        phone += "-";
		        phone += number.substr(3);
		    } else if(number.length < 11) {
		        phone += number.substr(0, 3);
		        phone += "-";
		        phone += number.substr(3, 3);
		        phone += "-";
		        phone += number.substr(6);
		    } else {
		        phone += number.substr(0, 3);
		        phone += "-";
		        phone += number.substr(3, 4);
		        phone += "-";
		        phone += number.substr(7);
		    }
			$('#'+domId).val(phone);
		});
	}
}

var addGenderClickEvent = function(){
	$('#male').click(function(e){
		$('#male').addClass('on');
		$('#female').removeClass('on');
	});
	$('#female').click(function(e){
		$('#male').removeClass('on');
		$('#female').addClass('on');
	});
};

var daumPostCode = function(){
	var homeZip = document.getElementById("homeZip");
	var homeAddr = document.getElementById("homeAddr");
	execDaumPostcode(homeZip, homeAddr);
}

var join4 = {
	proc: function(){
		if(this.validate()){
			this.insert();
		}
	},
	validate: function(){
		if($("#memNm").val() === ''){
			alert('이름을 입력해 주세요.');
			$("#memNm").focus();
			return false;
		};
		if(!/^[가-힣]{2,4}$/.test($("#memNm").val())){
			alert("이름은 한글 2~4자리로 입력해주세요.");
			$("#memNm").focus();
			return false;
		};
		if($("#id").val() === ''){
			alert('아이디을 입력해 주세요.');
			$("#id").focus();
			return false;
		};
		if(!/^[a-z0-9]{4,20}$/.test($("#id").val())){
			alert("아이디는 영 소문자, 숫자 4~20자리로 입력해주세요.");
			return false;
		};
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
		if($("#hp").val() === ''){
			alert('휴대전화번호를 입력해 주세요.');
			$("#hp").focus();
			return false;
		};
		if(!/^\d{3}-\d{3,4}-\d{4}$/.test($("#hp").val())){
			alert("휴대전화번호 형식이 맞지 않습니다.");
			$("#hp").focus();
			return false;
		};
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
		if($("#yyyy").val() === '' || $("#mm").val() === '' || $("#dd").val() === ''){
			alert('생년월일을 입력해 주세요.');
			return false;
		};
		if($("#zipcode").val() === ''){
			alert('주소를 검색해 주세요.');
			daumPostCode();
			return false;
		};
		if($("#addr").val() === ''){
			alert('주소를 입력해 주세요.');
			$("#addr").focus();
			return false;
		};
		return true;
	},
	insert: function(){
		var paramInput = makeParam.input('cardNo', 'memNm', 'id', 'pw', 'hp', 'email', 'homeZip', 'homeAddr', 'homeTel', 'etcNo');
		var paramRadio = makeParam.radio('birthSec', 'emailYn', 'smsYn');
		var paramSelect = makeParam.concat('secBirthDate', makeParam.select('yyyy', 'mm', 'dd'));
		var paramCustom = {gender:$('#male').hasClass('on')?'M':'F'};
		var param = Object.assign(paramInput, paramRadio, paramSelect, paramCustom);
		$.get('/data/mem/join/id/isDuplicate', param, function(isDuplicatedId){
			if(isDuplicatedId){
				alert('중복된 아이디 입니다.');
				return;
			}else{
				$.post('/data/mem/join', param, function(data){
					if(data === 1){
						location.href='/mem/join5';
					}else{
						alert('일시적으로 오류가 발생하였습니다. 지속적으로 문제 발생 시 관리자에게 연락 바랍니다.');
					}
				});
			}
		});
	}
}
