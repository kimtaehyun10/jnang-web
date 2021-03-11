package com.dwict.jfmc.client.imre.com.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gpki.gpkiapi_jni;
import com.gpki.gpkiapi.GpkiApi;
import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.gpkiapi.crypto.PrivateKey;
import com.gpki.gpkiapi.storage.Disk;

public class NewGpkiUtil {
	private static Logger logger = Logger.getLogger(NewGpkiUtil.class);
	byte[] myEnvCert, myEnvKey, mySigCert, mySigKey;
	private Map<String, X509Certificate> targetServerCertMap = new HashMap<String, X509Certificate>();

	// properties
	private String myServerId;
	private String targetServerIdList;
	private String envCertFilePathName;
	private String envPrivateKeyFilePathName;
	private String envPrivateKeyPasswd;
	private String sigCertFilePathName;
	private String sigPrivateKeyFilePathName;
	private String sigPrivateKeyPasswd;
	private String certFilePath;
	private String gpkiLicPath = ".";
	private boolean isLDAP;
	private boolean testGPKI = false;
	

	public void init() throws Exception {
		GpkiApi.init(gpkiLicPath);
		gpkiapi_jni gpki = this.getGPKI();
		if(logger.isDebugEnabled()){
			if(gpki.API_GetInfo()==0)
				logger.debug(gpki.sReturnString);
			else
				logger.error(gpki.sDetailErrorString);
		}
		if(targetServerIdList!=null){
			String certIdList[] = targetServerIdList.split(",");
			for(int i = 0 ; i < certIdList.length ; i++){
				String certId = certIdList[i].trim();
				if(!certId.equals("")){
					load(gpki, certId);
				}
			}
		}
		
		logger.info("Loading gpki certificate : myServerId="
				+ this.getMyServerId());

		X509Certificate _myEnvCert = Disk.readCert(this
				.getEnvCertFilePathName());
		myEnvCert = _myEnvCert.getCert();

		PrivateKey _myEnvKey = Disk.readPriKey(this
				.getEnvPrivateKeyFilePathName(), this.getEnvPrivateKeyPasswd());
		myEnvKey = _myEnvKey.getKey();

		X509Certificate _mySigCert = Disk.readCert(this
				.getSigCertFilePathName());
		mySigCert = _mySigCert.getCert();

		PrivateKey _mySigKey = Disk.readPriKey(this
				.getSigPrivateKeyFilePathName(), this.getSigPrivateKeyPasswd());
		mySigKey = _mySigKey.getKey();

		//test my cert GPKI
		if(testGPKI){
			load(gpki, this.getMyServerId());
			testGpki(gpki);
		}
		this.finish(gpki);
		logger.info("GpkiUtil initialized");
	}

	private void load(gpkiapi_jni gpki, String certId) throws Exception {

		logger.debug("Loading gpki certificate : targetServerId="
				+ certId);

		X509Certificate cert = targetServerCertMap.get(certId);
		if (cert != null) {
			return;
		}

		if (isLDAP) {
			String ldapUrl = "ldap://152.99.57.127:389/cn=";
			String ldapUri;
			if (certId.charAt(3) > '9') { 
				ldapUri = ",ou=Group of Server,o=Public of Korea,c=KR";
			} else {
				ldapUri = ",ou=Group of Server,o=Government of Korea,c=KR";
			}
			
			int ret = gpki.LDAP_GetAnyDataByURL("userCertificate;binary", ldapUrl + certId + ldapUri);
			this.checkResult(ret, gpki);
			cert = new X509Certificate(gpki.baReturnArray);
		} else {
			if(certFilePath != null){
				cert = Disk.readCert(certFilePath + File.separator + certId + ".cer");
			}else{
				logger.debug("not certFilePath");
			}
		}

		targetServerCertMap.put(certId, cert);
	}
	
