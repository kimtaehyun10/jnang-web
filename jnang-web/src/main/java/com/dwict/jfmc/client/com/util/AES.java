package com.dwict.jfmc.client.com.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AES {

	private SecretKeySpec secretKey;
	private byte[] key;

	private void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
			key = Arrays.copyOf(sha.digest(myKey.getBytes("UTF-8")), 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			log.error("Error while setting Key: " + e.toString());
			e.printStackTrace();
		}
	}

	public String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (final Exception e) {
			log.error("Error while encrypting: " + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String strToDecrypt, String secret) {
		try {
			setKey(secret);
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} 
		catch (final Exception e) {
			log.error("Error while decrypting: " + e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
