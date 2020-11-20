<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/declaration/dwict.board.declarationCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/declaration/dwict.board.declarationPage.js"></script>

<div id="subCon">	
	<input type="hidden" id="h_cmsCd" name="h_cmsCd" value="${cmsCd}" />	 		
</div>
<div id="boardCon">
	<div class='sub_cleanreport'>	
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>신고서 작성 안내문</div>
		<div class='padding_left_1dot0 fontsize_1dot15 margin_top_1dot0 lineheight_1dot8'>
			<ul class='ul_hyphen1a13'>
				<li>중랑구시설관리공단은 소속 임직원의 비위행위를 익명으로 제보하는 공간으로 이곳을 통한 제보는 감찰, 감사정보 등으로 활용됩니다.</li>
				<li>이 익명신고시스템은 조직의 윤리적 가치와 청렴성을 유지하기 위한 목적으로 설치되었으며, 제보자를 보호하기 위하여 제보자의 IP정보가 남지 않는 보안기술 적용으로 제보자의 익명성을 철저히 보장합니다.</li>
				<li>제보대상은 조직의 윤리적 가치와 청렴성에 반하는 행위나 직원의 고충<span class='fc_red1'>(갑질, 인권침해, 성희롱, 성폭력 포함)</span>을 제보하는 것으로 구체적으로 아래의 신고 유형을 참고하여 주시기 바랍니다.</li>
				<li>내용은 아래 각 항목의 안내 예시를 참조하여 6하 원칙에 따라 구체적으로 명확하게 작성하여 주시고,가급적 증빙자료나 확인방법 등 참고자료를 첨부하여 주시면 사실 확인 등 조치를 좀 더 신속하게 처리할 수 있습니다.</li>
				<li><span class='fc_red1'>아울러 단순 민원에 해당하거나, 대상 및 내용이 불명확한 경우, 근거 없는 비방으로 판단되는 경우에는 제보하여도 접수 및 처리가 불가할 수 있음을 양해바랍니다.</span></li>
			</ul>
		</div>	
		<br>
		<br>				
		<div class='border_top_3_green'></div>
		<table class='stbl_w3b border_top_0' summary='이 표는 제목/내용 등의 정보로 구성된 익명신고 등록/수정 폼입니다.'>
		<caption>익명신고 등록/수정 폼</caption>
		<colgroup>
			<col width='100px'/>
			<col width='*'/>
		</colgroup>
		<tr>
			<th>기관명</th>
			<td>중랑구시설관리공단</td>
		</tr>
		<tr>
			<th>신고유형</th>
			<td>
				<select id='dType' name='dType' class='inputbox_01a inputbox_01_s1' required>
					<option value=''>-- 선택하세요 --</option>
					<option value='1'>1.갑질 피해 신고센터</option>
					<option value='2'>2.직원의 부닥이득 수수행위</option>
					<option value='3'>3.직무수행 저해행위</option>
					<option value='4'>4.성범죄 발생 신고</option>
					<option value='5'>5.인권침해 발생 신고</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type='text' id='dTitle' name='dTitle' value='' maxlength='' required class='inputbox_01a inputbox_01_s3'></td>
		</tr>
		<tr>
			<th>누가</th>
			<td>
				<input type='text' id='dWho' name='dWho' value='' maxlength='' required class='inputbox_01a inputbox_01_s1'>
				<div class='margin_t10 fontsize_0dot90 lineheight_1dot8'>예시) [○○기관 ○○팀 ○○○ 대리] : 모든 관련자를 빠짐없이 기재</div>
			</td>
		</tr>
		<tr>
			<th>언제</th>
			<td>
				<input type='text' id='dWhen' name='dWhen' value='' maxlength='' required class='inputbox_01a inputbox_01_s1'>
				<div class='margin_t10 fontsize_0dot90 lineheight_1dot8'>예시) [2017.07.07(금), 17:00경] 또는 [약 1년전부터, 약 2개월전부터 등]</div>
			</td>
		</tr>
		<tr>
			<th>어디서</th>
			<td>
				<input type='text' id='dWhere' name='dWhere' value='' maxlength='' required class='inputbox_01a inputbox_01_s1'>
				<div class='margin_t10 fontsize_0dot90 lineheight_1dot8'>예시) [○○동 ○○시설 사무실] : 장소를 특정하기 어려운 경우 업무처리과정 기재</div>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<div class='margin_b10 fontsize_0dot90 lineheight_1dot8'>
					신고사항에 관한 상세한 것들을 최대한 구체적으로 빠짐없이 작성해 주십시오.<br>
					<span class='fc_red1'>※ 주의사항 : 신고자의 신분이 드러날 수 있는 내용은 제외하여 기재</span>
				</div>
				<textarea id='dContents' name='dContents' required class='inputbox_01a inputbox_01_s5'></textarea>
			</td>
		</tr>
		<tr>
			<th>이 문제를<br>아는 사람<br>(*중요)</th>
			<td>
				<textarea id='dAcquaintance' name='dAcquaintance' required class='inputbox_01a inputbox_01_s5' style='height:100px;'></textarea>
				<div class='margin_t10 fontsize_0dot90 lineheight_1dot8'>신고내용을 알고 있거나 알 것으로 예상되는 모든 사람들을 기재</div>
			</td>
		</tr>
		<tr>
			<th>이 문제의<br>확인 방법</th>
			<td>
				<textarea id='dConfirm' name='dConfirm' required class='inputbox_01a inputbox_01_s5' style='height:100px;'></textarea>
				<div class='margin_t10 fontsize_0dot90 lineheight_1dot8'>
					신고내용을 중랑구시설관리공단에서 확인하기 위해 가장 좋은 방법을 기재<br>
					예시) [2017.07.07(금) 수입장부와 대조], [○○○ 대리를 상대로 사실 확인]<br>
				</div>
			</td>
		</tr>
		<tr>
			<th>이 문제의<br>파악 경위</th>
			<td class='lineheight_1dot8'>
				<label><input type='radio' name='dInspector' value='1'> 내게 일어난 일이라서</label><br>
				<label><input type='radio' name='dInspector' value='2'> 내가 직접 보거나 들은 일이라서</label><br>
				<label><input type='radio' name='dInspector' value='3'> 직장 동료에게 들었음</label><br>
				<label><input type='radio' name='dInspector' value='4'> 외부인에게 들었음</label><br>
				<label><input type='radio' name='dInspector' value='5'> 소문으로 들었음</label><br>
				<label><input type='radio' name='dInspector' value='6'> 우연히 문서나 파일을 보다가 알게되었음</label><br>
			</td>
		</tr>
		<tr>
			<th>이 문제의<br>지속 기간</th>
			<td class='lineheight_1dot8'>
				<label><input type='radio' name='dTime' value='1'> 한번</label><br>
				<label><input type='radio' name='dTime' value='2'> 일주일</label><br>
				<label><input type='radio' name='dTime' value='3'> 1-3 개월</label><br>
				<label><input type='radio' name='dTime' value='4'> 3개월에서 1년</label><br>
				<label><input type='radio' name='dTime' value='5'> 1년 이상</label><br>
			</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>
				<input type='password' id='dPass' name='dPass' value='' maxlength='' required class='inputbox_01a inputbox_01_s1'>
				<div class='margin_t10 fontsize_0dot90 lineheight_1dot8'>[비밀번호는 숫자 4자리입니다]</div>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type='file' id='file' name='file' multiple class='inputbox_01a inputbox_01_s3'>
				<div class='margin_t10 fontsize_0dot90 lineheight_1dot8'>
					<ul class='ul_hyphen1a13'>
						<li>100MB 이하, 파일이 여러개일경우 압축, 업로드 파일명에 특수 문자 제외</li>
						<li>파일첨부시 파일속성내에 사용자 정보가 포함되지않도록 삭제후 업로드 요망</li>
						<li>해당화일 오른쪽 마우스 클릭 → 속성 → 자세히 → 속성및개인정보제거</li>
					</ul>
				</div>
			</td>
		</tr>
		</table>	
		<br>
		<br>	
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>주의사항</div>
		<div class='padding_left_1dot5 fontsize_1dot15 margin_top_1dot0 lineheight_1dot8'>
			※ 신고서 제출 후에는 <span class='fc_red1'>12자리 고유번호가 부여</span>되며, 고유번호는 재발행 되지 않으므로</span> 반드시 메모하시기 바랍니다.<br>
		</div>	
		<br>
		<br>	
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>이용약관에 대한 동의</div>
		<div class='inputbox_01a wid_100p margin_top_1dot0'>
			중랑구시설관리공단 익명신고 시스템은 조직내부의 문제점에 대하여 신분노출과 불이익의 불안에서 벗어나 용기 있는 신고를 할 수 있도록 익명성과 보안이 보장된 신고 시스템입니다.<br>
			익명신고 시스템을 효율적으로 운영할 수 있도록 아래 이용약관에 따라 신고서를 작성하여 주시기 바랍니다.<br><br>
	
			1. 신고서의 수정, 삭제, 취소<br>
			신고서는 제출과 동시에 해당 담당자에게 실시간으로 전달되므로 제출된 신고내용의 수정, 삭제, 취소는 불가능합니다.<br>
			따라서 신고서 제출 전에 다시 한 번 신고내용과 제출여부를 신중하게 검토한 후에 제출하시기 바랍니다.<br><br>
	
			2. 신고자의 주의사항<br>
			신고자가 스스로 신분을 밝히지 않는 한, 중랑구시설관리공단에 접수되는 모든 익명신고는 비밀과 익명을 보장받습니다.<br>
			신고자 스스로 신고서에 본인의 인적사항을 밝히거나 본인이 누구인지를 짐작할 수 있는 내용을 적시하거나 본인과 관련된 정보가 포함되는 등의<br>
			부주의로 인하여 본인의 신분이 밝혀지고 그로인하여 불이익을 입는 일이 없도록 각별히 주의하시기 바랍니다.<br><br>
	
			3. 처리결과의 확인<br>
			신고서 제출 후에 발급되는 고유번호와 본인이 입력한 비밀번호를 별도로 적어놓고 잘 관리하시기 바랍니다.<br>
			신고 후 진행경과 확인을 위하여 반드시 필요하며 고유번호와 비밀번호는 분실 시 재발급 되지 않습니다.<br><br>
	
			4. 신고내용의 처리<br>
			신고내용이 민원이나 불편사항신고, 불친절신고, 일방적 불만제기 등 익명신고 시스템을 통한 신고대상 행위가 아닌 경우나 불명확한 신고,<br>
			근거 없는 비방, 험담으로 판단되는 경우 등 익명신고 시스템의 운영목적에 위반 되는 경우에는 접수거부, 처리보류 등이 있을 수 있습니다.<br><br>
	
			신고자는 이와 같은 중랑구시설관리공단의 역할을 이해하고, 이용약관상의 주의사항을 유의하여 신고할 것을 고지 받았으며 이에 동의합니다.<br>
		</div>	
		<br>	
		<div>
			<label><input type='checkbox' id='dAgree' name='dAgree' value='Y'> 본 신고서를 제출함에 있어 이용약관에 대하여 동의합니다.</label>
		</div>	
		<div class='bx_btns_01a ali_c'>
			<input type="button" class='size_m2 btn_green1' value='작성완료' onclick="find.proc()">
		</div>		
	</div>
</div>