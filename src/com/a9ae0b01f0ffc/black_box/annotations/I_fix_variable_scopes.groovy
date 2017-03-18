package com.a9ae0b01f0ffc.black_box.annotations

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@GroovyASTTransformationClass("com.a9ae0b01f0ffc.black_box.annotations.T_fix_variable_scopes_transformation")
@interface I_fix_variable_scopes {
    String value() default ""
}