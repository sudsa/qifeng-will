package com.bpaas.doc.framework.web.controller.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bpaas.doc.framework.base.command.NotProguard;
import com.bpaas.doc.framework.base.common.BaseConstant;
import com.bpaas.doc.framework.base.util.CheckEmptyUtil;
import com.bpaas.doc.framework.base.util.RexUtil;

@NotProguard
@Component
@Aspect
public class ValidateAspectHandle implements Ordered {

    @Override
    public int getOrder() {
        return BaseConstant.AspectOrder.Validate;
    }


    @Pointcut("@annotation(com.bpaas.doc.framework.web.controller.validate.ValidateGroup)")
    private void pointCutMethod() {

    }

    private static final Logger logger = LoggerFactory.getLogger(ValidateAspectHandle.class);

    /**
     * 使用AOP对使用了ValidateGroup的方法进行代理校验
     *
     * @throws Throwable
     */
    @Around("@annotation(com.bpaas.doc.framework.web.controller.validate.ValidateGroup)")
    public Object validateAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ValidateGroup an = null;
        Object[] args = null;
        Method method = null;
        Object target = null;
        String methodName = null;
        try {
            Signature signature = joinPoint.getSignature();
            methodName = signature.getName();//方法名称
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] argNames = methodSignature.getParameterNames();//方法参数名

