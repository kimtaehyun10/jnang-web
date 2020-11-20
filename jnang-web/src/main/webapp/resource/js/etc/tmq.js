/**
* @projectDescription tmq.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '종목별 시설현황', '', true);
	$('#sub_navi2').append('종목별 시설현황');
	/**
	 * @Summary 서브
	 * @Discription 서브 > 상단2 > 네비메뉴1__onoff
	 */
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
	/**
	 * @Summary 서브
	 * @Discription 서브 > 본문html > 탭메뉴2__onoff
	 */
	$('.tab_ty2_selbox_1a > div > a').on('click', function(){
		$(this).next().slideToggle(150);
	});
	/**
	 * @Summary 서브
	 * @Discription 서브 > 본문html > 탭클릭에따른콘텐츠onoff
	 */
	$('.jsc_tab1a_con').css('display','none');
	$('.jsc_tab1a_con').eq(0).css('display','');
	$('.jsc_tab1a_btn a').on('click',function(){
		if(!$(this).parent().hasClass('on')){
			var idx=$(this).parent().index();
			$('.jsc_tab1a_btn li').removeClass('on');
			$('.jsc_tab1a_con').css('display','none');
			$('.jsc_tab1a_btn li').eq(idx).addClass('on');
			$('.jsc_tab1a_con').eq(idx).css('display','');
		}
	});
});