package com.treeyh.common.model;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TreeYH
 * @version 1.0
 * @description 日志对象
 * @create 2019-05-17 18:03
 */
public class LogModel {

    private static final Logger logger = LoggerFactory.getLogger(LogModel.class);

    private Map<String, Object> datas = new HashMap();
    private String method;
    private long start = 0L;
    private final AtomicInteger serialId = new AtomicInteger(0);

    private LogModel(String name) {
        this.start = System.currentTimeMillis();
        this.method = name + "#" + this.start + "#";
        this.datas.put("_method", this.method);
    }

    public static LogModel newLogModel(String method) {
        return new LogModel(method);
    }

    public LogModel setResultMessage(long result, String message) {
        this.addMetaData("_result", result).addMetaData("_message", message);
        return this;
    }

    public LogModel addMetaData(String key, Object value) {
        if (value != null) {
            this.datas.put(key, value);
        } else {
            this.datas.put(key, "");
        }
        return this;
    }

    public LogModel addMetaDataError(Object value){
        return this.addMetaData("error", value);
    }

    public LogModel addMetaDataResult(Object value){
        return this.addMetaData("return", value);
    }

    public LogModel delMetaData(String key) {
        if (key != null) {
            this.datas.remove(key);
        }

        return this;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap();
        Iterator var2 = this.datas.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)var2.next();
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    public String toJson(boolean purge) {
        try {
            this.datas.put("_serialId", this.serialId.incrementAndGet());
            if (purge) {
                this.datas.put("handleCost", System.currentTimeMillis() - this.start);
                JSONObject ja = (JSONObject)JSONObject.toJSON(this.datas);
                this.purge();
                return ja.toString();
            } else {
                Map<String, Object> map = this.toMap();
                map.put("handleCost", System.currentTimeMillis() - this.start);
                JSONObject ja = (JSONObject)JSONObject.toJSON(map);
                return ja.toString();
            }
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            return "{data:error}";
        }
    }

    private void purge() {
        this.datas.clear();
        this.datas.put("_method", this.method);
    }

    public String endJson() {
        return this.toJson(true);
    }

    public String toJson() {
        return this.toJson(true);
    }
}
