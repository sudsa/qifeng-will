

package com.bpaas.doc.framework.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bpaas.doc.framework.base.common.BaseConstant;
import com.bpaas.doc.framework.base.util.CheckEmptyUtil;
import com.bpaas.doc.framework.base.util.RexUtil;
import com.bpaas.doc.framework.base.util.StringUtil;

/**
 * 非法字符过滤器 1.所有非法字符配置在web.xml中，如需添加新字符，请自行配置
 * 2.请注意请求与相应时的编码格式设置，否则遇到中文时，会出现乱码(GBK与其子集应该没问题)
 * 
 * @author dengzm
 */
public class CharFilter implements Filter {

    private static final Logger logger = LoggerFactory
            .getLogger(CharFilter.class);

    private String encoding;

    @SuppressWarnings("unused")
    private String[] legalNames;

    private String[] illegalChars;

    @Override
    public void destroy() {
        encoding = null;
        legalNames = null;
        illegalChars = null;
    }

    /**
     * 校验get或者post请求的参数
     * 
     * @param req
     *            请求
     * @return 参数合法：false，非法：true
     */
    private boolean checkParams(HttpServletRequest req) {
        // 非法状态 true：非法 false；不非法
        boolean illegalStatus = false;
        Enumeration<?> params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = (String) params.nextElement();
            // 校验参数名
            if (CheckEmptyUtil.isNotEmpty(paramName)
                    && RexUtil.isValidParam(paramName)) {
                if ("password".equals(paramName)) {
                    continue;
                }
                // 校验参数值
                String[] paramValues = req.getParameterValues(paramName);
                if (CheckEmptyUtil.isEmpty(paramValues)) {
                    for (String paramValue : paramValues) {
                        if (StringUtil.inStrs(paramValue, illegalChars)) {
                            illegalStatus = true;
                            break;
                        }
                    }
                }
            } else {
                illegalStatus = true;
                break;
            }
        }
        return illegalStatus;
    }

    private boolean checkUri(HttpServletRequest req) {
        // 非法状态 true：非法 false；不非法
        boolean illegalStatus = false;
        String uri = req.getRequestURI();
        logger.debug("tempURL:{}", uri);
        if (!CheckEmptyUtil.isEmpty(illegalChars)) {
            for (String illegalChar : illegalChars) {
                if (uri.indexOf(illegalChar) != -1) {
                    illegalStatus = true;// 非法状态
                    break;
                }
            }
        }
        return illegalStatus;
    }

    /**
     * 异常提示
     * 
     * @param res
     *            异常响应
     * @throws IOException
     *             抛出io异常
     */
    private void errorResp(HttpServletResponse res) throws IOException {
        // 必须手动指定编码格式
        res.setContentType(BaseConstant.CONTENT_TYPE);
        res.getWriter().print("<script>window.alert('当前链接中存在非法字符');window.history.go(-1);</script>");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 必须手动指定编码格式
        req.setCharacterEncoding(encoding);
        // 防止跨站点请求伪造
//        String referer = req.getHeader("Referer"); // REFRESH
//        String serverName = request.getServerName();
//        if (null != referer && referer.indexOf(serverName) < 0) {
//            req.getRequestDispatcher(req.getRequestURI())
//                    .forward(req, response);
//        }
        // 非法状态 true：非法 false；不非法
        boolean illegalStatus = checkParams(req);
        if (illegalStatus) {
            // 非法
            errorResp(res);
        } else {
            if (checkUri(req)) {
                // 教研uri的合法性
                errorResp(res);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        if (!CheckEmptyUtil
                .isEmpty(filterConfig.getInitParameter("legalNames"))) {
            legalNames = filterConfig.getInitParameter("legalNames").split(",");
        }
        if (!CheckEmptyUtil.isEmpty(filterConfig
                .getInitParameter("illegalChars"))) {
            illegalChars = filterConfig.getInitParameter("illegalChars").split(
                    ",");
        }
    }

}
