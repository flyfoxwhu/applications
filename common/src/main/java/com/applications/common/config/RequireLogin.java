package com.applications.common.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 标识方法或类为需要登录，如果标识在类上面，则所有方法都需要登录
 */
@Target({ TYPE, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLogin {
}
