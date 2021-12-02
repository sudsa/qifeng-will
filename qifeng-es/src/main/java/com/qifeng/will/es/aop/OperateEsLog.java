package com.qifeng.will.es.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateEsLog {
    
	//01:新增，02：修改，03：删除
	String operType();
	//埋点描述
	String operDesc();
	//业务类型（如设备管理、产品管理等）
    String businessType();
}
