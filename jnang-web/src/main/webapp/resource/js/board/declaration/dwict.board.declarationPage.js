/**
* @projectDescription dwict.board.declarationPage.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '고객광장', '신고서 작성');
});
var find = {	
	proc: function(){
		if(this.validate()){
			this.checkOk();
		}
	},
	validate: function(){		
		if($("#dType").val() === ''){
			alert('신고유형을 선택해주세요.');			
			$("#dType").focus();
			return false;
		};				
		if($("#dTitle").val() === ''){
			alert('제목을 입력해주세요.');
			$("#dTitle").focus();
			return false;
		};
		if($("#dWho").val() === ''){
			alert('누가를 입력해주세요.');
			$("#dWho").focus();
			return false;
		};
		if($("#dWhen").val() === ''){
			alert('언제를 입력해주세요.');
			$("#dWhen").focus();
			return false;
		};
		if($("#dWhere").val() === ''){
			alert('어디서를 입력해주세요.');
			$("#dWhere").focus();
			return false;
		};
		if($("#dContents").val() === ''){
			alert('내용을 입력해주세요.');
			$("#dContents").focus();
			return false;
		};
		if($("#dAcquaintance").val() === ''){
			alert('이 문제를 아는사람을 입력해주세요.');
			$("#dAcquaintance").focus();
			return false;
		};
		if($("#dConfirm").val() === ''){
			alert('이 문제의 확인방법을 입력해주세요.');
			$("#dConfirm").focus();
			return false;
		};		
		if(!$('input[name="dInspector"]:checked').val()){
			alert('이 문제의 파악 경위를 선택해주세요.');
			$('input[name="dInspector"]').focus();
			return false;
		};
		if(!$('input[name="dTime"]:checked').val()){
			alert('이 문제의 지속 기간을 선택해주세요.');
			$('input[name="dTime"]').focus();
			return false;
		};
		if($("#dPass").val() === ''){
			alert('비밀번호를 입력해주세요.');
			$("#dPass").focus();
			return false;
		};
		if(!/^[0-9]+$/.test($("#dPass").val())){
			alert('비밀번호는 숫자만 입력해주세요.');
			$("#dPass").focus();
			return false;
		};
		if($("#dPass").val().length!=4){
			alert('비밀번호는 4자리 입니다.');
			$("#dPass").focus();
			return false;
		};
		if(!$('input[name="dAgree"]:checked').val()){
			alert('이용약관 동의해주세요.');	
			$('input[name="dAgree"]').focus();
			return false;
		};
		return true;
	},
	checkOk: function(){				
		var formData=new FormData();
		var inputFile=$("#file");
		var files=inputFile[0].files;						
		formData.append("dType",$("#dType").val());		
		formData.append("dTitle",$("#dTitle").val());		
		formData.append("dWho",$("#dWho").val());
		formData.append("dWhen",$("#dWhen").val());
		formData.append("dWhere",$("#dWhere").val());
		formData.append("dContents",$("#dContents").val());
		formData.append("dAcquaintance",$("#dAcquaintance").val());
		formData.append("dConfirm",$("#dConfirm").val());		
		formData.append("dInspector",$('input[name="dInspector"]:checked').val());
		formData.append("dTime",$('input[name="dTime"]:checked').val());
		formData.append("dPass",$("#dPass").val());							
		formData.append("dAgree",$('input[name="dAgree"]:checked').val());							
		for(var i=0;i<files.length;i++){
			formData.append("files",files[i]);
		}		
		$.ajax({
	        type: "post",
	        enctype: 'multipart/form-data',
	        url:'/data/declaration/'+$('#h_cmsCd').val()+'',
	        data: formData,
	        processData: false,
	        contentType: false,       
	        success: function (data) {
	        	alert(data.result);
	        	let cont = '';
	        	cont += '<div class="sub_greeting01">';
				cont += '<div class="ment1 ipt_info margin_b50">';
				cont += '<div class="bx">';
				cont += '<div class="fontsize_2dot00 lineheight_1dot8 t1">';
				cont += '<div class="sub_greeting01">';
				cont += '고유번호 : ' + data.dCode + '<br>고유번호는 처리결과 확인 때 필요합니다.<br>고유번호는 재발행 되지 않으므로 반드시 메모하시기 바랍니다.';
				cont += '</div>';
				cont += '</div>';
				cont += '</div>';
				cont += '</br>';
				cont += '</br>';
				cont += '</div>';
				cont += '<div class="btnarea">';
				cont += '<a class="green" href="/decComment/06030200">처리결과 확인</a>';
				cont += '</div>';
				$('#boardCon').empty().append(cont);	        	
	        },        
	        error: function (jqXHR,textStatus,errorThrown) { 
	        	console.log(jqXHR,textStatus,errorThrown);
	        }
	    });
	}
}