package com.treeyh.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * @author TreeYH
 * @version 1.0
 * @description Json工具类
 * @create 2019-05-17 18:03
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static{
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 对象转换为json
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * json转换为对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return JSON.parseObject(json, clazz);
        }catch (Exception ex){
            logger.error("json decode error. json:"+json+";class:"+clazz.getName());
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
            return JSON.parseObject(json, type);
        }catch (Exception ex){
            logger.error("json decode error. json:"+json+";type:"+type.getType().getTypeName());
            return null;
        }
    }

    /**
     * json转换为泛型
     * @param json
     * @param type
     * @return
     */
    public static <T> T fromJson(String json, Type type){
        try {
            return JSON.parseObject(json, type);
        }catch (Exception ex){
            logger.error("json decode error. json:"+json+";type:"+type.getTypeName());
            return null;
        }
    }


}
