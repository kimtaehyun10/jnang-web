/**
* @projectDescription joinCommon.js
*
* @author RGJ
* @version 1.1
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '회원관리', '회원가입', true);
});

var join = {
	moreThan14: function(){
		location.href='/mem/join2/more';
	},
	lessThan14: function(){
		location.href='/mem/join2/less';
	},
	authentication: function(){
		join2.proc();
	},
	phoneAuthentication: function(){
		this.logintalk.authentication(13);
	},
	iPinAuthentication: function(){
		this.ipin.popup();
	},
	joinProc: function(){
		join4.proc();
	},
	cancel: function(){
		location.href='/mem/join1';
	},
	logintalk: {
		authentication: function(serviceNo){
			var options = {service: serviceNo};
			logintalk({key:'tZ3wZy9Z7', action:'/data/mem/join/logintalk/token', method:'get', verify:true, redirect:false});
			logintalk.main(options);
		}
	},
	ipin: {
		popup: function(){
			window.name = 'Parent_window';
			window.open('', 'popupIPIN2', 'width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
			document.form_ipin.target = "popupIPIN2";
			document.form_ipin.action = "https://cert.vno.co.kr/ipin.cb";
			document.form_ipin.submit();
		}
	}
};
