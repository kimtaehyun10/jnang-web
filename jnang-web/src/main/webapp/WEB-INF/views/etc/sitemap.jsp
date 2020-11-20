<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/etc/sitemap.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id='sub_sitemap'>
<c:forEach items="${menuList}" var="depth1MenuList">
	<c:if test="${depth1MenuList.DEP eq '1'}">
	<ul>
	<h4>${depth1MenuList.CMS_NM}</h4>
	<c:forEach items="${menuList}" var="depth2MenuList">
		<c:if test="${depth2MenuList.DEP eq '2' and depth1MenuList.CMS_CD eq depth2MenuList.UP_CMS_CD}">														
			<c:set var="doneLoop2" value="false"/>
			<c:forEach items="${menuList}" var="depth3MenuList">
				<c:if test="${depth3MenuList.DEP eq '3' and depth2MenuList.CMS_CD eq depth3MenuList.UP_CMS_CD}">						
					<c:if test="${not doneLoop2}">																						
						<c:if test="${depth3MenuList.MTYPE eq 'HTM'}">
							<c:choose>
								<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																																																																			
									<li><a href='/html/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList.CMS_NM}</a></li>
								</c:when>
								<c:otherwise>																	
									<li><a href='/html/${depth3MenuList.CMS_CD}'>${depth2MenuList.CMS_NM}</a></li>
								</c:otherwise>
							</c:choose>							
						</c:if>
						<c:if test="${depth3MenuList.MTYPE eq 'BRD' or depth3MenuList.MTYPE eq 'NTC' or depth3MenuList.MTYPE eq 'FAQ'}">
							<c:choose>
								<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																																																																												
									<li><a href='/board/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList.CMS_NM}</a></li>
								</c:when>
								<c:otherwise>																	
									<li><a href='/board/${depth3MenuList.CMS_CD}'>${depth2MenuList.CMS_NM}</a></li>
								</c:otherwise>
							</c:choose>							
						</c:if>
						<c:if test="${depth3MenuList.MTYPE eq 'EMP'}">
							<c:choose>
								<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																																																																																					
									<li><a href='/emp/${depth3MenuList.CMS_CD}' target='_blank'>${depth2MenuList.CMS_NM}</a></li>
								</c:when>
								<c:otherwise>																	
									<li><a href='/emp/${depth3MenuList.CMS_CD}'>${depth2MenuList.CMS_NM}</a></li>
								</c:otherwise>
							</c:choose>							
						</c:if>
						<c:if test="${depth3MenuList.MTYPE eq 'LNK'}">
							<c:choose>
								<c:when test="${depth3MenuList.WDP_YN eq 'Y'}">																																																																																																																																																																																																																																																																					
									<li><a href='${depth3MenuList.CONT}' target='_blank'>${depth2MenuList.CMS_NM}</a></li>
								</c:when>
								<c:otherwise>																	
									<li><a href='${depth3MenuList.CONT}'>${depth2MenuList.CMS_NM}</a></li>
								</c:otherwise>
							</c:choose>							
						</c:if>
					</c:if>
					<c:set var="doneLoop2" value="true"/>																			
				</c:if>
			</c:forEach>							
		</c:if>
	</c:forEach>
	</ul>																
	</c:if>
</c:forEach>
</div>