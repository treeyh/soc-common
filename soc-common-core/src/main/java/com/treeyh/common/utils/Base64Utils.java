package com.treeyh.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author TreeYH
 * @version 1.0
 * @description Base64工具类
 * @create 2019-05-17 17:59
 */
public class Base64Utils {

    public static String getBase64(String str){
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }
}
