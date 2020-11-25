package com.dwict.jfmc.client.etc.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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

}
