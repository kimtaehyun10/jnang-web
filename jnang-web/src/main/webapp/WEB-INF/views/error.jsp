<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sub_cont1">
	<div class="con_bx">
		<div id="error404" class="error">
			<h3 class="margin_t50 margin_b20"><c:out value="${error.STATUS_CODE}"></c:out></h3>
			<p class="margin_b60">${error.MESSAGE}</p>
			<div class="btnarea">
				<a href="/" id="gotomain" class="blue">메인으로</a>
			</div>
		</div>
	</div>
</div>