package com.treeyh.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author TreeYH
 * @version 1.0
 * @description Json工具类
 * @create 2019-05-17 18:03
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper objectMapper;

    static{
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //属性为null的转换
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置为东八区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //类为空时，不要抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //反序列化时,遇到未知属性时是否引起结果失败
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //单引号处理
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //解析器支持解析结束符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * 对象转换为json
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * json转换为对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * json转换为泛型
     * @param json
     * @param type
     * @return
     */
    public static <T> T fromJson(String json, TypeReference<T> type){
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


}
