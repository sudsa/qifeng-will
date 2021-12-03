package com.bpaas.doc.framework.base.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.bpaas.doc.framework.base.command.NotProguard;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

/**
 * PKCS#1/X.509密钥加解密
 * 
 * private key PKCS#1
 * public key X.509
 * @author huyang
 *
 */
@NotProguard
public class RSAEncryptPKCS1 {

	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


    public static KeyPair genKeyPair(int keySize) {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-2048位
        keyPairGen.initialize(keySize, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        return keyPairGen.generateKeyPair();
    }

	/**
	 * 随机生成密钥对
	 * 
	 * @param keySize
	 *            推荐1024或2048
	 * @param privateKeyFilePath
	 * @param publicKeyFilePath
	 */
	public static void genKeyPair(int keySize, String privateKeyFilePath, String publicKeyFilePath) {
        KeyPair keyPair = genKeyPair(keySize);
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 将密钥对写入到文件
		writeKeyToPemFile(new PemObject("RSA PRIVATE KEY", convertPrivateKeyToPKCS1(privateKey)), privateKeyFilePath);
		writeKeyToPemFile(new PemObject("PUBLIC KEY", publicKey.getEncoded()), publicKeyFilePath);
	}

    public static RSAKeyPair genKeyPairStr(int keySize) {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPair keyPair = genKeyPair(keySize);
        RSAKeyPair rsaKeyPair = new RSAKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        rsaKeyPair.setPrivateKey(Base64.encodeBase64String(convertPrivateKeyToPKCS1(privateKey)));
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        rsaKeyPair.setPublicKey(Base64.encodeBase64String(publicKey.getEncoded()));
        return rsaKeyPair;
    }

	/**
	 * Convert private key from PKCS8 to PKCS1
	 * @param privateKey
	 * @return
	 */
	private static byte[] convertPrivateKeyToPKCS1(RSAPrivateKey privateKey) {
		byte[] privBytes = privateKey.getEncoded();
		PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privBytes);
		byte[] privateKeyPKCS1 = null;
		try {
			ASN1Encodable encodable = pkInfo.parsePrivateKey();
			ASN1Primitive primitive = encodable.toASN1Primitive();
			privateKeyPKCS1 = primitive.getEncoded();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return privateKeyPKCS1;
	}

	/**
	 * Convert public key from X.509 SubjectPublicKeyInfo to PKCS1:
	 * @param publicKey
	 * @return
	 */
//	private static byte[] convertPublicKeyToPKCS1(RSAPublicKey publicKey) {
//		byte[] pubBytes = publicKey.getEncoded();
//
//		SubjectPublicKeyInfo spkInfo = SubjectPublicKeyInfo.getInstance(pubBytes);
//		byte[] publicKeyPKCS1 = null;
//		try {
//			ASN1Primitive primitive = spkInfo.parsePublicKey();
//			publicKeyPKCS1 = primitive.getEncoded();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return publicKeyPKCS1;
//	}

	/**
	 * Convert key in PKCS1 to PEM
	 * @param pemObject
	 * @param path
	 */
	private static void writeKeyToPemFile(PemObject pemObject, String path){
		File file = new File(path);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try (FileWriter writer = new FileWriter(file, false); PemWriter pemWriter = new PemWriter(writer);) {
			pemWriter.writeObject(pemObject);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	private static String writeKeyToPemString(PemObject pemObject){
//		StringWriter writer = new StringWriter();
//		try (PemWriter pemWriter = new PemWriter(writer);) {
//			pemWriter.writeObject(pemObject);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return writer.toString();
//	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param path
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static String loadPublicKeyByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return loadPublicKeyByFileString(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

/**
 * 从文件中输入流中加载公钥
 * 
 * @param keyString
 *            公钥输入流
 * @throws Exception
 *             加载公钥时产生的异常
 */
public static String loadPublicKeyByFileString(String keyString) {
	return keyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n?|-+END PUBLIC KEY-+\\r?\\n?)", "");
}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param path
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public static String loadPrivateKeyByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return loadPrivateKeyByFileString(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}
	/**
	 * 从文件中加载私钥
	 * 
	 * @param keyString
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public static String loadPrivateKeyByFileString(String keyString) {
		return keyString.replaceAll("(-+BEGIN (RSA )?PRIVATE KEY-+\\r?\\n?|-+END (RSA )?PRIVATE KEY-+\\r?\\n?)", "");
	}

	@SuppressWarnings("restriction")
	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
		try {byte[] buffer = Base64.decodeBase64(privateKeyStr);
		
        DerInputStream derReader = new DerInputStream(buffer);
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
			return (RSAPrivateKey) keyFactory.generatePrivate(spec);
		} catch (IOException e) {
			throw new Exception("私钥非法");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 私钥加密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 私钥解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 公钥解密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
		if (publicKey == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) throws Exception {
//		String privateKeyFilePath = "C:/temp/test/privateKey.pem";
//		String publicKeyFilePath = "C:/temp/test/publicKey.pem";
//		String privateKeyFilePath = "C:/temp/test/webhooksPrivateKey.pem";
//		String publicKeyFilePath = "C:/temp/test/webhooksPublicKey.pem";
//		String privateKeyFilePath = "C:/temp/pgsm_key/20160426/fc9793681f8045d6b95ac710c12feb9f/privateKey.pem";
//		String publicKeyFilePath = "C:/temp/pgsm_key/20160426/fc9793681f8045d6b95ac710c12feb9f/publicKey.pem";
		

//		RSAEncryptPKCS1.genKeyPair(2048, privateKeyFilePath, publicKeyFilePath);
//
//		System.out.println("--------------公钥加密私钥解密过程-------------------");
//		String plainText = "{\"amount\":\"1\",\"app\":{\"id\":\"fc9793681f8045d6b95ac710c12feb9f\",\"username\":\"18\",\"return_url\":\"http://192.168.4.184:8080/platform/charge/alipay_pc_direct.do\"},\"body\":\"湖北百旺金赋科技有限公司服务费\",\"extra\":{\"product_id\":\"0000000000\",\"show_url\":\"http://www.whtytest.cn/download/client/20150702111326pro03.png\"},\"subject\":\"百旺税控服务 专业高效\",\"livemode\":true,\"channel\":\"alipay_pc_direct\",\"order_no\":\"160817521688208950\",\"currency\":\"cny\"}";
		// 公钥加密过程
//		byte[] cipherData = RSAEncryptPKCS1.encrypt(RSAEncryptPKCS1.loadPublicKeyByStr(RSAEncryptPKCS1.loadPublicKeyByFile(publicKeyFilePath)),
//				plainText.getBytes());
//		String cipher = Base64.encodeBase64String(cipherData);
//		// 私钥解密过程
//		byte[] res = RSAEncryptPKCS1.decrypt(RSAEncryptPKCS1.loadPrivateKeyByStr(RSAEncryptPKCS1.loadPrivateKeyByFile(privateKeyFilePath)),
//				Base64.decodeBase64(cipher));
//		String restr = new String(res);
//		System.out.println("原文：" + plainText);
//		System.out.println("加密：" + cipher);
//		System.out.println("解密：" + restr);
//		System.out.println();
//
//		System.out.println("--------------私钥加密公钥解密过程-------------------");
//		plainText = "ihep_私钥加密公钥解密";
//		// 私钥加密过程
//		cipherData = RSAEncryptPKCS1.encrypt(RSAEncryptPKCS1.loadPrivateKeyByStr(RSAEncryptPKCS1.loadPrivateKeyByFile(privateKeyFilePath)),
//				plainText.getBytes());
//		cipher = Base64.encodeBase64String(cipherData);
//		// 公钥解密过程
//		res = RSAEncryptPKCS1.decrypt(RSAEncryptPKCS1.loadPublicKeyByStr(RSAEncryptPKCS1.loadPublicKeyByFile(publicKeyFilePath)), Base64.decodeBase64(cipher));
//		restr = new String(res);
//		System.out.println("原文：" + plainText);
//		System.out.println("加密：" + cipher);
//		System.out.println("解密：" + restr);
//		System.out.println();

//		System.out.println("---------------私钥签名过程------------------");
//		String content = "ihep_这是用于签名的原始数据";
//		String signstr = RSASignaturePKCS1.signSHA256(content, RSAEncryptPKCS1.loadPrivateKeyByFile(privateKeyFilePath));
//		System.out.println("签名原串：" + content);
//		System.out.println("签名串：" + signstr);
//		System.out.println();
//
//		System.out.println("---------------公钥校验签名------------------");
//		System.out.println("签名原串：" + content);
//		System.out.println("签名串：" + signstr);
//
//		System.out.println("验签结果：" + RSASignaturePKCS1.verifySHA256(content, signstr, RSAEncryptPKCS1.loadPublicKeyByFile(publicKeyFilePath)));
//		System.out.println();

        RSAKeyPair rsaKeyPair = RSAEncrypt.genKeyPairStr(1024);
        System.out.println(rsaKeyPair.getPrivateKey());
        System.out.println(rsaKeyPair.getPublicKey());

	}
}
