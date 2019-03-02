package com.bee.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName RequestParamDecrypt
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/27 16:23
 * @Version 1.0
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParamDecrypt {
    String value() default "";
    boolean required() default true;
    boolean keyDecrypt() default false;
    boolean valueDecrypt() default true;
}