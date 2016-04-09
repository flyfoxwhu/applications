package com.applications.common.utils;


import com.applications.common.enums.PrefixEnum;

import java.util.ArrayList;
import java.util.List;

public class KeyUtil {

    public static String getListKey(Object id1,Object id2,PrefixEnum prefixEnum) {
        return prefixEnum.getValue() + id1 + ":" + id2;
    }

    public static String getKey(Object id,PrefixEnum prefixEnum){
        return prefixEnum.getValue()+id;
    }

    public static String getPatternKey(PrefixEnum prefixEnum){
        return prefixEnum.getValue()+"*";
    }

    public static String getPatternKey(PrefixEnum prefixEnum,Object id) {
        return prefixEnum.getValue() + id + "*";
    }


    public static List<String> getKeys(List<Long> ids, PrefixEnum prefixEnum) {
        List<String> result = new ArrayList<String>();
        for (Long id : ids){
            String key = prefixEnum.getValue() + id;
            result.add(key);
        }
        return result;
    }

    /**
     * 传入天数，获取总共的秒数
     * @param days
     * @return
     */
    public static int getSecondByDays(int days){
        if(days>0){
            return 60*60*24*days;
        }return 0;
    }

}
