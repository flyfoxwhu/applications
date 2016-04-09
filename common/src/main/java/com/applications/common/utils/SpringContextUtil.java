package com.applications.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx=applicationContext;
    }

    public static Object getBean(String name){
        return ctx.getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> t){
        return ctx.getBeansOfType(t);
    }


}
