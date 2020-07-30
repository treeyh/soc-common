package com.treeyh.common.model.result;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author TreeYH
 * @version 1.0
 * @description 返回描述
 * @create 2019-05-17 19:10
 */
public class ReturnResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private T data;


    public ReturnResult(int code, String msg){
        this(code, msg, null);
    }

    public ReturnResult(ResultCodeInfo resultCodeInfo, Object... args){

        this(resultCodeInfo.getCode(), String.format(resultCodeInfo.getDesc(), args), null);
    }

    public ReturnResult(T data, ResultCodeInfo resultCodeInfo){
        this(resultCodeInfo.getCode(), resultCodeInfo.getDesc(), data);
    }

    public ReturnResult(T data, ResultCodeInfo resultCodeInfo, Object... args){
        this(resultCodeInfo.getCode(), String.format(resultCodeInfo.getDesc(), args), data);
    }

    //解决多个构造函数的json转化冲突
    @JsonCreator
    public ReturnResult(@JsonProperty("code") Integer code, @JsonProperty("message") String message, @JsonProperty("data") T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ReturnResult success(){
        return new ReturnResult(ResultCode.SUCCESS);
    }

    public static <T>ReturnResult<T> success(T data){
        return new ReturnResult<T>(data, ResultCode.SUCCESS);
    }


    public static ReturnResult error(Integer code, String msg){
        return new ReturnResult(code, msg);
    }


    public static ReturnResult error(ResultCodeInfo resultCodeInfo, Object... args){
        return new ReturnResult(resultCodeInfo, args);
    }
    public static <T>ReturnResult<T> error(T data, ResultCodeInfo resultCodeInfo, Object... args){
        return new ReturnResult(data, resultCodeInfo, args);
    }
}
