package com.treeyh.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author TreeYH
 * @version 1.0
 * @description 容器工具类
 * @create 2019-05-17 18:00
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
