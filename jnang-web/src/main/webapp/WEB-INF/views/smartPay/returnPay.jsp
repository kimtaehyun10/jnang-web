<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
<!--
table.type {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
}
table.type thead th {
    padding: 8px;
    font-weight: bold;
    vertical-align: top;
    color: #369;
    border-bottom: 3px solid #036;
    text-align:center;
}
table.type tbody th {
    width: 150px;
    padding: 8px;
    font-weight: bold;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
    background: #f3f6f7;
    text-align:center;
}
table.type td {
    width: 250px;
    padding: 8px;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
    text-align:center;
}

.selectbox {
  position: relative;
  width: auto;
  border: 1px solid #999;
  z-index: 1;
  padding: 4px;
  color: #369;
}

input {
    width: 100%;
    padding: 6px 10px;
    margin: 2px 0;
    box-sizing: border-box;
}

.btnblue {
    background-color: #6DA2D9;
    box-shadow: 0 0 0 1px #6698cb inset,
                0 0 0 2px rgba(255,255,255,0.15) inset,
                0 4px 0 0 rgba(110, 164, 219, .7),
                0 4px 0 1px rgba(0,0,0,.4),
                0 4px 4px 1px rgba(0,0,0,0.5);
}

-->
</style>
<script type="text/javascript">

function gtnUrl(val1) {

	opener.location.href='/mypage/classStatus';
	self.close();
}

