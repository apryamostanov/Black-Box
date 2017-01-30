package com.a9ae0b01f0ffc.black_box.implementation.annotations
import org.codehaus.groovy.transform.GroovyASTTransformationClass
import java.lang.annotation.*
import com.a9ae0b01f0ffc.black_box.implementation.annotations.BlackBoxTransformation

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass("com.a9ae0b01f0ffc.black_box.implementation.annotations.BlackBoxTransformation")
@interface BlackBox {
    Class value() default {true}
}