package com.dwict.jfmc.client.etc.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
import com.dwict.jfmc.client.mem.model.Member;
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
import net.sf.json.JSONObject;

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
	public String apiKioskDayItemList(Map<String, Object> requestMap, HttpServletRequest request) {					        

		String gtype 		= request.getParameter("GTYPE").trim();
		String COMCD 		= request.getParameter("COMCD").trim();	//사업장
		String SPORTS_CD 	= request.getParameter("SPORTS_CD"); 	//종목(수영,헬스..)
		String USE_TYPE 	= request.getParameter("USE_TYPE");  	//평일,주말
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("GTYPE", gtype); //
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
	public String kioskDayInsert(HttpServletRequest request) {

		
		  /*List<Map<String,Object>> info = new ArrayList<Map<String,Object>>();
		    info = JSONArray.fromObject(requestMap);
		    for (Map<String, Object> memberInfo : info) {
		        System.out.println("xxxxxxxxxxxxxxxx========="+ memberInfo.get("ITEMLIST") + " : " + memberInfo.get("ITEM_CD"));
		    } */ 
		
	   	//	정보가져오기
		//String prgList = (String)requestMap.get("PRG");//강습반 및 프로그램 정보

		try {
			
		
		String aaaaa = request.getParameter("PAYLIST");//강습반 및 프로그램 정보
		Gson gson = new Gson();
		Map<String, String> aPayList = new HashMap<>();
		aPayList = (Map<String, String>) gson.fromJson(aaaaa, aPayList.getClass());
		String ss1 = aPayList.get("COMCD");
		String ss2 = aPayList.get("USER_ID");
		System.out.println("ss1: "+ ss1);
		System.out.println("ss2: "+ ss2);

		String bbbbb = (String) request.getParameter("ITEMLIST");//강습반 및 프로그램 정보
		List<Map<String, Object>> aPrgList = new ArrayList<Map<String, Object>>();
		aPrgList = (List<Map<String, Object>>) gson.fromJson(bbbbb, aPrgList.getClass());
		
		/*
		for (int ii = 0; ii < aPrgList.size(); ii++) {
			ss1 = aPrgList.get(ii).get("PART_CD").toString();
			System.out.println("ss1: "+ ss1);
			ss2 = aPrgList.get(ii).get("ITEM_CD").toString();
			System.out.println("ss2: "+ ss2);
		}
		*/

  		//select * from PAY_LIST
   		String NEXT_RECEIPT_NO = mapper.getNextReceiptNo();

   		//현금영수번호 가 없을경우 보조추가용
   		String NEXT_APP_NO = "";

   		//카드 결제정보  ##################################
   		/*
   		String COMCD		= aPayList.getJSONObject(0).getString("COMCD").toString(); //COMCD
   		String USER_ID 		= aPrgList.getJSONObject(0).getString("USER_ID").toString(); //kiosk1, 2,3,....
       	String APP_DATE 	= aPayList.getJSONObject(0).getString("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
       	String APP_NO 		= aPayList.getJSONObject(0).getString("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
       	//String APP_TIME 	= aPayList.getJSONObject(0).getString("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
       	String APP_TIME 	= APP_DATE.substring(APP_DATE.length()-6, APP_DATE.length());

       	
       	String REAL_CARD_CD = aPayList.getJSONObject(0).getString("CARD_CD").toString(); //결제수단코드
       	String CARD_NAME	= aPayList.getJSONObject(0).getString("CARD_NM").toString(); //카드사명
       	String CARD_NO 		= aPayList.getJSONObject(0).getString("CARD_NO").toString(); //카드번호
       	String HALBU_CNT	= aPayList.getJSONObject(0).getString("HALBU_CNT").toString(); //할부
       	String REMARK		= aPayList.getJSONObject(0).getString("REMARK").toString(); //memo
       	*/
   		String COMCD		= aPayList.get("COMCD").toString(); //COMCD
   		String USER_ID 		= aPayList.get("USER_ID").toString(); //kiosk1, 2,3,....
       	String APP_DATE 	= aPayList.get("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
       	String APP_NO 		= aPayList.get("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
       	//String APP_TIME 	= aPayList.get("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
       	String APP_TIME 	= APP_DATE.substring(8,12); //HHII

       	String REAL_CARD_CD = aPayList.get("CARD_CD").toString(); //결제수단코드
       	String CARD_NAME	= aPayList.get("CARD_NM").toString(); //카드사명
       	String CARD_NO 		= aPayList.get("CARD_NO").toString(); //카드번호
       	String HALBU_CNT	= aPayList.get("HALBU_CNT").toString(); //할부
       	String REMARK		= aPayList.get("REMARK").toString(); //memo   		
       	
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
			String CLASS_CD 	= ""; //일일이용 사용안함 aPrgList.get(jj).get("CLASS_CD").toString();
			String PART_CD 		= aPrgList.get(jj).get("PART_CD").toString();
			String SPORTS_CD 	= aPrgList.get(jj).get("SPORTS_CD").toString();
			String ITEM_CD 		= aPrgList.get(jj).get("ITEM_CD").toString();

			
			String SALE_AMT 	= aPrgList.get(jj).get("SALE_AMT").toString(); //판매금액
			
			String SALE_NUM 	= aPrgList.get(jj).get("SALE_NUM").toString(); //판매수량
			int USE_CNT = 0; //aPrgList.getJSONObject(jj).getInt("USE_CNT"); //이용기간내횟수 = 0; //이용횟수
			int DC_AMT = 0; //aPrgList.getJSONObject(jj).getInt("DC_AMT"); //할인금액
			String VAT_YN 		= aPrgList.get(jj).get("VAT_YN").toString(); //부가세여부YN
			String VAT_AMT 		= aPrgList.get(jj).get("VAT_AMT").toString(); //부가세금액
			String CALMEM_NO 	= MEM_NO; //aPrgList.get(jj).get("CALMEM_NO").toString(); //이용회원번호
			//int DEPOSIT_AMT = aPrgList.getJSONObject(jj).getInt("DEPOSIT_AMT"); // 보증금
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
			maps.put("PAY_AMT", SALE_AMT);//결제금액
			maps.put("CASH_AMT",0);
			maps.put("CARD_AMT",SALE_AMT);
 	
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
	    	maps.put("CARD_INFO", REAL_CARD_CD +"00"+ CARD_NAME);//실제 결제 카드사정보
	    	maps.put("HALBU_CNT", HALBU_CNT);//카드사 할부
			maps.put("PAY_AMT", SALE_AMT);//결제금액
	    	maps.put("APP_AMT", SALE_AMT);//결제금액
	    	
	    	maps.put("STORE_NO", "");//???????????
	    	maps.put("PAY_LIST_YN", "");//
	    	maps.put("WRITER", USER_ID);//운영자
	    	maps.put("WRITE_DH", ymdhis);//
	    	maps.put("REMARK", REMARK);
    	
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
			maps.put("COST_AMT", SALE_AMT);//	판매원가
			maps.put("UNIT_AMT", SALE_AMT);//	판매단가
			maps.put("SALE_NUM", SALE_NUM);//	판매수량
			maps.put("USE_CNT", USE_CNT);//	이용기간내횟수
			maps.put("DC_AMT", DC_AMT);//	할인금액
			maps.put("SALE_AMT", SALE_AMT);//	판매금액
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
	    	
	  		//등록강습반 및 프로그램 저장
			//select * from mem_sale order by  WRITE_DH desc
			mapper.setMemSale(maps);  
	  		
	  		String NEXT_SALE_SEQ = maps.get("NEXT_SALE_SEQ").toString();
	  		
	  		//Map<String, Object> requestMapTrainHist = new HashMap<String, Object>();

  		
	  		//회원_강습신청내역 저장
    	  	maps.put("ACT_MODE", "I"); //추가(I), 수정(E) 여부
    	  	maps.put("SALE_SEQ", NEXT_SALE_SEQ);//가입프로그램신청순번
    	  	maps.put("CLASS_CD", CLASS_CD);//강습반__TRAIN_CLASS__CLASS_CD
    		maps.put("TRAIN_SDATE", yyyymmdd);//이용시작일_Ymd
    		maps.put("TRAIN_EDATE", yyyymmdd);//이용종료일_Ymd
    		maps.put("USER_NO", "");//강사ID						
			maps.put("CHANGE_YN", "N");//	변경상태__Y_변경후강습__B_변경전강습__N_변경없음
   	  		maps.put("ASSIGN_YN", "N");//강사여부
    	
    	  	mapper.setTrainHist(maps);
  		
    		//결제완료 후 일일 입장 처리 ##################################################
      		//requestMap.put("ENTR_DATE", yyyymmdd);
      		mapper.setMemCheckIn(maps);

  		} //end for
   		
  		Map<String, Object> rtnMap = new HashMap<>();
  		rtnMap.put("msg", "ok");//강습반코드:일일회원은 강습반이 없음
  		String rtn = new Gson().toJson(rtnMap);
  		return rtn;
		
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        
	        Map<String, Object> rtnMap = new HashMap<>();
	        rtnMap.put("msg", errors.toString());//강습반코드:일일회원은 강습반이 없음
			String rtn = new Gson().toJson(rtnMap);
			return rtn;
		}

	}
	
	
	
	//일일권 매출 취소 API가 필요합니다.
	@Override
	@Transactional
	public String kioskDayCancel(Map<String, Object> requestMap, HttpServletRequest request) {
		
		Map<String, Object> maps = new HashMap<>();
		int dbSaveYN = 0;

		maps.put("COMCD", request.getParameter("COMCD"));
		maps.put("TID", request.getParameter("TID"));
		maps.put("PAY_AMT", request.getParameter("CancelAmt"));
		maps.put("SALE_SEQ", request.getParameter("otherParam"));
		/*Member	members = (Member) session.getAttribute("member");
		String MEM_ID = members.getId();
    	String MEM_NO = members.getMemNo();
    	String MEM_NM = members.getMemNm();
		maps.put("MEM_ID", MEM_ID);
		maps.put("MEM_NO", MEM_NO);
		maps.put("MEM_NM", MEM_NM);
		*/
			
		//취소처리 부분
		//String ymd = FormatUtil.getDefaultDate(1, "-","");
		maps.put("WRITER", "kiosk1,2,3..");
		maps.put("MEM_NM", "kiosk1,2,3..");
		maps.put("REMARK", "kiosk 당일취소처리");
		
		//키오스크에서 승인번호로 SLIP_NO 정보 표시
		maps = etcMapper.kioskCancelData(maps);
		
		//강좌 당일 결제 취소처리 부분################################################

		//select *, CANCEL_YN, REMARK from mem_sale  where MEM_NO ='00135090' order by  WRITE_DH desc
		dbSaveYN = mapper.memLecCancelStep6(maps);
		//온라인 오프라인 확인 
		if (dbSaveYN == 1) {
		
			//Select * from CALC_MASTER order by  WRITE_DH desc
			mapper.memLecCancelStep2(maps);
			
			//Select * from PAY_LIST where SLIP_NO in (Select SLIP_NO from CALC_MASTER where MEM_NO ='00135090') order by  WRITE_DH desc
			mapper.memLecCancelStep3(maps);
			
			//Select * from card_app_hist_damo where SLIP_NO in (Select SLIP_NO from CALC_MASTER where MEM_NO ='00135090') order by  WRITE_DH desc
			mapper.memLecCancelStep4(maps);
			
			//select * from train_hist where MEM_NO ='00135090' order by  WRITE_DH desc
			mapper.memLecCancelStep5(maps);
		
		} else {
			dbSaveYN = 0;
		}
		
		return null;
	}
	
}