</script>
	<table class="type">
		<thead>
			<tr>
				<th colspan="3">[결제 결과]</th>
			</tr>
			<tr>
				<th scope="cols">항목명</th>
				<th scope="cols">파라미터</th>
				<th scope="cols">입력</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th scope="row">지불수단</th>
				<td>PayMethod</td>
				<td><strong><c:out value="${rtnMap.PayMethod}"/><%//=PayMethod%></strong></td>
			</tr>
			<tr>
				<th scope="row">상점ID
					</td>
					<td>MID</td>
					<td><strong><c:out value="${rtnMap.MID}"/><%//=MID%></strong></td>
			</tr>
			<tr>
				<th scope="row">금액
					</td>
					<td>Amt</td>
					<td><strong><c:out value="${rtnMap.Amt}"/><%//=Amt%>원</strong></td>
			</tr>
			<tr>
				<th scope="row">결제자명
					</td>
					<td>BuyerName</td>
					<td><c:out value="${rtnMap.BuyerNameDe}"/><%//=BuyerName%></strong></td>
			</tr>
			<tr>
				<th scope="row">상품명
					</td>
					<td>GoodsName</td>
					<td><c:out value="${rtnMap.GoodsNameDe}"/><strong><%//=GoodsName%></strong></td>
			</tr>
			<tr>
				<th scope="row">고객사회원ID
					</td>
					<td>mallUserID</td>
					<td><strong><c:out value="${rtnMap.mallUserID}"/><%//=mallUserID%></strong></td>
			</tr>
			<tr>
				<th scope="row">거래고유번호
					</td>
					<td>TID</td>
					<td><strong><c:out value="${rtnMap.TID}"/><%//=TID%></strong></td>
			</tr>
			<tr>
				<th scope="row">주문번호
					</td>
					<td>OID</td>
					<td><strong><c:out value="${rtnMap.OID}"/><%//=OID%></strong></td>
			</tr>
			<tr>
				<th scope="row">승인일자
					</td>
					<td>AuthDate</td>
					<td><strong><c:out value="${rtnMap.AuthDate}"/><%//=AuthDate%></strong></td>
			</tr>
			<tr>
				<th scope="row">승인번호 (AuthCode)
					</td>
					<td>AuthCode</td>
					<td><c:out value="${rtnMap.AuthCode}"/><%//=AuthCode%></td>
			</tr>
			<tr>
				<th scope="row">결과코드
					</td>
					<td>ResultCode</td>
					<td><strong><c:out value="${rtnMap.ResultCode}"/><%//=ResultCode%></strong></td>
			</tr>
			<tr>
				<th scope="row">결과메시지
					</td>
					<td>ResultMsg</td>
					<td><strong><c:out value="${rtnMap.ResultMsgDe}"/><%//=ResultMsg%></strong></td>
			</tr>
			<tr>
				<th scope="row">가상계좌번호
					</td>
					<td>VbankNum</td>
					<td><strong><c:out value="${rtnMap.VbankNum}"/><%//=VbankNum%></strong></td>
			</tr>
			<tr>
				<th scope="row">가상계좌은행명
					</td>
					<td>VbankName</td>
					<td><strong><%//=VbankName%></strong></td>
			</tr>
			<tr>
				<th scope="row">결제카드사코드
					</td>
					<td>fn_cd</td>
					<td><strong><c:out value="${rtnMap.xxxx}"/><%//=fn_cd%></strong></td>
			</tr>
			<tr>
				<th scope="row">결제카드사명
					</td>
					<td>fn_name</td>
					<td><strong><c:out value="${rtnMap.fn_name}"/><%//=fn_name%></strong></td>
			</tr>
			<tr>
				<th scope="row">할부개월수
					</td>
					<td>CardQuota</td>
					<td><strong><c:out value="${rtnMap.CardQuota}"/><%//=CardQuota%></strong></td>
			</tr>
			<tr>
				<th scope="row">구매자전화번호
					</td>
					<td>BuyerTel</td>
					<td><strong><c:out value="${rtnMap.BuyerTel}"/><%//=BuyerTel%></strong></td>
			</tr>
			<tr>
				<th scope="row">구매자이메일주소
					</td>
					<td>BuyerEmail</td>
					<td><strong><c:out value="${rtnMap.BuyerEmail}"/><%//=BuyerEmail%></strong></td>
			</tr>
			<tr>
				<th scope="row">구매자주민번호
					</td>
					<td>BuyerAuthNum</td>
					<td><strong><c:out value="${rtnMap.BuyerAuthNum}"/><%//=BuyerAuthNum%></strong></td>
			</tr>
			<tr>
				<th scope="row">현금영수증유형
					</td>
					<td>ReceiptType</td>
					<td><strong><c:out value="${rtnMap.ReceiptType}"/><%//=ReceiptType%></strong></td>
			</tr>
			<tr>
				<th scope="row">위변조 사인값
					</td>
					<td>SignValue</td>
					<td><strong><c:out value="${rtnMap.SignValue}"/><%//=SignValue%></strong></td>
			</tr>
			<tr>
				<th scope="row">TAX 코드
					</td>
					<td>TaxCD</td>
					<td><strong><c:out value="${rtnMap.TaxCD}"/><%//=TaxCD%></strong></td>
			</tr>
			<tr>
				<th scope="row">봉사료
					</td>
					<td>SvcAmt</td>
					<td><strong><c:out value="${rtnMap.SvcAmt}"/><%//=SvcAmt%></strong></td>
			</tr>
			<tr>
				<th scope="row">부가세
					</td>
					<td>Tax</td>
					<td><strong><c:out value="${rtnMap.Tax}"/><%//=Tax%></strong></td>
			</tr>
			<tr>
				<th scope="row">매입사코드
					</td>
					<td>AcquCardCode</td>
					<td><strong><c:out value="${rtnMap.AcquCardCode}"/><%//=AcquCardCode%></strong></td>
			</tr>
			
			<tr>
				<th scope="row">검증 사인값
					</td>
					<td>VerifySignValue</td>
					<td><strong><c:out value="${rtnMap.VerifySignValue}"/><%//=VerifySignValue%></strong></td>
			</tr>

			<tr>
				<th scope="row">서브몰 정보
				</td>
				<td>DivideInfo</td>
				<td><strong><%//=DivideInfo%></strong></td>
			</tr>

			<tr>
				<td class="btnblue" colspan="3" onclick="gtnUrl();">닫기</td>
			</tr>

		</tbody>

	</table>
</body>
</html>
<%
/* public String getDefaultStr(String src, String defaultStr) {
		if (src == null || src.length() < 1) {
			if (defaultStr == null) {
				return "";
			}
			return defaultStr;
		}
		return src;
	}

	public static final String encodeMD5HexBase64(String pw) {
		return new String(
				Base64.encodeBase64(DigestUtils.md5Hex(pw).getBytes()));
	} */
%>