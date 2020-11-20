<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page language="java" import="Kisinfo.Check.IPINClient" %>

<%
	/********************************************************************************************************************************************
		NICE������ Copyright(c) KOREA INFOMATION SERVICE INC. ALL RIGHTS RESERVED
		
		���񽺸� : IPIN �����ֹι�ȣ���� ����
		�������� : IPIN �����ֹι�ȣ���� ��� ������
	*********************************************************************************************************************************************/
	
	String sSiteCode				= "";			// NICE���������� �߱��� IPIN ���� ����Ʈ�ڵ�
	String sSitePw					= "";			// NICE���������� �߱��� IPIN ���� ����Ʈ�н�����
			
	// ipin_process���� ���޹��� ������� ��ȣȭ ������ ���
	String sResponseData = requestReplace(request.getParameter("enc_data"), "encodeData");    
    
	// ipin_main���� ���ǿ� ������ CP��û��ȣ ���
	String sCPRequest = (String)session.getAttribute("CPREQUEST");   
		
	// ��� ��ü ����
	IPINClient pClient = new IPINClient();
	
	// ������� ������ ��ȣȭ
	// : CP��û��ȣ �Ķ���� �߰� �� ���� ���Ⱚ�� ���۵� ������ ���� ������ ������ �˻� ����
	// ��) int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData, sCPRequest);	
	int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData);

	// ������� ����
	String sRtnMsg					= "";							// ó����� �޼���
	String sVNumber					= pClient.getVNumber();			// �����ֹι�ȣ (13byte, ������ ����)
	String sName					= pClient.getName();			// ���� (EUC-KR)
	String sAgeCode					= pClient.getAgeCode();			// ���ɴ��ڵ� (0~7: ���̵� ����)
	String sGenderCode				= pClient.getGenderCode();		// ���� (0:����, 1: ����)
	String sBirthDate				= pClient.getBirthDate();		// ������� (YYYYMMDD)
	String sNationalInfo			= pClient.getNationalInfo();	// ��/�ܱ����ڵ� (0:������, 1:�ܱ���)
	String sCPRequestNum			= pClient.getCPRequestNO();		// CP ��û��ȣ
	String sDupInfo					= pClient.getDupInfo();			// �ߺ�����Ȯ�ΰ� (64byte, ���νĺ���, DI:Duplicate Info)
			
	// ��ȣȭ ó������ڵ忡 ���� ó��
	if (iRtn == 1)
	{
		/*
			����� ����� ������ '����'�� �̿��ڿ��� ���� �����մϴ�.
		
			����� ������ �ٸ� ���������� �̿��ϴ� ���,
			��ȣȭ ������(sResponseData)�� ���� �� �ش� ���������� ��ȣȭ�ϴ� ���·� �̿����ֽʽÿ�
			
			��ȣȭ�� ����� ������ ������ ���, 
           	 �����Ͱ� ������� �ʵ��� ����ó���� ����帳�ϴ�. (��: CP��û��ȣ�� ���ǿ� ����/����ϴ� ���)
			form�� hidden �±׸� �̿��� ���޹���� ������ ���� ������ �����Ƿ� �������ֽñ� �ٶ��ϴ�.
		*/
				
		out.println("�����ֹι�ȣ : " + sVNumber + "<br>");
		out.println("�̸� : " + sName + "<br>");
		out.println("���ɴ� �ڵ� : " + sAgeCode + "<br>");
		out.println("���� �ڵ� : " + sGenderCode + "<br>");
		out.println("������� : " + sBirthDate + "<br>");
		out.println("��/�ܱ��� ���� : " + sNationalInfo + "<br>");
		out.println("CP ��û��ȣ : " + sCPRequestNum + "<br><br>");
		out.println("�ߺ����� Ȯ�ΰ� (DI) : " + sDupInfo + "<br>");
		out.println("***** ��ȣȭ �� ������ ��Ȯ���� Ȯ���� �ֽñ� �ٶ��ϴ�. *****<br><br><br><br>");
		
		sRtnMsg = "���� ó���Ǿ����ϴ�.";
		
	}
	else if (iRtn == -1 || iRtn == -4)
	{
		sRtnMsg = "��ȣȭ �ý��� ���� :<br> �ͻ� ���� ȯ�濡 �´� ����� �̿����ֽʽÿ�.<br>������ ���ӵǴ� ��� iRtn ��, ���� ȯ������, ����Ʈ�ڵ带 ������ �����ֽñ� �ٶ��ϴ�.";
	}
	else if (iRtn == -6)
	{
		sRtnMsg =	"��ȣȭ ó�� ����: ��翡�� �̿��ϴ� charset�� EUC-KR�� ���������� �޾����� Ȯ�����ֽʽÿ�. <br>������ ���ӵǴ� ���, ���� ���̵��� <b>\"��� ������ Ȯ�� ���\"</b>�� �������ֽñ� �ٶ��ϴ�.";
	}
	else if (iRtn == -9)
	{
		sRtnMsg = "�Է� ���� ����: ��ȣȭ �Լ��� �Էµ� �Ķ���� ���� Ȯ�����ֽʽÿ�.<br>������ ���ӵǴ� ���, �Լ� ���� ���� �� �Ķ���� ���� �α׷� ����� �߼����ֽñ� �ٶ��ϴ�.";
	}
	else if (iRtn == -12)
	{
		sRtnMsg = "CP �н����� ����ġ: IPIN ���� ����Ʈ�н����带 Ȯ�����ֽñ� �ٶ��ϴ�.";
	}
	else if (iRtn == -13)
	{
		sRtnMsg = "CP ��û��ȣ ����ġ: ���ǿ� ����� CP��û��ȣ(sCPRequest) ���� Ȯ�����ֽñ� �ٶ��ϴ�.";
	}
	else
	{
		sRtnMsg = "��Ÿ����: iRtn �� Ȯ�� �� NICE������ ���� ����ڿ��� �������ֽñ� �ٶ��ϴ�.";
	}
%>
<%!
	// ���ڿ� ���� �Լ�
	public String requestReplace (String paramValue, String gubun) {
        String result = "";
        
        if (paramValue != null) {
        	
        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");
        	
        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
        	}        	
        	result = paramValue;            
        }
        return result;
  }
%>

<html>
<head>
	<meta charset="EUC-KR">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>NICE������ �����ֹι�ȣ ����</title>		
	<style type="text/css"> 
		body
		{
			color: #7f7f7f;
			font-family: "Dotum","DotumChe","Arial";
			background-color: #ffffff;
		}
	</style>
</head>

<body>
	iRtn [<%= iRtn %>] - <%= sRtnMsg %><br><br>

    <!-- ������� form -->
    <form name="form_ipin_result" method="post">
		<!-- ������� ��ȣȭ ������ -->
		<input type="hidden" name="enc_data" value="<%= sResponseData %>"><br>
	</form>
</body>
</html>