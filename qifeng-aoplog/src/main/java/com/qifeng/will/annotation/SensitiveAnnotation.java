package com.hanxiaozhang.annotation;

import com.hanxiaozhang.enums.SensitiveType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 功能描述: <br>
 * 〈脱敏字段注解〉
 *
 * @Author:hanxinghua
 * @Date: 2020/1/29
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveAnnotation {
    SensitiveType type();
}
