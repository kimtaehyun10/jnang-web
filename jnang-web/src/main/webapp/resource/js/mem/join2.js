/**
* @projectDescription join2.js
*
* @author RGJ
* @version 1.12
*/
'use strict'
$(function(){
	
});

var join2 = {
	proc: function(){
		if(this.validate()){
			location.href='/mem/join3';
		}
	},
	validate: function(){
		if($("input:checkbox[name=agree1]").is(":checked") === false){
			alert('이용약관 동의가 필요합니다.');
			$("input:checkbox[name=agree1]").focus();
			return false;
		};
		if($("input:checkbox[name=agree2]").is(":checked") === false){
			alert('개인정보 수집 동의가 필요합니다.');
			$("input:checkbox[name=agree2]").focus();
			return false;
		};
		return true;
	}
}
