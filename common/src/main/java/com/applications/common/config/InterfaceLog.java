package com.applications.common.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by hukaisheng on 16/3/9.
 * 记录接口调用的日志
 */
@Target({ TYPE, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InterfaceLog {
    String interName();
}
