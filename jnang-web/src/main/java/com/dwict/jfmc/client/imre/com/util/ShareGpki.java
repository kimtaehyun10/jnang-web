package com.dwict.jfmc.client.imre.com.util;

import org.springframework.beans.factory.annotation.Value;

public class ShareGpki {
	
	// 이용기관 서버CN
	//String myServerId = "SVR1311000030";
	@Value("#{appConfig['ImreGlobals.myServerId']}")
	static String myServerId;
	
	//GPKI 인증서 설정
	@Value("#{appConfig['ImreGlobals.CertFilePath']}")
	static String CertFilePath;
	
	@Value("#{appConfig['ImreGlobals.CertFilePathName']}")
	static String CertFilePathName;
	
	@Value("#{appConfig['ImreGlobals.envPrivateKeyFilePathName']}")
	static String envPrivateKeyFilePath;
	
	//GPKI 이용기관 서버인증서 비밀번호
	@Value("#{appConfig['ImreGlobals.envPrivateKeyPasswd']}")
	static String envPrivateKey;
	
	@Value("#{appConfig['ImreGlobals.sigCertFilePathName']}")
	static String sigCertFilePath;
	
	@Value("#{appConfig['ImreGlobals.sigPrivateKeyFilePathName']}")
	static String sigPrivateKeyFilePath;
	
	//GPKI 이용기관 서버전자서명 비밀번호
	@Value("#{appConfig['ImreGlobals.sigPrivateKeyPasswd']}")
	static String sigPrivateKey;
	
	@Value("#{appConfig['ImreGlobals.GpkiLicPath']}")
	static String GpkiLicPath;
	
	public ShareGpki(){
		
	}
	
	public static NewGpkiUtil getGpkiUtil(String targetServerId)throws Exception{
		NewGpkiUtil g = new NewGpkiUtil();				
		
		
		// 이용기관 서버인증서 경로
		//g.setCertFilePath("C:/gpki2/gpkisecureweb/certs");
		//String envCertFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_env.cer";
		//String envPrivateKeyFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_env.key";

		//GPKI 인증서 설정
		g.setCertFilePath(CertFilePath);
		
		String envCertFilePathName = CertFilePathName;
		String envPrivateKeyFilePathName = envPrivateKeyFilePath;
		
		//GPKI 이용기관 서버인증서 비밀번호
		String envPrivateKeyPasswd = envPrivateKey;

		//GPKI 이용기관 서버전자서명 경로
		//String sigCertFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_sig.cer";
		//String sigPrivateKeyFilePathName = "C:/gpki2/gpkisecureweb/certs/SVR1311000030_sig.key";

		String sigCertFilePathName = sigCertFilePath;
		String sigPrivateKeyFilePathName = sigPrivateKeyFilePath;
		
		//GPKI 이용기관 서버전자서명 비밀번호
		String sigPrivateKeyPasswd = sigPrivateKey;
		
		
		// 이용기관 GPKI API 라이선스파일 경로
		//g.setGpkiLicPath("C:/gpki2/gpkisecureweb/conf");
		g.setGpkiLicPath(GpkiLicPath);
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
