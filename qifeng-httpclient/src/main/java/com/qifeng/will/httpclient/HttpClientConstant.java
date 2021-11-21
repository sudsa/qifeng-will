package com.qifeng.will.httpclient;

/**
 * 〈一句话功能简述〉<br>
 * 〈httpclient常量〉
 *
 * @author howill.zou
 * @create 2019/10/8
 * @since 1.0.0
 */
public class HttpClientConstant {
    /**
     * 连接超时时间
     */
    public static final int CONNECTION_TIMEOUT = 5000;

    /**
     * 请求超时时间
     */
    public static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    /**
     * 数据读取等待超时
     */
    public static final int SOCKET_TIMEOUT = 10000;

    /**
     * http
     */
    public static final String HTTP = "http";

    /**
     * https
     */
    public static final String HTTPS = "https";

    /**
     * http端口
     */
    public static final int DEFAULT_HTTP_PORT = 80;

    /**
     * https端口
     */
    public static final int DEFAULT_HTTPS_PORT = 443;

    /**
     * 默认编码
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

}