	private gpkiapi_jni getGPKI(){
		gpkiapi_jni gpki = new gpkiapi_jni();
		if(gpki.API_Init(gpkiLicPath) != 0){
			logger.error(gpki.sDetailErrorString);
		}
		return gpki;
	}
	private void finish(gpkiapi_jni gpki){
		if(gpki.API_Finish() != 0){
			logger.error(gpki.sDetailErrorString);
		}
	}

	public byte[] encrypt(byte[] plain, String certId , boolean load) throws Exception {
		X509Certificate targetEnvCert = targetServerCertMap.get(certId);
		if (targetEnvCert == null) {
			throw new Exception("Certificate not found : targetServerId=" + certId);
		}
		
		gpkiapi_jni gpki = this.getGPKI();
		try{
			int result = gpki.CMS_MakeEnvelopedData(targetEnvCert.getCert(), plain,
					gpkiapi_jni.SYM_ALG_NEAT_CBC);
			checkResult(result, "Fail to encrypt message", gpki);
	
			return gpki.baReturnArray;
		}catch(Exception ex){
			throw ex;
		}finally{
			finish(gpki);
		}
	}
	
	public byte[] encrypt(byte[] plain, String certId) throws Exception {
		return encrypt(plain,certId , false);
	}
	
	public byte[] decrypt(byte[] encrypted) throws Exception {

		gpkiapi_jni gpki = this.getGPKI();
		try{
			int result = gpki.CMS_ProcessEnvelopedData(myEnvCert, myEnvKey,
					encrypted);
			checkResult(result, "Fail to decrpyt message", gpki);
	
			return gpki.baReturnArray;
		}catch(Exception ex){
			throw ex;
		}finally{
			finish(gpki);
		}
	}

	public byte[] sign(byte[] plain) throws Exception {

		gpkiapi_jni gpki = this.getGPKI();
		try{
			int result = gpki.CMS_MakeSignedData(mySigCert, mySigKey, plain, null);
			checkResult(result, "Fail to sign message", gpki);
	
			return gpki.baReturnArray;
		}catch(Exception ex){
			throw ex;
		}finally{
			finish(gpki);
		}
	}

	public byte[] validate(byte[] signed) throws Exception {

		gpkiapi_jni gpki = this.getGPKI();
		try{
			int result = gpki.CMS_ProcessSignedData(signed);
			checkResult(result, "Fail to validate signed message", gpki);
			return gpki.baData;
		}catch(Exception ex){
			throw ex;			
		}finally{
			finish(gpki);
		}
	}

	public String encode(byte[] plain) throws Exception {

		gpkiapi_jni gpki = this.getGPKI();
		try{
			int result = gpki.BASE64_Encode(plain);
			checkResult(result, "Fail to encode message", gpki);
	
			return gpki.sReturnString;
		}catch(Exception ex){
			throw ex;
		}finally{
			finish(gpki);
		}
		
	}

	public byte[] decode(String base64) throws Exception {

		gpkiapi_jni gpki = this.getGPKI();
		try{
			int result = gpki.BASE64_Decode(base64);
			checkResult(result, "Fail to decode base64 message", gpki);
	
			return gpki.baReturnArray;
		}catch(Exception ex){
			throw ex;
		}finally{
			finish(gpki);
		}
	}
	
	private void checkResult(int result, gpkiapi_jni gpki)throws Exception{
		this.checkResult(result, null, gpki);
	}
	
	private void checkResult(int result ,String message,  gpkiapi_jni gpki)throws Exception{
		if( 0 != result){
			if(null != gpki){
				throw new Exception(message + " : gpkiErrorMessage=" + gpki.sDetailErrorString);
			}else{
				throw new Exception(message + " : gpkiErrorCode=" + result);
			}
		}
	}
	
