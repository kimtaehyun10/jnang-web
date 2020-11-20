/**
* @ProjectDescription UI Script.
*
* @Author RGJ
* @Version 1.10
*/
'use strict'
/**
 * @Summary 공용
 * @Discription 글자크기확대축소
 */
var scrzoom_now=100;
var scrzoom_max=300;
var scrzoom_min=100;
function fn_scrzoom(ac){
	if(ac==1){
		if(scrzoom_now>=scrzoom_max){
			alert('최대 화면입니다.');
			return;
		}
		scrzoom_now+=10;
	}else{
		if(scrzoom_now<=scrzoom_min){
			alert('최소 화면입니다.');
			return;
		}
		scrzoom_now-=10;
	}
	document.body.style.zoom=scrzoom_now+'%';
}
	
$(function(){
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 메뉴점프
	 */
	$('.head_goto1 a').on('focus',function(){
		$(this).css({'opacity':'1','padding':'1em 0','height':'auto'});
	});
	$('.head_goto1 a').on('blur',function(){
		$(this).css({'opacity':'0','padding':'0','height':'0'});
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 메뉴점프 > 주메뉴
	 */
	$('.btn_anchor_topmenu').on('click',function(){
		$('#anchor_topmenu').trigger('focus');
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 메뉴점프 > 본문영역
	 */
	$('.btn_anchor_content').on('click',function(){
		$('#anchor_content').trigger('focus');
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 통합예약
	 */
	$('.addmenu1_btn').on('click',function(){
		$('.addmenu1_ly').slideToggle();
		var osrc=$('.addmenu1_btn img').attr('src');
		var nsrc='';
		if(osrc.indexOf('_up.')>-1) nsrc=osrc.replace('_up.','_down.');
		else nsrc=osrc.replace('_down.','_up.');
		$('.addmenu1_btn img').attr('src',nsrc);
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 대메뉴 > PC > 마우스overout > 2차메뉴들쭉날쭉높이맞춤위해
	 */
	$('.head_menu_p .head_topmenu .topmenu .dp1 .sm1 dl:nth-of-type(4n)').after("<div class='clear'></div>");
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 대메뉴 > PC > 마우스overout
	 */
	$('.head_menu_p .head_topmenu .topmenu .dp1 .tm1').on('focus mouseover',function(){
		$('.head_menu_p .head_topmenu .topmenu .dp1 .sm1').css({'display':'none'});
		$('.head_menu_p .head_topmenu .topmenu .dp1:eq('+$(this).parent().index()+') .sm1').css({'display':'block'});
		$('.head_menu_p .topmenu_ly').addClass('on');
		$('.head_menu_p .topmenu_ly').css({'height':($(this).next().outerHeight()+$('.head_menu_p .head_topmenu').height())+'px'});
	});
	$('.head_menu_p .head_topmenu, .head_menu_p .topmenu_ly').on('blur mouseleave',function(){
		$('.head_menu_p .topmenu_ly').removeClass('on');
		$('.head_menu_p .topmenu_ly').css({'height':$('.head_menu_p .head_topmenu').height()+'px'});
		$('.head_menu_p .head_topmenu .topmenu .dp1 .sm1').css({'display':'none'});
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 주메뉴바로가기__포커스on일때
	 */
	$('#anchor_topmenu').on('focus',function(){
		$(document).scrollTop(0);
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 본문영역바로가기__포커스on일때
	 */
	$('#anchor_sub_navi1, #anchor_content').on('focus',function(){
		$('.head_menu_p .topmenu_ly').removeClass('on');
		$('.head_menu_p .topmenu_ly').css({'height':$('.head_menu_p .head_topmenu').height()+'px'});
		$('.head_menu_p .head_topmenu .topmenu .dp1 .sm1').css({'display':'none'});
		$(document).scrollTop(0);
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 반응형 > PC메뉴 > 탑메뉴3depth마우스오버시2depth강조처리
	 */
	$('.head_menu_p .head_topmenu .topmenu .dp1 .sm1 dl').on('focus mouseover',function(){
		$(this).find('dt').addClass('on');
	});
	$('.head_menu_p .head_topmenu .topmenu .dp1 .sm1 dl').on('blur mouseleave',function(){
		$(this).find('dt').removeClass('on');
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 반응형 > 모바일메뉴 > 전체메뉴onoff
	 */
	$('.btn_allmenu_toggle').on('click', function(){
		var win_w=$(window).innerWidth();
		var topmenu_ly__w=$('.head_menu_m .topmenu_ly').width();
		if(topmenu_ly__w<100){
			$('.head_menu_m .topmenu_bx').prepend("<div class='topmenu_bg'></div>");
			$('.head_menu_m .topmenu_ly').css({'display':'block','opacity':'1','width':(win_w>=865?60:80)+'%','overflow-y':'auto'});
			$('.head_menu_m .topmenu').focus();
			$('.btn_allmenu_toggle').attr('title','전체메뉴 닫기');
			$('.head_menu_m .topmenu_bg').on('click',function(){
				$('.head_menu_m .topmenu_ly').animate({'width':'0','opacity':'0','display':'none','overflow-y':'hidden'},100);
				$('.head_menu_m .topmenu_bg').fadeOut('slow',function(){$(this).remove();});
				$('.head_menu_m .bx_allmenu').focus();
				$('.btn_allmenu_toggle').attr('title','전체메뉴 열기');
			});
		}else{
			$('.head_menu_m .topmenu_ly').animate({'width':'0','opacity':'0','display':'none','overflow-y':'hidden'},100);
			$('.head_menu_m .topmenu_bg').fadeOut('slow',function(){$(this).remove();});
			$('.head_menu_m .bx_allmenu').focus();
			$('.btn_allmenu_toggle').attr('title','전체메뉴 열기');
		}
	});
	/**
	 * @Summary 헤드
	 * @Discription 헤드 > 반응형 > 모바일메뉴 > 상단검색폼
	 */
	$('.head_menu_m .headlogo .sch_btn a').on('click',function(){
		$('.head_menu_m .bx_sch .ly1').css({'display':'block','width':'100%','opacity':'1'});
		$('form[name=fsearch2] input[name=s_val]').focus();
	});
	$('.head_menu_m .bx_sch .ly1 .btn_sch2_close').on('click',function(){
		$('form[name=fsearch2] input[name=s_val]').val('');
		$('.head_menu_m .bx_sch .ly1').css({'display':'none','width':'0','opacity':'0'});
		$('.head_menu_m .headlogo .sch_btn a').focus();
	});
	/**
	 * @Summary 푸터
	 * @Discription 푸터 > 관련사이트 바로가기
	 */
	$('.foot_sc1 .dp1 .site1 .btn_toggle').on('click',function(){
		var tar=$('.foot_sc1 .dp1 .site1 .data');
		tar.css('height',tar.outerHeight()+'px');
		tar.slideToggle(300);
	});
});