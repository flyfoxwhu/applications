package com.applications.common.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * Created by weishuai on 15/11/24.
 * 断言工具，进行提前判空等操作
 */
public class AssertUtil {
    public static void notNull(Object obj,String name){
        if(null == obj){
            throw new NullPointerException(name+" can not be null");
        }
    }

    public static void isTrue(boolean expression,String name){
        if(!expression){
            throw new RuntimeException(name +"must be true");
        }
    }

    public static void notBlank(String str, String name){
        if(StringUtils.isBlank(str)){
            throw new RuntimeException(name +"must be blank");
        }
    }

    public static <T>  void notEmpty(Collection<T> collection, String name){
        if(CollectionUtils.isEmpty(collection)){
            throw new RuntimeException(name +"must be empty");
        }
    }

}
