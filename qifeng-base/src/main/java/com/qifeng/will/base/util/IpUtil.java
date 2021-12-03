/**
 * 
 */
package com.bpaas.doc.framework.base.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 *
 */
public class IpUtil {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取经过nginx的真是IP，nginx参考以下文章增加配置
     * https://blog.csdn.net/sinat_32247833/article/details/79375281
     * @param request
     * @return
     */
    public static String getIpAddrViaNginx(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = getIpAddr(request);
        }
        return ip;
    }
}