            target = joinPoint.getTarget();
            method = getMethodByClassAndName(target.getClass(), methodName); // 得到拦截的方法
            args = joinPoint.getArgs(); // 方法的参数
            an = (ValidateGroup) getAnnotationByMethod(method, ValidateGroup.class);
            // 校验参数
            validateField(an.fileds(), args, argNames);
        } catch (Exception e) {
            logger.error("exception type:{}", e.getClass());
            throw e;
        } finally {
//            if (flag) {
//                logger.debug("验证通过");
//                return joinPoint.proceed();
//            } else { // 这里使用了Spring MVC ，所有返回值应该为Strng或ModelAndView ，如果是用Struts2，直接返回一个String的resutl就行了
//                logger.debug("验证未通过");
//                Class returnType = method.getReturnType(); // 得到方法返回值类型
//                if (returnType == String.class) { // 如果返回值为Stirng
//                    return "error/404"; // 返回错误页面
//                } else if (returnType == ModelAndView.class) {
//                    return new ModelAndView("error/404");// 返回错误页面
//                } else { // 当使用Ajax的时候 可能会出现这种情况
//                    return null;
//                }
//            }
        }
        return joinPoint.proceed();
    }

    /**
     * 验证参数是否合法
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public void validateField(ValidateField[] valiedatefiles, Object[] args, String[] argNames) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String fieldName = null;
        Object arg = null;
        for (ValidateField validateField : valiedatefiles) {
            fieldName = validateField.fieldName();
            if ("".equals(fieldName)) {
//                fieldName = "" + validateField.index();
                fieldName = argNames[validateField.index()];
                arg = args[validateField.index()];
            } else {
                arg = getFieldByObjectAndFileName(
                        args[validateField.index()],
                        fieldName);

            }
            // 判断参数是否为空
            if (validateField.notNull()) {
                String message = validateField.message().length() == 0 ? String.format("参数：%s不能为空", fieldName) : validateField.message();
                Assert.notNull(arg, message);
                Assert.isTrue(!(arg instanceof String && CheckEmptyUtil.isEmpty(arg.toString())), message);
            } else { // 如果该参数能够为空，并且当参数为空时，就不用判断后面的了 ，直接返回true
                if (arg == null) continue;
            }

            if (validateField.maxLen() > 0) { // 判断字符串最大长度
                String message = validateField.message().length() == 0 ? String.format("参数：%s最大长度不能超过%d", fieldName, validateField.maxLen()) : validateField.message();
                Assert.isTrue(!(((String) arg).length() > validateField.maxLen()), message);
            }

            if (validateField.minLen() > 0) { // 判断字符串最小长度
                String message = validateField.message().length() == 0 ? String.format("参数：%s最小长度不能小于%d", fieldName, validateField.minLen()) : validateField.message();
                Assert.isTrue(!(((String) arg).length() < validateField.minLen()), message);
            }

            if (validateField.maxVal() != -1) { // 判断数值最大值
                String message = validateField.message().length() == 0 ? String.format("参数：%s最大值不能超过%d", fieldName, validateField.maxVal()) : validateField.message();
                Assert.isTrue(!((Integer) arg > validateField.maxVal()), message);
            }

            if (validateField.minVal() != -1) { // 判断数值最小值
                String message = validateField.message().length() == 0 ? String.format("参数：%s最小值不能小于%d", fieldName, validateField.minVal()) : validateField.message();
                Assert.isTrue(!((Integer) arg < validateField.minVal()), message);
            }

            if (validateField.maxValD() != -1) { // 判断数值最大值
                String message = validateField.message().length() == 0 ? String.format("参数：%s最大值不能超过%f", fieldName, validateField.maxValD()) : validateField.message();
                Assert.isTrue(!((Double) arg > validateField.maxVal()), message);
            }

            if (validateField.minValD() != -1) { // 判断数值最小值
                String message = validateField.message().length() == 0 ? String.format("参数：%s最小值不能小于%f", fieldName, validateField.minValD()) : validateField.message();
                Assert.isTrue(!((Double) arg < validateField.minVal()), message);
            }
            if (validateField.mobile()) {
                String message = validateField.message().length() == 0 ? String.format("参数：%s手机号格式不正确", fieldName) : validateField.message();
                Assert.isTrue((RexUtil.isMobile((String) arg)), message);
            }
            if (validateField.email()) {
                String message = validateField.message().length() == 0 ? String.format("参数：%s邮箱格式不正确", fieldName) : validateField.message();
                Assert.isTrue((RexUtil.isEmail((String) arg)), message);
            }
            if (validateField.solrParam()) {
                if (arg != null) {
                    String str = (String) arg;
                    if (CheckEmptyUtil.isNotEmpty(validateField.excludeParam())) {
                        String[] excludeParams = validateField.excludeParam().split(BaseConstant.Separate.COMMA);
                        boolean isexclude = false;
                        for (String param : excludeParams) {
                            if (param.equals(str)) {
                                isexclude = true;
                                break;
                            }
                        }
                        if (isexclude) {
                            continue;
                        }
                    }
                    String message = validateField.message().length() == 0 ? String.format("参数：%s包含了特殊字符-和*", fieldName) : validateField.message();
                    Assert.isTrue(!(str.indexOf("-") >= 0 || str.indexOf("*") >= 0), message);
                }
            }

            if (!CheckEmptyUtil.isEmpty(validateField.regStr())) { // 判断正则
                String message = validateField.message().length() == 0 ? String.format("参数：%s格式不正确", fieldName) : validateField.message();
                Assert.isTrue(((String) arg).matches(validateField.regStr()), message);
            }
        }
    }


    /**
     * 根据对象和属性名得到 属性
     */
    public Object getFieldByObjectAndFileName(Object targetObj, String fileName)
            throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        String tmp[] = fileName.split("\\.");
        Object arg = targetObj;
        for (int i = 0; i < tmp.length; i++) {
            Method methdo = arg.getClass().getMethod(getGetterNameByFiledName(tmp[i]));
            arg = methdo.invoke(arg);
        }
        return arg;
    }

    /**
     * 根据属性名 得到该属性的getter方法名
     */
    public String getGetterNameByFiledName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 根据目标方法和注解类型 得到该目标方法的指定注解
     */
    public Annotation getAnnotationByMethod(Method method, Class<?> annoClass) {
        Annotation all[] = method.getAnnotations();
        for (Annotation annotation : all) {
            if (annotation.annotationType() == annoClass) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 根据类和方法名得到方法
     */
    public Method getMethodByClassAndName(Class<?> c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
