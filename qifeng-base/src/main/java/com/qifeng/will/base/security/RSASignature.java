package com.bpaas.doc.framework.base.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.bpaas.doc.framework.base.util.CheckEmptyUtil;
import org.apache.commons.codec.binary.Base64;

/**
 * RSA签名验签类
 */
public class RSASignature {

	/**
	 * 签名算法
	 */
	public static final String SHA1withRSA = "SHA1withRSA";
	public static final String SHA256withRSA = "SHA256withRSA";

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String algorithm, String encode) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(algorithm);
			signature.initSign(priKey);
			if (CheckEmptyUtil.isEmpty(encode)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(encode));
			}
			byte[] signed = signature.sign();
			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sign(String content, String privateKey, String algorithm) {
		return sign(content, privateKey, algorithm, null);
	}

	public static String signSHA1(String content, String privateKey) {
		return sign(content, privateKey, SHA1withRSA, null);
	}

	public static String signSHA256(String content, String privateKey) {
		return sign(content, privateKey, SHA256withRSA, null);
	}

	public static String signSHA1(String content, String privateKey, String encode) {
		return sign(content, privateKey, SHA1withRSA, encode);
	}

	public static String signSHA256(String content, String privateKey, String encode) {
		return sign(content, privateKey, SHA256withRSA, encode);
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @param encode
	 *            字符集编码
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign, String publicKey, String algorithm, String encode) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decodeBase64(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			if (CheckEmptyUtil.isEmpty(encode)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(encode));
			}
			boolean bverify = signature.verify(Base64.decodeBase64(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verify(String content, String sign, String publicKey, String algorithm) {
		return verify(content, sign, publicKey, algorithm, null);
	}

	public static boolean verifySHA1(String content, String sign, String publicKey) {
		return verify(content, sign, publicKey, SHA1withRSA, null);
	}

	public static boolean verifySHA256(String content, String sign, String publicKey) {
		return verify(content, sign, publicKey, SHA256withRSA, null);
	}

	public static boolean verifySHA1(String content, String sign, String publicKey, String encode) {
		return verify(content, sign, publicKey, SHA1withRSA, encode);
	}

	public static boolean verifySHA256(String content, String sign, String publicKey, String encode) {
		return verify(content, sign, publicKey, SHA256withRSA, encode);
	}
}
