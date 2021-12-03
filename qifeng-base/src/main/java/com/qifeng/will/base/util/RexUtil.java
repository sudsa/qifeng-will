package com.bpaas.doc.framework.base.util;

import java.util.regex.Matcher ;
import java.util.regex.Pattern ;

/**
 * 正则表达式工具类
 * 
 * @author dengzm
 *
 */
public class RexUtil {

  /**
   * 
   * @Description: 判断某个字符串是否满足某个正则表达式
   * @param regExp
   *        正则表达式
   * @param target
   *        待处理的字符串
   * @return boolean 匹配：true，不匹配：false
   */
  public static boolean isMatch(String regExp , String target) {
    if (regExp == null || target == null) {
      return false ;
    }
    Pattern pattern = Pattern.compile(regExp) ;
    Matcher matcher = pattern.matcher(target) ;
    return matcher.matches() ;
  }

  /**
   * @Description: 判断某个字符串是否是邮箱格式
   * @param target
   *        待处理的邮箱地址
   * @return 匹配：true，不匹配：false
   */
  public static boolean isEmail(String target) {
    return isMatch("^([\\w]+)(.[\\w]+)*@([\\w-]+\\.){1,5}([A-Za-z]){2,4}$" , target) ;
  }
  
  /**
   * 
   * @Description: 判断某个字符串是否是手机格式
   * @param @param target
   * @param @return   
   * @return boolean    
   * @throws
   */
  public static boolean isMobile(String target){
      return isMatch("^(13|15|18)\\d{9}$" , target) ;
  }

  /**
   * 判断某个字符串是否包含数字和字母
   * 
   * @param target
   *        待处理的字符串
   * @return 匹配：true，不匹配：false
   */
  public static boolean isAlphaAndNumber(String target) {
    return isMatch("^[A-Za-z0-9]+$" , target) ;
  }

  /**
   * 校验是否合法的命名
   * 校验是否合法的命名
   * @param target 待处理的字符串
   * @return 匹配：true，不匹配：false
   */
	public static boolean isValidNamed(String target) {
		return isMatch("[_a-zA-Z][_a-zA-Z0-9]*", target);
	}

	public static boolean isValidParam(String target) {
		return isMatch("[_a-zA-Z0-9\\[\\]\\.\".,:{}]*", target);
	}

}
