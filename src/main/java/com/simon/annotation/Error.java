package com.simon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description Error
 * @Author simon
 * @Date 2020/6/12 15:40
 * @Version 1.0
 */
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
public @interface Error {

    /**
     * 错误信息
     * @return
     */
    String msg();
}
