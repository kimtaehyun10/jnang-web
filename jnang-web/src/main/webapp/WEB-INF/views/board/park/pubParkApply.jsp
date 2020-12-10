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

<div class='clbx'>
	<ul class='tab_ty1_cnt_4a jsc_tab1a_btn'>
		<li class='on'><a href='javascript:;'>수영</a></li>
		<li><a href='javascript:;'>아기스포츠단</a></li>
		<li><a href='javascript:;'>헬스</a></li>
		<li><a href='javascript:;'>(1개월)체육/문화/영어</a></li>
		<li><a href='javascript:;'>(3개월)체육/문화/수영</a></li>
	</ul>
</div>

<br>
<br>
<br>

<!-- 수영 -->
<div class='jsc_tab1a_con clbx'>
	<div class='teacher_item'>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207175543.jpg' alt=''></dt>
			<dd>
				<h6>고성애</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>한국아쿠아운동협회</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>저녁아쿠아로빅</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20140328110928.jpg' alt=''></dt>
			<dd>
				<h6>권수정</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 1급(수영), 생활스포츠지도사 2급(수영), 수상안전요원, 생존수영지도사 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>성인수영 및 어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207163803.jpg' alt=''></dt>
			<dd>
				<h6>권순영</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd></dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207155336.jpg' alt=''></dt>
			<dd>
				<h6>김균목</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이수영 및 저녁수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20170916154316.jpg' alt=''></dt>
			<dd>
				<h6>김민수</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>주말수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207161552.jpg' alt=''></dt>
			<dd>
				<h6>김여진</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>수상인명구조원</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>조기수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521175050.jpg' alt=''></dt>
			<dd>
				<h6>박준형</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>수상인명구조원 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>주말수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521174956.jpg' alt=''></dt>
			<dd>
				<h6>백명숙</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521174134.jpg' alt=''></dt>
			<dd>
				<h6>손정진</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>수상인명구조원 자격증</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이수영 및 저녁수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20170916153758.jpg' alt=''></dt>
			<dd>
				<h6>심은정</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>주말수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20170916153522.jpg' alt=''></dt>
			<dd>
				<h6>양은영</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>오전수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207175507.jpg' alt=''></dt>
			<dd>
				<h6>위보섭</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>아쿠아휘트니스지도자</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아쿠아로빅</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521181939.jpg' alt=''></dt>
			<dd>
				<h6>이소현</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>수상인명구조원 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>주말수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207174729.jpg' alt=''></dt>
			<dd>
				<h6>이창환</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>저녁수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207162434.jpg' alt=''></dt>
			<dd>
				<h6>이철원</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>조기수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20130123160445.jpg' alt=''></dt>
			<dd>
				<h6>이희석</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 1급(수영), 생활스포츠지도사 2급(수영), 수상안전요원, 생존수영지도사 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>성인수영 및 어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20170916154055.jpg' alt=''></dt>
			<dd>
				<h6>정양순</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20140328135845.jpg' alt=''></dt>
			<dd>
				<h6>정재훈</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 2급(수영), 수상안전요원, 생존수영지도사 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>성인수영 및 어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20130123160615.jpg' alt=''></dt>
			<dd>
				<h6>조용철</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 2급(수영), 수상안전요원, 생존수영지도사 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>성인수영 및 어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190905175529.jpg' alt=''></dt>
			<dd>
				<h6>최현우</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>수상인명구조원 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이수영</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20170916084633.jpg' alt=''></dt>
			<dd>
				<h6>한현석</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>조기수영 및 오전수영 </dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181207175156.jpg' alt=''></dt>
			<dd>
				<h6>허정</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사2급(수영)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>주말소그룹08C,09C,10C</dd>
					</dl>
				</div>
			</dd>
		</dl>
	</div>
</div>

<!-- 아기스포츠단 -->
<div class='jsc_tab1a_con clbx'>
	<div class='teacher_item'>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521175331.jpg' alt=''></dt>
			<dd>
				<h6>김성주</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>에듀짐 유아체육</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단 줄넘기</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521175931.jpg' alt=''></dt>
			<dd>
				<h6>문희숙</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>인라인 지도자 자격증</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단(인라인)</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521175413.jpg' alt=''></dt>
			<dd>
				<h6>신영미</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>쪼물딱 지도자 자격증</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단 쪼물딱</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181210113421.jpg' alt=''></dt>
			<dd>
				<h6>유은서</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>보육교사 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단 백곰반</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181210113537.jpg' alt=''></dt>
			<dd>
				<h6>임선미</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>보육교사 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단 다람쥐반</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521175306.jpg' alt=''></dt>
			<dd>
				<h6>전희정</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>- 오르프 지도자 과정 수료<br>- 한샘교육</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단 오르프</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181210113454.jpg' alt=''></dt>
			<dd>
				<h6>정다은</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>보육교사 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단 호랑이반</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190521175745.jpg' alt=''></dt>
			<dd>
				<h6>황보영</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>아기스포츠단</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>영어지도자 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>아기스포츠단 영어 </dd>
					</dl>
				</div>
			</dd>
		</dl>
	</div>
