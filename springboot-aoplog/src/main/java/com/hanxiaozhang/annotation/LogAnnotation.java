package com.hanxiaozhang.annotation;

import java.lang.annotation.*;


/**
 * 功能描述: <br>
 * 〈切面日志注解〉
 *
 * 元注解知识：
 * @Target:标识注解的使用范围，赋值为ElementType类型
 *  ElementType类型:TYPE(类、接口、枚举类、注解类)，FIELD(成员变量、枚举中常量)，METHOD、PARAMETER(形式参数)、
 *                CONSTRUCTOR、LOCAL_VARIABLE(局部变量)、ANNOTATION_TYPE(注解类型)、PACKAGE、
 *                TYPE_PARAMETER(类型参数)、TYPE_USE(任何类型名称)
 *  Tips:类型参数是泛型的知识
 *
 * @Retention:标识注解的声明周期(存留时间)，赋值为RetentionPolicy类型
 *  RetentionPolicy：SOURCE(Java源文件阶段)，CLASS(编译到class文件阶段)，RUNTIME(运行期阶段)
 *
 * @Documented:指定自定义注解是否能随着被定义的java文件生成到JavaDoc文档当中
 *
 * @Inherited:指定某个自定义注解如果写在了父类的声明部分，那么子类的声明部分也能自动拥有该注解
 *  Tips:@Inherited注解只对那些@Target被定义为ElementType.TYPE的自定义注解起作用
 *
 * @Author:hanxinghua
 * @Date: 2020/1/29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LogAnnotation {
}
