<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/park/dwict.board.pubParkApply.js"></script>


<script type="text/javascript">
$(function(){		
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
</script>

<!-- <style>
.greenDiv{	
	background: url('/resource/images/sub/bg_green.png') no-repeat;
	background-size: 60px;
	width: 70px;
	height: 70px;
}
</style>

	<div class='sub_ethic'>
        <div class='img-txt ali_c' style="">
            <div class='txt'>
                <h5 class="ali_c margin_b10"></h5>
                <p class="ali_c">행복이 시작되는 곳<br>중랑구시설관리공단 인권경영헌장</p>
            </div>	
        </div>

        <div class='fontsize_1dot70 lineheight_1dot8 ali_c'>
            우리는 중랑구민을 위하여 봉사하는 중랑구의 일꾼이다.<br>
            중랑구민 앞에 우리의 사명과 중랑구의 양심을 다짐하고,<br>
            우리가 나가야 할 지표를 밝힌다.
        </div>
    
        <br>
        <br>
        <br>
    	
    	<div class='ly3' style="text-align: center; position: relative;"><img src='/resource/images/sub/bg_green_title.png' alt=''></div>
    	
        <div class='fontsize_1dot15 frame_pattern_01' style='font-size:18px; position: relative;top: -15px;'>
            <div class='ly1'><img src='/resource/images/sub/icon__frame_pattern_01_ly1.png' alt=''></div>
            <div class='ly2'><img src='/resource/images/sub/icon__frame_pattern_01_ly2.png' alt=''></div>
    		
            <div class='dl_col_2a'>
                <dl>
                    style="background-image: url('/resource/images/sub/bg_yellow.png');"
                    <dt> 
                    	<div class="greenDiv"><font style="color: white; padding-left: 12px; padding-top: 8px;" size="4">하나.</font></div>
                    </dt>
                    <dd>우리는 확고한 윤리관으로 정직하고 청렴하게 업무를 처리한다.</dd>
                </dl>
                <dl>
                    <dt>
                    	<div class="greenDiv"><font style="color: white; padding-left: 12px; padding-top: 8px;" size="4">하나.</font></div>
                    </dt>
                    <dd>우리는 법령과 양심에 따라 공명정대하게 업무를 처리한다.</dd>
                </dl>
                <dl>
                    <dt>
                    	<div class="greenDiv"><font style="color: white; padding-left: 12px; padding-top: 8px;" size="4">하나.</font></div>
                    </dt>
                    <dd>우리는 공익우선의 정신으로 특정개인이나 단체에 대한 차별적 특혜를 거부한다.</dd>
                </dl>
                <dl>
                    <dt>
                    	<div class="greenDiv"><font style="color: white; padding-left: 12px; padding-top: 8px;" size="4">하나.</font></div>
                    </dt>
                    <dd>우리는 솔선 헌신하여 중랑구 발전의 최일선에 선다.</dd>
                </dl>
                <dl>
                    <dt>
                    	<div class="greenDiv"><font style="color: white; padding-left: 12px; padding-top: 8px;" size="4">하나.</font></div>
                    </dt>
                    <dd>우리는 이모든 사명을 달성하기 위하여 끊임없이 인격과 지식의 연마에 노력한다.</dd>
                </dl>
            </div>
        </div>
    
        <br>
        <br>
    
        <div class='fontsize_1dot15 lineheight_1dot8 ali_c'>
            우리는 위와 같이 실행할 윤리헌장을 모든 임직원이 한결같은 마음으로<br>
            성실히 실처해 나갈 것을 다짐합니다.<br><br>
    
            중랑구시설관리공단 <strong>임직원 일동</strong>
        </div>
    
        <br>
        <br>
    
        <div class='bx_btns_01a'>
            파일다운로드처리
            <a class='size_m2 btn_a_green1' href='/data/file/윤리강령 시행내규.pdf' download>윤리강령 시행 내규</a>
            <a class='size_m2 btn_a_green1' href='/data/file/임직원행동강령.pdf' download>임직원 행동 강령</a>
        </div>
    </div> -->






