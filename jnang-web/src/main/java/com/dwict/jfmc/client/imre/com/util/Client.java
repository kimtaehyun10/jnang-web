package com.dwict.jfmc.client.imre.com.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

	public static String doService(String targetUrl, String requestXml) {

		String serviceUrl,contentType, encoding, transactionUniqueId;
		int connectTimeout, soTimeout;
		
		{
			contentType = "text/xml; charset=utf-8"; 
			encoding = "UTF-8";

			connectTimeout = 5 * 60 * 1000; 
			soTimeout = 900 * 1000;
			
			//0.목적지 서버 설정 + 서비스Url 설정			
			serviceUrl = "http://152.99.88.156:10101"; //운영서버
			//serviceUrl = "http://152.99.88.156:50201"; //테스트서버
			serviceUrl = serviceUrl + targetUrl;
			
			//LOGGER.info(serviceUrl);
			
			//1.서비스명(각 호출 서비스에서 생성)
			
			//2.이용시스템코드 생성
			requestXml = requestXml.replace("#USE_SYSTEM_CODE#", "B551443CMC");
			
			//3.GPKI인증서 아이디
			requestXml = requestXml.replace("#CERT_SERVER_ID#", "SVRB551443002"); 
			
			//4.트랜잭션 유일식별자 생성
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);
			transactionUniqueId = formatter.format(new Date()) + String.valueOf(Math.random()).substring(2, 10);

			requestXml = requestXml.replaceAll("#TRANSACTION_UNIQUE_ID#",transactionUniqueId);
			
			
			//5.이용자부서코드(비공무원 공란)
			
			//6.이용자명(비공무원 공란)

			LOGGER.info("requestXml : " + requestXml);
		}
		PostMethod method = null; 


		String responseXml = null; 
		try {

			HttpClient client; 
			{
				HttpConnectionManagerParams params = new HttpConnectionManagerParams();

				params.setConnectionTimeout(connectTimeout);
				params.setSoTimeout(soTimeout);
				params.setTcpNoDelay(true);

				HttpConnectionManager conn = new SimpleHttpConnectionManager();
				conn.setParams(params);
				client = new HttpClient(conn);

				method = new PostMethod(serviceUrl);
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler(0, false));

				method.setRequestHeader("Content-Type", contentType);
				method.setRequestHeader("Connection", "close");

				RequestEntity requestEntity = new StringRequestEntity(
						requestXml, contentType, encoding);
				method.setRequestEntity(requestEntity);
			}


			int responseCode = client.executeMethod(method); 


			{
				InputStream is = method.getResponseBodyAsStream();
				try {
					int readLen;
					byte[] buffer = new byte[1024];
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					while ((readLen = is.read(buffer)) >= 0) {
						baos.write(buffer, 0, readLen);
					}
					byte[] data = baos.toByteArray();
					responseXml = new String(data, encoding);
				} finally {
					is.close();
				}
			}


			if (responseCode == HttpStatus.SC_OK) {
			} else if (responseXml.indexOf("Fault>") > 0) {
			} else {
			}

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (method != null) {
				method.releaseConnection(); 
			}
		}

		return responseXml;
	}
}