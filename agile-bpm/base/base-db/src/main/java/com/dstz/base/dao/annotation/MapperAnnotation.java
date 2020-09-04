package com.dstz.base.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/4-11:52
 */
//mybatis 注解，需要 mybatis 映射的必須加上這個注解
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperAnnotation {

}

