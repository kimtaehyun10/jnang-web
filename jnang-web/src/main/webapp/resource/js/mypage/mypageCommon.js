/**
* @projectDescription mypageCommon.js
*
* @author KTH
* @version 1.11
*/
'use strict'
$(function(){
	$('#emptyDep2').on('click',function(){
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
	let cont = '';
	cont += '<li><a href="/mypage/classStatus">수강신청현황</a></li>';
	cont += '<li><a href="/mypage/cart">장바구니</a></li>';
	cont += '<li><a href="/mypage/lockerStatus">사물함신청현황</a></li>';
	cont += '<li><a href="/mypage/rent">대관신청현황</a></li>';
	cont += '<li><a href="/mypage/modify">회원정보수정</a></li>';
	cont += '<li><a href="/mypage/myBoard">마이게시판</a></li>';
	$(".mbx1:eq(1)>ul").append(cont);
});
