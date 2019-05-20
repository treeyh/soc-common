package com.treeyh.common.utils;

import java.util.UUID;

/**
 * @author TreeYH
 * @version 1.0
 * @description UUID工具类
 * @create 2019-05-17 18:16
 */
public class UuidUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getNewId(){

        String newId = System.currentTimeMillis() +
                (Long.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).toString().substring(0, 6));

        return newId;
    }

    public static Long getNewIdByLong(){

        String newId = System.currentTimeMillis() +
                (Long.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).toString().substring(0, 6));
        return Long.parseLong(getNewId());
    }
}
