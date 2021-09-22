package com.qifeng.will.base.security;

import com.qifeng.will.base.util.CheckEmptyUtil;
import org.apache.commons.codec.binary.Base64;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名验签类
 * 
 * private key PKCS#1 public key X.509
 */
public class RSASignaturePKCS1 {

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
			byte[] privateKeyBytes = Base64.decodeBase64(privateKey);

			DerInputStream derReader = new DerInputStream(privateKeyBytes);
			DerValue[] seq = derReader.getSequence(0);

			if (seq.length < 9) {
	            throw new InvalidKeySpecException("Could not parse a PKCS1 private key.");
			}
			// skip version seq[0];
			BigInteger modulus = seq[1].getBigInteger();
			BigInteger publicExp = seq[2].getBigInteger();
			BigInteger privateExp = seq[3].getBigInteger();
			BigInteger prime1 = seq[4].getBigInteger();
			BigInteger prime2 = seq[5].getBigInteger();
			BigInteger exp1 = seq[6].getBigInteger();
			BigInteger exp2 = seq[7].getBigInteger();
			BigInteger crtCoef = seq[8].getBigInteger();
			RSAPrivateCrtKeySpec spec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(spec);

			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			if (CheckEmptyUtil.isEmpty(encode)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(encode));
			}
			byte[] signBytes = signature.sign();
			return Base64.encodeBase64String(signBytes).replaceAll("\n|\r", "");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
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
			Signature signature = Signature.getInstance(algorithm);
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
}
