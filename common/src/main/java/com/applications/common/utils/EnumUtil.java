package com.applications.common.utils;

import java.lang.reflect.Method;

public class EnumUtil {



    public static Object valueOf(Object clazz,Object val)  {
        if(clazz == null || val == null){
            return null;
        }
        try {
            Class<?> aClass = Class.forName(String.valueOf(clazz));
            Method method = aClass.getMethod("name");
            Object[] enumConstants = aClass.getEnumConstants();
            for (Object obj : enumConstants) {
               if(method.invoke(obj).equals(val)){
                   return obj;
               }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object valueOfByStatus(Object clazz,Object val)  {
        if(clazz == null || val == null){
            return null;
        }
        try {
            Class<?> aClass = Class.forName(String.valueOf(clazz));
            Method method = aClass.getMethod("getStatus");
            Object[] enumConstants = aClass.getEnumConstants();
            for (Object obj : enumConstants) {
                if(String.valueOf(method.invoke(obj)).equals(String.valueOf(val))){
                    return obj;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args){
//        System.out.println(((PayWayEnum)EnumUtil.valueOfByStatus("com.dawanju.common.enums.PayWayEnum",9)).getDesc());
//    }
}
