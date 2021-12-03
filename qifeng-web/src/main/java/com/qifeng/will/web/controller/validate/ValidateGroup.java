package com.bpaas.doc.framework.web.controller.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bpaas.doc.framework.base.command.NotProguard;

@NotProguard
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateGroup {
    public ValidateField[] fileds();
}
