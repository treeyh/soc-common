package com.treeyh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author TreeYH
 * @version 1.0
 * @description 线程工具类
 * @create 2019-05-17 18:07
 */
public class ThreadUtils {

    public static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("thread sleep fail." + e.getMessage(), e);
        }
    }
}
