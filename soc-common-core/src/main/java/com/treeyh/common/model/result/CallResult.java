package com.treeyh.common.model.result;

import java.io.Serializable;

/**
 * @author TreeYH
 * @version 1.0
 * @description 调用结果对象
 * @create 2019-05-17 19:05
 */
public class CallResult<T> implements Serializable {

    /**
     * 接口执行成功,取到结果,返回true
     */
    private boolean     isSuccess;
    /**
     * 结果状态信息
     */
    private ResultCodeInfo resultCodeInfo;
    /**
     * 返回的查询结果
     */
    private T result;
    /**
     * 返回异常信息
     */
    private Throwable   throwable;
    /**
     * 异常信息msg列表
     */
    private Object[] args;

    private CallResult(
            boolean isSuccess,
            ResultCodeInfo  resultCodeInfo,
            T result,
            Throwable throwable,
            Object...args) {
        super();
        this.isSuccess = isSuccess;
        this.resultCodeInfo = resultCodeInfo;
        this.result = result;
        this.throwable = throwable;
        this.args = args;
    }

    public static <T>CallResult<T> makeCallResult(
            boolean isSuccess,
            ResultCodeInfo  resultCodeInfo,
            T businessResult,
            Throwable throwable,
            Object...args){
        return new CallResult<T>(isSuccess, resultCodeInfo, businessResult, throwable, args);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public ResultCodeInfo getResultCodeInfo() {
        return resultCodeInfo;
    }

    public Integer getCode(){
        return resultCodeInfo.getCode();
    }

    public String getMessage(){
        return String.format(resultCodeInfo.getDesc(), args);
    }

    public T getResult() {
        return result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Object[] getArgs() {
        return args;
    }
}