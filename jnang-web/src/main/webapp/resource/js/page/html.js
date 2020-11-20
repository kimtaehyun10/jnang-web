/**
* @projectDescription html.js
*
* @author RGJ
* @version 1.1
*/
'use strict'
$(function(){
	var cont='';
	$.get('/data/htmlPage/'+$('#h_cmsCd').val(), {}, function(data){
		setMenuTitle('bg_'+$('#h_cmsCd').val().substr(0,2), data.upCmsNm, data.cmsNm);
		$('#subCon').append(data.cont);
	}).always(function(){
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
		/**
		 * @Summary 서브
		 * @Discription 서브 > 본문html > 오시는길 > daumMap > 리사이징 > 가로스크롤바중앙맞춤
		 */
		function fn_resizescroll_daummapimg01(){
			var win_w=$(window).innerWidth();
			$('.resizescroll_daummapimg01').each(function(){
				$(this).scrollLeft(Math.ceil((1200-win_w)/2)+10);
			});
		}
		fn_resizescroll_daummapimg01();
		$(window).on('resize',function(){
			fn_resizescroll_daummapimg01();
		});
		/**
		 * @Summary 서브
		 * @Discription 서브 > 본문html > 자주묻는질문__토글
		 */
		var faq_data01a__o_toggle_idx=null;
		$('.faq_data01a .cel1 > dd > a').on('click',function(){
			var idx=$(this).parent().parent().parent().parent().index();
			if(faq_data01a__o_toggle_idx!=idx){
				if(faq_data01a__o_toggle_idx!=null){
					$('.faq_data01a > dl:eq('+faq_data01a__o_toggle_idx+') > dd').removeClass('on');
				}
				$('.faq_data01a > dl:eq('+idx+') > dd').addClass('on');
				faq_data01a__o_toggle_idx=idx;
			}else{
				$('.faq_data01a > dl:eq('+idx+') > dd').removeClass('on');
				faq_data01a__o_toggle_idx=null;
			}
		});
		/**
		 * @Summary 서브
		 * @Discription 서브 > 시설안내 > 시설사진
		 */
		var bxslider_sub_sisul_photo01__rolling1;
		bxslider_sub_sisul_photo01__rolling1=$('.sub_sisul_photo01__rolling1').bxSlider({
			auto:true,speed:500,pause:4000,mode:'horizontal',autoControls:true,autoHover:true,captions:true,onSliderLoad:function(){
				if(this){
					if(this.getSlideCount()){
						if(this.getSlideCount()<2){
							$('.sub_sisul_photo01 .bx-wrapper .bx-pager').css('display','none');
							$('.sub_sisul_photo01 .bx-wrapper .bx-controls-auto').css('display','none');
						}
					}
				}
			}
		});
	});
});
