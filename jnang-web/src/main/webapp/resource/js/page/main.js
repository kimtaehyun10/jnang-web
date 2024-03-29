/**
* @projectDescription main.js
*
* @author RGJ
* @version 1.21
*/
'use strict'
var main = {
	safetyStatus: function(){
		var now = new Date();
		var diffDate = new Date();
		diffDate.setFullYear(dateUtil.getYear(),0,1);
		this.showSafetyStatusBoard(dateUtil.getYear(), (now.getTime() - diffDate.getTime())/1000/60/60/24);
	},
	showSafetyStatusBoard: function(sBaseDate, sDiffDate){
		$('#baseDate').text(dateUtil.getYear());
		$('#diffDate').text(sDiffDate);
	},
	popup: function(){
		this.showPopup(14, 600, 901);
		this.showPopup(15, 602, 751);
	},
	showPopup: function(popNo, width, height){
		if(!this.isReject(popNo)){
			var option = 'width='+width+', height='+height+', scrollbars=0, toolbar=0, menubar=no';
			window.open('/popup/popup'+popNo, 'popup'+popNo, option);
		}
	},
	isReject: function(popNo){
		var now = new Date();
		var diff = new Date(localStorage.getItem('popup'+popNo));
		var matched1 = dateUtil.getYear() === diff.getFullYear();
		var matched2 = dateUtil.getMonth() === diff.getMonth()+1;
		var matched3 = dateUtil.getDay() === diff.getDate();
		if(matched1 && matched2 && matched1) return true;
		else return false;
	}
}

