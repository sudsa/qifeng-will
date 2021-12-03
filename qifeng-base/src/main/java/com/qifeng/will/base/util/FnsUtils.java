package com.bpaas.doc.framework.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FnsUtils {
	
	/**
	 * 
	 * @Description: 格式化数字，每隔4位加一个空格
	 * @param @param num
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatNum(String num){
		String regex = "(.{4})";
		return num.replaceAll (regex, "$1 ");
	}
	
	/**
	 * 
	 * @Description: 格式化时间
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatDate(String date){
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		return year+"年"+month+"月"+day+"日";
	}
	
	/**
	 * 
	 * @Description: 格式化时间
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatDateOther(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(date!=null){
			String dateTime = sdf.format(date);
			return dateTime;
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * @Description: 格式化时间
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatDateSecond(String date){
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		return year+"."+month+"."+day;
	}
	
	/**
	 * 
	 * @Description: 格式化时间
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatDateThird(String date){
		return date.substring(0, 10);
	}
	
	/**
	 * 
	 * @Description: 格式化时间
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatDateLast(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		if(date!=null){
			String dateTime = sdf.format(date);
			return dateTime;
		}else{
			return "";
		}
	}
	
	/***
	 * 
	 * @Description: 服务费日期格式化
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatFeeDate(String date){
		int year = Integer.parseInt(date.substring(0, 4))+1;
		String month = date.substring(4, 6);
		int day = Integer.parseInt(date.substring(6, 8))-1;
		return year+"-"+month+"-"+day;
	}
	
	/***
	 * 
	 * @Description: 服务费日期格式化
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatFeeStartDate(String date){
		int year = Integer.parseInt(date.substring(0, 4));
		String month = date.substring(4, 6);
		int day = Integer.parseInt(date.substring(6, 8));
		return year+"-"+month+"-"+day;
	}
	
	/***
	 * 
	 * @Description: 服务费日期格式化
	 * @param @param date
	 * @param @return   
	 * @return String    
	 * @throws
	 */
	public static String formatFeeDateOther(String date){
		int year = Integer.parseInt(date.substring(0, 4))+1;
		String month = date.substring(4, 6);
		int day = Integer.parseInt(date.substring(6, 8))-1;
		return year+month+day;
	}
}
