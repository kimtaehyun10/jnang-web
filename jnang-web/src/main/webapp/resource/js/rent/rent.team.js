/**
* @projectDescription rent.list.js
*
* @author 
* @version 1.21
*/


$(function(){
	setMenuTitle('bg_99', '체육시설', '팀등록', true);
	$('.sub_navi1 .navi1 .mbx1 > a').on('click',function(){
		if($('.sub_navi1 .bg1').css('display')==='none'){
			var idx_n=$(this).parent().parent().index();
			$('.sub_navi1 .navi1 .dp1').each(function(){
				if(idx_n!=$(this).index()){
					$(this).find('ul').slideUp(100);
				}
			});
			$(this).next().slideToggle(150);
		}else{
			$(this).next().slideToggle(150);
		}
	});
	
	//$(".sdate").datepicker();
	
	
	/*var formData = new FormData();
	
	formData.append("attach_id",$("#attach_id").val());
alert()*/
	
	
/*	$.ajax({
            type: "GET",
            url: "/data/ntc/noticeNo/boardNo/ " + selectedAttachId,
            data: {},
			dataType: 'json',
            success: function (data) {
				alert("asfd");
				if (data == "1" || data == "2") {
					alert('팀이 저장 되었습니다.');
					//top.location.href ='/rent/team';
					top.location.reload();
					
				} else if (data == "-9") {
					alert("사용자 동의후 접수 가능합니다.");
				} else if (data == "3"){
					alert("이미 등록된 팀이 있습니다. 확인해주십시요.")
				}else {
					alert('접수 오류 !  다시 신청 하십시요!~');
				}
				
            },
            error: function (e) {
                console.log("ERROR : ", e);
                alert("fail");
            }
        });*/
	
	
	var selectedAttachId = $("#attach_id").val();
	
	
	var param = {};
	// dwict.board.const.js 재활용 (공지사항쪽)
	// cmsCd, brdNo의 값이 필요 없어서 임의로 작성
	$.get('/data/ntc/cmsCd/1/'+selectedAttachId, param, function(data){			
		let cont3 = '';
		for(var i=0;i<data.length;i++){								
			cont3 += '<a onclick="fileDownload(\''+data[i].attachId+'\','+data[i].attachNo+');">'+data[i].fileNm+'</a><br>';
			$("#ATTACH").html(cont3);								
		}
	});
});
const fileDownload = function(attachId, attachNo){
	window.location.href = '/data/file/board/'+attachId+'/'+attachNo;
}

var arrayIdx =new Array(); //삭제
var saveRtn = "${saveRtn}";
if (saveRtn == "-9") {
	alert("사용자 동의후 접수 가능합니다.");
}
if (saveRtn == "0") {
	alert("대관 접수 오류 다시 접수 하여 주세요.");
}


//var send = function(){
var dataCnt = 0;

function mem_add() {
	var dataCnt = $('input:text[name="uname"]').length;	
	dataCnt ++;
	var fld = "<div id='m_list"+ dataCnt +"' style='margin:5px;'>"+ dataCnt +". 성명 : <span style='margin:0px 5px 0 5px;display:inline-block; width:100px; border: none;'><input type='text' id='uname' name='uname' value='' style='width:100%;'></span> "
		+ "연락처 : <span style='margin:0px 5px 0 5px;display:inline-block; width:110px; border: none;' class='ali_c'><input type='text' name='hp' style='width:100%;' value='' placeholder='ex)01011112222' oninput=\"this.value = this.value.replace(/[^0-9.]/g, '');\"></span> "
		+ "생년월일 : <span style='margin:0px 5px 0 5px;display:inline-block; width:100px; border: none;' class='ali_c'><input type='text' class='sdate' id='ubrth' value='' name='ubrth' placeholder='ex)19830101' data-min='8' maxlength='8' style='width:100%;' oninput=\"this.value = this.value.replace(/[^0-9.]/g, '');\"></span> "
		+ "주소 : <span style='margin:0px 5px 0 5px;display:inline-block; width:300px; border: none;' class='ali_c'><input type='text' id='addr"+dataCnt+"' value='' name='uaddr' style='width:100%;' readonly='readonly' onclick='execDaumPostcode(document.getElementById(\"zip\"), document.getElementById(\"addr"+dataCnt+"\"));'></span> "
		+ "상세주소 : <span style='margin:0px 5px 0 5px;display:inline-block; width:100px; border: none;' class='ali_c'><input type='text' name='uaddr2' style='width:100%;'></span> "
		+ "<a onclick='del(\""+ dataCnt +"\",0);'> - "+ dataCnt +". 삭제</a></div>";
	//sessionStorage.setItem("k"+dataCnt, fld);
	$('#mem_list').append(fld);
}





