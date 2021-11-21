package com.hanxiaozhang.aspect;

import com.hanxiaozhang.annotation.TargetDataSource;
import com.hanxiaozhang.config.DynamicDataSourceContextHolder;
import com.hanxiaozhang.enums.DataSourceKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br>
 * 〈动态切换切换数据源切面〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {


    @Pointcut("(execution(* com.hanxiaozhang..service.*.list*(..)))||" +
            "(execution(* com.hanxiaozhang..service.*.count*(..)))||"+
            "(execution(* com.hanxiaozhang..service.*.select*(..)))||"+
            "(execution(* com.hanxiaozhang..service.*.get*(..)))")
    public void pointCut() {
    }



    @Before("pointCut()")
    public void doBeforeWithSlave(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Execute DataSource AOP Method：{}",methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
        //获取当前切点方法对象
        Method method = methodSignature.getMethod();
        //判断是否为接口方法
        if (method.getDeclaringClass().isInterface()) {
            try {
                //获取实际类型的方法对象
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                log.error("方法不存在！", e);
            }
        }
        //判断该方法是否存在TargetDataSource注解
        if (null == method.getAnnotation(TargetDataSource.class)) {
            DynamicDataSourceContextHolder.setSlave();
        }

    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Clear DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.get(),
                methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
        DynamicDataSourceContextHolder.clear();

    }


    /**
     * 执行方法前更换数据源
     *
     * @param joinPoint        切点
     * @param targetDataSource 动态数据源
     */
    @Before("@annotation(targetDataSource)")
    public void doBefore(JoinPoint joinPoint, TargetDataSource targetDataSource) {

        DataSourceKey dataSourceKey = targetDataSource.dataSourceKey();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (dataSourceKey != DataSourceKey.DB_MASTER) {
            DynamicDataSourceContextHolder.set(dataSourceKey);
            log.info("Set DataSource to [{}] in Method [{}]", dataSourceKey,
                    methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
        } else {
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_MASTER);
            log.info("Use Default DataSource to [{}] in Method [{}]", DataSourceKey.DB_MASTER,
                    methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
        }
    }

    /**
     * 执行方法后清除数据源设置
     *
     * @param joinPoint        切点
     * @param targetDataSource 动态数据源
     */
    @After("@annotation(targetDataSource)")
    public void doAfter(JoinPoint joinPoint, TargetDataSource targetDataSource) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Clear DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.get(),
                methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
        DynamicDataSourceContextHolder.clear();

    }


}