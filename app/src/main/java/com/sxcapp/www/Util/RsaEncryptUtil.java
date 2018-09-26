package com.sxcapp.www.Util;

import java.io.ByteArrayOutputStream;  
import java.security.Key;  
import java.security.KeyFactory;  
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.Security;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;  

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;  

/** 
 * RSA非对称加密解密工具类 
 *  
 * @ClassName RsaEncryptUtil 
 * @content 
 */  
public class RsaEncryptUtil {
	/** */
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";// RSA/ECB/PKCS1Padding

	/**
	 * String to hold name of the encryption padding.
	 */
	public static final String PADDING = "RSA/NONE/PKCS1Padding";// RSA/NONE/NoPadding

	/**
	 * String to hold name of the security provider.
	 */
	public static final String PROVIDER = "BC";

	/** */
	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";


	/** */
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALE1DyvWWQFL5be8zHrGO0DbXk2I690gIUddkbu9DebYlJLigqJdCCJIZH7RjTEF1pxeTBMse2EwsJNxgNX5bgYu3ULgNu0lUB4t6ST8gUnw2rqz4/EB1Grp3T1hqHDvGtgtwdzbau4yLjnPCRR10owlyHTp4HHRTeQseVcnep0dAgMBAAECgYBVxMCbDxv1LK76xz6RDfEP6x0xmdvPIwX4bKlknJRl7t26WjewLicJsNAzMWmNGK5O8LURcq4k1FQiiQU6i4dcWID8sts4S7jpqAX6JU8ee5y5v7TBP9qGhiAGCjIaWBIkbM54HCV89rAC8dQEcrmtM0RB3ONlkmC/ycikZSWFYQJBANri9K/nf7CFFkQ3mQxwxF7jAi7tzgovTDfdPosqnHnrGPa8/MBym+8DQ23tCZdXDo+n9f4Da0HaRDjtDJElO1cCQQDPQPZI6QCLL/eSBG42G3eaJDox0Fu/UFv2Ukg69BOFKZO05bcmatnAIBoyshr0tTZU1wtGunapvj6RJcbkgZarAkAJ+DZe4LNvLdCi0Ml2yJgZHkvWKVyuGBNno/saSZmQ8AVWD6uPdGjnzQVAN4CtVPjD0EmjBTLYcAGVei6NWd3HAkBeC+F2hlzbzEFwfhgw71ffl27c6eqoLuP8K6xhLB6rf4lqPg9VHImBGFArDw0SXCSybP7N8pEkR5MGtHjDelaPAkBD8Qdsq3ma1Fvo/ioKj6Ln9yOtYZkx4dgDeR+aF1Hpu1xqWsZ6LYYdWuxHXUhTTI0VngGbKu7kmk4hKhXEbUE1";

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;



	/**
	 * 私钥加密
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * @comment
	 */
	public static String encryptByPrivateKey(String str) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);

		// 获得私钥
		Key privateKey = getPrivateKey();

		// 用私钥加密
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		// 读数据源
		byte[] data = str.getBytes("UTF-8");

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();

		return Base64Util.encode(encryptedData);
	}


	/*
	 * 私钥解密
	 */
	public static String decryptByPrivateKey(String str) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
		// 得到Key
		Key privateKey = getPrivateKey();
		// 用私钥去解密
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 读数据源
		byte[] encryptedData = Base64Util.decode(str);

		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();

		// 二进制数据要变成字符串需解码
		return new String(decryptedData, "UTF-8");
	}





	/**
	 * 获取私钥
	 * 
	 * @return
	 * @throws Exception
	 * @comment
	 */
	private static Key getPrivateKey() throws Exception {
		String key = PRIVATE_KEY;
		byte[] keyBytes;
		keyBytes = Base64Util.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
}
