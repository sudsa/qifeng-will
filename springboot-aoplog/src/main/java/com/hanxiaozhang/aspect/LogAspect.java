package com.hanxiaozhang.aspect;

import com.alibaba.fastjson.JSON;


import com.hanxiaozhang.util.SensitiveUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能描述: <br>
 * 〈AOP切面日志拦截〉
 *
 * @Author:hanxinghua
 * @Date: 2020/1/29
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LogAspect {


    /*
    Tips:${hanxiaozhang.log.prefix:pro}，其中:冒号后面的是默认值
     */
    /**
     * 侧面
     */
    @Value("${hanxiaozhang.log.prefix:pro}")
    private String prefix;

    /**
     * 最大长度
     */
    @Value("${hanxiaozhang.log.max.length:2000}")
    private int maxLength;

    /**
     * 请求标识
     */
    @Value("${hanxiaozhang.log.request.flag:==>}")
    private String reqFlag;

    /**
     * 相应标识
     */
    @Value("${hanxiaozhang.log.response.flag:<==}")
    private String resFlag;

    /**
     * 是否启动标识
     */
    @Value("${hanxiaozhang.log.sensitive.enable:true}")
    private boolean sensitiveEnable;


    /**
     * 提供一个方法声明，定义切面的切入点
     * 指示符类型：
     * i.within 类型签名表达式
     * ii.execution 方法签名表达式
     *  ..：代表所有子目录，最后括号里的两个..代表所有参数
     * iii.bean(*Service) bean表达式
     * iv.@annotation() 注解
     *
     */
    @Pointcut("execution( * com.hanxiaozhang..controller.*.*(..))")
    public void logPointCut() {

    }


    /**
     * “通知”在实际方法调用之前执行
     *
     * @param joinPoint
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint){
        log.info("-----Before开始执行-----");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("HTTP METHOD : " + request.getMethod());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        log.info("参数 : " + Arrays.toString(joinPoint.getArgs()));

    }


    /**
     * “通知”在实际方法调用之后执行，
     * 并且实际方法是成功返回或抛出异常，该“通知”正常被执行
     *
     * 疑问：使用场景是什么？20200130
     *
     * @param joinPoint
     */
    @After("logPointCut()")
    public void doAfter(JoinPoint joinPoint){
        log.info("-----After开始执行-----");
//        System.out.println("doAfter传入参数 : " + Arrays.toString(joinPoint.getArgs()));
    }


    /**
     * “通知”在实际方法调用之后执行，可以获取实现方法的返回值，
     *  如果从一个“通知”方法中抛出一个异常，则不会执行该“通知”
     *
     *  Tips:returning的值和doAfterReturning的形式参数名一致
     *
     * @param ret
     */
    @AfterReturning(pointcut = "logPointCut()",returning = "ret")
    public void doAfterReturning(Object ret) {
        log.info("-----AfterReturning开始执行-----");
        // 处理完请求，返回内容(返回值太复杂时，只打印的是物理存储空间的地址)
        log.info("doAfterReturning返回值 : " + ret);
    }



    /**
     * “通知”在抛出一个异常且该异常被调用方法捕获之前执行，
     *     该“通知”可以任何需要执行的特定业务逻辑
     *
     * @param joinPoint
     * @param t
     */
    @AfterThrowing(pointcut = "logPointCut()",throwing="t")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable t){
        log.info("-----AfterThrowing开始执行-----");
        log.info("doAfterThrowing抛出异常 : " + t);
        //模拟“通知”对异常的修复...
    }



    /**
     * 环绕通知,
     * Tips：它可以修改实际方法返回的值和调用@AfterReturning返回的值
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "@within(com.hanxiaozhang.annotation.LogAnnotation)||logPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("-----Around开始执行-----");
        String msg = null;
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        long startTime = System.currentTimeMillis();
        // 取得所执行的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 取得所执行的方法参数
        Object[] param = joinPoint.getArgs();
        // 输出方法名LOG
        log.info("{}{}方法:{}()", prefix, reqFlag, method.getName());
        if (param != null) {
            int i = 0;
            for (Object obj : param) {
                msg = objectToString(obj);
                log.info("{}{}【参数{}】:{}", prefix, reqFlag, i++, msg);
            }
        }
        //让目标方法执行，获取实际方法的返回值
        Object obj = joinPoint.proceed(joinPoint.getArgs());
        String resultJson = objectToString(obj);
        long elapsed = System.currentTimeMillis() - startTime;
        log.info("{}{}【方法{}()执行时间】:{} ms", prefix, resFlag, method.getName(), elapsed);
        log.info("{}{}【方法{}()返回值】:{}", prefix, resFlag, method.getName(), resultJson);

        return obj;
    }

    /**
     * 将对象转为字符串作为log输出
     *
     * @param obj
     * @return 字符串
     */
    private String objectToString(Object obj) {
        // 对象为空
        if (obj == null) {
            return "";
        }
        // 对象为枚举类
        if (obj instanceof Enum) {
            return obj.toString();
        }
        try {
            String json = null;
            if (sensitiveEnable) {
                json = SensitiveUtil.toJsonString(obj);
            } else {
                json = JSON.toJSONString(obj);
            }
            if (json.length() > maxLength) {
                return json.substring(0, maxLength) + "...";
            }
            return json;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return obj.toString();
        }
    }


}
