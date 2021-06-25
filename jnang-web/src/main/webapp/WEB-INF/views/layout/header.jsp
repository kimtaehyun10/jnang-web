<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/page/header.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/header.min.css">
<div class='fr_head'>
	<div class='head_goto1 responsive_p'>
		<a href='#anchor_topmenu' class='btn_anchor_topmenu'>주메뉴 바로가기</a>
		<a href='#anchor_content' class='btn_anchor_content'>본문영역 바로가기</a>
	</div>
	<div class='head_login responsive_p'>
		<div class='con_bx'>
			<div class='sc_login'>
				<ul class='weather'>
					<li><img id="weatherImg"></li>
					<li class='ondo'></li>
					<li class='weatherStatus'></li>
					<li class='airStatus'></li>
				</ul>
				<ul class='login'>
					<li><a href='/'>홈</a></li>
					<sec:authorize access="isAnonymous()">
						<li><a href='/mem/login'>로그인</a></li>
						<li><a href='/mem/join1'>회원가입</a></li>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_USER')">
						<li><a href='/mem/logout'>로그아웃</a></li>
						<li><a href='/mypage/classStatus'>마이페이지</a></li>
					</sec:authorize>
					<li><a href='/etc/sitemap'>사이트맵</a></li>
					<li>글자크기</li>
					<li class='scrzoom'><a href='javascript:fn_scrzoom(1);'><img src='${pageContext.request.contextPath}/resource/images/common/btn_screen_size_up.png' alt='화면 확대'></a></li>
					<li class='scrzoom'><a href='javascript:fn_scrzoom(0);'><img src='${pageContext.request.contextPath}/resource/images/common/btn_screen_size_down.png' alt='화면 축소'></a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 반응형 > PC메뉴 { -->
	<div class='head_menu_p responsive_p'>
		<div class='head_logo'>
			<div class='con_bx'>
				<ul class='sc1'>
					<li class='dp1'><a href='/'><img src='${pageContext.request.contextPath}/resource/images/common/logo_home_01.png' alt='홈으로'></a></li>
					<li class='dp1'>
						<ul class='bx_search'>
							<li><input type='text' name='keyword' maxlength='10' placeholder='검색어를 입력하세요' onkeyup="searchPage()"></li>
							<li><input type='image' src='${pageContext.request.contextPath}/resource/images/common/btn_search_01.png' alt='검색' onclick="searchPage()"></li>
						</ul>
					</li>
					<%-- <li class='dp1'>
						<div class='bx_addmenu1'>
							<div>
								<a href='javascript:;' class='addmenu1_btn'>시설 통합예약<img src='${pageContext.request.contextPath}/resource/images/common/icon_arrow_01_down.png' alt='통합예약 메뉴 열기'></a>
								<ul class='addmenu1_ly'>
									<li><a href='/lecture/list'>수강신청</a></li>
									<li><a href='/rentlist'>대관신청</a></li>
									<li><a href='/mypage/lockerStatus'>사물함신청</a></li>
								</ul>
							</div>
						</div>
					</li> --%>
				</ul>
			</div>
		</div>
		<a href='javascript:;' id='anchor_topmenu' title='주메뉴'></a>
		<div class='head_topmenu head_topmenu_cnt7'>
			<div class='topmenu_ly'>
				<div class='topmenu_bx_out'>
					<div class='topmenu_bx_in'>
						<ul class='topmenu'>
							<c:forEach items="${menuList}" var="topMenuList">
								<c:if test="${topMenuList.DEP eq '1'}">
								<li class='dp1'>
									<a class='tm1'>${topMenuList.CMS_NM}</a>
									<div class='sm1'>
										<div class='dp2'>
											<h3>${topMenuList.CMS_NM}</h3>
											<div>중랑구시설관리공단은<br>구민의 소리에 귀를 기울이겠습니다.</div>
											<c:if test="${topMenuList.DEP eq '1' and topMenuList.CMS_CD eq '01000000'}">
											<%-- 
											<div class='bx_topmenu_quick_a01'>
												<ul>
													<li><a href='/etc/tmq'><img src='${pageContext.request.contextPath}/resource/images/common/icon_topmenu_quick_a01.png' alt='수영'><h6>수영</h6></a></li>
													<li><a href='/etc/tmq'><img src='${pageContext.request.contextPath}/resource/images/common/icon_topmenu_quick_a02.png' alt='헬스'><h6>헬스</h6></a></li>
													<li><a href='/etc/tmq'><img src='${pageContext.request.contextPath}/resource/images/common/icon_topmenu_quick_a03.png' alt='문화ㆍ체육'><h6>문화ㆍ체육</h6></a></li>
												</ul>
											</div>
											--%>
											</c:if>
										</div>
										<div class='dp2'>
											<c:forEach items="${menuList}" var="depth2MenuList">
												<c:if test="${depth2MenuList.UP_CMS_CD eq topMenuList.CMS_CD && depth2MenuList.DEP eq '2'}">
											<dl>
												<dt><a class="tm2">${depth2MenuList.CMS_NM}</a></dt>
													<c:forEach items="${menuList}" var="depth3MenuList">
														<c:if test="${depth3MenuList.UP_CMS_CD eq depth2MenuList.CMS_CD && depth3MenuList.DEP eq '3'}">
															<c:choose>
																<c:when test="${depth3MenuList.MTYPE eq 'EMP'}">
																	<c:choose>
																		<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
												<dd><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:when>
																		<c:otherwise>
												<dd><a href='/emp/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																
																<c:when test="${depth3MenuList.MTYPE eq 'HTM'}">
																	<c:choose>
																		<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
												<dd><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:when>
																		<c:otherwise>
												<dd><a href='/html/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																
																<c:when test="${depth3MenuList.MTYPE eq 'LNK'}">
																	<c:choose>
																		<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
												<dd><a href='${depth3MenuList.CONT}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:when>
																		<c:otherwise>
												<dd><a href='${depth3MenuList.CONT}'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																
																<c:when test="${depth3MenuList.MTYPE eq 'NTC' || depth3MenuList.MTYPE eq 'BRD' || depth3MenuList.MTYPE eq 'FAQ' || depth3MenuList.MTYPE eq 'ALB'}">
																	<c:choose>
																		<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
												<dd><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:when>
																		<c:otherwise>
												<dd><a href='/board/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></dd>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
												<dd><a href='#'>${depth3MenuList.CMS_NM}</a></dd>
																</c:otherwise>
															</c:choose>
														</c:if>
													</c:forEach>
											</dl>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- } 반응형 > PC메뉴 -->
	<!-- 반응형 > 모바일메뉴 { -->
	<div class='head_menu_m responsive_m'>
		<div class='con_bx'>
			<div class='clbx'>
				<ul class='headlogo'>
					<li class='dp1 allmenu'>
						<a href='javascript:;' class='btn_allmenu_toggle' title='전체메뉴 열기'>
							<ul>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>
						</a>
					</li>
					<li class='dp1 home'><a href='/'><img src='${pageContext.request.contextPath}/resource/images/common/logo_home_01.png' alt='홈으로'></a></li>
					<li class='dp1 sch_btn'><a href='javascript:;'><img src='${pageContext.request.contextPath}/resource/images/common/btn_search_02.png' alt='검색폼 열기'></a></li>
				</ul>
			</div>
			<div class='bx_sch'>
				<div class='ly1 clbx'>
					<ul class='bx_search'>
						<li><input type='text' name='keyword' maxlength='10' placeholder='검색어를 입력하세요' onkeyup="searchPage()"></li>
						<li><input type='image' src='${pageContext.request.contextPath}/resource/images/common/btn_search_01.png' alt='검색' onclick="searchPage()"></li>
					</ul>
				</div>
			</div>
		</div>

		<div class='topmenu_bx'>
			<div class='topmenu_ly'>
				<div class='topmenu'>
					<div class='tit1'>
						전체메뉴
						<a href='javascript:;' class='btn_allmenu_toggle'><img src='${pageContext.request.contextPath}/resource/images/common/btn_allmenu_close1.png' alt='전체메뉴 닫기'></a>
					</div>

					<ul class='menu'>
						<li>
							<div><a>마이페이지</a></div>
							<sec:authorize access="isAnonymous()" >
							<dl>
								<dt><a href='/mem/login'>로그인</a></dt>
							</dl>
							<dl>
								<dt><a href='/mem/join1'>회원가입</a></dt>
							</dl>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_USER')">
								<dt><a href='/mem/logout'>로그아웃</a></dt>
								<dt><a href='/mypage/cart'>마이페이지</a></dt>
							</sec:authorize>
							<!-- <div><a>시설통합예약</a></div>
							<dl><dt><a href='/lecture/list'>수강신청</a></dt></dl>
							<dl><dd><a href='/lecture/class/1'>수영</a></dd></dl>
							<dl><dd><a href='/lecture/class/2'>헬스</a></dd></dl>
							<dl><dd><a href='/lecture/class/3'>문화ㆍ체육</a></dd></dl>
							<dl><dt><a href='/rentlist'>대관신청</a></dt></dl>
							<dl><dt><a href='/locker/list'>사물함신청</a></dt></dl> -->
							
						</li>
						<c:forEach items="${menuList}" var="topMenuList">
							<c:if test="${topMenuList.DEP eq '1'}">
						<li>
							<div><a>${topMenuList.CMS_NM}</a></div>
								<c:forEach items="${menuList}" var="depth2MenuList">
									<c:if test="${depth2MenuList.UP_CMS_CD eq topMenuList.CMS_CD && depth2MenuList.DEP eq '2'}">
							<dl>
								<dt><a>${depth2MenuList.CMS_NM}</a></dt>
										<c:forEach items="${menuList}" var="depth3MenuList">
											<c:if test="${depth3MenuList.UP_CMS_CD eq depth2MenuList.CMS_CD && depth3MenuList.DEP eq '3'}">
												<c:choose>
													<c:when test="${depth3MenuList.MTYPE eq 'EMP'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
								<dd><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
															</c:when>
															<c:otherwise>
								<dd><a href='/emp/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></dd>
															</c:otherwise>
														</c:choose>
													</c:when>
													
													<c:when test="${depth3MenuList.MTYPE eq 'HTM'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
								<dd><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
															</c:when>
															<c:otherwise>
								<dd><a href='/html/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></dd>
															</c:otherwise>
														</c:choose>
													</c:when>
													
													<c:when test="${depth3MenuList.MTYPE eq 'LNK'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
								<dd><a href='${depth3MenuList.CONT}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
															</c:when>
															<c:otherwise>
								<dd><a href='${depth3MenuList.CONT}'>${depth3MenuList.CMS_NM}</a></dd>
															</c:otherwise>
														</c:choose>
													</c:when>
													
													<c:when test="${depth3MenuList.MTYPE eq 'NTC' || depth3MenuList.MTYPE eq 'BRD' || depth3MenuList.MTYPE eq 'FAQ' || depth3MenuList.MTYPE eq 'ALB'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">
								<dd><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></dd>
															</c:when>
															<c:otherwise>
								<dd><a href='/board/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></dd>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
								<dd><a href='#'>${depth3MenuList.CMS_NM}</a></dd>
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:forEach>
							</dl>
									</c:if>
								</c:forEach>
						</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 서브네비메뉴1 -->	
	<a href='javascript:;' id='anchor_sub_navi1' title='네비메뉴'></a>
	<div id="subNavMenu" class='sub_navi1'>
		<div class='bg1'></div>
		<div id="upTitle" class='tit1'></div>
		<div class='bg2'></div>
		<div class='ly1'>
			<div class='con_bx con_bx2'>
				<ul class='navi1'>
					<li class='dp1'><a href='/'><img src='${pageContext.request.contextPath}/resource/images/sub/icon_shome_01.png' alt='홈으로'></a></li>
					<li class='dp1'>
						<div class='mbx1'>
						<a id='emptyDep1'></a>						
						<c:forEach items="${menuList}" var="depth3Menu">							
							<c:if test="${depth3Menu.CMS_CD eq cmsCd}">
								<c:choose>
									<c:when test="${depth3Menu.DEP eq '4'}">
										<c:forEach items="${menuList}" var="depth2MenuList">
											<c:if test="${depth3Menu.UP_CMS_CD eq depth2MenuList.CMS_CD}">																																		
												<c:forEach items="${menuList}" var="depth1MenuList">
													<c:if test="${depth1MenuList.CMS_CD eq depth2MenuList.UP_CMS_CD}">
														<c:forEach items="${menuList}" var="depth0MenuList">
															<c:if test="${depth0MenuList.CMS_CD eq depth1MenuList.UP_CMS_CD}">
																<a>${depth0MenuList.CMS_NM}</a>																									
															</c:if>
														</c:forEach>																									
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${menuList}" var="depth2MenuList">
											<c:if test="${depth3Menu.UP_CMS_CD eq depth2MenuList.CMS_CD}">																																		
												<c:forEach items="${menuList}" var="depth1MenuList">
													<c:if test="${depth1MenuList.CMS_CD eq depth2MenuList.UP_CMS_CD}">
														<a>${depth1MenuList.CMS_NM}</a>																									
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>																																		
							</c:if>
						</c:forEach>														
						<ul>
						<c:forEach items="${menuList}" var="depth1MenuList">
							<c:if test="${depth1MenuList.DEP eq '1'}">
								<c:set var="doneLoop1" value="false"/>
								<c:forEach items="${menuList}" var="depth2MenuList">
									<c:if test="${depth2MenuList.DEP eq '2' and depth1MenuList.CMS_CD eq depth2MenuList.UP_CMS_CD}">
										<c:if test="${not doneLoop1}">											
											<c:set var="doneLoop2" value="false"/>
											<c:forEach items="${menuList}" var="depth3MenuList">
												<c:if test="${depth3MenuList.DEP eq '3' and depth2MenuList.CMS_CD eq depth3MenuList.UP_CMS_CD}">
													<c:if test="${not doneLoop2}">															
														<c:if test="${depth3MenuList.MTYPE eq 'HTM'}">
															<c:choose>
																<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																	
																	<li><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth1MenuList.CMS_NM}</a></li>
																</c:when>
																<c:otherwise>																	
																	<li><a href='/html/${depth3MenuList.CMS_CD}'>${depth1MenuList.CMS_NM}</a></li>
																</c:otherwise>
															</c:choose>																														
														</c:if>
														<c:if test="${depth3MenuList.MTYPE eq 'BRD' or depth3MenuList.MTYPE eq 'NTC' or depth3MenuList.MTYPE eq 'FAQ'}">
															<c:choose>
																<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																		
																	<li><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth1MenuList.CMS_NM}</a></li>
																</c:when>
																<c:otherwise>																	
																	<li><a href='/board/${depth3MenuList.CMS_CD}'>${depth1MenuList.CMS_NM}</a></li>
																</c:otherwise>
															</c:choose>															
														</c:if>
														<c:if test="${depth3MenuList.MTYPE eq 'EMP'}">
															<c:choose>
																<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																			
																	<li><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth1MenuList.CMS_NM}</a></li>
																</c:when>
																<c:otherwise>																	
																	<li><a href='/emp/${depth3MenuList.CMS_CD}'>${depth1MenuList.CMS_NM}</a></li>
																</c:otherwise>
															</c:choose>															
														</c:if>
														<c:if test="${depth3MenuList.MTYPE eq 'LNK'}">
															<c:choose>
																<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																			
																	<li><a href='${depth3MenuList.CONT}' target='_blank'>${depth1MenuList.CMS_NM}</a></li>
																</c:when>
																<c:otherwise>																	
																	<li><a href='${depth3MenuList.CONT}'>${depth1MenuList.CMS_NM}</a></li>
																</c:otherwise>
															</c:choose>															
														</c:if>
													</c:if>
													<c:set var="doneLoop2" value="true"/>							
												</c:if>
											</c:forEach>
										</c:if>
										<c:set var="doneLoop1" value="true"/>							
									</c:if>
								</c:forEach>																
							</c:if>
						</c:forEach>
						</ul>
						</div>
					</li>
					<!-- 메뉴깊이가3depth이상일때출력함 -->
					<li class='dp1'>
						<div class='mbx1'>
							<a id='emptyDep2'></a>							
							<c:forEach items="${menuList}" var="depth3Menu">
								<c:if test="${depth3Menu.CMS_CD eq cmsCd}">										
									<c:choose>
										<c:when test="${depth3Menu.DEP eq '4'}">
											<c:forEach items="${menuList}" var="depth2MenuList">
												<c:if test="${depth3Menu.UP_CMS_CD eq depth2MenuList.CMS_CD}">																							
													<c:forEach items="${menuList}" var="depth1MenuList">
														<c:if test="${depth2MenuList.UP_CMS_CD eq depth1MenuList.CMS_CD}">																							
															<a>${depth1MenuList.CMS_NM}</a>
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach items="${menuList}" var="depth2MenuList">
												<c:if test="${depth3Menu.UP_CMS_CD eq depth2MenuList.CMS_CD}">																							
													<a>${depth2MenuList.CMS_NM}</a>
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>																
								</c:if>
							</c:forEach>							
							<ul>																																				
							<c:forEach items="${menuList}" var="depth3Menu">
								<c:if test="${depth3Menu.CMS_CD eq cmsCd}">										
									<c:choose>
										<c:when test="${depth3Menu.DEP eq '4'}">
											<c:forEach items="${menuList}" var="depth3MenuList">
												<c:if test="${depth3Menu.UP_CMS_CD eq depth3MenuList.CMS_CD}">
													<c:forEach items="${menuList}" var="depth2MenuList">
														<c:if test="${depth3MenuList.UP_CMS_CD eq depth2MenuList.CMS_CD}">												
															<c:forEach items="${menuList}" var="depth2MenuList2">
																<c:if test="${depth2MenuList.UP_CMS_CD eq depth2MenuList2.UP_CMS_CD}">													
																	<c:set var="doneLoop3" value="false"/>
																	<c:forEach items="${menuList}" var="depth3MenuList">
																		<c:if test="${depth2MenuList2.CMS_CD eq depth3MenuList.UP_CMS_CD}">
																			<c:if test="${not doneLoop3}">
																				<c:if test="${depth3MenuList.MTYPE eq 'HTM'}">
																					<c:choose>
																						<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																							
																							<li><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:when>
																						<c:otherwise>																	
																							<li><a href='/html/${depth3MenuList.CMS_CD}'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:otherwise>
																					</c:choose>																					
																				</c:if>
																				<c:if test="${depth3MenuList.MTYPE eq 'BRD' or depth3MenuList.MTYPE eq 'NTC' or depth3MenuList.MTYPE eq 'FAQ'}">
																					<c:choose>
																						<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																														
																							<li><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:when>
																						<c:otherwise>																	
																							<li><a href='/board/${depth3MenuList.CMS_CD}'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:otherwise>
																					</c:choose>																					
																				</c:if>
																				<c:if test="${depth3MenuList.MTYPE eq 'EMP'}">
																					<c:choose>
																						<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																					
																							<li><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:when>
																						<c:otherwise>																	
																							<li><a href='/emp/${depth3MenuList.CMS_CD}'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:otherwise>
																					</c:choose>																					
																				</c:if>
																				<c:if test="${depth3MenuList.MTYPE eq 'LNK'}">
																					<c:choose>
																						<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																					
																							<li><a href='${depth3MenuList.CONT}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:when>
																						<c:otherwise>																	
																							<li><a href='${depth3MenuList.CONT}'>${depth2MenuList2.CMS_NM}</a></li>
																						</c:otherwise>
																					</c:choose>																					
																				</c:if>																															                                        
																			</c:if>
																			<c:set var="doneLoop3" value="true"/>							
																		</c:if>
																	</c:forEach>													
																</c:if>
															</c:forEach>											
														</c:if>
													</c:forEach>		
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach items="${menuList}" var="depth2MenuList">
												<c:if test="${depth3Menu.UP_CMS_CD eq depth2MenuList.CMS_CD}">												
													<c:forEach items="${menuList}" var="depth2MenuList2">
														<c:if test="${depth2MenuList.UP_CMS_CD eq depth2MenuList2.UP_CMS_CD}">													
															<c:set var="doneLoop3" value="false"/>
															<c:forEach items="${menuList}" var="depth3MenuList">
																<c:if test="${depth2MenuList2.CMS_CD eq depth3MenuList.UP_CMS_CD}">
																	<c:if test="${not doneLoop3}">
																		<c:if test="${depth3MenuList.MTYPE eq 'HTM'}">
																			<c:choose>
																				<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																										
																					<li><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:when>
																				<c:otherwise>																	
																					<li><a href='/html/${depth3MenuList.CMS_CD}'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:otherwise>
																			</c:choose>																			
																		</c:if>
																		<c:if test="${depth3MenuList.MTYPE eq 'BRD' or depth3MenuList.MTYPE eq 'NTC' or depth3MenuList.MTYPE eq 'FAQ'}">
																			<c:choose>
																				<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																															
																					<li><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:when>
																				<c:otherwise>																	
																					<li><a href='/board/${depth3MenuList.CMS_CD}'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:otherwise>
																			</c:choose>																				
																		</c:if>
																		<c:if test="${depth3MenuList.MTYPE eq 'EMP'}">
																			<c:choose>
																				<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																				
																					<li><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:when>
																				<c:otherwise>																	
																					<li><a href='/emp/${depth3MenuList.CMS_CD}'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:otherwise>
																			</c:choose>																			
																		</c:if>
																		<c:if test="${depth3MenuList.MTYPE eq 'LNK'}">
																			<c:choose>
																				<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																				
																					<li><a href='${depth3MenuList.CONT}' target='_blank'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:when>
																				<c:otherwise>																	
																					<li><a href='${depth3MenuList.CONT}'>${depth2MenuList2.CMS_NM}</a></li>
																				</c:otherwise>
																			</c:choose>																			
																		</c:if>																															                                        
																	</c:if>
																	<c:set var="doneLoop3" value="true"/>							
																</c:if>
															</c:forEach>													
														</c:if>
													</c:forEach>											
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>																
								</c:if>
							</c:forEach>
							</ul>
						</div>
					</li>
					<!-- 메뉴깊이가4depth이상일때출력함 -->
					<li class='dp1'>
						<div class='mbx1'>
							<c:forEach items="${menuList}" var="depth3Menu">
								<c:if test="${depth3Menu.CMS_CD eq cmsCd}">
									<c:choose>
										<c:when test="${depth3Menu.DEP eq '4'}">
											<c:forEach items="${menuList}" var="depth3MenuList">
												<c:if test="${depth3Menu.UP_CMS_CD eq depth3MenuList.CMS_CD}">
													<a>${depth3MenuList.CMS_NM}</a>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<a>${depth3Menu.CMS_NM}</a>
										</c:otherwise>
									</c:choose>																								
								</c:if>
							</c:forEach>
							<ul>
							<c:forEach items="${menuList}" var="depth3Menu">
								<c:if test="${depth3Menu.CMS_CD eq cmsCd}">
									<c:choose>
										<c:when test="${depth3Menu.DEP eq '4'}">
											<c:forEach items="${menuList}" var="depth3Menu2">
												<c:if test="${depth3Menu.UP_CMS_CD eq depth3Menu2.CMS_CD}">
													<c:forEach items="${menuList}" var="depth3MenuList">
														<c:if test="${depth3MenuList.UP_CMS_CD eq depth3Menu2.UP_CMS_CD}">
															<c:if test="${depth3MenuList.MTYPE eq 'HTM'}">
																<c:choose>
																	<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																						
																		<li><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
																	</c:when>
																	<c:otherwise>																	
																		<li><a href='/html/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></li>
																	</c:otherwise>
																</c:choose>																
															</c:if>
															<c:if test="${depth3MenuList.MTYPE eq 'BRD' or depth3MenuList.MTYPE eq 'NTC' or depth3MenuList.MTYPE eq 'FAQ'}">
																<c:choose>
																	<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																								
																		<li><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
																	</c:when>
																	<c:otherwise>																	
																		<li><a href='/board/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></li>
																	</c:otherwise>
																</c:choose>																
															</c:if>
															<c:if test="${depth3MenuList.MTYPE eq 'EMP'}">
																<c:choose>
																	<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																										
																		<li><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
																	</c:when>
																	<c:otherwise>																	
																		<li><a href='/emp/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></li>
																	</c:otherwise>
																</c:choose>																
															</c:if>
															<c:if test="${depth3MenuList.MTYPE eq 'LNK'}">
																<c:choose>
																	<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																										
																		<li><a href='${depth3MenuList.CONT}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
																	</c:when>
																	<c:otherwise>																	
																		<li><a href='${depth3MenuList.CONT}'>${depth3MenuList.CMS_NM}</a></li>
																	</c:otherwise>
																</c:choose>																
															</c:if>																
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach items="${menuList}" var="depth3MenuList">
												<c:if test="${depth3MenuList.UP_CMS_CD eq depth3Menu.UP_CMS_CD}">
													<c:if test="${depth3MenuList.MTYPE eq 'HTM'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																										
																<li><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
															</c:when>
															<c:otherwise>																	
																<li><a href='/html/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></li>
															</c:otherwise>
														</c:choose>														
													</c:if>
													<c:if test="${depth3MenuList.MTYPE eq 'BRD' or depth3MenuList.MTYPE eq 'NTC' or depth3MenuList.MTYPE eq 'FAQ'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																																										
																<li><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
															</c:when>
															<c:otherwise>																	
																<li><a href='/board/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></li>
															</c:otherwise>
														</c:choose>															
													</c:if>
													<c:if test="${depth3MenuList.MTYPE eq 'EMP'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																																																										
																<li><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
															</c:when>
															<c:otherwise>																	
																<li><a href='/emp/${depth3MenuList.CMS_CD}'>${depth3MenuList.CMS_NM}</a></li>
															</c:otherwise>
														</c:choose>														
													</c:if>
													<c:if test="${depth3MenuList.MTYPE eq 'LNK'}">
														<c:choose>
															<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																																																										
																<li><a href='${depth3MenuList.CONT}' target='_blank'>${depth3MenuList.CMS_NM}</a></li>
															</c:when>
															<c:otherwise>																	
																<li><a href='${depth3MenuList.CONT}'>${depth3MenuList.CMS_NM}</a></li>
															</c:otherwise>
														</c:choose>														
													</c:if>																
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>																
								</c:if>
							</c:forEach>	
							</ul>							
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 서브네비메뉴1 -->
</div>
