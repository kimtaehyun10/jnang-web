package com.dwict.jfmc.client.com.util;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class FormatUtil {

	public static String phone(String src) {
		if (src == null) {
			return "";
		}
		if (src.length() == 8) {
			return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (src.length() == 12) {
			return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		}
		return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	}

	
	
	/**
	 * 기본datetime 출력
	 * @param	uTypes 출력형식(YMD)
	 * @param	ch 구분자(-, /)
	 * <pre>
	 * 0 : yyyy-MM-dd 
	 * 1 : yyyy-MM-dd HH:mm:ss
	 * 2 : yyyyMMdd
	 * 3 : yyyyMMddHHmmss
	 * 4 : yyyyMMddHHmmssSSS
	 * 5 : HH:mm:ss
	 * </pre>
	 * @return    String 포맷형식(yyyy-MM-dd HH:mm:ss ...)
	 */	
	public static String getDefaultDate(int uTypes, String ch) {
		String rtn = "";
		ch = ("".equals(ch)) ? "-" : ch;
		switch (uTypes) {
		case 0:
			rtn = getCurrentTime("yyyy"+ ch +"MM"+ ch +"dd");
			break;
		case 1:
			rtn = getCurrentTime("yyyy"+ ch +"MM"+ ch +"dd HH:mm:ss");
			break;
		case 2:
			rtn = getCurrentTime("yyyyMMdd");
			break;
		case 3:
			rtn = getCurrentTime("yyyyMMddHHmmss");
			break;
		case 4:
			rtn = getCurrentTime("yyyyMMddHHmmssSSS");
			break;
		case 5:
			rtn = getCurrentTime("HH:mm:ss");
			break;			

		default:
			rtn = getCurrentTime("yyyy"+ ch +"MM"+ ch +"dd");
			break;
		}
		return rtn;
	}
	
	//날짜 포멧 변환
	public static String getCurrentTime(String timeFormat) {
		try {
			return new SimpleDateFormat(timeFormat, Locale.KOREA).format(System.currentTimeMillis());
					
		} catch (Exception ex) {
			return new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(System.currentTimeMillis());
		}
	}
	
	/**
	 * <pre>
	 * MD5+Base64
	 * </pre>
	 * @param pw
	 * @return String
	 */
	public static final String encodeMD5Base64(String str){
		return new String(Base64.encodeBase64(DigestUtils.md5(str)));
	}
	 
	public static final String encodeMD5HexBase64(String pw){
		return new String(Base64.encodeBase64(DigestUtils.md5Hex(pw).getBytes()));
	}
	
	/**
	 * <pre>
	 * request ==> HashMap 변환
	 * </pre>
	 * @param HttpServletRequest
	 * @return HashMap
	 */
	public static HashMap<String,Object> formatMapRequest(HttpServletRequest request) {
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    
	    Enumeration<String> enumber = request.getParameterNames();
	    
	    while (enumber.hasMoreElements()) {
	        String key = enumber.nextElement().toString();
	        String value = request.getParameter(key);

	        map.put(key, value);  
	    }
	    
	    return map;
	}	
	
}
