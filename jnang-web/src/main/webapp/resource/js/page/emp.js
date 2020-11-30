/**
* @projectDescription emp.js
*
* @author RGJ
* @version 1.0
*/
'use strict'
$(function(){
	if(!$('#emptyDep1').text()) addUIEvnet.navi();				
	var errorCode={
		invalidLayout:'레이아웃 구성이 정의되지 않았습니다.'
	};
	var currentPage = $('#h_cmsCd').val();
	var parentPage = $('#h_cmsCd').val().substring(0,6).concat('00');
	var obj = new Object;
	$.get('/data/empPage/'+parentPage, {}, function(data){
		/**
		 * @Discription 4뎁스 레이아웃 설정
		 */
		var parentPage = $('#h_cmsCd').val().substring(0,6).concat('00');
		var searchPage = $('#h_cmsCd').val() != parentPage ? $('#h_cmsCd').val() : $('#h_cmsCd').val().substring(0,6).concat('01');
		$('#h_cmsCd').val(searchPage)
		if(data.child===0){
			setMenuTitle('bg_'+$('#h_cmsCd').val().substr(0,2), data.upCmsNm, data.cmsNm);
			$('#subCon').append(data.cont);
		}else{
			var html = '';
			setMenuTitle('bg_'+$('#h_cmsCd').val().substr(0,2), data.upCmsNm, data.cmsNm);
			switch(data.ltype){
			case 'L02':
			case 'L04':
				html+='<div class=\'clbx\'>';
				if(data.ltype === 'L02') html+='  <ul class=\'tab_ty1_cnt_2a\'>';
				if(data.ltype === 'L04') html+='  <ul class=\'tab_ty1_cnt_4a\'>';
				for(var i=0;i<data.child;i++){
					if(currentPage === parentPage){
						obj.cmsCd = data.childs[0].cmsCd;
						obj.mtype = data.childs[0].mtype;
						obj.cont = data.childs[0].cont;
					}
					if(data.childs[i].cmsCd === currentPage){
						obj.cmsCd = data.childs[i].cmsCd;
						obj.mtype = data.childs[i].mtype;
						obj.cont = data.childs[i].cont;
						html+='    <li id=\''+data.childs[i].cmsCd+'\' class=\'on\'><a href=\''+data.childs[i].cmsCd+'\'>'+data.childs[i].cmsNm+'</a></li>';
					}else{
						html+='    <li id=\''+data.childs[i].cmsCd+'\'><a href=\''+data.childs[i].cmsCd+'\'>'+data.childs[i].cmsNm+'</a></li>';
					}
				}
				html+='  </ul>';
				html+='</div>';
				html+='<br><br><br>';
				$('#subCon').append(html);
				break;
			case 'CBB':
				html+='<div class=\'clbx\'>';
				html+='  <div class=\'tab_ty2_selbox_1a\'>';
				html+='    <div>';
				for(var i=0;i<data.child;i++){
					if(currentPage === parentPage){
						obj.cmsCd = data.childs[0].cmsCd;
						obj.mtype = data.childs[0].mtype;
						obj.cont = data.childs[0].cont;
						html+='      <a href=\'javascript:;\'>'+data.childs[0].cmsNm+'</a>';
						break;
					}else if(data.childs[i].cmsCd === currentPage){
						obj.cmsCd = data.childs[i].cmsCd;
						obj.mtype = data.childs[i].mtype;
						obj.cont = data.childs[i].cont;
						html+='      <a href=\'javascript:;\'>'+data.childs[i].cmsNm+'</a>';
						break;
					}
				}
				html+='      <ul>';
				for(var i=0;i<data.child;i++){
					if(data.childs[i].cmsCd === currentPage){
						html+='    <li id=\''+data.childs[i].cmsCd+'\' class=\'on\'><a href=\''+data.childs[i].cmsCd+'\'>'+data.childs[i].cmsNm+'</a></li>';
					}else{
						html+='    <li id=\''+data.childs[i].cmsCd+'\'><a href=\''+data.childs[i].cmsCd+'\'>'+data.childs[i].cmsNm+'</a></li>';
					}
				}
				html+='      </ul>';
				html+='    </div>';
				html+='  </div>';
				html+='</div>';
				html+='<br><br><br>';
				$('#subCon').append(html);
				break;
			default:
				alert(errorCode.invalidLayout);
				location.href='/';
			}
		}
		if(currentPage===parentPage) $('#'+$('#h_cmsCd').val().substring(0,6).concat('01')).addClass('on');
	}).done(function(data){
		/**
		 * @Discription 메뉴 컨텐츠 정의
		 */
		
		//개발중
		//EMP 일반
		//HTM HTML
		//LNK 외부링크
		//NTC 공지형 게시판
		//BRD 답변형 게시판
		//FAQ FAQ 게시판
		//ALB 앨범형 게시판
		
		switch(obj.mtype){
		case 'HTM':
			$('#subCon').append(obj.cont)
			break;
		case 'LNK':
			window.open(obj.cont,'_blank');
			break;
		case 'FAQ':
			$('#h_mType').val(obj.mtype);
			$('#boardCon').append(faqHtml);
			searchFaq();
			break;
		case 'NTC':			
			$('#h_mType').val(obj.mtype);
			$('#boardCon').append(ntcHtml);
			searchNtc();
			break;
		default:
			break;
		}
	}).always(function(data){
		for(var i=0;i<data.child;i++){
			if(data.childs[i].cmsCd === currentPage){				
				switch (data.childs[i].mtype) {
				case 'FAQ':
				case 'NTC':
				case 'BRD':
				case 'HTM':					 
					break;
				default:
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
					break;
				}
			}
		}
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
