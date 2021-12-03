package com.bpaas.doc.framework.base.security;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 3des加解密工具类
 * @author Administrator
 *
 */
public class DESUtil {
	
	private final static String CIPHER_ALGORITHM = "DES/ECB/NoPadding";
	private final static String DES = "DES";
	
	/**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] key,byte[] data) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
    
	/**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] key,byte[] data) throws Exception {
        // 生成一个可信任的随机数源
         SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
	    /**
		 * 解密json ,加密直接用以前的xml就可以
		 * @param allContent 接口传递过来的参数（包含8位romdom数字和xml主体以及hash算法之后的32位字符）
		 * @return
	     * @throws Exception 
	     * @throws IOException 
		 */
	    @SuppressWarnings("restriction")
		public static String dencodeJson(String allContent, String myKey) throws Exception{
	    	//这里addProvider方法是增加一个新的加密算法提供者(个人理解没有找到好的答案,求补充)
	        Security.addProvider(new com.sun.crypto.provider.SunJCE());
			// 取前8位随机数
			String romdomCount  = new String(allContent.getBytes()).substring(0,8);
			//根据特定秘钥进行解析
			// 取中间报文体
			String xml = allContent.substring(8,allContent.length()-32);
			// 取后32位hash值
			String hash_val = allContent.substring(allContent.length()-32);
			// 用前8位字符串当成秘钥 解析xml体
//			String xmlBytes ="";
			byte[] key = encrypt(myKey.getBytes(), romdomCount.getBytes());
			byte[] data = decrypt(key, HexDump.hexStringToByteArray(xml));
			//处理填充问题
			int buwei = Integer.parseInt(romdomCount.substring(0, 1));
			String xmlBytes = new String(data,"GBK");
			xmlBytes = xmlBytes.substring(0, xmlBytes.length()-buwei);
			// 用hash值和xml体算出的hash值作对比
			if(hash_val.equalsIgnoreCase(Kit.getMd5_GBK(xmlBytes))){
			}else{
				return null;
			}
			return xmlBytes;
		}
	    
		
	    public static byte[] spellingChar(byte[] bytes){
//	    	byte[] bytes= xml.getBytes();
				byte[] byte_temp = null;
		    	int len = bytes.length;
		    	if(len%8 != 0){
		    		int yushu = len/8;
		    		int mo = len%8;
		    		int totalen = (yushu+1)*8; 
		    		byte_temp = new byte[totalen];
		    		int xiangjian = 8-mo;
		    		for(int j =0;j<xiangjian;j++){
		    			byte_temp[len+j]=(byte)(xiangjian); //用余数去填充
		    		}
		    		System.arraycopy(bytes, 0, byte_temp, 0, len);
		    		return byte_temp;
		    	}
	    	return bytes;
	    }
	    
	    @SuppressWarnings("static-access")
		public static void main(String[] args){
	    	DESUtil desutil = new DESUtil();
	    	try {
			  String str=desutil.dencodeJson("5042584282E8D3C1E6CDB89136DE32524FA2AFE2190A99A445A687D3FD0B4CDEFF90CE4D0F5ACD0D96F98D94DACA8D0B25A89789E15C11309C081E5C7A8D44E0F086DCCAEA25629D7E0F1C23F1236B0ED55C5949AF549900011D9FDD3A4438984044050B1CDFD09C201C6978"
					  ,"YSMbwyfz");
			  System.out.println(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}
