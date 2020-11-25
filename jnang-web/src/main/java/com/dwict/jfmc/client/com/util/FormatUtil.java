package com.dwict.jfmc.client.com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	public static String getDefaultDate(int uTypes, String ch,String changDate) {
		String rtn = "";
		ch = ("".equals(ch)) ? "-" : ch;
		switch (uTypes) {
		case 0:
			rtn = getCurrentTime("yyyy"+ ch +"MM"+ ch +"dd", changDate);
			break;
		case 1:
			rtn = getCurrentTime("yyyy"+ ch +"MM"+ ch +"dd HH:mm:ss", changDate);
			break;
		case 2:
			rtn = getCurrentTime("yyyyMMdd", changDate);
			break;
		case 3:
			rtn = getCurrentTime("yyyyMMddHHmmss", changDate);
			break;
		case 4:
			rtn = getCurrentTime("yyyyMMddHHmmssSSS", changDate);
			break;
		case 5:
			rtn = getCurrentTime("HH:mm:ss", changDate);
			break;			

		default:
			rtn = getCurrentTime("yyyy"+ ch +"MM"+ ch +"dd", changDate);
			break;
		}
		return rtn;
	}
	
	//날짜 포멧 변환
	public static String getCurrentTime(String timeFormat, String changDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.KOREA);
			//SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.KOREA).format(System.currentTimeMillis());
			if (!changDate.equals("")) {
				Date secondDate = sdf.parse(changDate);
				return secondDate.toString();
			} else {
				return sdf.format(System.currentTimeMillis());
			}
			//return "1";
		} catch (Exception ex) {
			return new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(System.currentTimeMillis());
		}
	}
	
	/**
	 * 특정 날짜에 대하여 요일을 구함(일 ~ 토)
	 * @param date
	 * @param dateType
	 * @return
	 * @throws Exception
	 */
	public static String getDateYoil(String date, String dateType) throws Exception {
	 
	    String day = "" ;
	     
	    SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
	    Date nDate = dateFormat.parse(date) ;
	     
	    Calendar cal = Calendar.getInstance() ;
	    cal.setTime(nDate);
	     
	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
	     
	    switch(dayNum){
	        case 1:
	            day = "일";
	            break ;
	        case 2:
	            day = "월";
	            break ;
	        case 3:
	            day = "화";
	            break ;
	        case 4:
	            day = "수";
	            break ;
	        case 5:
	            day = "목";
	            break ;
	        case 6:
	            day = "금";
	            break ;
	        case 7:
	            day = "토";
	            break ;
	             
	    }
	     
	     
	     
	    return day ;
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
