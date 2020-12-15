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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dwict.jfmc.client.etc.service.EtcService;
import com.dwict.jfmc.client.etc.mapper.EtcMapper;

import lombok.extern.slf4j.Slf4j;

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
		final HashMap<String, String> airInfo = getAirInfo();
		weatherInfo.put("pm10Value", airInfo.get("pm10Value"));
		weatherInfo.put("airStatus", airInfo.get("airStatus"));
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
	public Map<String, Object> scc(Map<String, Object> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
				int sccCnt = etcMapper.scc(requestMap);
				if(sccCnt==0) {
					msg="일치하는 회원을 찾을 수 없습니다.";
					location=(String)requestMap.get("fail_url");
				}else {
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
}