//삭제
function del(id,seq) {
	$('#m_list'+id).remove();
	if (seq != "0") {
		arrayIdx.push(seq);
	}
}


function send() {
	try {
		
		debugger;
		
		var frm = document.frm1;
		var dataCnt = $("input[name=uname]").length;
		/*var file = $("input[name=fileFrm]").val();
		if (file != undefined) {
			var inputFile=$("#fileFrm");
			var files=inputFile[0].files;	
			var fileCheck = document.getElementById("fileFrm").value;	
		}*/
		
		var formData = new FormData();
		
		
	
		
		
		var aItemList = new Array();
		var arrayData = "";
		
		if (dataCnt < 1) {
			alert("팀원을 추가 하세요.");
			mem_add();
			mem_add();
			return false;
		}
				
		
		for (ii = 0 ; ii < dataCnt; ii++) {
			var uname = $("input[name=uname]").eq(ii).val();
			var ubrth = $("input[name=ubrth]").eq(ii).val();
			var hp = $("input[name=hp]").eq(ii).val();
			if (uname == "") {
				alert((ii+1) +"번째줄 이름 입력은 필수값입니다.");
				return false;
			}
			if (ubrth == "") {
				alert((ii+1) +"번째줄 생년월일 입력은 필수값입니다.");
				return false;
			}
			if (hp == "") {
				alert((ii+1) +"번째줄 연락처 입력은 필수값입니다.");
				return false;
			}
			var data = uname
					+ "|"+ ubrth
					+ "|"+ hp
					+ "|"+ $("input[name=uaddr]").eq(ii).val()
					+ "|"+ $("input[name=uaddr2]").eq(ii).val()
					+ "//";
			
			arrayData += data;
		}
		$("#arryData").val(arrayData);
		$("#arryDel").val(arrayIdx);
		
		formData.append("uname",$("#uname").val());
		formData.append("arryDel",$("#arryDel").val());
		formData.append("arryData",$("#arryData").val());
		formData.append("ubrth",$("#ubrth").val());
		formData.append("hp",$("#hp").val());
		/*if (file != undefined) {
			for(var i=0;i<files.length;i++){
				formData.append("files",files[i]);
			}	
		}*/	
		formData.append("uaddr",$("#uaddr").val());
		formData.append("uaddr2",$("#uaddr2").val());
		formData.append("tm_nm",$("#tm_nm").val());
		formData.append("tm_type",$("input[name=tm_type]:checked").val());
		formData.append("sp_type",$("#sp_type").val());
		formData.append("teamSEQ",$("#teamSEQ").val());
		formData.append("mem_id", $("#mem_id").val());
		console.log(arrayIdx);
		if (confirm("\n 팀 정보를 저장  하시겠습니까?\n ")) {
		} else {
			return false;	
		}
		
		/*$.post('/rent/teamSave', $("#frm1").serialize() ,function(data){
			
			debugger;
						
			if (data == "1" || data == "2") {
				alert('팀이 저장 되었습니다.');
				//top.location.href ='/rent/team';
				top.location.reload();
				
			} else if (data == "-9") {
				alert("사용자 동의후 접수 가능합니다.");
			} else if (data == "3"){
				alert("이미 등록된 팀이 있습니다. 확인해주십시요.")
			}else {
				alert('접수 오류 !  다시 신청 하십시요!~');
			}
		},"json");*/
		
		
		
		 $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/rent/teamSave",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {

				if (data == "1" || data == "2") {
					alert('팀 신청이 완료되었습니다.\n팀은 48시간내에 관리자에 의해 등록여부가 완료됩니다.');
					//top.location.href ='/rent/team';
					top.location.reload();
					
				} else if (data == "-9") {
					alert("사용자 동의후 접수 가능합니다.");
				} else if (data == "3"){
					alert("이미 등록된 팀이 있습니다. 확인해주십시요.")
				}else {
					alert('접수 오류 !  다시 신청 하십시요!~');
				}
				
            },
            error: function (e) {
                console.log("ERROR : ", e);
                alert("fail");
            }
        });

	} catch (exception) 
	{
		console.log(exception);
		return false;
	}
		return false;
	
};
