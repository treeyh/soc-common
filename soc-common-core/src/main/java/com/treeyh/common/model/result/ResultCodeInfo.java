package com.treeyh.common.model.result;

import java.io.Serializable;

/**
 * @author TreeYH
 * @version 1.0
 * @description 返回描述
 * @create 2019-05-17 19:05
 */
public class ResultCodeInfo implements Serializable {

    private Integer code;

    private String message;

    private String desc;

    public ResultCodeInfo(){}

    public ResultCodeInfo(Integer code, String message, String desc){
        this.code = code;
        this.message = message;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

