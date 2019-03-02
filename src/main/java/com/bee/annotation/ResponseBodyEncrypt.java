package com.bee.annotation;

import java.lang.annotation.*;

/**
 * @ClassName ResponseBodyEncrypt
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/27 9:41
 * @Version 1.0
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBodyEncrypt {
    boolean encrypt() default true;
}