</div>

<!-- 헬스 -->
<div class='jsc_tab1a_con clbx'>
	<div class='teacher_item'>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181019112336.jpg' alt=''></dt>
			<dd>
				<h6>김민성</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>헬스</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>헬스(A, B/ PT)</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20161101181552.jpg' alt=''></dt>
			<dd>
				<h6>허은학</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>헬스</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd> 생활스포츠지도사 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>헬스(A, B/ PT)</dd>
					</dl>
				</div>
			</dd>
		</dl>
	</div>
</div>

<!-- (1개월)체육/문화/영어 -->
<div class='jsc_tab1a_con clbx'>
	<div class='teacher_item'>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190905180610.jpg' alt=''></dt>
			<dd>
				<h6>구본주</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>요가 및 필라테스 지도작 자격증</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>파워요가, 요가C, 요가E</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190523130835.jpg' alt=''></dt>
			<dd>
				<h6>김건직</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 2급(배드민턴)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>저녁배드민턴 개인레슨 A, B</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20140331132716.jpg' alt=''></dt>
			<dd>
				<h6>김록영</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>음악줄넘기 지도자3급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>음악줄넘기 A,C,E,H,F</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190905180819.jpg' alt=''></dt>
			<dd>
				<h6>김민지</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>발레전공자</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>발레A,B,C 및 작품발레</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190523130918.jpg' alt=''></dt>
			<dd>
				<h6>김소라</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>유아체육2급자격, 인라인스케이트 준강사 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이 인라인 A, B, 소수정예 C, D</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20160105131143.jpg' alt=''></dt>
			<dd>
				<h6>김승환</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>- 유아체육 2급 자격<br>- 인라인중강사 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>소수정예인라인A,B,E</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181210104906.jpg' alt=''></dt>
			<dd>
				<h6>김예일</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 2급(축구)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>소수유아실내축구 A, B</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190523130627.jpg' alt=''></dt>
			<dd>
				<h6>김창규</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 자격(배드민턴)</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>배드민턴 개인레슨(조기 A, 아침 A)</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20160409145205.jpg' alt=''></dt>
			<dd>
				<h6>김현지</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>국제 필라테스 지도자 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>필라테스A,B,D, 매트필라테스</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181210103456.jpg' alt=''></dt>
			<dd>
				<h6>문수정</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>라인댄스 지도자 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>라인댄스A</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20181210104217.jpg' alt=''></dt>
			<dd>
				<h6>박봉순</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>댄스스포츠 지도자자격증2급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>사교댄스A,B,E, 댄스스포츠A</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20140331131331.jpg' alt=''></dt>
			<dd>
				<h6>박은재</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>밸리댄스 지도자 자격증 2급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>밸리댄스A,B 어린이밸리댄스A,B, 직장인밸리</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20191004111718.jpg' alt=''></dt>
			<dd>
				<h6>박중민</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>중등2급정교사 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>소수초등(1~2학년) 실내축구 A, B</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='/resource/images/teacher/teacher_1.jpg' alt=''></dt>
			<dd>
				<h6>이희동</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 배드민턴 2급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>배드민턴 개인레슨 오전 A, B</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20171013180430.jpg' alt=''></dt>
			<dd>
				<h6>정기순</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 배드민턴 2급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>청소년 배드민턴 A, B, C, 주말</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190523130702.jpg' alt=''></dt>
			<dd>
				<h6>진선혜</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 농구 1급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이농구 A, B, C</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='/resource/images/teacher/teacher_2.jpg' alt=''></dt>
			<dd>
				<h6>조인호</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/영어</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 농구 2급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>주말농구교실</dd>
					</dl>
				</div>
			</dd>
		</dl>
	</div>
</div>

<!-- (3개월)체육/문화/수영 -->
<div class='jsc_tab1a_con clbx'>
	<div class='teacher_item'>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20140328161439.jpg' alt=''></dt>
			<dd>
				<h6>정민혜</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>- 동화구연 지도자 자격<br>- 유아스피치 지도자 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>동화구연, 자신감스피치</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20190523130918.jpg' alt=''></dt>
			<dd>
				<h6>김소라</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>유아체육2급자격, 인라인스케이트 준강사 자격</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>어린이 인라인 A, B, 소수정예 C, D</dd>
					</dl>
				</div>
			</dd>
		</dl>
		<dl class='dp1'>
			<dt><img src='http://jungnangspo.seoul.kr/photo/JUNGNANG01_T_20160105132649.jpg' alt=''></dt>
			<dd>
				<h6>이운희</h6>
				<div class='tstr1'>
					<dl>
						<dt>종목</dt>
						<dd>체육/문화/수영</dd>
					</dl>
					<dl>
						<dt>강사이력</dt>
						<dd>생활스포츠지도사 축구 2급</dd>
					</dl>
					<dl>
						<dt>담당강습반</dt>
						<dd>주말실내축구</dd>
					</dl>
				</div>
			</dd>
		</dl>
	</div>
</div>