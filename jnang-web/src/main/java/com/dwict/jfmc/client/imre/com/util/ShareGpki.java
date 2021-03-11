package com.dwict.jfmc.client.imre.com.util;

import org.springframework.beans.factory.annotation.Value;

import com.dwict.jfmc.client.imre.com.util.NewGpkiUtil;

public class ShareGpki {
	
	public ShareGpki(){
		
	}
	public static NewGpkiUtil getGpkiUtil(String targetServerId)throws Exception{
		NewGpkiUtil g = new NewGpkiUtil();
		
		// 이용기관 서버CN
		//String myServerId = "SVR1311000030";
		String myServerId = "SVRB551443002"; //EgovProperties.getProperty("ImreGlobals.myServerId");
		
		// 이용기관 서버인증서 경로
		//g.setCertFilePath("C:/gpki2/gpkisecureweb/certs");
		//String envCertFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_env.cer";
		//String envPrivateKeyFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_env.key";

		//GPKI 인증서 설정
		g.setCertFilePath("/data/gpkiapi/sign"); //EgovProperties.getProperty("ImreGlobals.CertFilePath")
		String envCertFilePathName = "/data/gpkiapi/sign/SVRB551443002_env.cer"; //EgovProperties.getProperty("ImreGlobals.CertFilePathName");
		String envPrivateKeyFilePathName = "/data/gpkiapi/sign/SVRB551443002_env.key";//EgovProperties.getProperty("ImreGlobals.envPrivateKeyFilePathName");
		
		//GPKI 이용기관 서버인증서 비밀번호
		String envPrivateKeyPasswd = "tltjf06863#";//EgovProperties.getProperty("ImreGlobals.envPrivateKeyPasswd");

		//GPKI 이용기관 서버전자서명 경로
		//String sigCertFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_sig.cer";
		//String sigPrivateKeyFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_sig.key";

		String sigCertFilePathName = "/data/gpkiapi/sign/SVRB551443002_sig.cer"; //EgovProperties.getProperty("ImreGlobals.sigCertFilePathName");
		String sigPrivateKeyFilePathName = "/data/gpkiapi/sign/SVRB551443002_sig.key"; //EgovProperties.getProperty("ImreGlobals.sigPrivateKeyFilePathName");
		
		//GPKI 이용기관 서버전자서명 비밀번호
		String sigPrivateKeyPasswd = "tltjf06863#"; //EgovProperties.getProperty("ImreGlobals.sigPrivateKeyPasswd");
		
		
		// 이용기관 GPKI API 라이선스파일 경로
		//g.setGpkiLicPath("C:/gpki2/gpkisecureweb/conf");
		g.setGpkiLicPath("/data/gpkiapi/conf"); // EgovProperties.getProperty("ImreGlobals.GpkiLicPath")
		g.setEnvCertFilePathName(envCertFilePathName);
		g.setEnvPrivateKeyFilePathName(envPrivateKeyFilePathName);
		g.setEnvPrivateKeyPasswd(envPrivateKeyPasswd);
		// LDAP 의 사용유무
		// 미사용일 경우 암호화할 타겟의 인증서를 파일로 저장해놓고 사용하여야함.
		g.setIsLDAP(true);
		g.setMyServerId(myServerId);
		g.setSigCertFilePathName(sigCertFilePathName);
		g.setSigPrivateKeyFilePathName(sigPrivateKeyFilePathName);
		g.setSigPrivateKeyPasswd(sigPrivateKeyPasswd);
		
		g.setTargetServerIdList(targetServerId);

		g.init();
		return g;
	}
}
