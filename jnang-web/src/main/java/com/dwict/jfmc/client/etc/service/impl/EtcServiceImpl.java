package com.dwict.jfmc.client.etc.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dwict.jfmc.client.etc.service.EtcService;
import com.dwict.jfmc.client.security.service.AccountService;
import com.dwict.jfmc.client.smpay.mapper.PayMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.com.util.SHA256PasswordEncoder;
import com.dwict.jfmc.client.etc.mapper.EtcMapper;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;

@Slf4j
@Service("etcService")
public class EtcServiceImpl implements EtcService {

	@Value("#{appConfig['kma.api.key']}")
	private String sKmaKey;

	@Value("#{appConfig['kma.api.url']}")
	private String sKmaUrl;

	@Value("#{appConfig['air.api.key']}")
	private String sAirKey;

	@Value("#{appConfig['air.api.url']}")
	private String sAirUrl;

	@Resource(name = "etcMapper")
	private EtcMapper etcMapper;

	@Resource(name = "payMapper")
	private PayMapper mapper;
	
	@Autowired
	private AccountService accountService;
	
	private static String key = "3D5853AA8A94D22A2C6518968F0DCF78";
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	@Override
	public HashMap<String, String> getWeatherInfo() {
		log.info("::: getWeatherInfo :::");
		final LocalDateTime localDateTime = LocalDateTime.now();
		String yyyyMMdd = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		final int HHmm = Integer.parseInt(localDateTime.format(DateTimeFormatter.ofPattern("HHmm")));
		String hh00 = null;
		if (HHmm > 2300) {
			hh00 = "2300";
		} else if(HHmm > 2000) {
			hh00 = "2000";
		} else if(HHmm > 1700) {
			hh00 = "1700";
		} else if(HHmm > 1400) {
			hh00 = "1400";
		} else if(HHmm > 1100) {
			hh00 = "1100";
		} else if(HHmm > 800) {
			hh00 = "0800";
		} else if(HHmm > 500) {
			hh00 = "0500";
		} else if(HHmm > 200) {
			hh00 = "0200";
		} else {
			yyyyMMdd = localDateTime.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			hh00 = "2300";
		}

		final StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(sKmaUrl);
		sbUrl.append("?ServiceKey=");
		sbUrl.append(sKmaKey);
		sbUrl.append("&numOfRows=20&pageNo=1&base_date="+yyyyMMdd+"&base_time="+hh00+"&nx=62&ny=128");

		final DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			log.error("This element neither has attached source nor attached Javadoc and hence no Javadoc could be found.");
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(sbUrl.toString());
		} catch (final SAXException e) {
			log.error("This element neither has attached source nor attached Javadoc and hence no Javadoc could be found.");
			e.printStackTrace();
		} catch (final IOException e) {
			log.error("Signals that an I/O exception of some sort has occurred. Thisclass is the general class of exceptions produced by failed orinterrupted I/O operations.");
			e.printStackTrace();
		}

