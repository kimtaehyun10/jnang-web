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
	
	$(".sdate").datepicker();
});
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
		+ "생년월일 : <span style='margin:0px 5px 0 5px;display:inline-block; width:100px; border: none;' class='ali_c'><input type='text' class='sdate' value='' name='ubrth' style='width:100%;'></span> "
		+ "주소 : <span style='margin:0px 5px 0 5px;display:inline-block; width:400px; border: none;' class='ali_c'><input type='text' id='addr"+dataCnt+"' value='' name='uaddr' style='width:100%;' onclick='execDaumPostcode(document.getElementById(\"zip\"), document.getElementById(\"addr"+dataCnt+"\"));'></span> "
		+ "상세주소 : <span style='margin:0px 5px 0 5px;display:inline-block; width:200px; border: none;' class='ali_c'><input type='text' name='uaddr2' style='width:100%;'></span> "
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
		var frm = document.frm1;
		var dataCnt = $("input[name=uname]").length;
		var aItemList = new Array();
		
		var arrayData = "";
		for (ii = 0 ; ii < dataCnt; ii++) {
			var uname = $("input[name=uname]").eq(ii).val();
			var ubrth = $("input[name=ubrth]").eq(ii).val();
			if (uname == "") {
				alert(dataCnt +"번째 이름 입력은 필수값입니다.");
				return false;
			}
			if (ubrth == "") {
				alert(dataCnt +"번째 생년월일 입력은 필수값입니다.");
				return false;
			}
			var data = uname
					+ "|"+ ubrth
					+ "|"+ $("input[name=uaddr]").eq(ii).val()
					+ "|"+ $("input[name=uaddr2]").eq(ii).val()
					+ "//";
			
			arrayData += data;
		}
		$("#arryData").val(arrayData);
		$("#arryDel").val(arrayIdx);
		console.log(arrayIdx);
		if (confirm("\n 팀 정보를 저장  하시겠습니까?\n ")) {
		} else {
			return false;	
		}
		
		$.post('/rent/teamSave',  $("#frm1").serialize() , function(data){
			
			if (data == "1") {
				alert('팀이 저장 되었습니다.');
				top.location.href ='/rent/team';
			} else if (data == "-9") {
				alert("사용자 동의후 접수 가능합니다.");
			} else {
				alert('접수 오류 !  다시 신청 하십시요!~');
			}
		},"json");
	} catch (exception) 
	{
		console.log(exception);
		return false;
	}
		return false;
	
};
