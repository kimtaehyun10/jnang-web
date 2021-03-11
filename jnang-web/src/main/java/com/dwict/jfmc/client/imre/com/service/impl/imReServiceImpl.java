
package com.dwict.jfmc.client.imre.com.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.dwict.jfmc.client.imre.com.util.Client;
import com.dwict.jfmc.client.imre.com.util.NewGpkiUtil;
import com.dwict.jfmc.client.imre.com.util.ShareGpki;
import com.dwict.jfmc.client.imre.com.mapper.ImreMapper;
import com.dwict.jfmc.client.imre.com.model.DiscountItemVO;
import com.dwict.jfmc.client.imre.com.service.imReService;


/**
 * The type Common service.
 */
@Service("imreService")
public class imReServiceImpl implements imReService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(imReServiceImpl.class);
    
    //테스트 플래그    
    @Value("#{appConfig['ImreGlobals.testflag']}")
	private String TEST_FLAG;
    
	@Resource(name="imreMapper")
	private ImreMapper imreMapper;

    //할인항목 조회
	@Override
	public List<HashMap<String, Object>> selectDisItemList(Map requestMap) {
		List<HashMap<String, Object>> result;
		result = imreMapper.selectDisItemList(requestMap);
		
		return result;
	}

	@Override
	public Map<String, String> mainService(Map requestMap, DiscountItemVO discountItemVO) {
		Map<String, String> resultMap = new HashMap<String,String>();

		  
		//DCITMKEY_00000000018 518민주유공자 (0) [0]
		//DCITMKEY_00000000017 거주자   (0) [-]
		//DCITMKEY_00000000001 경차     (28) [0]
		//DCITMKEY_00000000007 고엽제자동차  -- (0) [0]       
		//DCITMKEY_00000000020 공동이용자동차 (x)
		//DCITMKEY_00000000003 국가유공상이자 -- (0)  [0]
		//DCITMKEY_00000000006 다둥이(두자녀) --   (0)
		//DCITMKEY_00000000005 다둥이(세자녀이상) -- 	(0)
		//DCITMKEY_00000000008 모범납세자   --    (0)
		//DCITMKEY_00000000009 병역명문가     --     (0)
		//DCITMKEY_00000000019 상근자   (x)       
		//DCITMKEY_00000000011 영업용차량    (x) 
		//DCITMKEY_00000000010 의사상자및가족    (30) <-- 확인필요
		//DCITMKEY_00000000002 장애인    --  <-- 요청기관코드, 요청업무코드 확인필요 [0]
		//DCITMKEY_00000000004 저공해    -- (0)    
		//DCITMKEY_00000000021 기초 수급자 -- (0)
		
		// 할인항목별 즉시감면 대상자 확인
  		if(discountItemVO.getArrDiscountItemKey() != null){
  			  			
  			for (int i = 0; i < discountItemVO.getArrDiscountItemKey().length; i++) {
  				
  				String tmpvalue = discountItemVO.getArrDiscountItemKey()[i];
  				String targetid = discountItemVO.getArrDiscountItemId()[i];
  				
  				//국가유공상이자
  				if (tmpvalue.equals("DCITMKEY_00000000003")){
  					resultMap.putAll(getNationalMerit1(requestMap,targetid));
  					
  				}

  				//20200613 lsh 할인적용하지 않음
  				//518민주유공자 
  				//if (tmpvalue.equals("DCITMKEY_00000000018")){
  				//	resultMap.putAll(getNationalMerit2(requestMap));
  				//}

  				//20200613 lsh 국가유공상이자와 병합
  				//고엽제자동차
  				//if (tmpvalue.equals("DCITMKEY_00000000007")){
  				//	resultMap.putAll(getNationalMerit3(requestMap));
  				//}
  				
  				//거주자
  				//if (tmpvalue.equals("DCITMKEY_00000000017")){
  				//	resultMap.putAll(getResident(requestMap,targetid));
  				//}

  				//다둥이(세자녀이상)
  				if (tmpvalue.equals("DCITMKEY_00000000005")){
  					resultMap.putAll(getCountChildren1(requestMap,targetid));
  				}
  				
  				//다둥이(두자녀)
  				if (tmpvalue.equals("DCITMKEY_00000000006")){
  					resultMap.putAll(getCountChildren2(requestMap,targetid));
  				}
  				
  				//모범납세자 
  				//if (tmpvalue.equals("DCITMKEY_00000000008")){
  				//	resultMap.putAll(getGreateTax(requestMap,targetid));
  				//}
  				
  				//병역명분가
  				if (tmpvalue.equals("DCITMKEY_00000000009")){
  					resultMap.putAll(getGreateArmy(requestMap,targetid));
  				}
  				
  				//장애인
  				if (tmpvalue.equals("DCITMKEY_00000000002")){
  					resultMap.putAll(getHandicap(requestMap,targetid));
  				}

  				//저공해
  				//if (tmpvalue.equals("DCITMKEY_00000000004")){
  				//	resultMap.putAll(getLowCar(requestMap,targetid));
  				//}
  				
  				//경차
  				//if (tmpvalue.equals("DCITMKEY_00000000001")){
  				//	resultMap.putAll(reductionCar(requestMap,targetid));
  				//}

  				//기초수급자
  				if (tmpvalue.equals("DCITMKEY_00000000021")){
  					resultMap.putAll(getBasePerson(requestMap,targetid));
  				}
  				
  				
  				
  			}
  			
  		}
  		
		return resultMap;
	}

	@Override
	//국가유공상이자[DCITMKEY_00000000003]
	public Map<String, String> getNationalMerit1(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		
		String xml = null;

		//1.request xml 본문 생성
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("		<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"> 		 \n");
		sb.append("		   <Header>				 \n");
		sb.append("		      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">				 \n");
		sb.append("		         <serviceName>NaManMeritFamInfoService</serviceName>				 \n");
		sb.append("		         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>				 \n");
		sb.append("		         <certServerId>#CERT_SERVER_ID#</certServerId>				 \n");
		sb.append("		         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>				 \n");
		sb.append("		         <userDeptCode></userDeptCode>				 \n");
		sb.append("		         <userName></userName>				 \n");
		sb.append("		      </commonHeader>				 \n");
		sb.append("		   </Header>				 \n");
		sb.append("		   <Body>				 \n");
		sb.append("		      <getNaManMeritFamInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">				 \n");
		sb.append("		          <juminNo>" + jumiNo + "</juminNo> 				 \n");		
		sb.append("		      </getNaManMeritFamInfo>				 \n");
		sb.append("		   </Body>				 \n");
		sb.append("		</Envelope>				 \n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/nrw/infoservice/mpva/NaManMeritFamInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1312286006";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getNaManMeritFamInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">")[1]
					.split("</getNaManMeritFamInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);


			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";
		
		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getNaManMeritFamInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">")[1]
					.split("</getNaManMeritFamInfoResponse>")[0];


			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			dcriptMsg = responseMsg.replace(responseEncData, decrypted);

			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
			
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "	<soap:Header>";
			dcriptMsg = dcriptMsg + "		<commonHeader";
			dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			dcriptMsg = dcriptMsg + "			<serviceName>NaManMeritFamInfoService</serviceName>";
			dcriptMsg = dcriptMsg + "			<useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "			<certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "			<transactionUniqueId>2018012217473952889982181</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "			<userDeptCode></userDeptCode>";
			dcriptMsg = dcriptMsg + "			<userName>***</userName>";
			dcriptMsg = dcriptMsg + "		</commonHeader>";
			dcriptMsg = dcriptMsg + "	</soap:Header>";
			dcriptMsg = dcriptMsg + "	<soap:Body>";
			dcriptMsg = dcriptMsg + "		<getNaManMeritFamInfoResponse";
			dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			dcriptMsg = dcriptMsg + "			<cnt>2</cnt>";
			dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "				<resName>이수현</resName>";
			dcriptMsg = dcriptMsg + "				<resSecrNum>8708021063615</resSecrNum>";
			dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			dcriptMsg = dcriptMsg + "				<subjKbnCd>00004</subjKbnCd>";
			dcriptMsg = dcriptMsg + "				<wondClassCd>90001</wondClassCd>";
			dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "				<resName>김수현</resName>";
			//dcriptMsg = dcriptMsg + "				<resSecrNum>9708021063615</resSecrNum>";
			//dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			//dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			//dcriptMsg = dcriptMsg + "				<subjKbnCd>00017</subjKbnCd>";
			//dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			//dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			//dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			//dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "				<resName>박수현</resName>";
			//dcriptMsg = dcriptMsg + "				<resSecrNum>9708021063615</resSecrNum>";
			//dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			//dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			//dcriptMsg = dcriptMsg + "				<subjKbnCd>00023</subjKbnCd>";
			//dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			//dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			//dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			//dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "		</getNaManMeritFamInfoResponse>";
			dcriptMsg = dcriptMsg + "	</soap:Body>";
			dcriptMsg = dcriptMsg + "</soap:Envelope>";

			//dcriptMsg = dcriptMsg + "	<soap:Body>";
			//dcriptMsg = dcriptMsg + "		<getNaManMeritFamInfoResponse";
			//dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			//dcriptMsg = dcriptMsg + "			<cnt>0</cnt>";
			//dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "				<resName>이수현</resName>";
			//dcriptMsg = dcriptMsg + "				<resSecrNum>8708021063615</resSecrNum>";
			//dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			//dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			//dcriptMsg = dcriptMsg + "				<subjKbnCd>00004</subjKbnCd>";
			//dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			//dcriptMsg = dcriptMsg + "				<inquRsltCd>N</inquRsltCd>";
			//dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			//dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "		</getNaManMeritFamInfoResponse>";
			//dcriptMsg = dcriptMsg + "	</soap:Body>";
			//dcriptMsg = dcriptMsg + "</soap:Envelope>";

			//dcriptMsg = dcriptMsg + "<soap:Body>";
			//dcriptMsg = dcriptMsg + "    <soap:Fault>";
			//dcriptMsg = dcriptMsg + "      <faultcode>soap:Server</faultcode>";
			//dcriptMsg = dcriptMsg + "      <faultstring>Fault occured</faultstring>";
			//dcriptMsg = dcriptMsg + "    </soap:Fault>";
			//dcriptMsg = dcriptMsg + "  </soap:Body>";
			//dcriptMsg = dcriptMsg + " </soap:Envelope>";

		}
		
		//6.자료검출
		//국가유공상이자 코드
		//전상군경(00004), 공상군경(00006), 4ㆍ19혁명부상자(00011), 공상공무원(00014), 국가사회발전 특별공로상이자(이하 "특별공로상이자"라 한다)(00016)	
		//0023(고엽제후유의증),0024(6.18자유상이자)
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("cnt",element).equals("0")){
            		returnMap.put(targetid,"해당 사항이 없습니다.");
                    //LOGGER.info("cnt : " + getTagValue("cnt",element));
            	}else{
                    NodeList list = element.getElementsByTagName("meritFamInfoBody");
                    boolean tmpchk1 = false;
            		
            		for (int i=0; i<list.getLength(); i++){
            			Node node = list.item(i);
            			
            			if (node.getNodeType() == Node.ELEMENT_NODE){
            				Element eElement = (Element)node;
            				
            				//본인 OR 배우자 OR 수권자
            				if (getTagValue("inquRsltCd",eElement).equals("S") && (getTagValue("authoriPsnYn",eElement).equals("Y") || getTagValue("relCd",eElement).equals("A") || getTagValue("relCd",eElement).equals("B")))
            				{
            					//모든 국가유공자 true 처리
            					returnMap.put(targetid,"1");
            					tmpchk1 = true;
            					
            						//국가유공자 분기처리
            						/*
            						//전상군경(00004), 공상군경(00006), 4ㆍ19혁명부상자(00011), 공상공무원(00014), 국가사회발전 특별공로상이자(이하 "특별공로상이자"라 한다)(00016)	
            						//00024(6.18자유상이자)
            						if (getTagValue("subjKbnCd",eElement).equals("00004") || getTagValue("subjKbnCd",eElement).equals("00006")
            								|| getTagValue("subjKbnCd",eElement).equals("00011") || getTagValue("subjKbnCd",eElement).equals("00014")
            								|| getTagValue("subjKbnCd",eElement).equals("00016")|| getTagValue("subjKbnCd",eElement).equals("00024")
            								){
            							//장애급수(90001,90002,90003,90004,90090)는 미해당
            							if (getTagValue("wondClassCd",eElement).equals("90001") || getTagValue("wondClassCd",eElement).equals("90002")
            									 || getTagValue("wondClassCd",eElement).equals("90003") || getTagValue("wondClassCd",eElement).equals("90004")
            									  || getTagValue("wondClassCd",eElement).equals("90090")
            									){
            								//대상자 아님
            							}else{
                							returnMap.put(targetid,"1");
                        					tmpchk1 = true;
            							}
            						}
            						
            						//00023(고엽제후유의증)            						
            						if (getTagValue("subjKbnCd",eElement).equals("00023")){
            							//장애급수(90002,90003,90004,90090)는 미해당
            							if (getTagValue("wondClassCd",eElement).equals("90002") || getTagValue("wondClassCd",eElement).equals("90003")
            									|| getTagValue("wondClassCd",eElement).equals("90004") || getTagValue("wondClassCd",eElement).equals("90090")
            									){
            								//대상자 아님
            							}else{
                							returnMap.put(targetid,"1");
                        					tmpchk1 = true;
            							}
            						}
            					
            	                //LOGGER.info("subjKbnCd : " + getTagValue("subjKbnCd",element));
            	                
            					//LOGGER.info("resName : " + getTagValue("resName",eElement));
                				//LOGGER.info("resSecrNum : " + getTagValue("resSecrNum",eElement));
                				//LOGGER.info("relCd : " + getTagValue("relCd",eElement));
                				//LOGGER.info("authoriPsnYn : " + getTagValue("authoriPsnYn",eElement));
                				//LOGGER.info("subjKbnCd : " + getTagValue("subjKbnCd",eElement));
                				//LOGGER.info("wondClassCd : " + getTagValue("wondClassCd",eElement));
                				//LOGGER.info("inquRsltCd : " + getTagValue("inquRsltCd",eElement));
                				//LOGGER.info("validDate : " + getTagValue("validDate",eElement));
            					*/	
            				}	
            			}
            		}
            		
            		if (!tmpchk1) {
            			returnMap.put(targetid,"해당 사항이 없습니다.");
            		}
            	}	
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }

		return returnMap;
	}
	

	@Override
	//국가유공자차량[DCITMKEY_00000000003] <-상이자와 동일한 코드 사용
	public Map<String, String> getNationalMerit1Car(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String carnum = (String)requestMap.get("carNumber");
		
		String xml = null;

		//1.request xml 본문 생성
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("		<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"> 		 \n");
		sb.append("		   <Header>				 \n");
		sb.append("		      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">				 \n");
		sb.append("		         <serviceName>MeritCarYnService</serviceName>				 \n");
		sb.append("		         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>				 \n");
		sb.append("		         <certServerId>#CERT_SERVER_ID#</certServerId>				 \n");
		sb.append("		         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>				 \n");
		sb.append("		         <userDeptCode></userDeptCode>				 \n");
		sb.append("		         <userName></userName>				 \n");
		sb.append("		      </commonHeader>				 \n");
		sb.append("		   </Header>				 \n");
		sb.append("		   <Body>				 \n");
		sb.append("		      <getMeritCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">				 \n");
		sb.append("		          <carNo>" + carnum + "</carNo> 				 \n");		
		sb.append("		      </getMeritCarYn>				 \n");
		sb.append("		   </Body>				 \n");
		sb.append("		</Envelope>				 \n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/rid/ynservice/mpva/MeritCarYnService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1312286006";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getMeritCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">")[1].split("</getMeritCarYn>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);


			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";
		
		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getMeritCarYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">")[1]
					.split("</getMeritCarYnResponse>")[0];

			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			dcriptMsg = responseMsg.replace(responseEncData, decrypted);

			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
			
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "	<soap:Header>";
			dcriptMsg = dcriptMsg + "		<commonHeader";
			dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			dcriptMsg = dcriptMsg + "			<serviceName>NaManMeritFamInfoService</serviceName>";
			dcriptMsg = dcriptMsg + "			<useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "			<certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "			<transactionUniqueId>2018012217473952889982181</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "			<userDeptCode></userDeptCode>";
			dcriptMsg = dcriptMsg + "			<userName>***</userName>";
			dcriptMsg = dcriptMsg + "		</commonHeader>";
			dcriptMsg = dcriptMsg + "	</soap:Header>";
			dcriptMsg = dcriptMsg + "	<soap:Body>";
			dcriptMsg = dcriptMsg + "		<getMeritCarYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">";
			dcriptMsg = dcriptMsg + "			<resultCd>Y</resultCd>";
			dcriptMsg = dcriptMsg + "			<resultMsg></resultMsg>";
			dcriptMsg = dcriptMsg + "		</getMeritCarYnResponse>";
			dcriptMsg = dcriptMsg + "	</soap:Body>";
			dcriptMsg = dcriptMsg + "</soap:Envelope>";

		}
		
		//6.자료검출
		//국가유공자차량
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{

                LOGGER.info("resultCd : " + getTagValue("resultCd",element));
                if (getTagValue("resultCd",element).equals("Y")){
    				returnMap.put(targetid,"1");
                }else{
                	if (ntoS(getTagValue("resultMsg",element)).equals("ERR11")){
                		returnMap.put(targetid,"필수입력정보 누락");
                	}else{
                		returnMap.put(targetid,"해당 사항이 없습니다.");
                	}
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }

		return returnMap;
	}
	
	@Override
	//518민주유공자[DCITMKEY_00000000018]
	public Map<String, String> getNationalMerit2(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		
		String xml = null;

		//1.request xml 본문 생성
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("		<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"> 		 \n");
		sb.append("		   <Header>				 \n");
		sb.append("		      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">				 \n");
		sb.append("		         <serviceName>NaManMeritFamInfoService</serviceName>				 \n");
		sb.append("		         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>				 \n");
		sb.append("		         <certServerId>#CERT_SERVER_ID#</certServerId>				 \n");
		sb.append("		         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>				 \n");
		sb.append("		         <userDeptCode></userDeptCode>				 \n");
		sb.append("		         <userName></userName>				 \n");
		sb.append("		      </commonHeader>				 \n");
		sb.append("		   </Header>				 \n");
		sb.append("		   <Body>				 \n");
		sb.append("		      <getNaManMeritFamInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">				 \n");
		sb.append("		          <juminNo>" + jumiNo + "</juminNo> 				 \n");		
		sb.append("		      </getNaManMeritFamInfo>				 \n");
		sb.append("		   </Body>				 \n");
		sb.append("		</Envelope>				 \n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/nrw/infoservice/mpva/NaManMeritFamInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1312286006";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getNaManMeritFamInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">")[1]
					.split("</getNaManMeritFamInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);


			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";
		
		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getNaManMeritFamInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">")[1]
					.split("</getNaManMeritFamInfoResponse>")[0];


			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			dcriptMsg = responseMsg.replace(responseEncData, decrypted);

			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
			
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "	<soap:Header>";
			dcriptMsg = dcriptMsg + "		<commonHeader";
			dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			dcriptMsg = dcriptMsg + "			<serviceName>NaManMeritFamInfoService</serviceName>";
			dcriptMsg = dcriptMsg + "			<useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "			<certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "			<transactionUniqueId>2018012217473952889982181</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "			<userDeptCode></userDeptCode>";
			dcriptMsg = dcriptMsg + "			<userName>***</userName>";
			dcriptMsg = dcriptMsg + "		</commonHeader>";
			dcriptMsg = dcriptMsg + "	</soap:Header>";
			dcriptMsg = dcriptMsg + "	<soap:Body>";
			dcriptMsg = dcriptMsg + "		<getNaManMeritFamInfoResponse";
			dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			dcriptMsg = dcriptMsg + "			<cnt>2</cnt>";
			dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "				<resName>이수현</resName>";
			dcriptMsg = dcriptMsg + "				<resSecrNum>8708021063615</resSecrNum>";
			dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			dcriptMsg = dcriptMsg + "				<subjKbnCd>00004</subjKbnCd>";
			dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "				<resName>김수현</resName>";
			dcriptMsg = dcriptMsg + "				<resSecrNum>9708021063615</resSecrNum>";
			dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			dcriptMsg = dcriptMsg + "				<subjKbnCd>00017</subjKbnCd>";
			dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "				<resName>박수현</resName>";
			dcriptMsg = dcriptMsg + "				<resSecrNum>9708021063615</resSecrNum>";
			dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			dcriptMsg = dcriptMsg + "				<subjKbnCd>00023</subjKbnCd>";
			dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "		</getNaManMeritFamInfoResponse>";
			dcriptMsg = dcriptMsg + "	</soap:Body>";
			dcriptMsg = dcriptMsg + "</soap:Envelope>";

			//dcriptMsg = dcriptMsg + "	<soap:Body>";
			//dcriptMsg = dcriptMsg + "		<getNaManMeritFamInfoResponse";
			//dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			//dcriptMsg = dcriptMsg + "			<cnt>0</cnt>";
			//dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "				<resName>이수현</resName>";
			//dcriptMsg = dcriptMsg + "				<resSecrNum>8708021063615</resSecrNum>";
			//dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			//dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			//dcriptMsg = dcriptMsg + "				<subjKbnCd>00004</subjKbnCd>";
			//dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			//dcriptMsg = dcriptMsg + "				<inquRsltCd>N</inquRsltCd>";
			//dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			//dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "		</getNaManMeritFamInfoResponse>";
			//dcriptMsg = dcriptMsg + "	</soap:Body>";
			//dcriptMsg = dcriptMsg + "</soap:Envelope>";

			//dcriptMsg = dcriptMsg + "<soap:Body>";
			//dcriptMsg = dcriptMsg + "    <soap:Fault>";
			//dcriptMsg = dcriptMsg + "      <faultcode>soap:Server</faultcode>";
			//dcriptMsg = dcriptMsg + "      <faultstring>Fault occured</faultstring>";
			//dcriptMsg = dcriptMsg + "    </soap:Fault>";
			//dcriptMsg = dcriptMsg + "  </soap:Body>";
			//dcriptMsg = dcriptMsg + " </soap:Envelope>";

		}
		
		//6.자료검출
		//국가유공상이자 : 전상군경(00004), 공상군경(00006), 4ㆍ19혁명부상자(00011), 공상공무원(00014), 국가사회발전 특별공로상이자(이하 "특별공로상이자"라 한다)(00016)		
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("cnt",element).equals("0")){
            		returnMap.put("DCITMKEY_00000000018","해당 사항이 없습니다.");
                    //LOGGER.info("cnt : " + getTagValue("cnt",element));
            	}else{
                    NodeList list = element.getElementsByTagName("meritFamInfoBody");
                    boolean tmpchk2 = false;
            		
            		for (int i=0; i<list.getLength(); i++){
            			Node node = list.item(i);
            			
            			if (node.getNodeType() == Node.ELEMENT_NODE){
            				Element eElement = (Element)node;
            				
            				//518민주유공자[DCITMKEY_00000000018]
            				if (getTagValue("inquRsltCd",eElement).equals("S") && getTagValue("authoriPsnYn",eElement).equals("Y") && getTagValue("relCd",eElement).equals("A")
            						&& (getTagValue("subjKbnCd",eElement).equals("00017") || getTagValue("subjKbnCd",eElement).equals("00018")
            								|| getTagValue("subjKbnCd",eElement).equals("00019"))
            					){
            					returnMap.put("DCITMKEY_00000000018","1");
            					tmpchk2 = true;

            	                //LOGGER.info("subjKbnCd : " + getTagValue("subjKbnCd",element));
            				} 
            			}
            		}
            		
            		if (!tmpchk2) {
            			returnMap.put("DCITMKEY_00000000018","해당 사항이 없습니다.");
            		}
            	}	
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }

		return returnMap;
	}
	
	@Override
	//고엽제자동차[DCITMKEY_00000000007]
	public Map<String, String> getNationalMerit3(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		
		String xml = null;

		//1.request xml 본문 생성
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("		<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"> 		 \n");
		sb.append("		   <Header>				 \n");
		sb.append("		      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">				 \n");
		sb.append("		         <serviceName>NaManMeritFamInfoService</serviceName>				 \n");
		sb.append("		         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>				 \n");
		sb.append("		         <certServerId>#CERT_SERVER_ID#</certServerId>				 \n");
		sb.append("		         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>				 \n");
		sb.append("		         <userDeptCode></userDeptCode>				 \n");
		sb.append("		         <userName></userName>				 \n");
		sb.append("		      </commonHeader>				 \n");
		sb.append("		   </Header>				 \n");
		sb.append("		   <Body>				 \n");
		sb.append("		      <getNaManMeritFamInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">				 \n");
		sb.append("		          <juminNo>" + jumiNo + "</juminNo> 				 \n");		
		sb.append("		      </getNaManMeritFamInfo>				 \n");
		sb.append("		   </Body>				 \n");
		sb.append("		</Envelope>				 \n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/nrw/infoservice/mpva/NaManMeritFamInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1312286006";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getNaManMeritFamInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">")[1]
					.split("</getNaManMeritFamInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);


			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";
		
		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getNaManMeritFamInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">")[1]
					.split("</getNaManMeritFamInfoResponse>")[0];


			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			dcriptMsg = responseMsg.replace(responseEncData, decrypted);

			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
			
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "	<soap:Header>";
			dcriptMsg = dcriptMsg + "		<commonHeader";
			dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			dcriptMsg = dcriptMsg + "			<serviceName>NaManMeritFamInfoService</serviceName>";
			dcriptMsg = dcriptMsg + "			<useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "			<certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "			<transactionUniqueId>2018012217473952889982181</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "			<userDeptCode></userDeptCode>";
			dcriptMsg = dcriptMsg + "			<userName>***</userName>";
			dcriptMsg = dcriptMsg + "		</commonHeader>";
			dcriptMsg = dcriptMsg + "	</soap:Header>";
			dcriptMsg = dcriptMsg + "	<soap:Body>";
			dcriptMsg = dcriptMsg + "		<getNaManMeritFamInfoResponse";
			dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			dcriptMsg = dcriptMsg + "			<cnt>2</cnt>";
			dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "				<resName>이수현</resName>";
			dcriptMsg = dcriptMsg + "				<resSecrNum>8708021063615</resSecrNum>";
			dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			dcriptMsg = dcriptMsg + "				<subjKbnCd>00004</subjKbnCd>";
			dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "				<resName>김수현</resName>";
			dcriptMsg = dcriptMsg + "				<resSecrNum>9708021063615</resSecrNum>";
			dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			dcriptMsg = dcriptMsg + "				<subjKbnCd>00017</subjKbnCd>";
			dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "				<resName>박수현</resName>";
			dcriptMsg = dcriptMsg + "				<resSecrNum>9708021063615</resSecrNum>";
			dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			dcriptMsg = dcriptMsg + "				<subjKbnCd>00023</subjKbnCd>";
			dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			dcriptMsg = dcriptMsg + "				<inquRsltCd>S</inquRsltCd>";
			dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			dcriptMsg = dcriptMsg + "		</getNaManMeritFamInfoResponse>";
			dcriptMsg = dcriptMsg + "	</soap:Body>";
			dcriptMsg = dcriptMsg + "</soap:Envelope>";

			//dcriptMsg = dcriptMsg + "	<soap:Body>";
			//dcriptMsg = dcriptMsg + "		<getNaManMeritFamInfoResponse";
			//dcriptMsg = dcriptMsg + "			xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
			//dcriptMsg = dcriptMsg + "			<cnt>0</cnt>";
			//dcriptMsg = dcriptMsg + "			<meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "				<resName>이수현</resName>";
			//dcriptMsg = dcriptMsg + "				<resSecrNum>8708021063615</resSecrNum>";
			//dcriptMsg = dcriptMsg + "				<relCd>A</relCd>";
			//dcriptMsg = dcriptMsg + "				<authoriPsnYn>Y</authoriPsnYn>";
			//dcriptMsg = dcriptMsg + "				<subjKbnCd>00004</subjKbnCd>";
			//dcriptMsg = dcriptMsg + "				<wondClassCd>00011</wondClassCd>";
			//dcriptMsg = dcriptMsg + "				<inquRsltCd>N</inquRsltCd>";
			//dcriptMsg = dcriptMsg + "				<validDate>2072244</validDate>";
			//dcriptMsg = dcriptMsg + "			</meritFamInfoBody>";
			//dcriptMsg = dcriptMsg + "		</getNaManMeritFamInfoResponse>";
			//dcriptMsg = dcriptMsg + "	</soap:Body>";
			//dcriptMsg = dcriptMsg + "</soap:Envelope>";

			//dcriptMsg = dcriptMsg + "<soap:Body>";
			//dcriptMsg = dcriptMsg + "    <soap:Fault>";
			//dcriptMsg = dcriptMsg + "      <faultcode>soap:Server</faultcode>";
			//dcriptMsg = dcriptMsg + "      <faultstring>Fault occured</faultstring>";
			//dcriptMsg = dcriptMsg + "    </soap:Fault>";
			//dcriptMsg = dcriptMsg + "  </soap:Body>";
			//dcriptMsg = dcriptMsg + " </soap:Envelope>";

		}
		
		//6.자료검출
		//국가유공상이자 : 전상군경(00004), 공상군경(00006), 4ㆍ19혁명부상자(00011), 공상공무원(00014), 국가사회발전 특별공로상이자(이하 "특별공로상이자"라 한다)(00016)		
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("cnt",element).equals("0")){
            		returnMap.put("DCITMKEY_00000000007","해당 사항이 없습니다.");
                    //LOGGER.info("cnt : " + getTagValue("cnt",element));
            	}else{
                    NodeList list = element.getElementsByTagName("meritFamInfoBody");
                    boolean tmpchk3 = false;
            		
            		for (int i=0; i<list.getLength(); i++){
            			Node node = list.item(i);
            			
            			if (node.getNodeType() == Node.ELEMENT_NODE){
            				Element eElement = (Element)node;
            				            				
            				//고엽제자동차[DCITMKEY_00000000007]
            				if (getTagValue("inquRsltCd",eElement).equals("S") && getTagValue("authoriPsnYn",eElement).equals("Y") && getTagValue("relCd",eElement).equals("A")
            						&& (getTagValue("subjKbnCd",eElement).equals("00023"))
            					){
            					returnMap.put("DCITMKEY_00000000007","1");
            					tmpchk3 = true;
            					
            	                //LOGGER.info("subjKbnCd : " + getTagValue("subjKbnCd",element));
            					
            				}
            			}
            		}
            		
            		if (!tmpchk3) {
            			returnMap.put("DCITMKEY_00000000007","해당 사항이 없습니다.");
            		}
            	}	
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }

		return returnMap;
	}
	
	@Override
	//[거주자 ]DCITMKEY_00000000017   
	public Map<String, String> getResident(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		String name = (String)requestMap.get("mberNm");  
		
		String xml = null;

		//1.request xml 본문 생성
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\t\n");
		sb.append("   <Header>\t\n");
		sb.append("      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/ResideInsttCnfirm/types\">\t\n");
		sb.append("         <serviceName>ResideInsttCnfirmService</serviceName>\t\n");
		sb.append("         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>\t\n");
		sb.append("         <certServerId>#CERT_SERVER_ID#</certServerId>\t\n");
		sb.append("         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>\t\n");
		sb.append("         <userDeptCode></userDeptCode>\t\n");
		sb.append("         <userName></userName>\t\n");
		sb.append("      </commonHeader>\t\n");
		sb.append("   </Header>\t\n");
		sb.append("   <Body>\t\n");
		sb.append("      <getResideInsttCnfirm xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/ResideInsttCnfirm/types\">\t\n");
		sb.append("         <orgCode>1174000001</orgCode>\t\n");
		sb.append("         <id>" + jumiNo + "</id>\t\n");
		sb.append("         <name>" + name + "</name>\t\n");
		sb.append("      </getResideInsttCnfirm>\t\n");
		sb.append("   </Body>\n");
		sb.append("</Envelope>\n");
		
		xml = sb.toString();

		//LOGGER.info("xml : " + xml);
		
		//2.서비스Url 선언
		String targetUrl = "/jmn/infoservice/jumin/ResideInsttCnfirmService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getResideInsttCnfirm xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/ResideInsttCnfirm/types\">")[1].split("</getResideInsttCnfirm>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);

			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";
		
		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getResideInsttCnfirmResponse xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/ResideInsttCnfirm/types\">")[1]
					.split("</getResideInsttCnfirmResponse>")[0];

			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			dcriptMsg = responseMsg.replace(responseEncData, decrypted);

			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");

			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{

			dcriptMsg = dcriptMsg + " <soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + " 	<soap:Header>";
			dcriptMsg = dcriptMsg + " 		<commonHeader";
			dcriptMsg = dcriptMsg + " 			xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/ResideInsttCnfirm/types\">";
			dcriptMsg = dcriptMsg + " 			<serviceName>ResideInsttCnfirmService</serviceName>";
			dcriptMsg = dcriptMsg + " 			<useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + " 			<certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + " 			<transactionUniqueId>2017121113110597438275152</transactionUniqueId>";
			dcriptMsg = dcriptMsg + " 			<userDeptCode>1380000</userDeptCode>";
			dcriptMsg = dcriptMsg + " 			<userName>홍길동</userName>";
			dcriptMsg = dcriptMsg + " 		</commonHeader>";
			dcriptMsg = dcriptMsg + " 	</soap:Header>";
			dcriptMsg = dcriptMsg + " 	<soap:Body>";
			dcriptMsg = dcriptMsg + " 		<getResideInsttCnfirmResponse";
			dcriptMsg = dcriptMsg + " 			xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/ResideInsttCnfirm/types\">";
			dcriptMsg = dcriptMsg + " 			<orgCode>1174000001</orgCode>";
			dcriptMsg = dcriptMsg + " 			<id>1111111111111</id>";
			dcriptMsg = dcriptMsg + " 			<name>박**</name>";
			dcriptMsg = dcriptMsg + " 			<hangkikCd>1159053000</hangkikCd>";
			dcriptMsg = dcriptMsg + " 			<serviceResult>1</serviceResult>";
			//dcriptMsg = dcriptMsg + " 			<serviceResult>99</serviceResult>";
			dcriptMsg = dcriptMsg + " 		</getResideInsttCnfirmResponse>";
			dcriptMsg = dcriptMsg + " 	</soap:Body>";
			dcriptMsg = dcriptMsg + " </soap:Envelope>";
			
		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	
            	if (getTagValue("serviceResult",element).equals("1") && (getTagValue("hangkikCd",element).equals("1159051000") ||
            			getTagValue("hangkikCd",element).equals("1159052000") || getTagValue("hangkikCd",element).equals("1159066000") ||
            			getTagValue("hangkikCd",element).equals("1159053000") || getTagValue("hangkikCd",element).equals("1159054000") ||
            			getTagValue("hangkikCd",element).equals("1159055000") || getTagValue("hangkikCd",element).equals("1159056000") ||
            			getTagValue("hangkikCd",element).equals("1159060500") || getTagValue("hangkikCd",element).equals("1159062000") ||
            			getTagValue("hangkikCd",element).equals("1159065000") || getTagValue("hangkikCd",element).equals("1159065100") ||
            			getTagValue("hangkikCd",element).equals("1159067000") || getTagValue("hangkikCd",element).equals("1159068000"))
            			){
					returnMap.put(targetid,"1");
            	}else{
            		if (ntoS(getTagValue("serviceResult",element)).equals("2")){
            			returnMap.put(targetid,"주민등록번호 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("3")){
            			returnMap.put(targetid,"성명 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("4")){
            			returnMap.put(targetid,"거주자 아님");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("5")){
            			returnMap.put(targetid,"대상자아님(세대주,배우자)");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("9")){
            			returnMap.put(targetid,"시스템 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("99")){
            			returnMap.put(targetid,"등록된 이용기관이 아님");
            		}else{
            			returnMap.put(targetid,"해당 사항이 없습니다.");
            		}
            	}
                LOGGER.info("serviceResult : " + getTagValue("serviceResult",element));
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		return returnMap;
	}

	@Override
	//[장애인]DCITMKEY_00000000002       
	public Map<String, String> getHandicap(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		String name = (String)requestMap.get("mberNm");  
		String ReqOrgCd = "B551443";
		String ReqBizCd = "B551443S004";
		
		
		String xml = null;

		//1.request xml 본문 생성

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\t\n");
		sb.append("   <Header>\t\n");
		sb.append("      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledYn/types\">\t\n");
		sb.append("         <serviceName>ReductionDisabledYnService</serviceName>\t\n");
		sb.append("         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>\t\n");
		sb.append("         <certServerId>#CERT_SERVER_ID#</certServerId>\t\n");
		sb.append("         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>\t\n");
		sb.append("         <userDeptCode></userDeptCode>\t\n");
		sb.append("         <userName></userName>\t\n");
		sb.append("      </commonHeader>\t\n");
		sb.append("   </Header>\t\n");
		sb.append("   <Body>\t\n");
		sb.append("      <getReductionDisabledYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledYn/types\">\t\n");
		sb.append("		      <ReqOrgCd>" + ReqOrgCd + "</ReqOrgCd>\t\n");
        sb.append("		      <ReqBizCd>" + ReqBizCd + "</ReqBizCd>\t\n");
        sb.append("		      <TGTR_RRN>" + jumiNo + "</TGTR_RRN>\t\n");
        sb.append("		      <TGTR_NM>" + name + "</TGTR_NM>\t\n");
		sb.append("      </getReductionDisabledYn>\t\n");
		sb.append("   </Body>\t\n");
		sb.append("</Envelope>\t\n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/rid/ynservice/swsdn/ReductionDisabledYnService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml
					.split("<getReductionDisabledYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledYn/types\">")[1]
					.split("</getReductionDisabledYn>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);

			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg
					.split("<getReductionDisabledYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledYn/types\">")[1]
					.split("</getReductionDisabledYnResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			
			dcriptMsg = dcriptMsg + " <soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "     <soap:Header>";
			dcriptMsg = dcriptMsg + "         <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledYn/types\">";
			dcriptMsg = dcriptMsg + "             <serviceName>ReductionDisabledYnService</serviceName>";
			dcriptMsg = dcriptMsg + "             <useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "             <certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "             <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "             <userDeptCode>1234567</userDeptCode>";
			dcriptMsg = dcriptMsg + "             <userName>김공무</userName>";
			dcriptMsg = dcriptMsg + "         </commonHeader>";
			dcriptMsg = dcriptMsg + "     </soap:Header>";
			dcriptMsg = dcriptMsg + "     <soap:Body>";
			dcriptMsg = dcriptMsg + "         <getReductionDisabledYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledYn/types\">";
			dcriptMsg = dcriptMsg + "             <DISPS_NM></DISPS_NM>";
			dcriptMsg = dcriptMsg + "             <DISPS_WEL_MBD_IDNF_NO></DISPS_WEL_MBD_IDNF_NO>";
			dcriptMsg = dcriptMsg + "             <QUFY_YN>Y</QUFY_YN>";
			dcriptMsg = dcriptMsg + "             <OVL_DSBTY_GOR></OVL_DSBTY_GOR>";
			dcriptMsg = dcriptMsg + "             <MDTY_CD></MDTY_CD>";
			dcriptMsg = dcriptMsg + "             <MNDSB_RTG_CD></MNDSB_RTG_CD>";
			dcriptMsg = dcriptMsg + "             <SBDTY_CD></SBDTY_CD>";
			dcriptMsg = dcriptMsg + "             <SBDIS_RTG_CD></SBDIS_RTG_CD>";
			dcriptMsg = dcriptMsg + "         </getReductionDisabledYnResponse>";
			dcriptMsg = dcriptMsg + "     </soap:Body>";
			dcriptMsg = dcriptMsg + " </soap:Envelope>";

		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("QUFY_YN",element).equals("Y")){
					returnMap.put(targetid,"1");
            	}else{
            		if (ntoS(getTagValue("QUFY_YN",element)).equals("N")){
            			returnMap.put(targetid,"비대상자");
            		}else if (ntoS(getTagValue("QUFY_YN",element)).equals("M")){
            			returnMap.put(targetid,"비대상자");
            		}else{
            			returnMap.put(targetid,"해당사항이 없습니다.");
            		}
            		
            	}
                LOGGER.info("QUFY_YN : " + getTagValue("QUFY_YN",element));
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		return returnMap;
	}

	@Override
	//[다둥이(세자녀이상)]DCITMKEY_00000000005  
	public Map<String, String> getCountChildren1(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		String name = (String)requestMap.get("mberNm");  
		String orgCode = "1174000001";
		
		String xml = null;

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
		sb.append("   <Header>\n");
		sb.append("      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">\n");
		sb.append("         <serviceName>AllChldrnCoBirthInfoService</serviceName>\n");
		sb.append("         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>\n");
		sb.append("         <certServerId>#CERT_SERVER_ID#</certServerId>\n");
		sb.append("         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>\n");
		sb.append("         <userDeptCode></userDeptCode>\n");
		sb.append("         <userName></userName>\n");
		sb.append("      </commonHeader>\n");
		sb.append("   </Header>\n");
		sb.append("   <Body>\n");
		sb.append("      <getAllChldrnCoBirthInfo xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">\n");
		sb.append("         <orgCode>" + orgCode + "</orgCode>\n");
		sb.append("         <id>" + jumiNo + "</id>\n");
		sb.append("         <name>" + name + "</name>\n");
		sb.append("      </getAllChldrnCoBirthInfo>\n");
		sb.append("   </Body>\n");
		sb.append("</Envelope>\n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/jmn/infoservice/jumin/AllChldrnCoBirthInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getAllChldrnCoBirthInfo xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">")[1].split("</getAllChldrnCoBirthInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);
			
			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getAllChldrnCoBirthInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">")[1]
					.split("</getAllChldrnCoBirthInfoResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			dcriptMsg = dcriptMsg +"<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			dcriptMsg = dcriptMsg +"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg +"    <soap:Header>";
			dcriptMsg = dcriptMsg +"        <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">";
			dcriptMsg = dcriptMsg +"            <serviceName>AllChldrnCoBirthInfoService</serviceName>";
			dcriptMsg = dcriptMsg +"            <useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg +"            <certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg +"            <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
			dcriptMsg = dcriptMsg +"            <userDeptCode>1234567</userDeptCode>";
			dcriptMsg = dcriptMsg +"            <userName>김공무</userName>";
			dcriptMsg = dcriptMsg +"        </commonHeader>";
			dcriptMsg = dcriptMsg +"    </soap:Header>";
			dcriptMsg = dcriptMsg +"    <soap:Body>";
			dcriptMsg = dcriptMsg +"        <getAllChldrnCoBirthInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">";
			dcriptMsg = dcriptMsg +"            <orgCode>1174000001</orgCode>";
			dcriptMsg = dcriptMsg +"            <id>주민등록번호</id>";
			dcriptMsg = dcriptMsg +"            <name>이름</name>";
			dcriptMsg = dcriptMsg +"            <childrenCnt>2</childrenCnt>";
			dcriptMsg = dcriptMsg +"            <birthDay>19900101|19500101|20060520</birthDay>";
			dcriptMsg = dcriptMsg +"            <serviceResult>자녀수확인결과</serviceResult>";
			dcriptMsg = dcriptMsg +"        </getAllChldrnCoBirthInfoResponse>";
			dcriptMsg = dcriptMsg +"    </soap:Body>";
			dcriptMsg = dcriptMsg +"</soap:Envelope>";

		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("serviceResult",element).equals("1") && Integer.parseInt(getTagValue("childrenCnt",element)) >= 3){

            		String[] tmpStr = getTagValue("birthDay",element).split("\\|");
        			//LOGGER.info("birthDay" + getTagValue("birthDay",element));
            		
        			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        			String styymmdd = formatter.format(new Date());
        			int styy = Integer.parseInt(styymmdd.substring(0,4));
        			int stmm = Integer.parseInt(styymmdd.substring(4,6));
        			int stdd = Integer.parseInt(styymmdd.substring(6,8));
        			int targetAge = 99;
            		
                	for (int i=0; i<tmpStr.length; i++){
                		int tmpyy = Integer.parseInt(tmpStr[i].substring(0,4));
                		int tmpmm = Integer.parseInt(tmpStr[i].substring(4,6));
                		int tmpdd = Integer.parseInt(tmpStr[i].substring(6,8));
                		
                		int manAge = styy - tmpyy;
                		
                		if (tmpmm > stmm){
                			manAge = manAge - 1;
                		}else if (tmpmm == stmm){
                			if (tmpdd > stdd){
                				manAge = manAge - 1;	
                			}
                		}
                		
                		//LOGGER.info("manAge : " + manAge);
                		
                		if (manAge < targetAge){
                			targetAge = manAge;
                		}
                		
                		//LOGGER.info("targetAge : " + targetAge);
                	}
                	
                	if (targetAge <= 13 ){
        				returnMap.put(targetid,"1");
                	}else{
        				returnMap.put(targetid,"해당 사항이 없습니다.");                		
                	}
                	
            	}else{
            		if (ntoS(getTagValue("serviceResult",element)).equals("1") && Integer.parseInt(getTagValue("childrenCnt",element)) < 2){
            			returnMap.put(targetid,"자녀수가 1명입니다.");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("2")){
            			returnMap.put(targetid,"주민등록번호 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("3")){
            			returnMap.put(targetid,"성명 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("4")){
            			returnMap.put(targetid,"거주자 아님");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("5")){
            			returnMap.put(targetid,"대상자아님(세대주,배우자)");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("9")){
            			returnMap.put(targetid,"시스템 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("80")){
            			returnMap.put(targetid,"주민등록번호 오류");
            		}else{
            			returnMap.put(targetid,"해당 사항이 없습니다.");
            		}
            	}
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		
		return returnMap;
	}
	

	//[다둥이(두자녀)]DCITMKEY_00000000006     
	public Map<String, String> getCountChildren2(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		String name = (String)requestMap.get("mberNm");  
		String orgCode = "1174000001";
		
		String xml = null;

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\t\n");
		sb.append("   <Header>\t\n");
		sb.append("      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">\t\n");
		sb.append("         <serviceName>AllChldrnCoBirthInfoService</serviceName>\t\n");
		sb.append("         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>\t\n");
		sb.append("         <certServerId>#CERT_SERVER_ID#</certServerId>\t\n");
		sb.append("         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>\t\n");
		sb.append("         <userDeptCode></userDeptCode>\t\n");
		sb.append("         <userName></userName>\t\n");
		sb.append("      </commonHeader>\t\n");
		sb.append("   </Header>\t\n");
		sb.append("   <Body>\t\n");
		sb.append("      <getAllChldrnCoBirthInfo xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">\t\n");
		sb.append("         <orgCode>" + orgCode + "</orgCode>\t\n");
		sb.append("         <id>" + jumiNo + "</id>\t\n");
		sb.append("         <name>" + name + "</name>\t\n");
		sb.append("      </getAllChldrnCoBirthInfo>\t\n");
		sb.append("   </Body>\t\n");
		sb.append("</Envelope>\t\n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/jmn/infoservice/jumin/AllChldrnCoBirthInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getAllChldrnCoBirthInfo xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">")[1].split("</getAllChldrnCoBirthInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);
			
			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getAllChldrnCoBirthInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">")[1]
					.split("</getAllChldrnCoBirthInfoResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			dcriptMsg = dcriptMsg +"<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			dcriptMsg = dcriptMsg +"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg +"    <soap:Header>";
			dcriptMsg = dcriptMsg +"        <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">";
			dcriptMsg = dcriptMsg +"            <serviceName>AllChldrnCoBirthInfoService</serviceName>";
			dcriptMsg = dcriptMsg +"            <useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg +"            <certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg +"            <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
			dcriptMsg = dcriptMsg +"            <userDeptCode>1234567</userDeptCode>";
			dcriptMsg = dcriptMsg +"            <userName>김공무</userName>";
			dcriptMsg = dcriptMsg +"        </commonHeader>";
			dcriptMsg = dcriptMsg +"    </soap:Header>";
			dcriptMsg = dcriptMsg +"    <soap:Body>";
			dcriptMsg = dcriptMsg +"        <getAllChldrnCoBirthInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/jmn/services/jumin/AllChldrnCoBirthInfo/types\">";
			dcriptMsg = dcriptMsg +"            <orgCode>1174000001</orgCode>";
			dcriptMsg = dcriptMsg +"            <id>주민등록번호</id>";
			dcriptMsg = dcriptMsg +"            <name>이름</name>";
			dcriptMsg = dcriptMsg +"            <childrenCnt>2</childrenCnt>";
			dcriptMsg = dcriptMsg +"            <birthDay>19900101|19500101|20060520</birthDay>";
			dcriptMsg = dcriptMsg +"            <serviceResult>자녀수확인결과</serviceResult>";
			dcriptMsg = dcriptMsg +"        </getAllChldrnCoBirthInfoResponse>";
			dcriptMsg = dcriptMsg +"    </soap:Body>";
			dcriptMsg = dcriptMsg +"</soap:Envelope>";

		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("serviceResult",element).equals("1") && Integer.parseInt(getTagValue("childrenCnt",element)) >= 2){

            		String[] tmpStr = getTagValue("birthDay",element).split("\\|");
        			//LOGGER.info("birthDay" + getTagValue("birthDay",element));
            		
        			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        			String styymmdd = formatter.format(new Date());
        			int styy = Integer.parseInt(styymmdd.substring(0,4));
        			int stmm = Integer.parseInt(styymmdd.substring(4,6));
        			int stdd = Integer.parseInt(styymmdd.substring(6,8));
        			int targetAge = 99;
            		
            		for (int i=0; i<tmpStr.length; i++){
                		int tmpyy = Integer.parseInt(tmpStr[i].substring(0,4));
                		int tmpmm = Integer.parseInt(tmpStr[i].substring(4,6));
                		int tmpdd = Integer.parseInt(tmpStr[i].substring(6,8));
                		
                		int manAge = styy - tmpyy;
                		
                		if (tmpmm > stmm){
                			manAge = manAge - 1;
                		}else if (tmpmm == stmm){
                			if (tmpdd > stdd){
                				manAge = manAge - 1;	
                			}
                		}
                		
                		LOGGER.info("manAge : " + manAge);
                		
                		if (manAge < targetAge){
                			targetAge = manAge;
                		}
                		
                		LOGGER.info("targetAge : " + targetAge);
                	}
                	
                	if (targetAge <= 13 ){
        				returnMap.put(targetid,"1");
                	}else{
        				returnMap.put(targetid,"해당 사항이 없습니다.");
                	}
            	}else{
            		if (ntoS(getTagValue("serviceResult",element)).equals("1") && Integer.parseInt(getTagValue("childrenCnt",element)) < 2){
            			returnMap.put(targetid,"자녀수가 1명입니다.");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("2")){
            			returnMap.put(targetid,"주민등록번호 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("3")){
            			returnMap.put(targetid,"성명 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("4")){
            			returnMap.put(targetid,"거주자 아님");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("5")){
            			returnMap.put(targetid,"대상자아님(세대주,배우자)");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("9")){
            			returnMap.put(targetid,"시스템 오류");
            		}else if (ntoS(getTagValue("serviceResult",element)).equals("80")){
            			returnMap.put(targetid,"주민등록번호 오류");
            		}else{
            			returnMap.put(targetid,"해당 사항이 없습니다.");
            		}
            	}
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		
		return returnMap;
	}

	@Override
	//[모범납세자]DCITMKEY_00000000008        
	public Map<String, String> getGreateTax(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		String name = (String)requestMap.get("mberNm");  
		
		String xml = null;

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\" >		\t\n");
		sb.append("   <Header>								\t\n");
		sb.append("      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">							\t\n");
		sb.append("         <serviceName>NtsGoodTaxpayInfoService</serviceName>				\t\n");
		sb.append("         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>				\t\n");
		sb.append("         <certServerId>#CERT_SERVER_ID#</certServerId>				\t\n");
		sb.append("         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>		\t\n");
		sb.append("         <userDeptCode></userDeptCode>				\t\n");
		sb.append("         <userName></userName>					\t\n");
		sb.append("      </commonHeader>							\t\n");
		sb.append("   </Header>								\t\n");
		sb.append("   <Body>								\t\n");
		sb.append("      <getNtsGoodTaxpayInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">			\t\n");
		sb.append("         <txprClCd>01</txprClCd>			\t\n");
		sb.append("         <txprNm>" + name + "</txprNm>			\t\n");
		sb.append("         <resno>" + jumiNo + "</resno>			\t\n");
		sb.append("         <bsno></bsno>			\t\n");
		sb.append("         <cprno></cprno>			\t\n");
		sb.append("         <mdlTxprCerAplnYn>Y</mdlTxprCerAplnYn>			\t\n");
		sb.append("      </getNtsGoodTaxpayInfo>		\t\n");
		sb.append("   </Body>								\t\n");
		sb.append("</Envelope>								\t\n");
		
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/nrw/infoservice/nts/NtsGoodTaxpayInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getNtsGoodTaxpayInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">")[1].split("</getNtsGoodTaxpayInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);

			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			//LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getNtsGoodTaxpayInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">")[1]
					.split("</getNtsGoodTaxpayInfoResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			
			dcriptMsg = dcriptMsg + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n";
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\t\n";
			dcriptMsg = dcriptMsg + "	<soap:Header>\t\n";
			dcriptMsg = dcriptMsg + "		<commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">\t\n";
			dcriptMsg = dcriptMsg + "			<serviceName>NtsGoodTaxpayInfoService</serviceName>\t\n";
			dcriptMsg = dcriptMsg + "			<useSystemCode>ESBTEST</useSystemCode>\t\n";
			dcriptMsg = dcriptMsg + "			<certServerId>SVR1311000030</certServerId>\t\n";
			dcriptMsg = dcriptMsg + "			<transactionUniqueId>2018012216572071707533133</transactionUniqueId>\t\n";
			dcriptMsg = dcriptMsg + "			<userDeptCode>1380000</userDeptCode>\t\n";
			dcriptMsg = dcriptMsg + "			<userName>홍길동</userName>\t\n";
			dcriptMsg = dcriptMsg + "		</commonHeader>\t\n";
			dcriptMsg = dcriptMsg + "	</soap:Header>\t\n";
			dcriptMsg = dcriptMsg + "	<soap:Body>\t\n";
			dcriptMsg = dcriptMsg + "		<getNtsGoodTaxpayInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">\t\n";
			dcriptMsg = dcriptMsg + "			<trtRsltCd>14</trtRsltCd>\t\n";
			dcriptMsg = dcriptMsg + "			<trtRsltCntn></trtRsltCntn>\t\n";
			dcriptMsg = dcriptMsg + "			<mdlTxprYn>Y</mdlTxprYn>\t\n";
			//dcriptMsg = dcriptMsg + "			<mdlTxprYn>N</mdlTxprYn>\t\n";
			dcriptMsg = dcriptMsg + "		</getNtsGoodTaxpayInfoResponse>\t\n";
			dcriptMsg = dcriptMsg + "	</soap:Body>\t\n";
			dcriptMsg = dcriptMsg + "</soap:Envelope>\t\n";
			
			

		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("mdlTxprYn",element).equals("Y")){
					returnMap.put(targetid,"1");
            	}else{
            		returnMap.put(targetid,"해당 사항 없습니다.");
            	}
                LOGGER.info("mdlTxprYn : " + getTagValue("mdlTxprYn",element));
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }		
		return returnMap;
	}

	@Override
	//[병역명문가]DCITMKEY_00000000009      
	public Map<String, String> getGreateArmy(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		String name = (String)requestMap.get("mberNm");  
		
		String xml = null;

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("		<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"> 		 \t\n");
		sb.append("		   <Header>				 \t\n");
		sb.append("		      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/MtrscNobleInfo/types\">				 \t\n");
		sb.append("		         <serviceName>MtrscNobleInfoService</serviceName>				 \t\n");
		sb.append("		         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>				 \t\n");
		sb.append("		         <certServerId>#CERT_SERVER_ID#</certServerId>				 \t\n");
		sb.append("		         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>				 \t\n");
		sb.append("		         <userDeptCode></userDeptCode>				 \t\n");
		sb.append("		         <userName></userName>				 \t\n");
		sb.append("		      </commonHeader>				 \t\n");
		sb.append("		   </Header>				 \t\n");
		sb.append("		   <Body>				 \t\n");
		sb.append("		      <getMtrscNobleInfo xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/MtrscNobleInfo/types\">				 \t\n");
		sb.append("		           <serviceCode>HABMMA0001</serviceCode>							 \t\n");
		sb.append("		           <requestReq>#TRANSACTION_UNIQUE_ID#</requestReq> 							 \t\n");
		sb.append("	               <name>" + name + "</name> 							 \t\n");
		sb.append("	               <juminNo>" + jumiNo + "</juminNo> 							 \t\n");
		sb.append("		      </getMtrscNobleInfo>				 \t\n");
		sb.append("		   </Body>				 \t\n");
		sb.append("		</Envelope>				 \t\n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/rid/infoservice/mms/MtrscNobleInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1312286015";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getMtrscNobleInfo xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/MtrscNobleInfo/types\">")[1].split("</getMtrscNobleInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);

			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getMtrscNobleInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/MtrscNobleInfo/types\">")[1]
					.split("</getMtrscNobleInfoResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "	<soap:Header>";
			dcriptMsg = dcriptMsg + "		<commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/MtrscNobleInfo/types\">";
			dcriptMsg = dcriptMsg + "			<serviceName>MtrscNobleInfoService</serviceName>";
			dcriptMsg = dcriptMsg + "			<useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "			<certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "			<transactionUniqueId>2018081009570432064855089</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "			<userDeptCode>111</userDeptCode>";
			dcriptMsg = dcriptMsg + "			<userName>김공무</userName>";
			dcriptMsg = dcriptMsg + "		</commonHeader>";
			dcriptMsg = dcriptMsg + "	</soap:Header>";
			dcriptMsg = dcriptMsg + "	<soap:Body>";
			dcriptMsg = dcriptMsg + "		<getMtrscNobleInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/MtrscNobleInfo/types\">";
			dcriptMsg = dcriptMsg + "			<serviceCode>HABMMA0001</serviceCode>";
			dcriptMsg = dcriptMsg + "			<requestReq>2018081009570432064855089</requestReq>";
			dcriptMsg = dcriptMsg + "			<name>이둘리</name>";
			dcriptMsg = dcriptMsg + "			<juminNo>8201011000519</juminNo>";
			dcriptMsg = dcriptMsg + "			<resultYn>Y</resultYn>";
			//dcriptMsg = dcriptMsg + "			<resultYn>N</resultYn>";
			dcriptMsg = dcriptMsg + "		</getMtrscNobleInfoResponse>";
			dcriptMsg = dcriptMsg + "	</soap:Body>";
			dcriptMsg = dcriptMsg + "</soap:Envelope>";

		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("resultYn",element).equals("Y")){
					returnMap.put(targetid,"1");
            	}else{
            		returnMap.put(targetid,"해당사항이 없습니다.");
            	}
                LOGGER.info("mdlTxprYn : " + getTagValue("resultYn",element));
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }		
		
		return returnMap;
	}

	@Override
	//[저공해]DCITMKEY_00000000004        
	public Map<String, String> getLowCar(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String carNumber = (String)requestMap.get("carNumber");  
		
		String xml = null;
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\t\n");
		sb.append("   <Header>\t\n");
		sb.append("      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/keco/LowPollutionCarYn/types\">\t\n");
		sb.append("         <serviceName>LowPollutionCarYnService</serviceName>\t\n");
		sb.append("         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>\t\n");
		sb.append("         <certServerId>#CERT_SERVER_ID#</certServerId>\t\n");
		sb.append("         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>\t\n");
		sb.append("         <userDeptCode></userDeptCode>\t\n");
		sb.append("         <userName></userName>\t\n");
		sb.append("      </commonHeader>\t\n");
		sb.append("   </Header>\t\n");
		sb.append("   <Body>\t\n");
		sb.append("      <getLowPollutionCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/keco/LowPollutionCarYn/types\">\t\n");
		sb.append("		      <reqId>ECA90004JH06</reqId>\t\n");
        sb.append("		      <carNumber>" + carNumber + "</carNumber>\t\n");
		sb.append("      </getLowPollutionCarYn>\t\n");
		sb.append("   </Body>\t\n");
		sb.append("</Envelope>\t\n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/rid/ynservice/keco/LowPollutionCarYnService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml
					.split("<getLowPollutionCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/keco/LowPollutionCarYn/types\">")[1]
					.split("</getLowPollutionCarYn>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);

			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg
					.split("<getLowPollutionCarYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/keco/LowPollutionCarYn/types\">")[1]
					.split("</getLowPollutionCarYnResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{

			dcriptMsg = dcriptMsg + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "    <soap:Header>";
			dcriptMsg = dcriptMsg + "        <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/keco/LowPollutionCarYn/types\">";
			dcriptMsg = dcriptMsg + "            <serviceName>LowPollutionCarYnService</serviceName>";
			dcriptMsg = dcriptMsg + "            <useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "            <certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "            <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "            <userDeptCode>1234567</userDeptCode>";
			dcriptMsg = dcriptMsg + "            <userName>김공무</userName>";
			dcriptMsg = dcriptMsg + "        </commonHeader>";
			dcriptMsg = dcriptMsg + "    </soap:Header>";
			dcriptMsg = dcriptMsg + "    <soap:Body>";
			dcriptMsg = dcriptMsg + "        <getLowPollutionCarYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/keco/LowPollutionCarYn/types\">";
			dcriptMsg = dcriptMsg + "          <DataList>";
			//dcriptMsg = dcriptMsg + "             <lowpoulltion_car>true</lowpoulltion_car>";
			dcriptMsg = dcriptMsg + "             <lowpoulltion_car>false</lowpoulltion_car>";
			dcriptMsg = dcriptMsg + "             <lowpoulltion_code>1</lowpoulltion_code>";
			dcriptMsg = dcriptMsg + "          </DataList>";
			dcriptMsg = dcriptMsg + "        </getLowPollutionCarYnResponse>";
			dcriptMsg = dcriptMsg + "    </soap:Body>";
			dcriptMsg = dcriptMsg + "</soap:Envelope>";

		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("lowpoulltion_car",element).equals("true")){
					returnMap.put(targetid,"1");
            	}else {
            		returnMap.put(targetid,"일반차량");
            	}
                LOGGER.info("lowpoulltion_car : " + getTagValue("lowpoulltion_car",element));
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }		
		
		return returnMap;
	}
	

	@Override
	//[경차]DCITMKEY_00000000001     
	public Map<String, String> reductionCar(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String carNumber = (String)requestMap.get("carNumber");  
		
		String xml = null;

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\t\n");
		sb.append("	<Header>	\t\n");
		sb.append("		<commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/ts/ReductionTsCarInfo/types\">	\t\n");
		sb.append("			<serviceName>ReductionTsCarInfoService</serviceName>	\t\n");
		sb.append("			<useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>	\t\n");
		sb.append("			<certServerId>#CERT_SERVER_ID#</certServerId>	\t\n");
		sb.append("			<transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>	\t\n");
		sb.append("			<userDeptCode></userDeptCode>	\t\n");
		sb.append("			<userName></userName>	\t\n");
		sb.append("		</commonHeader>	\t\n");
		sb.append("	</Header>	\t\n");
		sb.append("	<Body>	\t\n");
		sb.append("		<getReductionTsCarInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/ts/ReductionTsCarInfo/types\">	\t\n");
		sb.append("			<cntcInfoCode>AC1_A69_13</cntcInfoCode>	\t\n");
		sb.append("			<chargerId>B552903</chargerId>	\t\n");
		sb.append("			<chargerNm>이강민</chargerNm>	\t\n");
		sb.append("			<chargerIpAdres>45.64.175.5</chargerIpAdres>	\t\n");
		sb.append("			<vhrNo>" + carNumber + "</vhrNo>	\t\n");
		sb.append("		</getReductionTsCarInfo>	\t\n");
		sb.append("	</Body>	\t\n");
		sb.append("</Envelope>	\t\n");

		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/nrw/infoservice/ts/ReductionTsCarInfoService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml.split("<getReductionTsCarInfo xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/ts/ReductionTsCarInfo/types\">")[1]
					.split("</getReductionTsCarInfo>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);

			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg.split("<getReductionTsCarInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/ts/ReductionTsCarInfo/types\">")[1]
					.split("</getReductionTsCarInfoResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{

			dcriptMsg = dcriptMsg + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			dcriptMsg = dcriptMsg + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "    <soap:Header>";
			dcriptMsg = dcriptMsg + "        <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/keco/LowPollutionCarYn/types\">";
			dcriptMsg = dcriptMsg + "            <serviceName>LowPollutionCarYnService</serviceName>";
			dcriptMsg = dcriptMsg + "            <useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "            <certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "            <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "            <userDeptCode>1234567</userDeptCode>";
			dcriptMsg = dcriptMsg + "            <userName>김공무</userName>";
			dcriptMsg = dcriptMsg + "        </commonHeader>";
			dcriptMsg = dcriptMsg + "    </soap:Header>";
			dcriptMsg = dcriptMsg + "    <soap:Body>";
			dcriptMsg = dcriptMsg + "        <getReductionTsCarInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/ts/ReductionTsCarInfo/types\">";
			dcriptMsg = dcriptMsg + "          <cntcResultCode></cntcResultCode>";
			dcriptMsg = dcriptMsg + "          <cntcResultDtls></cntcResultDtls>";
			dcriptMsg = dcriptMsg + "          <vhctyAsortNm></vhctyAsortNm>";
			dcriptMsg = dcriptMsg + "          <vhctyAsortNm></vhctyAsortNm>";
			dcriptMsg = dcriptMsg + "          <dsplvl>500</dsplvl>";
			dcriptMsg = dcriptMsg + "          <cbdLt>2000</cbdLt>";
			dcriptMsg = dcriptMsg + "          <processImprtyResnCode>00</processImprtyResnCode>";
			dcriptMsg = dcriptMsg + "          <processImprtyResnDtls>100</processImprtyResnDtls>";
			dcriptMsg = dcriptMsg + "        </getReductionTsCarInfoResponse>";
			dcriptMsg = dcriptMsg + "    </soap:Body>";
			dcriptMsg = dcriptMsg + "</soap:Envelope>";
			
		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{
            	if (getTagValue("processImprtyResnCode",element).equals("00") && Integer.parseInt(getTagValue("dsplvl",element)) <= 1000 ){
            		if (Integer.parseInt(getTagValue("cbdLt",element)) <=3600) {
            			returnMap.put(targetid,"1");
            		}
            	}else {
            		returnMap.put(targetid,"해당 사항 없습니다.");
            	}
                LOGGER.info("reductionCar1 : " + getTagValue("dsplvl",element));
                LOGGER.info("reductionCar2 : " + getTagValue("cbdLt",element));
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }		
		
		return returnMap;
	}
	
	@Override
	//[기초수급자]DCITMKEY_00000000021
	public Map<String, String> getBasePerson(Map requestMap, String targetid) {
        HashMap returnMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String jumiNo = (String)requestMap.get("jumin1") + (String)requestMap.get("jumin2");  
		String name = (String)requestMap.get("mberNm"); 
		String ReqOrgCd = "B551443";
		String ReqBizCd = "B551443S003";
		
		String xml = null;

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\t\n");
		sb.append("   <Header>\t\n");
		sb.append("      <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">\t\n");
		sb.append("         <serviceName>ReductionBscLivYnService</serviceName>\t\n");
		sb.append("         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode>\t\n");
		sb.append("         <certServerId>#CERT_SERVER_ID#</certServerId>\t\n");
		sb.append("         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId>\t\n");
		sb.append("         <userDeptCode></userDeptCode>\t\n");
		sb.append("         <userName></userName>\t\n");
		sb.append("      </commonHeader>\t\n");
		sb.append("   </Header>\t\n");
		sb.append("   <Body>\t\n");
		sb.append("      <getReductionBscLivYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">\t\n");
		sb.append("		      <ReqOrgCd>" + ReqOrgCd + "</ReqOrgCd>\t\n");
        sb.append("		      <ReqBizCd>" + ReqBizCd + "</ReqBizCd>\t\n");
        sb.append("		      <TGTR_RRN>" + jumiNo + "</TGTR_RRN>\t\n");
        sb.append("		      <TGTR_NM>" + name + "</TGTR_NM>\t\n");
		sb.append("      </getReductionBscLivYn>\t\n");
		sb.append("   </Body>\t\n");
		sb.append("</Envelope>\t\n");
		
		xml = sb.toString();
		
		//2.서비스Url 선언
		String targetUrl = "/rid/ynservice/swsdn/ReductionBscLivYnService";

		//3.암호화 + 전자서명 날인
		NewGpkiUtil g = null;
		String encoded = null;
		String requestXml = null;
		
		try {
			String targetServerId = "SVR1311000030";			// 수정 금지
			g = ShareGpki.getGpkiUtil(targetServerId);

			String charset = "UTF-8";

			String original = xml
					.split("<getReductionBscLivYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">")[1]
					.split("</getReductionBscLivYn>")[0];

			//LOGGER.info("origin : " + xml);
			
			byte[] encrypted = g.encrypt(original.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);

			requestXml = xml;
			{
				requestXml = requestXml.replace(original, encoded);
			}

			LOGGER.info("eoncoded : " + requestXml);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String dcriptMsg = "";

		if (!TEST_FLAG.equals("true")){
			//4.API 호출
			String responseMsg = Client.doService(targetUrl, requestXml);
			LOGGER.info("responseMsg : " + responseMsg);
			
			//5.복호화 + 전자서명 검증
			String responseEncData = responseMsg
					.split("<getReductionBscLivYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">")[1]
					.split("</getReductionBscLivYnResponse>")[0];
			
			String decrypted = "";
			{
				byte[] decoded;
				try {
					decoded = g.decode(responseEncData);
					byte[] validated = g.validate(decoded);
					decrypted = new String(g.decrypt(validated), "UTF-8");
					decrypted = decrypted.replace("><", ">\n<");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			dcriptMsg = responseMsg.replace(responseEncData, decrypted);
	
			dcriptMsg = dcriptMsg.replaceAll("&amp;", "&");
			dcriptMsg = dcriptMsg.replaceAll("&lt;", "<");
			dcriptMsg = dcriptMsg.replaceAll("&gt;", ">");
			dcriptMsg = dcriptMsg.replaceAll("&nbsp;", "''");
	
			//LOGGER.info("dcriptMsg : " + dcriptMsg);
		}else{
			
			dcriptMsg = dcriptMsg + " <soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			dcriptMsg = dcriptMsg + "     <soap:Header>";
			dcriptMsg = dcriptMsg + "         <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">";
			dcriptMsg = dcriptMsg + "             <serviceName>ReductionBscLivYnService</serviceName>";
			dcriptMsg = dcriptMsg + "             <useSystemCode>ESBTEST</useSystemCode>";
			dcriptMsg = dcriptMsg + "             <certServerId>SVR1311000030</certServerId>";
			dcriptMsg = dcriptMsg + "             <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
			dcriptMsg = dcriptMsg + "             <userDeptCode>1234567</userDeptCode>";
			dcriptMsg = dcriptMsg + "             <userName>김공무</userName>";
			dcriptMsg = dcriptMsg + "         </commonHeader>";
			dcriptMsg = dcriptMsg + "     </soap:Header>";
			dcriptMsg = dcriptMsg + "     <soap:Body>";
			dcriptMsg = dcriptMsg + "         <getReductionBscLivYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">";
			dcriptMsg = dcriptMsg + "             <TGTR_NM></TGTR_NM>";
			dcriptMsg = dcriptMsg + "             <TGTR_RRN></TGTR_RRN>";
			dcriptMsg = dcriptMsg + "             <FCT_YN>Y</FCT_YN>";
			dcriptMsg = dcriptMsg + "             <BSLF01_YN></BSLF01_YN>";
			dcriptMsg = dcriptMsg + "             <BSLF02_YN></BSLF02_YN>";
			dcriptMsg = dcriptMsg + "             <BSLF03_YN></BSLF03_YN>";
			dcriptMsg = dcriptMsg + "             <BSLF04_YN></BSLF04_YN>";
			dcriptMsg = dcriptMsg + "         </getReductionBscLivYnResponse>";
			dcriptMsg = dcriptMsg + "     </soap:Body>";
			dcriptMsg = dcriptMsg + " </soap:Envelope>";

		}
		
		//6.자료검출
		try {
			//6-1. XML 파싱
            // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
            // 문자열을 InputStream으로 변환
            InputStream inputstream = new ByteArrayInputStream(dcriptMsg.getBytes());
            
            Reader reader = new InputStreamReader(inputstream,"UTF-8");
            
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            
            Document doc = documentbuilder.parse(is);
            // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
            Element element = doc.getDocumentElement();
 
            //6-2. API 데이터 검출
            
            if (getTagValue("faultstring",element) != null){
            	returnMap.put("faultstring",getTagValue("faultstring",element));
                LOGGER.info("faultstring : " + getTagValue("faultstring",element));
            }else{

            	if (getTagValue("FCT_YN",element).equals("Y")){
					returnMap.put(targetid,"1");
            	}else{
            		if (ntoS(getTagValue("FCT_YN",element)).equals("N")){
            			returnMap.put(targetid,"비대상자");
            		}else if (ntoS(getTagValue("FCT_YN",element)).equals("M")){
            			returnMap.put(targetid,"비대상자");
            		}else{
            			returnMap.put(targetid,"해당사항이 없습니다.");
            		}
            		
            	}
                LOGGER.info("FCT_YN : " + getTagValue("FCT_YN",element));
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }		
		
		return returnMap;
	}
	
	//노트 데이터 읽기
	private static String getTagValue(String tag, Element eElement) {
		Node nValue = null;
	    NodeList nlList = eElement.getElementsByTagName(tag);
	    if (nlList.getLength() > 0){
    		NodeList nlList2 = nlList.item(0).getChildNodes();
    	    nValue = (Node) nlList2.item(0);
    	    if(nValue == null) 
    	        return null;
	    }else{
	    	return null;
	    }
	    return nValue.getNodeValue();
	}
	
	//String null 처리
	private String ntoS(String parm){
		String result = "";
		if (parm == null || parm.length() == 0){
			result = "";
		}else{
			result = parm;
		}
		return result;
	}

}
