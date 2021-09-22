package com.qifeng.will.base.security;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: Kit 
 * @Description: 工具类 
 * @author: Ching Wang
 * @date 2013-5-18 下午1:04:46 
 *
 */
public class Kit {

	public static boolean isNotNull(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		}
		return false;
	}

	// 格式化日期时间字符串
	// @format 常用格式1.yyyy-MM-dd HH:mm:ss 2.yyyy-MM-dd
	public static String formatDateTime(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}
    //截取字符串长度(中文2个字节，半个中文显示一个)  
    public static String subTextString(String str,int len){  
        if(str.length()<len/2)return str;  
        int count = 0;  
        StringBuffer sb = new StringBuffer();  
        String[] ss = str.split("");  
        for(int i=1;i<ss.length;i++){  
            count+=ss[i].getBytes().length>1?2:1;  
            sb.append(ss[i]);  
            if(count>=len)break;  
        }  
        //不需要显示...的可以直接return sb.toString();  
        return (sb.toString().length()<str.length())?sb.append("...").toString():str;  
    }  

	// 解析日期时间,解析失败返回null
	// @format 常用格式1.yyyy-MM-dd HH:mm:ss 2.yyyy-MM-dd
	public static Date parseDateTimeStr(String dateTimeStr, String format) {
		dateTimeStr = dateTimeStr.replace('.', '-');
		dateTimeStr = dateTimeStr.replace('T', ' ');
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(format);
		Date date = null;
		try {
			date = sdf.parse(dateTimeStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

	public static long compareDate(Date plan, Date actual) {

		return plan.getTime() - actual.getTime();
	}

	// 判断是否是UTF-8编码
	public static boolean isUTF8(String s) {
		try {
			if (s != null && s.length() != 0) {
				String tmp = new String(s.getBytes("UTF-8"), "UTF-8");
				return s.equals(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	// 判断是否是ISO-8859-1编码
	public static boolean isISO8859(String s) {
		try {
			if (s != null && s.length() != 0) {
				String tmp = new String(s.getBytes("ISO-8859-1"), "ISO-8859-1");
				return s.equals(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String getMd5(String plainText) {
		try {
			//if(plainText != null && !"".equals(plainText))
			//	plainText = Constans.PRE_FIX + plainText + Constans.SU_FIX;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeDuplicateWithOrder(List list, Set set, List subList) {
		if(subList != null && subList.size() > 0){
			for (Iterator iter = subList.iterator(); iter.hasNext();) {
				Object element = iter.next();
				if (set.add(element)){
					list.add(element);
				}
			}
		}
	}
	
	 public static String getIpAddress(HttpServletRequest request){    
	        String ip = request.getHeader("x-forwarded-for");    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("Proxy-Client-IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("WL-Proxy-Client-IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("HTTP_CLIENT_IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getRemoteAddr();    
	        }    
	        return ip;    
	    }
	 
	 public static Double doubleFormat(double d){
		 DecimalFormat df=new DecimalFormat(".##");
		 String st=df.format(d);
		 return Double.parseDouble(st);
	 }
	 
	 public static String getMd5_GBK(String plainText) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(plainText.getBytes("GBK"));
				byte b[] = md.digest();

				int i;

				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				// 32位加密
				return buf.toString().toUpperCase();
				// 16位的加密
				// return buf.toString().substring(8, 24);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return null;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}

}
