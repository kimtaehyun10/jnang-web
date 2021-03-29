/**
* @projectDescription dwict.board.pubParkApply.js
*
* @author KTH
* @version 1.0
*/
'use strict'

var pubParkfind = {	
	proc: function(){
		if(this.validate()){
			this.checkOk();
		}
	},
	validate: function(){
		
		
		if(!$("#name").val().trim()){
			alert('성명을 입력해주세요.');			
			$("#name").focus();
			return false;
		};				
		if(!$("#phone1").val().trim()){
			alert('연락처를 입력해주세요.');
			$("#phone1").focus();
			return false;
		};
		if(!$("#phone2").val().trim()){
			alert('연락처를 입력해주세요.');
			$("#phone2").focus();
			return false;
		};
		if(!$("#phone3").val().trim()){
			alert('연락처를 입력해주세요.');
			$("#phone3").focus();
			return false;
		};
		if(!$("#car_number").val().trim()){
			alert('차량번호를 입력해주세요.');
			$("#car_number").focus();
			return false;
		};
		if(!$("#car_model").val().trim()){
			alert('차종(모델명)을 입력해주세요.');
			$("#car_model").focus();
			return false;
		};
		if($("#pub_park_name").val() === ''){
			alert('주차장명을 선택해주세요.');
			$("#pub_park_name").focus();
			return false;
		};		
		if($("#use_time").val() === ''){
			alert('이용시간을 선택해주세요.');
			$("#use_time").focus();
			return false;
		};
		if($("#discount").val() === ''){
			alert('요금감면을 선택해주세요.');
			$("#discount").focus();
			return false;
		};			
		if(!$('input[name="agree1"]:checked').val()){
			alert('이용기본규약 동의해주세요.');	
			$('input[name="agree1"]').focus();
			return false;
		};
		if(!$('input[name="agree2"]:checked').val()){
			alert('개인정보 수집.이용 동의해주세요.');	
			$('input[name="agree2"]').focus();
			return false;
		};
				
		// 동일차량 신청 체크 (개발해야함)
		
		var param = {
			car_number : $("#car_number").val().replace(/(\s*)/g, "")	
		}
		var dataMsg = '';
		$.ajax({
	        type: "get",	        
	        url:'/data/pubParkApply/pubParkCheck.json',
	        data: param,
	        dataType: 'json',
	        async: false,
	        success: function (data) {	        	
	        	dataMsg = data.msg;
	        },        
	        error: function (jqXHR,textStatus,errorThrown) { 
	        	console.log(jqXHR,textStatus,errorThrown);
	        }
	    });
		if(dataMsg == 'NO'){
			alert('이미 신청된 차량입니다.');
			return false;
		}else{
			return true;
		}
		
	},
	checkOk: function(){
		
		var formData=new FormData();
		var inputFile=$("#file");
		var files=inputFile[0].files;	
		
		var phone = $("#phone1").val() + "-" + $("#phone2").val() + "-" + $("#phone3").val();
		
		formData.append("name",$("#name").val());		
		formData.append("phone", phone);		
		formData.append("car_number",$("#car_number").val().replace(/(\s*)/g, ""));
		formData.append("car_model",$("#car_model").val());
		formData.append("pub_park_name",$("#pub_park_name").val());
		formData.append("use_time",$("#use_time").val());
		formData.append("discount",$("#discount").val());									
		for(var i=0;i<files.length;i++){
			formData.append("files",files[i]);
		}		
		$.ajax({
	        type: "post",
	        enctype: 'multipart/form-data',
	        url:'/data/pubParkApply/02020102',
	        data: formData,
	        processData: false,
	        contentType: false,       
	        success: function (data) {
	        	alert(data.result);
	        	window.location.href='/emp/02020100';	        	
	        },        
	        error: function (jqXHR,textStatus,errorThrown) { 
	        	console.log(jqXHR,textStatus,errorThrown);
	        }
	    });				
	}
}