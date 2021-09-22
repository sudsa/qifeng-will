package com.hanxiaozhang.annotation;

import com.hanxiaozhang.enums.DataSourceKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈一句话功能简述〉<br>
 * 〈自定义目标数据源注解类〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {

    DataSourceKey dataSourceKey() default DataSourceKey.DB_MASTER;

}