	public void testGpki(gpkiapi_jni gpki) throws Exception{
		//gpki test eng
		logger.info("=======================================================");
		logger.info("================ TEST GPKI START ======================");
		logger.info("=======================================================");
		String original_Eng = "abc";
		logger.info("=== TEST ENG STRING: "+ original_Eng);
		try {
			byte[] encrypted = encrypt(original_Eng.getBytes(), myServerId);
			logger.info("=== TEST ENG ENCRYPT STRING: "+ encode(encrypted));
			String decrypted = new String(decrypt(encrypted));
			logger.info("=== TEST ENG DECRYPT STRING: "+decrypted);
			
			if (!original_Eng.equals(decrypted)) {
				throw new Exception("GpkiUtil not initialized properly(english)");
			}
			logger.info("=== TEST ENG: OK");
		} catch (Exception e) {
			logger.warn("Gpki Test error(english)", e);
			throw e;
		}
		//gpki test kor
		String original = "한글테스트";
		logger.info("=== TEST KOR STRING: "+ original);
		try {
			byte[] encrypted = encrypt(original.getBytes(), myServerId);
			logger.info("=== TEST KOR ENCRYPT STRING: "+ encode(encrypted));
			String decrypted = new String(decrypt(encrypted));
			logger.info("=== TEST KOR DECRYPT STRING: "+decrypted);
			if (!original.equals(decrypted)) {
				throw new Exception("GpkiUtil not initialized properly(korean)");
			}
			logger.info("=== TEST KOR: OK");
		} catch (Exception e) {
			logger.warn("Gpki Test error(korean)", e);
			throw e;
		}finally{
			logger.info("=======================================================");
			logger.info("================ TEST GPKI END ========================");
			logger.info("=======================================================");
		}
	}
	
	public String getMyServerId() {
		return myServerId;
	}

	public void setMyServerId(String myServerId) {
		this.myServerId = myServerId.trim();
	}

	public String getEnvCertFilePathName() {
		return envCertFilePathName;
	}

	public void setEnvCertFilePathName(String envCertFilePathName) {
		this.envCertFilePathName = envCertFilePathName.trim();
	}

	public String getEnvPrivateKeyFilePathName() {
		return envPrivateKeyFilePathName;
	}

	public void setEnvPrivateKeyFilePathName(String envPrivateKeyFilePathName) {
		this.envPrivateKeyFilePathName = envPrivateKeyFilePathName.trim();
	}

	public String getEnvPrivateKeyPasswd() {
		return envPrivateKeyPasswd;
	}

	public void setEnvPrivateKeyPasswd(String envPrivateKeyPasswd) {
		this.envPrivateKeyPasswd = envPrivateKeyPasswd.trim();
	}

	public String getSigPrivateKeyPasswd() {
		return sigPrivateKeyPasswd;
	}

	public void setSigPrivateKeyPasswd(String sigPrivateKeyPasswd) {
		this.sigPrivateKeyPasswd = sigPrivateKeyPasswd.trim();
	}

	public String getSigCertFilePathName() {
		return sigCertFilePathName;
	}

	public void setSigCertFilePathName(String sigCertFilePathName) {
		this.sigCertFilePathName = sigCertFilePathName.trim();
	}

	public String getSigPrivateKeyFilePathName() {
		return sigPrivateKeyFilePathName;
	}

	public void setSigPrivateKeyFilePathName(String sigPrivateKeyFilePathName) {
		this.sigPrivateKeyFilePathName = sigPrivateKeyFilePathName.trim();
	}

	public boolean getIsLDAP() {
		return isLDAP;
	}

	public void setIsLDAP(boolean isLDAP) {
		this.isLDAP = isLDAP;
	}

	public String getCertFilePath() {
		return certFilePath;
	}

	public void setCertFilePath(String certFilePath) {
		this.certFilePath = certFilePath.trim();
	}

	public String getTargetServerIdList() {
		return targetServerIdList;
	}

	public void setTargetServerIdList(String targetServerIdList) {
		this.targetServerIdList = targetServerIdList;
	}

	public String getGpkiLicPath() {
		return gpkiLicPath;
	}

	public void setGpkiLicPath(String gpkiLicPath) {
		this.gpkiLicPath = gpkiLicPath;
	}

	public boolean getTestGPKI() {
		return testGPKI;
	}

	public void setTestGPKI(boolean testGPKI) {
		this.testGPKI = testGPKI;
	}

}