var mainRollingBig;
$(function(){
	main.safetyStatus();
	//main.popup();
	$(document).on('click','.bx-next, .bx-prev',function(){
		mainRollingBig.stopAuto();
		mainRollingBig.startAuto();
	});
	$.get('/data/popupZone/', {}, function(data){
		try {
			var popupZone = '';
			if(data.length != 0){
				for(var i=0; i<data.length; i++){
					popupZone += '<li><a href="'+data[i].lnkUrl+'"><img src="/data/image/'+data[i].atchFileId+'" alt="'+data[i].imgTxt+'"></a></li>';
				}
			}else{
				popupZone += '<li><img src="/resource/images/temp/temp_main_rollingpopz1_01.jpg" alt="sample"></li>';
				popupZone += '<li><img src="/resource/images/temp/temp_main_rollingpopz1_02.jpg" alt="sample"></li>';
				popupZone += '<li><img src="/resource/images/temp/temp_main_rollingpopz1_03.jpg" alt="sample"></li>';
			}
			$('#popupZone').empty().append(popupZone);
		} catch (e) {
			console.error(e);
		}
	}).done(function(){
		/**
		 * 메인 > 공지사항/보도자료/입찰공고/채용공고 DATA..
		 */
		$.get('/data/cmsBoard', {brd1:"05010100", brd2:"05020100", brd3:"05080100", brd4:"05030100"}, function(data){
			try {
				var brdTab1 ="", brdTab2="", brdTab3="", brdTab4="";
				if(data.length != 0){
					
					
					for(var i=0; i<data.length; i++){
						var selectTab = data[i].tab;
						switch(selectTab)
						{
							case 1 :
								brdTab1 += "<ul class='loop'><li class='titcom'>"
									+ "	<div class='tit'><a href='/board/"+ data[i].CMS_CD +"/"+ data[i].BRD_NO +"'>공지사항 - "+ data[i].TITLE +"</a></div>"
									+ "		<div class='com'>"+ data[i].CONT +"</div>"
									+ "		</li> "
									+ "		<li class='wdate'>"+ data[i].YMD +"</li></ul>";
								break;

							case 2 :
								brdTab2 += "<ul class='loop'><li class='titcom'>"
									+ "	<div class='tit'><a href='/board/"+ data[i].CMS_CD +"/"+ data[i].BRD_NO +"'>보도자료 - "+ data[i].TITLE +"</a></div>"
									+ "		<div class='com'>"+ data[i].CONT +"</div>"
									+ "		</li> "
									+ "		<li class='wdate'>"+ data[i].YMD +"</li></ul>";							
								break;

							case 3 :
								brdTab3 += "<ul class='loop'><li class='titcom'>"
									+ "	<div class='tit'><a href='/board/"+ data[i].CMS_CD +"/"+ data[i].BRD_NO +"'>입찰공고 - "+ data[i].TITLE +"</a></div>"
									+ "		<div class='com'>"+ data[i].CONT +"</div>"
									+ "		</li> "
									+ "		<li class='wdate'>"+ data[i].YMD +"</li></ul>";							
								break;

							case 4 :
								brdTab4 += "<ul class='loop'><li class='titcom'>"
									+ "	<div class='tit'><a href='/board/"+ data[i].CMS_CD +"/"+ data[i].BRD_NO +"'>채용공고 - "+ data[i].TITLE +"</a></div>"
									+ "		<div class='com'>"+ data[i].CONT +"</div>"
									+ "		</li> "
									+ "		<li class='wdate'>"+ data[i].YMD +"</li></ul>";							
								break;
						}
					}
				}
				$('.latest1 .data').eq(0).html(brdTab1);
				$('.latest1 .data').eq(1).html(brdTab2);
				$('.latest1 .data').eq(2).html(brdTab3);
				$('.latest1 .data').eq(3).html(brdTab4);
			} catch (e) {
				console.error(e);
			}
		}).done(function(){
			/**
			 * @Summary 메인
			 * @Discription 메인 > 롤링 > 배너모음 > 반응형사이즈별출력개수처리
			 */
			function fn_resizing_rollingsite1(){
				$.get('/data/banner/', {}, function(data){					
					var hostIndex=location.href.indexOf(location.host)+location.host.length;
					var contextPath=location.href.substring(hostIndex,location.href.indexOf('/',hostIndex+1));
					var win_w=$(window).innerWidth();
					var htmls='';
					if(win_w>=1200){										
						for(var i=0; i<data.length; i++){
							if(i==0)htmls+='<li class="dp2"><dl>';
							switch ((i+1)%5) {
							case 0:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								htmls+='</dl></li>';
								if(i!=data.length-1){
									htmls+='<li class="dp2"><dl>';
								}								
								break;							
							default:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								break;
							}					
						}										
					}else if(win_w>=920){						
						for(var i=0; i<data.length; i++){
							if(i==0)htmls+='<li class="dp2"><dl>';
							switch ((i+1)%4) {
							case 0:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								htmls+='</dl></li>';
								if(i!=data.length-1){
									htmls+='<li class="dp2"><dl>';
								}								
								break;							
							default:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								break;
							}					
						}
					}else if(win_w>=740){
						for(var i=0; i<data.length; i++){
							if(i==0)htmls+='<li class="dp2"><dl>';
							switch ((i+1)%3) {
							case 0:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								htmls+='</dl></li>';
								if(i!=data.length-1){
									htmls+='<li class="dp2"><dl>';
								}								
								break;							
							default:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								break;
							}					
						}
					}else if(win_w>=550){
						for(var i=0; i<data.length; i++){
							if(i==0)htmls+='<li class="dp2"><dl>';
							switch ((i+1)%2) {
							case 0:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								htmls+='</dl></li>';
								if(i!=data.length-1){
									htmls+='<li class="dp2"><dl>';
								}								
								break;							
							default:
								htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
								break;
							}					
						}
					}else{						
						for(var i=0; i<data.length; i++){
							htmls+='<li class="dp2"><dl>';
							htmls+='<dd><a href="'+data[i].lnkUrl+'" target="_blank"><img src="/data/image/'+data[i].atchFileId+'"></a></dd>';
							htmls+='</dl></li>';
						}						
					}					
					$('.rollingsite1 ul.bx1').html(htmls);
				}).done(function(){
					/**
					 * @Summary 메인
					 * @Discription 메인 > 롤링 > 배너모음
					 */
					$('.rollingsite1 ul.bx1').carouFredSel({
						items:1,
						scroll:{
							items:1,//롤링넘어가는갯수
							duration:500,//롤링속도
							pauseOnHover:true//마우스 오버시 롤링멈춤 true, 롤링작동 false
						},
						prev:'#btn_main_rollingsite1_prev',//왼쪽으로 이동 버튼
						next:'#btn_main_rollingsite1_next',//오른쪽으로 이동 버튼
						responsive:false,
						direction:'up'//롤링 방향
					});
					/**
					 * @Summary 메인
					 * @Discription 메인 > 롤링 > 배너모음 > 웹접근성 > 버튼제어
					 */
					$('#btn_main_rollingsite1_stop').on('click',function(){
						$('.rollingsite1 ul.bx1').trigger('pause');
						$('.main_rollingsite1 .sc1 .btn_control li.stop').css('display', 'none');
						$('.main_rollingsite1 .sc1 .btn_control li.play').css('display', 'block');
					});
					$('#btn_main_rollingsite1_play').on('click',function(){
						$('.rollingsite1 ul.bx1').trigger('play');
						$('.main_rollingsite1 .sc1 .btn_control li.stop').css('display', 'block');
						$('.main_rollingsite1 .sc1 .btn_control li.play').css('display', 'none');
					});
					$('.rollingsite1 ul.bx1 a').on('focus',function(){
						$('.rollingsite1 ul.bx1').trigger('pause');
						$('.main_rollingsite1 .sc1 .btn_control li.stop').css('display', 'none');
						$('.main_rollingsite1 .sc1 .btn_control li.play').css('display', 'block');
					});
					$('.rollingsite1 ul.bx1 a').on('blur',function(){
						$('.rollingsite1 ul.bx1').trigger('play');
						$('.main_rollingsite1 .sc1 .btn_control li.stop').css('display', 'block');
						$('.main_rollingsite1 .sc1 .btn_control li.play').css('display', 'none');
					});
					$('#btn_main_rollingsite1_prev, #btn_main_rollingsite1_next').on('click',function(){
						$('.rollingsite1 ul.bx1').trigger('play');
						$('.main_rollingsite1 .sc1 .btn_control li.stop').css('display', 'block');
						$('.main_rollingsite1 .sc1 .btn_control li.play').css('display', 'none');
					});
				});
			}			
			fn_resizing_rollingsite1();
			/* 아래 코드로 수정
			 * $(window).on('resize',function(){
				fn_resizing_rollingsite1();
			});*/
			var rtime;
			var timeout = false;
			var delta = 200;
			$(window).resize(function() {
			    rtime = new Date();
			    if (timeout === false) {
			        timeout = true;
			        setTimeout(resizeend, delta);
			    }
			});
			function resizeend() {
			    if (new Date() - rtime < delta) {
			        setTimeout(resizeend, delta);
			    } else {
			        timeout = false;
			        fn_resizing_rollingsite1();
			    }
			}
		});
	}).always(function(){				
		/**
		 * @Summary 메인
		 * @Discription 메인 > 롤링 > 빅
		 */
		mainRollingBig = $('.main_rollingbig1').bxSlider({
			auto:true,speed:500,pause:4000,mode:'horizontal',autoControls:true,autoHover:true,captions:false,keyboardEnabled:true,responsive:true,touchEnabled:(navigator.maxTouchPoints > 0)
		});
		/**
		 * @Summary 메인
		 * @Discription 메인 > 최근게시물 > 게시판명탭
		 */
		$('.bx_main_late1 .latest1 .btn_sbj').on('click',function(){
			var linkurl_old=$('.bx_main_late1 .latest1 .more1 .more').attr('href');
			var linkurl_new=$(this).attr('linkurl');
			if(linkurl_old!=linkurl_new){
				$('.bx_main_late1 .latest1 .btn_sbj').removeClass('on');
				$('.bx_main_late1 .latest1 .dta:eq('+$(this).parent().index()+') .btn_sbj').addClass('on');
				$('.bx_main_late1 .latest1 .data').css('display','none');
				$('.bx_main_late1 .latest1 .dta:eq('+$(this).parent().index()+') .data').css('display', 'block');
				$('.bx_main_late1 .latest1 .more1 .more').attr('href',linkurl_new);
			}
		});
		/**
		 * @Summary 메인
		 * @Discription 메인 > 최근게시물 > 갤러리형보기 or 리스트형보기
		 */
		$('.bx_main_late1 .dsp_change').on('click',function(){
			var dsp_old=$('.bx_main_late1 .latest1').attr('class').replace('latest1 ','');
			var dsp_new=$(this).attr('dsp');
			if(dsp_old!=dsp_new){
				$('.bx_main_late1 .dsp_change').find('img').each(function(){
					$(this).attr('src',$(this).attr('src').replace('_on.','_off.'));
				});
				var tar_img=$('.bx_main_late1 .more1 li:eq('+$(this).parent().index()+') .dsp_change img');
				tar_img.attr('src',tar_img.attr('src').replace('_off.','_on.'));
				if(dsp_old=='dsp_g1'){
					$('.bx_main_late1 .latest1').removeClass('dsp_g1');
					$('.bx_main_late1 .latest1').addClass('dsp_b1');
				}else{
					$('.bx_main_late1 .latest1').removeClass('dsp_b1');
					$('.bx_main_late1 .latest1').addClass('dsp_g1');
				}
			}
		});
		/**
		 * @Summary 메인
		 * @Discription 메인 > 안전무사고현황판 > 리사이징
		 */
		function fn_popz1(){
			var win_w=$(window).innerWidth();
			$('.resizing_1264_864').each(function(){
				if(win_w<=1264&&win_w>864){
					var ofw=$(this).attr('osize_w');//원본width
					var ofh=$(this).attr('osize_h');//원본height
					var dfw=$(this).width();//현재width
					var fpe=Math.round(dfw/ofw*100);//비율
					var nfh=Math.ceil(ofh/(100/fpe));//최종height
					$(this).css('height',nfh+'px');
				}else{
					$(this).css('height','auto');
				}
			});
		}
		fn_popz1();
		$(window).on('resize',function(){
			fn_popz1();
		});
		/**
		 * @Summary 메인
		 * @Discription 메인 > 롤링 > 팝업존
		 */
		$('.main_rollingpopz1').bxSlider({
			auto:true,speed:500,pause:4000,mode:'horizontal',autoControls:true,autoHover:true,captions:false,touchEnabled:(navigator.maxTouchPoints > 0)
		});
		/**
		 * @Summary 메인
		 * @Discription 메인 > 시설안내
		 */
		function fn_main_sisul(){
			$('.main_sisul .menu1 .dp1').each(function(){
				if($(this).attr('class').indexOf('on')>-1){
					var objh=$('.main_sisul .menu1 .dp1:eq('+$(this).index()+') .dta1').outerHeight()+52;
					$('.main_sisul .ly1').css('height',objh+'px');
					$('.main_sisul').css('height',objh+'px');
					return false;
				}
			});
		}
		fn_main_sisul();
		$(window).on('resize',function(){
			fn_main_sisul();
		});
		$('.main_sisul .menu1 .dp1 .tab1').on('click',function(){
			$('.main_sisul .menu1 .dp1').removeClass('on');
			$('.main_sisul .menu1 .dp1 .dta1').css('display','none');
			$('.main_sisul .menu1 .dp1:eq('+$(this).parent().index()+')').addClass('on');
			$('.main_sisul .menu1 .dp1:eq('+$(this).parent().index()+') .dta1').css('display','block');
			var objh=$('.main_sisul .menu1 .dp1:eq('+$(this).parent().index()+') .dta1').outerHeight()+52;
			$('.main_sisul .ly1').css('height', objh+'px');
			$('.main_sisul').css('height', objh+'px');
		});				
	});
});
