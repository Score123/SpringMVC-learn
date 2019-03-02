package com.bee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName RequestBodyDecrypt
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/26 15:58
 * @Version 1.0
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBodyDecrypt {
    String value() default "";

    /**
     * 模仿RequestBody
     * @return
     */
    boolean required() default true;

}