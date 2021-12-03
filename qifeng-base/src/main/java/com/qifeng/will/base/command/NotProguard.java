package com.qifeng.will.base.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否混淆代码的注解，仅在编译时由效果，不影响运行
 * @author huyang
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.FIELD})
public @interface NotProguard {

}