		final HashMap<String, String> weatherInfo = new HashMap<>();
		weatherInfo.put("tm", yyyyMMdd+hh00);		
		final NodeList nlList = doc.getElementsByTagName("item");
		for(int i=0; i<nlList.getLength(); i++) {
			final Node nNode = nlList.item(i);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				final Element eElement = (Element) nNode;
				if(getTagValue("category", eElement).equals("PTY")) {
					weatherInfo.put("pty", getTagValue("fcstValue", eElement));
				} else if(getTagValue("category", eElement).equals("SKY")) {
					weatherInfo.put("sky", getTagValue("fcstValue", eElement));
				} else if(getTagValue("category", eElement).equals("T3H")) {
					weatherInfo.put("temp", getTagValue("fcstValue", eElement));
				}
			}
		}

		if(Integer.parseInt(weatherInfo.get("pty")) > 0) {
			switch (weatherInfo.get("pty")) {
			case "1":
				weatherInfo.put("status", "비");
				break;
			case "2":
				weatherInfo.put("status", "비/눈");
				break;
			case "3":
				weatherInfo.put("status", "눈");
				break;
			case "4":
				weatherInfo.put("status", "소나기");
				break;
			case "5":
				weatherInfo.put("status", "빗방울/눈날림");
				break;
			case "6":
				weatherInfo.put("status", "눈날림");
				break;
			}
		} else {
			switch (weatherInfo.get("sky")) {
			case "1":
			case "2":
				//기상청 동네예보 조회서비스 - 구름조금(2) 삭제(2019.06.04)
				weatherInfo.put("status", "맑음");
				break;
			case "3":
				weatherInfo.put("status", "구름많음");
				break;
			case "4":
				weatherInfo.put("status", "흐림");
				break;
			}
		}
		
		try {
			final HashMap<String, String> airInfo = getAirInfo();
			weatherInfo.put("pm10Value", airInfo.get("pm10Value"));
			weatherInfo.put("airStatus", airInfo.get("airStatus"));
		} catch (Exception e) {
			weatherInfo.put("pm10Value", "80");
			weatherInfo.put("airStatus", "보통");
		}
		return weatherInfo;
	}

	private String getTagValue(String tag, Element eElement) {
		final NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		final Node nValue = nlList.item(0);
		if(nValue == null) {
			return null;
		}
		return nValue.getNodeValue();
	}

	private HashMap<String, String> getAirInfo() {
		log.info("::: getAirInfo :::");
		final StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(sAirUrl);
		sbUrl.append("?ServiceKey=");
		sbUrl.append(sAirKey);
		sbUrl.append("&sidoName=%EC%84%9C%EC%9A%B8&numOfRows=50&pageNo=1&searchCondition=Hour");
		final DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			log.error("This element neither has attached source nor attached Javadoc and hence no Javadoc could be found.");
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(sbUrl.toString());
		} catch (final SAXException e) {
			log.error("This element neither has attached source nor attached Javadoc and hence no Javadoc could be found.");
			e.printStackTrace();
		} catch (final IOException e) {
			log.error("Signals that an I/O exception of some sort has occurred. Thisclass is the general class of exceptions produced by failed orinterrupted I/O operations.");
			e.printStackTrace();
		}
		
		//try {
			final HashMap<String, String> airInfo = new HashMap<>();
			final NodeList nlList = doc.getElementsByTagName("item");
			for(int i=0; i<nlList.getLength(); i++) {
				final Node nNode = nlList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					final Element eElement = (Element) nNode;
					if(getTagValue("cityName", eElement).equals("중랑구")) {
						airInfo.put("pm10Value", getTagValue("pm10Value", eElement));
					}
				}
			}
			final int pm10Value = !airInfo.get("pm10Value").equals("-") ? Integer.parseInt(airInfo.get("pm10Value")) : 999;
			if(pm10Value <= 30) {
				airInfo.put("airStatus", "좋음");
			} else if(pm10Value <= 80) {
				airInfo.put("airStatus", "보통");
			} else if(pm10Value <= 150) {
				airInfo.put("airStatus", "나쁨");
			} else {
				airInfo.put("airStatus", "매우나쁨");
			}

			return airInfo;
		
		//} catch (Exception e) {
		//	return null;
		//}		
	}
	
	
	@Override
	public List <Map<String, Object>> getHoliday(HttpServletRequest request) {
		
		String yy = request.getParameter("yy");		//?yy=2020
		String mm = request.getParameter("mm");		//?mm=01

		String ym = request.getParameter("ym");		//?ym=202001
		if (yy != null && mm != null) {
			ym = yy +mm;
		}
		String ymd = request.getParameter("ymd"); 	//?ymd=20201101,20201102,20201111,20201112
		
		List<String> codeList = new ArrayList<String>();
		Map<String, Object> maps = new HashMap<>();
		if (ym != null) {
			maps.put("YM", ym);	

		} else if (ymd != null) {
			codeList.add(ymd);
			maps.put("array", codeList);	
			
		} else {
			Date from = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
			ymd = transFormat.format(from);
			codeList.add("0000000");
			codeList.add(ymd);
			maps.put("array", codeList);
		}
		
		
	
		return etcMapper.getHoliday(maps);
	}

	@Override
	public Map<String, Object> scc(Map<String, Object> requestMap, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		String msg="";
		String location="";
		try {
			if(requestMap.get("sccCardNo").equals("") || requestMap.get("sccCardNo") == null) {				
				msg="카드번호 정보가 없습니다.";
				location=(String)requestMap.get("fail_url");
			}else {
				String decrypt = decrypt((String)requestMap.get("sccCardNo"));			
				String[] value = decrypt.split("-");
				String decryptCardNo = value[0];
				
				requestMap.put("decryptCardNo", decryptCardNo);					
				resultMap = etcMapper.scc(requestMap);
				if(resultMap == null) {
					resultMap = new HashMap<String, Object>();
					msg="일치하는 회원을 찾을 수 없습니다.";
					location=(String)requestMap.get("fail_url");
				}else {				
					//시큐리티 로그인 시켜야한다..
					final UserDetails account = accountService.loadUserByUsername((String)resultMap.get("id"));
					final Authentication auth = new UsernamePasswordAuthenticationToken(account, account.getPassword(), account.getAuthorities());
					final SecurityContext sc = SecurityContextHolder.getContext();
					final HttpSession session = request.getSession(true);
					sc.setAuthentication(auth);
					session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
					
					location=(String)requestMap.get("success_url");
				}
			}
			
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException
				| IllegalBlockSizeException | BadPaddingException e) {			
			msg="복호화에 실패하였습니다.";
			location=(String)requestMap.get("fail_url");
			e.printStackTrace();
		}
		resultMap.put("msg", msg);
		resultMap.put("location", location);
		return resultMap;
	}

	public static String encrypt(String unCrypt) throws InvalidKeyException, UnsupportedEncodingException,
	    NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
	  return encrypt(unCrypt, key);
	}
	
	public static String decrypt(String crypt) throws InvalidKeyException, NoSuchAlgorithmException,
	    NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
	  return decryptAES(crypt, key);
	}
	
	public static String encrypt(String text, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException,
	    NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	  if (text == null || text.length() == 0) {
	    return text;
	  }
	  String encrypted = null;
	  byte[] source = text.getBytes("UTF-8");
	  SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	
	  Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
	  cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	  int mod = source.length % 16;
	  byte[] changeSource = null;
	  if (mod != 0) {
	    changeSource = new byte[source.length + (16 - mod)];
	    System.arraycopy(source, 0, changeSource, 0, source.length);
	  } else {
	    changeSource = source;
	  }
	  encrypted = byteArrayToHex(cipher.doFinal(changeSource));
	  return encrypted;
	}
	
	public static String decryptAES(String s, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
	    InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
	  if (s == null || s.length() == 0) {
	    return s;
	  }
	  String decrypted = null;
	  SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
	
	  Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
	  cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	  decrypted = new String(cipher.doFinal(hexToByteArray(s)), "UTF-8");
	  return decrypted.trim();
	}
	
	private static byte[] hexToByteArray(String s) {
	  byte[] retValue = null;
	  if (s != null && s.length() != 0) {
	    retValue = new byte[s.length() / 2];
	    for (int i = 0; i < retValue.length; i++) {
	      retValue[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
	    }
	  }
	  return retValue;
	}
	
	private static String byteArrayToHex(byte buf[]) {
	  StringBuffer strbuf = new StringBuffer();
	  for (int i = 0; i < buf.length; i++) {
	    strbuf.append(String.format("%02X", buf[i]));
	  }
	
	  return strbuf.toString();
	}
	

	/**########################################################
	 * 키오스크 API 시작 
	 *#########################################################
	 */
	//정수클로벌 키오스크 일일권 종류/가격 정보를 제공하는 API를 요청합니다	
	@Override
	public String apiKioskDayItemList(HttpServletRequest request) {					        

		String gtype 		= request.getParameter("gtype").trim();
		String COMCD 		= request.getParameter("comcd").trim();			//사업장
		String SPORTS_CD 	= request.getParameter("spcd"); //종목(수영,헬스..)
		String USE_TYPE 	= request.getParameter("u_type");  //평일,주말
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("gtype", gtype); //
		maps.put("COMCD", COMCD);
		maps.put("SPORTS_CD", SPORTS_CD);
		maps.put("USE_TYPE", USE_TYPE);
		
		//회원조회/*
		List<Map <String,Object>> mapss = new ArrayList<Map<String, Object>>();
		mapss = etcMapper.apiKioskDayItemList(maps);
		
		String rtn = "";
		Gson gson = new Gson();
		JsonObject jsonobject = new JsonObject();
		JsonObject name1Info = new JsonObject();		
		JsonArray jsonArray = new JsonArray();
		/*
		if (maps == null) {
	        name1Info.addProperty("responseCode", "0");
	        name1Info.addProperty("responseData", "");
		} else {
			
			rtn = new Gson().toJson(mapss);
		}*/
		//jsonobject.add("response", name1Info);
		//rtn = gson.toJson(jsonobject);
		rtn = name1Info.toString();
		System.out.println(rtn);
		rtn = new Gson().toJson(mapss);
		return rtn;
		//return null;
        
	}
	
	//일일권 매출 발생시 귀사의 DB에 등록할 수 있도록 매출 정보를 전송할 API가 필요합니
	@Override
	@Transactional
	public String kioskDayInsert(Map<String, Object> requestMap, HttpServletRequest request) {

		
	   	//	정보가져오기
   		String prgList = (String)requestMap.get("PGR");//강습반 및 프로그램 정보
   		JSONArray aPrgList = JSONArray.fromObject(prgList);
   		
   		String payList = (String)requestMap.get("PAY");//
   		JSONArray aPayList = JSONArray.fromObject(payList);
   		

  		//select * from PAY_LIST
   		String NEXT_RECEIPT_NO = mapper.getNextReceiptNo();

   		//현금영수번호 가 없을경우 보조추가용
   		String NEXT_APP_NO = "";

   		//카드 결제정보  ##################################
   		String COMCD		= aPayList.getJSONObject(0).getString("COMCD").toString(); //COMCD
   		String USER_ID 		= aPrgList.getJSONObject(0).getString("USER_NO").toString(); //kiosk1, 2,3,....
       	String APP_DATE 	= aPayList.getJSONObject(0).getString("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
       	String APP_NO 		= aPayList.getJSONObject(0).getString("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
       	String APP_TIME 	= aPayList.getJSONObject(0).getString("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
       	String REAL_CARD_CD = aPayList.getJSONObject(0).getString("CARD_CD").toString(); //결제수단코드
       	String CARD_NAME	= aPayList.getJSONObject(0).getString("CARD_NO").toString(); //카드사명
       	String CARD_NO 		= aPayList.getJSONObject(0).getString("CARD_NO").toString(); //카드번호
       	String HALBU_CNT	= aPayList.getJSONObject(0).getString("HALBU_CNT").toString(); //할부
       	String REMARK		= aPayList.getJSONObject(0).getString("REMARK").toString(); //할부
       	
       	String P_COMCD		= "KIS"; //결제업체
   		String P_TYPE 		= "CARD";
   		String APP_GBN		= "0";
   		String ymdhis 		= FormatUtil.getDefaultDate(1, "-","");
   		String yyyymmdd		= FormatUtil.getDefaultDate(2, "-","");
   		String MEM_NO 		= "00000000";
   		
   		String METHOD_CD = mapper.getMethodCd(CARD_NAME);
       	
   		Map<String, Object> maps;
   		//상품(품목) 리스트 ##################################
  		for (int jj=0;jj < aPrgList.size(); jj++ ) {
  			
 			//프로그램/사물함구분 PRG:프로그램, LOCKER:사물함, DEPOSIT:사물함보증금
			String CLASS_CD = ""; //일일이용 사용안함 aPrgList.getJSONObject(jj).getString("CLASS_CD").toString();
			String PART_CD = aPrgList.getJSONObject(jj).getString("PART_CD").toString();
			String SPORTS_CD = aPrgList.getJSONObject(jj).getString("SPORTS_CD").toString();
			String ITEM_CD = aPrgList.getJSONObject(jj).getString("ITEM_CD").toString();
			//String ITEM_SDATE = aPrgList.getJSONObject(jj).getString("ITEM_SDATE").toString();
			//String ITEM_EDATE = aPrgList.getJSONObject(jj).getString("ITEM_EDATE").toString();
			//String DCREASON_CD = aPrgList.getJSONObject(jj).getString("DCREASON_CD").toString();
			//String DCREASON_CD2 = aPrgList.getJSONObject(jj).getString("DCREASON_CD2").toString();
			
			
			int BASE_AMT = aPrgList.getJSONObject(jj).getInt("BASE_AMT"); //판매금액
			
			String SALE_NUM = aPrgList.getJSONObject(jj).getString("SALE_NUM").toString(); //판매수량
			int USE_CNT = 0; //aPrgList.getJSONObject(jj).getInt("USE_CNT"); //이용기간내횟수 = 0; //이용횟수
			int DC_AMT = 0; //aPrgList.getJSONObject(jj).getInt("DC_AMT"); //할인금액
			String VAT_YN = aPrgList.getJSONObject(jj).getString("VAT_YN").toString(); //부가세여부YN
			int VAT_AMT = aPrgList.getJSONObject(jj).getInt("VAT_AMT"); //부가세금액
			String CALMEM_NO = aPrgList.getJSONObject(jj).getString("CALMEM_NO").toString(); //이용회원번호
			int DEPOSIT_AMT = aPrgList.getJSONObject(jj).getInt("DEPOSIT_AMT"); // 보증금
			//String REMARK 	=  arrayREMARK[jj]; //비고
			
		  	//다음정산번호 가져오기 select * from PAY_LIST
  	   		String NEXT_SLIP_NO = mapper.getNextSlipNo();
  	   		
  	   		//승인번호cnt
	  	   	if (jj > 0) {
	   			APP_NO = APP_NO +"_"+ jj;
	   		}
  	   		
			//주문_결제정보(CALC_MASTER) 저장  
	   		//select * from CALC_MASTER order by  WRITE_DH desc
			maps = new HashMap<String, Object>();
			maps.put("COMCD", COMCD);
			maps.put("USER_ID", USER_ID); //kiosk1,2,3..
			maps.put("RECEIPT_NO", NEXT_RECEIPT_NO);	  	
			maps.put("SLIP_NO", NEXT_SLIP_NO);
			maps.put("MEM_NO", MEM_NO);
			maps.put("PAY_AMT", BASE_AMT);//결제금액
			maps.put("CASH_AMT",0);
			maps.put("CARD_AMT",BASE_AMT);
 	
	  		maps.put("APP_DATE", APP_DATE);//승인일시__van또는pg또는현금영수증
	  		maps.put("APP_NO", APP_NO);//승인번호__van또는pg또는현금영수증
	  		maps.put("APP_GBN", APP_GBN);//승인구분
	  		maps.put("APP_TIME", APP_TIME);//승인시분Hi__van또는pg또는현금영수증  		  		
	  		maps.put("P_COMCD", P_COMCD);//결제업체
	  		maps.put("P_TYPE", P_TYPE);//지불수단
	    	maps.put("METHOD_CD", METHOD_CD);//지불수단코드(카드사코드  ,현금:00)
	    	maps.put("CHANGE_YN", "N");//변경여부
	    	maps.put("CANCEL_YN", "N");//승인취소여부YN
	    	maps.put("PAY_SEQ", jj);//결제 순번(카드,현금 2건이상경우 순번)

	    	maps.put("SEC_CARD_NO1", CARD_NO);//카드번호1
	    	
	    	maps.put("CARD_SEC", METHOD_CD);//카드사 코드
	    	maps.put("CARD_SEC2", "");//
	    	maps.put("CARD_INFO", REAL_CARD_CD);//실제 결제 카드사정보
	    	maps.put("HALBU_CNT", HALBU_CNT);//카드사 할부
			maps.put("PAY_AMT", BASE_AMT);//결제금액
	    	maps.put("APP_AMT", BASE_AMT);//결제금액
	    	
	    	maps.put("STORE_NO", "");//???????????
	    	maps.put("PAY_LIST_YN", "");//
	    	maps.put("WRITER", USER_ID);//운영자
	    	maps.put("WRITE_DH", ymdhis);//
    	
	    	//
	    	/*
	    	 select * from PAY_LIST  order by  WRITE_DH desc
	    	 select * from card_app_hist_damo  order by  WRITE_DH desc
	    	 */
		  	//결제정보 저장
			mapper.setPayList(maps);
			//일마감관리 > 일마감관리 > 카드결제처리현황
			mapper.setPayList2(maps);

  	   		//주문_결제정보(CALC_MASTER) 저장
	  		mapper.setCalcMaster(maps);
	  		
			maps.put("ACT_MODE", "I"); //추가(I), 수정(E) 여부
			maps.put("PART_CD", PART_CD);//업장코드
			maps.put("SPORTS_CD", SPORTS_CD);//종목코드(CM_SPORTS_CD)
			maps.put("ITEM_CD", ITEM_CD);//프로그램코드
			maps.put("SLIP_NO", NEXT_SLIP_NO);//정산번호
			maps.put("SALE_DATE", yyyymmdd);
			maps.put("ITEM_SDATE", yyyymmdd);//	이용시작일Ymd
			maps.put("ITEM_EDATE", yyyymmdd);//	이용종료일Ymd
			maps.put("DCREASON_CD2", "");//	감면대상할인__공통코드참조
			maps.put("DCREASON_CD", "0");//	감면대상할인__공통코드참조
			maps.put("COST_AMT", BASE_AMT);//	판매원가
			maps.put("UNIT_AMT", BASE_AMT);//	판매단가
			maps.put("SALE_NUM", SALE_NUM);//	판매수량
			maps.put("USE_CNT", USE_CNT);//	이용기간내횟수
			maps.put("DC_AMT", DC_AMT);//	할인금액
			maps.put("SALE_AMT", BASE_AMT);//	판매금액
			maps.put("VAT_YN", VAT_YN);//	부가세여부YN
			maps.put("VAT_AMT", VAT_AMT);//	부가세금액
			maps.put("WEB_TYPE", "OFFLINE");//	온오프라인구분
			maps.put("CALMEM_NO", CALMEM_NO);//	이용회원번호??
			maps.put("TRANSFER_GBN", "N");//	미사용인듯__전부N임
			maps.put("MIDCANCEL_YN", "N");//	중도해약여부YN  	
			maps.put("SALE_REL_NO", 0);
			maps.put("CHANGE_YN", "N");//	변경상태__Y_변경후강습__B_변경전강습__N_변경없음
			
			maps.put("RETURN_YN", "N");//	미사용인듯__전부N임
			maps.put("CANCEL_YN", "N");//	결제당일취소여부YN
			maps.put("PROMOTION_YN", "N");//	미사용인듯__전부N임
			maps.put("OLD_PROMOTION_YN", "N");//	미사용인듯__전부N임
			maps.put("SALE_SEC", "01"); //매출구분(SM_SALE_SEC)-01:정상
			maps.put("REMARK", REMARK);
	    	
	  		//등록강습반 및 프로그램 저장
			//select * from mem_sale order by  WRITE_DH desc
			mapper.setMemSale(maps);  
	  		
	  		String NEXT_SALE_SEQ = maps.get("NEXT_SALE_SEQ").toString();
	  		
	  		
	  		//Map<String, Object> requestMapTrainHist = new HashMap<String, Object>();

  		/*
	  		//회원_강습신청내역 저장
    	  	maps.put("ACT_MODE", "I"); //추가(I), 수정(E) 여부
    	  	maps.put("SALE_SEQ", NEXT_SALE_SEQ);//가입프로그램신청순번
    	  	maps.put("CLASS_CD", CLASS_CD);//강습반__TRAIN_CLASS__CLASS_CD
    		maps.put("TRAIN_SDATE", yyyymmdd);//이용시작일_Ymd
    		maps.put("TRAIN_EDATE", yyyymmdd);//이용종료일_Ymd
    		maps.put("USER_NO", USER_NO);//강사ID						
			maps.put("CHANGE_YN", "N");//	변경상태__Y_변경후강습__B_변경전강습__N_변경없음
    	  	if ("".equals(USER_NO)) {
    	  		maps.put("ASSIGN_YN", "N");//강사여부
    	  	} else {
    	  		maps.put("ASSIGN_YN", "Y");//강사여부
    	  	}
    	
    	  	mapper.setTrainHist(maps);
  		*/

  		}
   		
   		
/*
  		//등록강습반 및 프로그램 저장
  		String prgList = (String)requestMap.get("PRG");//강습반 및 프로그램 정보
  		String payList = (String)requestMap.get("PAY");//주문상품 정보
  		
  		JSONArray aPrgList = JSONArray.fromObject(prgList);
  		JSONArray aPayList = JSONArray.fromObject(payList);
  		String MEM_NO = (String)requestMap.get("MEM_NO");
  		String CH_DATE = (String)requestMap.get("CH_DATE"); //일일매출 매출일자변경
  		CH_DATE = (CH_DATE == null) ? "" : CH_DATE;
  		String [] arrayREMARK = new String[20]; //비고 메모 
		
  		String userId = (String)requestMap.get("userId");
  		String comCd = (String)requestMap.get("comCd");
  		String userNm = (String)requestMap.get("userNm");
  		String zeroCashPay = "N";//0원 현금결제인가 ?;
  		try {
  			zeroCashPay = (String)requestMap.get("zeroCashPay");
  		}
  		catch (Exception e) {
  			zeroCashPay = "N";
		}
  		

  		//ACT_MODE : "Change" 강좌 변경
  		String ACT_MODE = (String)requestMap.get("ACT_MODE");
		//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
  		
  		int CASH_AMT_SUM = 0;//현금결제금액
  		int CARD_AMT_SUM = 0;//카드결제금액
  		int RECEIVE_AMT = 0; //받은금액
  		int iDEPOSIT_AMT = 0; //보증금
  	

   		


		
    	String yyyymmddd = "";
    	//매출일자 변경(수동일자)
    	if (!"".equals(CH_DATE)) {
    		yyyymmddd = CH_DATE;
    	} else {
    		yyyymmddd = EgovDateUtil.getDefaultDate(2, "","");
    	}
    	
   		
   		
    	//영수번호
  		//select * from PAY_LIST
   		String NEXT_RECEIPT_NO = payManageMapper.getNextReceiptNo();
   		
   		
   		   		
   		
    	int TMP_CASH = CASH_AMT_SUM;
    	int APP_NO_CNT = 0; //카드 중복저장시 문제 Duplicate entry 'JUNGNANG01-20210209144424-0209144424' for key 'PRIMARY'
    	// 카드결제번호 중복첵크해서 카드정보 2번등록되면 중복안되게 첵크하기위해서
    	int pri_cash=0;
    	int pri_card=0;
  		String saveType = "";
  		//상품(품목) 리스트
  		for (int jj=0;jj<aPrgList.size();jj++ ) {       	
  			
  			//프로그램/사물함구분 PRG:프로그램, LOCKER:사물함, DEPOSIT:사물함보증금
  			//String GUBUN = aPrgList.getJSONObject(jj).getString("GUBUN").toString();
			String CLASS_CD = aPrgList.getJSONObject(jj).getString("CLASS_CD").toString();
			String PART_CD = aPrgList.getJSONObject(jj).getString("PART_CD").toString();
			String SPORTS_CD = aPrgList.getJSONObject(jj).getString("SPORTS_CD").toString();
			String ITEM_CD = aPrgList.getJSONObject(jj).getString("ITEM_CD").toString();
			String ITEM_SDATE = aPrgList.getJSONObject(jj).getString("ITEM_SDATE").toString();
			String ITEM_EDATE = aPrgList.getJSONObject(jj).getString("ITEM_EDATE").toString();
			String DCREASON_CD = aPrgList.getJSONObject(jj).getString("DCREASON_CD").toString();
			String DCREASON_CD2 = aPrgList.getJSONObject(jj).getString("DCREASON_CD2").toString();
			String USER_NO = aPrgList.getJSONObject(jj).getString("USER_NO").toString();
			
			int COST_AMT = aPrgList.getJSONObject(jj).getInt("COST_AMT"); //판매원가
			int UNIT_AMT = aPrgList.getJSONObject(jj).getInt("UNIT_AMT"); //판매단가
			int SALE_AMT = aPrgList.getJSONObject(jj).getInt("SALE_AMT"); //판매금액
			
			String SALE_NUM = aPrgList.getJSONObject(jj).getString("SALE_NUM").toString(); //판매수량
			int USE_CNT = aPrgList.getJSONObject(jj).getInt("USE_CNT"); //이용기간내횟수 = 0; //이용횟수
			int DC_AMT = aPrgList.getJSONObject(jj).getInt("DC_AMT"); //할인금액
			String VAT_YN = aPrgList.getJSONObject(jj).getString("VAT_YN").toString(); //부가세여부YN
			int VAT_AMT = aPrgList.getJSONObject(jj).getInt("VAT_AMT"); //부가세금액
			String CALMEM_NO = aPrgList.getJSONObject(jj).getString("CALMEM_NO").toString(); //이용회원번호
			int DEPOSIT_AMT = aPrgList.getJSONObject(jj).getInt("DEPOSIT_AMT"); // 보증금
			String REMARK 	=  arrayREMARK[jj]; //비고
			
			if ("".equals(CALMEM_NO)) {
				CALMEM_NO = MEM_NO;
			}
			
				  			
	   		//다음정산번호 가져오기 select * from PAY_LIST
	   		String NEXT_SLIP_NO = getNextSlipNo();
	   		
	   		//현금영수번호 가 없을경우 보조추가용
	   		String NEXT_APP_NO = getNextAppNo();
		

	
	   		pri_cash = 0;
				pri_card = 0;
	   		
	   		//카드,현금 결제 금액 정보 저장
	   		//현금만 일경우
	   		if (paytype.equals("cash")) {
	   			pri_cash = SALE_AMT;
	   		//카드만 일경우
	   		} else if (paytype.equals("card")) {
	   			pri_card = SALE_AMT;
	   		//현금 + 카드
	   		} else if (paytype.equals("cpc")) {
	   			
	   			//현금 기준 차감
	   			if (TMP_CASH >= SALE_AMT ) {
	   				TMP_CASH = TMP_CASH -SALE_AMT;
	   				pri_cash = SALE_AMT;
	   				pri_card = 0;
	   				
	   			//현금잔액 나머지 처리
	   			} else if (TMP_CASH > 0) {
	   				pri_cash = TMP_CASH;
	   				pri_card = SALE_AMT - TMP_CASH;
	   				TMP_CASH = 0;
	   				
	   			//나머지 카드처리
	   			} else {
	   				
	   				pri_cash = 0;
	   				pri_card = SALE_AMT;
	   			}
	   			
   			
	   		}

	   		//
	   		for (int ii=0;ii<aPayList.size();ii++ ) {
		  		
		   		
		   		requestMap.put("RECEIPT_NO", NEXT_RECEIPT_NO);
		   		requestMap.put("SLIP_NO", NEXT_SLIP_NO);
		   		requestMap.put("APP_NO", NEXT_APP_NO);
		   		
	  			String P_COMCD =  aPayList.getJSONObject(ii).getString("P_COMCD").toString(); //결제업체코드 KIS
	  			String P_TYPE =  aPayList.getJSONObject(ii).getString("P_TYPE").toString(); //결제수단구분(CARD,CASH,ZERO)
	  			
	  			//if (P_TYPE.equals("CARD")) {
	  			
		  	  		int PAY_AMT = aPayList.getJSONObject(ii).getInt("PAY_AMT"); //결제금액
		  			
		  			//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드 카드사코드.....)
		  			String METHOD_CD =  aPayList.getJSONObject(ii).getString("METHOD_CD").toString(); //결제수단코드
		       		String APP_DATE = aPayList.getJSONObject(ii).getString("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
		       		String APP_NO 	= aPayList.getJSONObject(ii).getString("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
		       		String APP_TIME = aPayList.getJSONObject(ii).getString("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
			    	
		       		//매출일자 변경(수동일자)
			    	//if (!"".equals(CH_DATE)) {
			    	//	APP_DATE = " concat(DATE_FORMAT('"+ CH_DATE +"', '%Y%m%d') , DATE_FORMAT('"+ ymdhis +"', '%H%i%s')) ";
			    		//System.out.println("APP_DATE "+ APP_DATE);
			    	//}
			    	
		       		if (APP_NO_CNT > 0) {
		       			APP_NO = APP_NO +"_"+ APP_NO_CNT;
		       		}
		       		
		       		//카드번호는 1번에 다 넣는걸로
		       		String APP_CARD_NO = aPayList.getJSONObject(ii).getString("APP_CARD_NO").toString(); //카드 번호
		       		String SEC_CARD_NO1 = APP_CARD_NO;
		       		String SEC_CARD_NO2 = "";
		       		String SEC_CARD_NO3 = "";
		       		String SEC_CARD_NO4 = "";
			       	
		       		String REAL_CARD_INFO = "";
		       		String APP_CARD_CD = aPayList.getJSONObject(ii).getString("APP_CARD_CD").toString(); //카드사 코드
		       		REAL_CARD_INFO = APP_CARD_CD;
		       		
		       		//APP_CARD_CD = APP_CARD_CD.substring(0,2); //카드사코드 2자리
		       		//카드사 코드 기존 중랑에 맞도록 변환
		       		if (!APP_CARD_CD.equals("") && APP_CARD_CD != null) {
			       		Map <String, Object> maps = new HashMap<>();
			       		maps.put("CARD_SEC", APP_CARD_CD);
			       		
			       		maps = getChangeCardcd(maps);
			       		if (maps == null) {
			       			APP_CARD_CD = "98";	
			       		} else {
			       			APP_CARD_CD = (String) maps.get("METHOD_CD");
			       		//String APP_CARD_NM = (String) maps.get("METHOD_NM");
			       		}
		       		}
		       		if (P_TYPE.equals("CARD") || P_TYPE.equals("ZERO")) {
		       			METHOD_CD =APP_CARD_CD;
		       		}
		       		
		       		String APP_CARD_HALBU = aPayList.getJSONObject(ii).getString("APP_CARD_HALBU").toString(); //카드사 할부       		
		       		String APP_CASH_INFO = aPayList.getJSONObject(ii).getString("APP_CASH_INFO").toString(); //현금영수 입력정보			       		
		       		
		       		//승인구분__1_카드결제승인__2_카드승인취소__3_현금영수증승인__4_현금영수증승인취소
		       		String APP_GBN	= "0";
		       		if (P_TYPE.equals("CARD") || P_TYPE.equals("ZERO")) {
		       			APP_GBN = "1";
		       		} else if (!APP_CASH_INFO.equals("")) {
		       			APP_GBN = "3";
		       		}
		       		
		  			Map<String, Object> requestMapPayList = new HashMap<String, Object>();
		  			
			  		requestMapPayList.put("COMCD", comCd);
			  		requestMapPayList.put("USER_ID", userId);
			  		requestMapPayList.put("MEM_NO", MEM_NO);
			  		requestMapPayList.put("SLIP_NO", NEXT_SLIP_NO);  	
			  		requestMapPayList.put("RECEIPT_NO", NEXT_RECEIPT_NO);
			  		requestMapPayList.put("APP_DATE", APP_DATE);//승인일시__van또는pg또는현금영수증
			  		requestMapPayList.put("APP_NO", APP_NO);//승인번호__van또는pg또는현금영수증
			  		requestMapPayList.put("APP_GBN", APP_GBN);//승인구분
			  		requestMapPayList.put("APP_TIME", APP_TIME);//승인시분Hi__van또는pg또는현금영수증  		  		
			  		requestMapPayList.put("P_COMCD", P_COMCD);//결제업체
			  		requestMapPayList.put("P_TYPE", P_TYPE);//지불수단
			    	requestMapPayList.put("METHOD_CD", METHOD_CD);//지불수단코드(카드사코드  ,현금:00)
			    	requestMapPayList.put("CHANGE_YN", "N");//변경여부
			    	requestMapPayList.put("CANCEL_YN", "N");//승인취소여부YN
			    	requestMapPayList.put("PAY_SEQ", ii);//결제 순번(카드,현금 2건이상경우 순번)

			    	requestMapPayList.put("SEC_CARD_NO1", SEC_CARD_NO1);//카드번호1
			    	requestMapPayList.put("SEC_CARD_NO2", SEC_CARD_NO2);//카드번호1
			    	requestMapPayList.put("SEC_CARD_NO3", SEC_CARD_NO3);//카드번호1
			    	requestMapPayList.put("SEC_CARD_NO4", SEC_CARD_NO4);//카드번호1
			    	
			    	requestMapPayList.put("CARD_SEC", APP_CARD_CD);//카드사 코드
			    	requestMapPayList.put("CARD_SEC2", "");//
			    	requestMapPayList.put("CARD_INFO", REAL_CARD_INFO);//실제 결제 카드사정보
			    	requestMapPayList.put("HALBU_CNT", APP_CARD_HALBU);//카드사 할부
			    	
			    	
			    	requestMapPayList.put("CASH_USER_INFO", APP_CASH_INFO);//현금영수 정보
			    	
			    	requestMapPayList.put("STORE_NO", "");//???????????
			    	requestMapPayList.put("PAY_LIST_YN", "");//
			    	requestMapPayList.put("WRITER", userNm);//운영자
			    	requestMapPayList.put("CH_DATE", CH_DATE);
		    	
			    	
			    	//
			    	
			    	// select * from PAY_LIST  order by  WRITE_DH desc
			    	// select * from card_app_hist_damo  order by  WRITE_DH desc
			    	
			    	//현금 결제정보 저장
					if ((P_TYPE.equals("CASH") && pri_cash > 0) || "Y".equals(zeroCashPay)) {
			       		
						requestMapPayList.put("PAY_DATE", yyyymmddd);//결제일
			       		
				    	//결제정보 저장
						requestMapPayList.put("PAY_AMT", pri_cash);//결제금액
						payManageMapper.setPayList(requestMapPayList);
													
						//현금영수증일경우 카드테이블에 저장
						if (APP_GBN == "3") {
							requestMapPayList.put("APP_AMT", pri_cash);//승인금액 정보
							payManageMapper.setPayList2(requestMapPayList);
						}
					}
					
					//카드 결제정보 저장
					if ((P_TYPE.equals("CARD") || P_TYPE.equals("ZERO")) && pri_card > 0) {
						
						//카드승인이면 오늘날짜
		       			String tmpDay = EgovDateUtil.getDefaultDate(2, "","");
		       			requestMapPayList.put("PAY_DATE", tmpDay);//결제일
						
						requestMapPayList.put("PAY_AMT", pri_card);//결제금액
						requestMapPayList.put("APP_AMT", pri_card);//승인금액 정보
				    	//결제정보 저장
						payManageMapper.setPayList(requestMapPayList);
				   		//일마감관리 > 일마감관리 > 카드결제처리현황
						payManageMapper.setPayList2(requestMapPayList);
						
						//카드중복저장 오류시 안뜨도록
						APP_NO_CNT++;
					}
					
					
	  			//}
	  		}
	   		

	   		
			//주문_결제정보(CALC_MASTER) 저장  
	   		//select * from CALC_MASTER order by  WRITE_DH desc
			Map<String, Object> requestMapCalcMaster = new HashMap<String, Object>();
			requestMapCalcMaster.put("COMCD", comCd);
		  	requestMapCalcMaster.put("USER_ID", userId);
		  	requestMapCalcMaster.put("SALE_DATE", yyyymmddd);
		  	requestMapCalcMaster.put("RECEIPT_NO", NEXT_RECEIPT_NO);	  	
			requestMapCalcMaster.put("SLIP_NO", NEXT_SLIP_NO);
			requestMapCalcMaster.put("MEM_NO", MEM_NO);
			requestMapCalcMaster.put("PAY_AMT", pri_cash + pri_card);//결제금액
			
			requestMapCalcMaster.put("CASH_AMT",pri_cash);
			requestMapCalcMaster.put("CARD_AMT",pri_card);
			
			requestMapCalcMaster.put("REMARK",arrayJoin(arrayREMARK,","));
			
			//주문_결제정보(CALC_MASTER) 저장
			payManageMapper.setCalcMaster(requestMapCalcMaster);
	  		
	  		
			
	  		//////////////////////
	  		//회원가입프로그램
	  		//////////////////////
			if ("PRG".equals(GUBUN)){
				Map<String, Object> requestMapMemSale = new HashMap<String, Object>();
				requestMapMemSale.put("USER_ID", userId);
				requestMapMemSale.put("COMCD", comCd);
				requestMapMemSale.put("ACT_MODE", "I"); //추가(I), 수정(E) 여부
				requestMapMemSale.put("MEM_NO", MEM_NO);//	회원번호
				requestMapMemSale.put("PART_CD", PART_CD);//업장코드
				requestMapMemSale.put("SPORTS_CD", SPORTS_CD);//종목코드(CM_SPORTS_CD)
				requestMapMemSale.put("ITEM_CD", ITEM_CD);//프로그램코드
				requestMapMemSale.put("SLIP_NO", NEXT_SLIP_NO);//정산번호
				requestMapMemSale.put("SALE_DATE", yyyymmddd);//결제일
				requestMapMemSale.put("MEM_NO", MEM_NO);//	회원번호
				requestMapMemSale.put("ITEM_SDATE", ITEM_SDATE);//	이용시작일Ymd
				requestMapMemSale.put("ITEM_EDATE", ITEM_EDATE);//	이용종료일Ymd
				requestMapMemSale.put("DCREASON_CD2", DCREASON_CD2);//	감면대상할인__공통코드참조
				requestMapMemSale.put("DCREASON_CD", DCREASON_CD);//	감면대상할인__공통코드참조
				requestMapMemSale.put("COST_AMT", COST_AMT);//	판매원가
				requestMapMemSale.put("UNIT_AMT", UNIT_AMT);//	판매단가
				requestMapMemSale.put("SALE_NUM", SALE_NUM);//	판매수량
				requestMapMemSale.put("USE_CNT", USE_CNT);//	이용기간내횟수
				requestMapMemSale.put("DC_AMT", DC_AMT);//	할인금액
				requestMapMemSale.put("SALE_AMT", SALE_AMT);//	판매금액
				requestMapMemSale.put("VAT_YN", VAT_YN);//	부가세여부YN
				requestMapMemSale.put("VAT_AMT", VAT_AMT);//	부가세금액
				requestMapMemSale.put("WEB_TYPE", "OFFLINE");//	온오프라인구분
				requestMapMemSale.put("CALMEM_NO", CALMEM_NO);//	이용회원번호??
				requestMapMemSale.put("TRANSFER_GBN", "N");//	미사용인듯__전부N임
				requestMapMemSale.put("MIDCANCEL_YN", "N");//	중도해약여부YN  		
				
		  		if ("Change".equals(ACT_MODE)) {
	    	  		requestMapMemSale.put("CHANGE_YN", "Y");//	변경상태__Y_변경후강습__B_변경전강습__N_변경없음
	    	  		requestMapMemSale.put("SALE_REL_NO", 0);// *************************************************
	    	  	} else {
	    	  		requestMapMemSale.put("CHANGE_YN", "N");//	변경상태__Y_변경후강습__B_변경전강습__N_변경없음
	    	  		requestMapMemSale.put("SALE_REL_NO", 0);// *************************************************
	    	  	}
				
				requestMapMemSale.put("RETURN_YN", "N");//	미사용인듯__전부N임
				requestMapMemSale.put("CANCEL_YN", "N");//	결제당일취소여부YN
				requestMapMemSale.put("PROMOTION_YN", "N");//	미사용인듯__전부N임
				requestMapMemSale.put("OLD_PROMOTION_YN", "N");//	미사용인듯__전부N임
				requestMapMemSale.put("SALE_SEC", "01"); //매출구분(SM_SALE_SEC)-01:정상
				requestMapMemSale.put("REMARK", REMARK);
		    	
		  		//등록강습반 및 프로그램 저장
		  		memSaleManageService.setMemSale(requestMapMemSale);  
		  		
		  		//회원_강습신청내역 저장
		  		String NEXT_SALE_SEQ = requestMapMemSale.get("NEXT_SALE_SEQ").toString();
		  		requestMap.put("SALE_SEQ", NEXT_SALE_SEQ);
		  		
		  		Map<String, Object> requestMapTrainHist = new HashMap<String, Object>();

		  		requestMapTrainHist.put("USER_ID", userId);
	    	  	requestMapTrainHist.put("COMCD", comCd);
	    	  	requestMapTrainHist.put("ACT_MODE", "I"); //추가(I), 수정(E) 여부
	    	  	requestMapTrainHist.put("MEM_NO", MEM_NO);//	회원번호
	    	  	requestMapTrainHist.put("SALE_SEQ", NEXT_SALE_SEQ);//가입프로그램신청순번

	    	  	requestMapTrainHist.put("CLASS_CD", CLASS_CD);//강습반__TRAIN_CLASS__CLASS_CD
	    		requestMapTrainHist.put("TRAIN_SDATE", ITEM_SDATE);//이용시작일_Ymd
	    		requestMapTrainHist.put("TRAIN_EDATE", ITEM_EDATE);//이용종료일_Ymd
	    		requestMapTrainHist.put("USER_NO", USER_NO);//강사ID
	    	  	
	    	  	requestMapTrainHist.put("CHNG_DATE", "");//강습반변경일_Ymd
	    	  	requestMapTrainHist.put("CHANGE_YN", "N");//	강습반변경여부YN
	    	  	
	    	  	if ("".equals(USER_NO)) {
	    	  		requestMapTrainHist.put("ASSIGN_YN", "N");//강사여부
	    	  	} else {
	    	  		requestMapTrainHist.put("ASSIGN_YN", "Y");//강사여부
	    	  	}
	    	
	    	  	//select * from train_hist
	  			memSaleManageService.setTrainHist(requestMapTrainHist);
	  							
			} else 
	  		//////////////////////
	  		//사물함임대료
	  		//////////////////////	
			if ("LOCKER".equals(GUBUN)){
			
				Map<String, Object> requestMapMemSale = new HashMap<String, Object>();
				requestMapMemSale.put("USER_ID", userId);
				requestMapMemSale.put("COMCD", comCd);

				requestMapMemSale.put("ACT_MODE", "I"); // 추가(I), 수정(E) 여부
				requestMapMemSale.put("MEM_NO", MEM_NO);// 회원번호
				requestMapMemSale.put("PART_CD", "");// 업장코드
				requestMapMemSale.put("SPORTS_CD", "");// 종목코드(CM_SPORTS_CD)
				requestMapMemSale.put("ITEM_CD", "I990001");// 프로그램코드(I990001:사물함임대료, I990002:사물함보증금)
				requestMapMemSale.put("SLIP_NO", NEXT_SLIP_NO);// 정산번호
				requestMapMemSale.put("SALE_DATE", yyyymmddd);//결제일
				requestMapMemSale.put("ITEM_SDATE", ITEM_SDATE);// 이용시작일Ymd
				requestMapMemSale.put("ITEM_EDATE", ITEM_EDATE);// 이용종료일Ymd
				requestMapMemSale.put("DCREASON_CD2", DCREASON_CD2);// 감면대상할인__공통코드참조
				requestMapMemSale.put("DCREASON_CD", DCREASON_CD);// 감면대상할인__공통코드참조

				requestMapMemSale.put("COST_AMT", COST_AMT);// 판매원가
				requestMapMemSale.put("UNIT_AMT", UNIT_AMT);// 판매단가
				requestMapMemSale.put("SALE_NUM", SALE_NUM);// 판매수량*************************************************
				requestMapMemSale.put("DC_AMT", DC_AMT);// 할인금액
				requestMapMemSale.put("SALE_AMT", SALE_AMT);// 판매금액
				requestMapMemSale.put("VAT_YN", VAT_YN);// 부가세여부YN
				requestMapMemSale.put("VAT_AMT", VAT_AMT);// 부가세금액

				requestMapMemSale.put("SALE_REL_NO", 0);// *************************************************
				requestMapMemSale.put("USE_CNT", 0);// 이용기간내횟수
				requestMapMemSale.put("WEB_TYPE", "OFFLINE");// 온오프라인구분
				requestMapMemSale.put("CALMEM_NO", CALMEM_NO);// 이용회원번호??
				requestMapMemSale.put("SALE_SEC", "01"); // 매출구분(SM_SALE_SEC)-(01:정상, 02:환불, 03:중도해약, 04:매출취소)

				requestMapMemSale.put("TRANSFER_GBN", "N");// 미사용인듯__전부N임
				requestMapMemSale.put("MIDCANCEL_YN", "N");// 중도해약여부YN
				requestMapMemSale.put("CHANGE_YN", "N");// 변경상태__Y_변경후강습__B_변경전강습__N_변경없음
				requestMapMemSale.put("RETURN_YN", "N");// 미사용인듯__전부N임
				requestMapMemSale.put("CANCEL_YN", "N");// 결제당일취소여부YN
				requestMapMemSale.put("PROMOTION_YN", "N");// 미사용인듯__전부N임
				requestMapMemSale.put("OLD_PROMOTION_YN", "N");// 미사용인듯__전부N임
				//select * from mem_sale order by  WRITE_DH desc
				memSaleManageService.setMemSale(requestMapMemSale);
				
				String NEXT_SALE_SEQ = requestMapMemSale.get("NEXT_SALE_SEQ").toString();
				requestMap.put("SALE_SEQ", NEXT_SALE_SEQ);
				
				//String NEXT_SALE_SEQ = requestMapMemSale.get("NEXT_SALE_SEQ").toString();	
				String PLACE_CD = aPrgList.getJSONObject(jj).getString("PLACE_CD").toString();// 사물함위치__공통코드참조
				String LOCKER_CD = aPrgList.getJSONObject(jj).getString("LOCKER_CD").toString();// 사물함코드
				
				Map<String, Object> requestMapLocker = new HashMap<String, Object>();
				requestMapLocker.put("USER_ID", userId);
				requestMapLocker.put("COMCD", comCd);
				requestMapLocker.put("ACT_MODE", "I"); // 추가(I), 수정(E) 여부
				requestMapLocker.put("SLIP_NO", NEXT_SLIP_NO);
				requestMapLocker.put("RECEIPT_NO", NEXT_RECEIPT_NO);
				requestMapLocker.put("MEM_NO", MEM_NO);
				requestMapLocker.put("PLACE_CD", PLACE_CD);
				requestMapLocker.put("LOCKER_CD", LOCKER_CD);
				requestMapLocker.put("RENT_MON", SALE_NUM);
				requestMapLocker.put("RENT_SDATE", ITEM_SDATE);
				requestMapLocker.put("RENT_EDATE", ITEM_EDATE);
				requestMapLocker.put("RENT_AMT", COST_AMT);
				requestMapLocker.put("DC_AMT", DC_AMT);
				requestMapLocker.put("DEPOSIT_AMT", DEPOSIT_AMT);
				requestMapLocker.put("END_YN", "N");// 반납구분__N_임대중__Y_임대종료__F_강제반납종료
				requestMapLocker.put("RENT_STAT", "01"); // 0:임대가능, 1:임대중, 2:고장, 3:키교체 ?????
				requestMapLocker.put("CANCEL_YN", "N");



				//사물함 상태 변경
				//사물함상태__0:임대가능__1:임대중__2:고장__3:키교체
				requestMapLocker.put("LOCKER_STAT", "1");
				lockerRentManageService.setLockerStat(requestMapLocker);

				//사물함임대 저장
				lockerRentManageMapper.setLockerRent(requestMapLocker);
				
			} else 
	  		//////////////////////
	  		//사물함보증금
	  		//////////////////////				
			if ("DEPOSIT".equals(GUBUN)){
				if (DEPOSIT_AMT > 0) {
					// 다음정산번호 가져오기
					NEXT_SLIP_NO = getNextSlipNo();
					//NEXT_RECEIPT_NO = payRentManageService.getNextReceiptNo();

					//주문_결제정보(CALC_MASTER) 저장
					requestMapCalcMaster.put("COMCD", comCd);
					requestMapCalcMaster.put("USER_ID", userId);
					requestMapCalcMaster.put("RECEIPT_NO", NEXT_RECEIPT_NO);
					requestMapCalcMaster.put("SLIP_NO", NEXT_SLIP_NO);
					requestMapCalcMaster.put("SALE_DATE", yyyymmddd);//결제일
					requestMapCalcMaster.put("MEM_NO", MEM_NO);
					requestMapCalcMaster.put("PAY_AMT", DEPOSIT_AMT);// 결제금액
					requestMapCalcMaster.put("CASH_AMT", DEPOSIT_AMT);
					requestMapCalcMaster.put("CARD_AMT", 0);
					requestMapCalcMaster.put("CH_DATE", CH_DATE);
					
					
					
					//주문_결제정보(CALC_MASTER) 저장
					payManageMapper.setCalcMaster(requestMapCalcMaster);

					String P_COMCD = "CASH"; // 결제업체코드
					String P_TYPE = "CASH"; // 결제수단구분

					// METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
					String METHOD_CD = "00"; // 결제수단코드
					String APP_DATE = ""; // 카드_승인일시__van또는pg또는현금영수증
					String APP_NO = ""; // 카드_승인번호__van또는pg또는현금영수증
					String APP_TIME = ""; // 카드_승인시분Hi__van또는pg또는현금영수증
					
					//현금영수번호 가 없을경우 보조추가용
			   		APP_NO = getNextAppNo();
			   		//현금결제일값 없을경우
			    	APP_DATE = EgovDateUtil.getDefaultDate(3, "","");

					Map<String, Object> requestMapPayList = new HashMap<String, Object>();

					requestMapPayList.put("COMCD", comCd);
					requestMapPayList.put("USER_ID", userId);
					requestMapPayList.put("SLIP_NO", NEXT_SLIP_NO);
					requestMapPayList.put("RECEIPT_NO", NEXT_RECEIPT_NO);
					requestMapPayList.put("PAY_DATE", yyyymmddd);//결제일
					requestMapPayList.put("PAY_AMT", DEPOSIT_AMT);// 결제금액
					requestMapPayList.put("APP_DATE", APP_DATE);// 승인일시__van또는pg또는현금영수증
					requestMapPayList.put("APP_NO", APP_NO);// 승인번호__van또는pg또는현금영수증
					requestMapPayList.put("APP_TIME", APP_TIME);// 승인시분Hi__van또는pg또는현금영수증
					requestMapPayList.put("P_COMCD", P_COMCD);// 결제업체
					requestMapPayList.put("P_TYPE", P_TYPE);// 지불수단
					requestMapPayList.put("METHOD_CD", METHOD_CD);// 지불수단코드
					requestMapPayList.put("CHANGE_YN", "N");// 변경여부
					requestMapPayList.put("CANCEL_YN", "N");// 승인취소여부YN

			    	//보증금은 현금으로
			    	requestMapPayList.put("SEC_CARD_NO1", "");//카드번호1""
			    	requestMapPayList.put("SEC_CARD_NO2", "");//카드번호1
			    	requestMapPayList.put("SEC_CARD_NO3", "");//카드번호1
			    	requestMapPayList.put("SEC_CARD_NO4", "");//카드번호1
			    	
			    	requestMapPayList.put("CARD_SEC", "");//카드사 코드
			    	requestMapPayList.put("CARD_SEC2", "");//
			    	requestMapPayList.put("HALBU_CNT", "0");//카드사 할부
			    	
			    	requestMapPayList.put("APP_AMT", DEPOSIT_AMT);//승인금액 정보
			    	requestMapPayList.put("CASH_USER_INFO", "");//현금영수 정보
			    	
			    	requestMapPayList.put("STORE_NO", "");//???????????
			    	requestMapPayList.put("PAY_LIST_YN", "");//
			    	requestMapPayList.put("WRITER", userNm);//운영자

			    	requestMapPayList.put("CH_DATE", CH_DATE);
			    	
					//보증금 정보 저장
			    	//
			    	// select * from PAY_LIST  order by  WRITE_DH desc
			    	//결제정보 저장
					//setPayList(requestMapPayList);
					payManageMapper.setPayList(requestMapPayList);
			   		//일마감관리 > 일마감관리 > 카드결제처리현황
					//카드, 현금영수증만 저장? 	payManageMapper.setPayList2(requestMapPayList);
					

					Map<String, Object> requestMapMemSale = new HashMap<String, Object>();
					requestMapMemSale.put("USER_ID", userId);
					requestMapMemSale.put("COMCD", comCd);			
					requestMapMemSale.put("MEM_NO", MEM_NO);

					// 사물함보증금(등록강습반 및 프로그램 저장)
					requestMapMemSale.put("SLIP_NO", NEXT_SLIP_NO);// 정산번호
					requestMapMemSale.put("SALE_DATE", yyyymmddd);//결제일
					
					requestMapMemSale.put("ITEM_CD", "I990002");// 프로그램코드(I990001:사물함임대료, I990002:사물함보증금)
					requestMapMemSale.put("ITEM_SDATE", "");// 이용시작일Ymd
					requestMapMemSale.put("ITEM_EDATE", "");// 이용종료일Ymd

					requestMapMemSale.put("COST_AMT", DEPOSIT_AMT);// 판매원가
					requestMapMemSale.put("UNIT_AMT", DEPOSIT_AMT);// 판매단가
					requestMapMemSale.put("SALE_NUM", 1);// 판매수량*************************************************
					requestMapMemSale.put("DC_AMT", 0);// 할인금액
					requestMapMemSale.put("SALE_AMT", DEPOSIT_AMT);// 판매금액
					requestMapMemSale.put("VAT_YN", "N");// 부가세여부YN
					requestMapMemSale.put("VAT_AMT", 0);// 부가세금액
					
					requestMapMemSale.put("TRANSFER_GBN", "N");//	미사용인듯__전부N임
					requestMapMemSale.put("MIDCANCEL_YN", "N");//	중도해약여부YN  		  	
					requestMapMemSale.put("CHANGE_YN", "N");//	변경상태__Y_변경후강습__B_변경전강습__N_변경없음
					requestMapMemSale.put("RETURN_YN", "N");//	미사용인mapsestMapMemSale.put("CANCEL_YN", "N");//	결제당일취소여부YN
					requestMapMemSale.put("PROMOTION_YN", "N");//	미사용인듯__전부N임
					requestMapMemSale.put("OLD_PROMOTION_YN", "N");//	미사용인듯__전부N임					
					
					//select * from mem_sale order by  WRITE_DH desc
					memSaleManageService.setMemSale(requestMapMemSale);
					//NEXT_SALE_SEQ = requestMapMemSale.get("NEXT_SALE_SEQ").toString();						
				}
				
				
				
			} //end if //보증금,사물함 
			
			
			//결제완료 후 일일 입장 처리 ##################################################
			requestMap.put("COMCD", comCd);
			requestMap.put("USER_ID", userId);
	  		requestMap.put("ITEM_CD", ITEM_CD);//품목코드
	  		requestMap.put("CLASS_CD", CLASS_CD);//강습반코드:일일회원은 강습반이 없음
	  		requestMap.put("ENTR_DATE", yyyymmddd);
	  		
	  		enterInfoManageMapper.setMemCheckIn(requestMap);
			
  		} //end for
  		
  		
	
  		//강좌 변경 ############################
  		if ("Change".equals(ACT_MODE)) {
  			
  			String CHNG_DATE = EgovDateUtil.getDefaultDate(2, "-","");
  			requestMap.put("CHNG_DATE", CHNG_DATE);
  			requestMap.put("MEM_NO", MEM_NO);
  			requestMap.put("CHNGR", userId);
  			requestMap.put("COMCD", comCd);
  			
  			payManageMapper.setChangeClass_step1(requestMap);
  			payManageMapper.setChangeClass_step2(requestMap);
  			
  		}
  		
  		txManager.commit(txStatus);
  		requestMap.put("resultNum", "1");
  		return requestMap;
   		
		*/
   		
		return null;
		

	}
	
}
