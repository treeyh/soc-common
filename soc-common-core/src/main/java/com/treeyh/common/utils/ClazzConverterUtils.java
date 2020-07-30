package com.treeyh.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author TreeYH
 * @version 1.0
 * @description 类型转换工具类
 * @create 2019-05-17 18:00
 */
public class ClazzConverterUtils {

    public static <T1, T2> T1 converterClass(T2 srcClazz, Class<T1> dstClazz) {
        String json = JsonUtils.toJson(srcClazz);
        return StringUtils.isEmpty(json) ? null : JsonUtils.fromJson(json, dstClazz);
    }

    public static <T1, T2> T1 converterClass(T2 srcClazz, TypeReference<T1> type) {
        String json = JsonUtils.toJson(srcClazz);
        return json == null ? null : JsonUtils.fromJson(json, type);
    }
}
