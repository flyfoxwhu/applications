package com.applications.common.utils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 产生UUID
     * @return
     */
    public static String generateUUID() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }
}
