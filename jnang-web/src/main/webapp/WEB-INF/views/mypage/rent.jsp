<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/rent.js"></script>
<script type="text/javascript">

</script>

<div id="sub_cart" class=' '>
	<form name="frm" method="post">
	<table class="stbl_l1a">
		<colgroup>
			<col width="5%"><col width="5%"><col width="*"><col width="100"><col width="100"><col width="100"><col width="100">
		</colgroup>
		<thead>
			<tr>
				<th>No.</th>
				<th>취소</th>
				<th>대관장소</th>
				<th>대관료</th>
				<th>대관시작일</th>
				<th>대관종료일</th>
				<th>확정여부</th>
				<th>접수일</th>
			</tr>
		</thead>
		<tbody id="dataList">
			<c:forEach items="${rentList}" var="result" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>
					<c:if test="${result.APP_TYPE == '10'}">	
					<input type="checkbox" id="cbox" name="cbox[]" value="${result.RENT_IDX}" style='width:30px; height:30px;'></td>
					</c:if>
				<td>${result.COMNM}  &nbsp; > &nbsp;  ${result.PLACE_NM}
					<c:if test="${result.OBJECT ne null}">
						&nbsp; ( ${result.OBJECT} )
					</c:if>
					<c:if test="${result.item ne ''}">
						&nbsp; / ( ${result.item} ) 시간   
					</c:if>
					<c:if test="${result.place_tab ne 0}">
						&nbsp; / ( ${result.place_tab} ) 코트
					</c:if>
					<c:if test="${result.add_light ne 0}">
						&nbsp; / 조명 사용
					</c:if>
				</td>
				<td>${result.PAY_AMT}</td>
				<td>${result.START_DATE}</td>
				<td>${result.END_DATE}</td>
				<td>${result.APP_TYPE_NM}</td>
				<td>${result.CHNG_DH}</td>
			</tr>
			</c:forEach>
		</tbody>

	</table>
	</form>
    <div class="btnarea margin_t80">
        <a href="#none" onClick="data.list('del');" id=" " class="gray2">선택 대관 취소</a>
    </div>
</div>
