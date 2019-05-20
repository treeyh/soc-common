package com.treeyh.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author TreeYH
 * @version 1.0
 * @description MD5工具类
 * @create 2019-05-17 18:04
 */
public class MD5Utils {

    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            byte[] hash = md.digest();
            StringBuilder secpwd = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16) {
                    secpwd.append(0);
                }
                secpwd.append(Integer.toString(v, 16));
            }
            return secpwd.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(String.format("str:%s, error: %s", str, e.getMessage()) , e);

        }
        return StringUtils.EMPTY;
    }
}
