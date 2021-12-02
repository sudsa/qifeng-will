package com.qifeng.will.es.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

;

/**
 * Eslogger切入增强方法
 *
 * @author huyang
 * @date 2021-08-09
 */
@Aspect
@Component
public class OperateEsLogAspect {

    private static Logger logger = LoggerFactory.getLogger(OperateEsLogAspect.class);

    //@Autowired
    //private OperateLogService operateLogService;

    @Pointcut("@annotation(com.qifeng.will.es.aop.OperateEsLog)")
    public void esAspect() {
        logger.info("==========================正在执行切入点===========================");
    }

    @Before("esAspect()")
    public void getTargetMethod(JoinPoint jp) {
        logger.info("==========================执行切入点开始前===========================");
        String targetName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        logger.info("当前执行类：{}, 当前执行方法：{}", targetName, methodName);
    }

    @Around("esAspect()")
    public Object getTargetProcess(ProceedingJoinPoint jp) {
        long start = System.currentTimeMillis();
        try {
            String targetName = jp.getTarget().getClass().getName();
            String methodName = jp.getSignature().getName();
            logger.info("执行类：{}, 方法：{}开始", targetName, methodName);
            Object obj = jp.proceed();
            long end = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("执行类：{}, 方法：{}，总共耗时{}ms", targetName, methodName, Long.valueOf(end - start));
            }
            logger.info("执行类：{}, 方法：{}结束", targetName, methodName);
            return obj;
        } catch (Throwable e) {
            if (logger.isInfoEnabled()) {
                logger.error("异常结束，结束原因：{}", e.getMessage());
            }
            throw new RuntimeException("处理失败，请重试!");
        }
    }

    @After("esAspect()")
    public void getTargetParam(JoinPoint jp) {
        String targetName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        logger.info("执行类：{}, 方法：{}结束", targetName, methodName);
        //获取方法中的参数
        Object args[] = jp.getArgs();
        //获取注解中的属性值
        Method[] methods = jp.getTarget().getClass().getMethods();
        String bussinessType = "";
        String operType = "";
        String operDesc = "";
        for (Method method : methods) {
            if (method.getModifiers() == 1 && method.getName().equals(methodName)) {
                bussinessType = ((OperateEsLog) method.getAnnotation(OperateEsLog.class)).businessType();
                operType = ((OperateEsLog) method.getAnnotation(OperateEsLog.class)).operType();
                operDesc = ((OperateEsLog) method.getAnnotation(OperateEsLog.class)).operDesc();
            }
        }

    }

/*    private HttpServletRequest getRequest(Object[] objs) throws OAuthException {
        // ServletRequestAttributes may be null, check it first.
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        logger.debug("未找到HttpServletRequest参数");
        return null;
    }*/
}
