<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/contract/dwict.board.contract.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/contract/dwict.board.priceDetailPage.js"></script>

<div id="subCon">	
	<input type="hidden" id="h_cmsCd" name="h_cmsCd" value="${cmsCd}" />
	<input type="hidden" id="h_brdNo" name="h_brdNo" value="${brdNo}" />	 		
	<input type="hidden" id="h_ceo" name="h_ceo" value="${ceo}" />	 		
	<input type="hidden" id="h_busiNumber" name="h_busiNumber" value="${busiNumber}" />	 		
	<input type="hidden" id="h_brdPwd" name="h_brdPwd" value="${brdPwd}" />	 		
</div>
<div id="boardCon">
	<div id="sub_find">
		<div>
			<div class="ipt_info margin_b50">
				<div class="inputbox margin_b50">										
					<table class="tb_inputInfo">
						<colgroup>
							<col width="30%">
							<col width="70%">
						</colgroup>
						<tbody>
							<tr>
								<th>대표자<span class="nece">*</span></th>
								<td><input type="text" id="ceo" name="ceo" placeholder="대표자를 입력하세요" onkeyup="enterKey();"></td>
							</tr>
							<tr>
								<th>사업자등록번호<span class="nece">*</span></th>
								<td><input type="text" id="busiNumber" name="busiNumber" placeholder="사업자등록번호를 입력하세요" onkeyup="enterKey();"></td>
							</tr>
							<tr>
								<th>비밀번호<span class="nece">*</span></th>
								<td><input type="password" id="brdPwd" name="brdPwd" placeholder="비밀번호를 입력하세요." onkeyup="enterKey();"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="btnarea">
				<a id="confirm" class="green" onclick="find.proc();">작성완료</a>				
				<a id="confirm" class="gray" href="/price/${cmsCd}">목록</a>				
			</div>			
		</div>
	</div>	
</div>