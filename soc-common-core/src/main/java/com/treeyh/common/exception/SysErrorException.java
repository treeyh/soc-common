package com.treeyh.common.exception;

import com.treeyh.common.model.result.ResultCodeInfo;

/**
 * @author TreeYH
 * @version 1.0
 * @description 系统自定义异常
 * @create 2019-05-17 19:12
 */
public class SysErrorException extends RuntimeException {
    private int code = -1;

    private ResultCodeInfo resultCodeInfo;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SysErrorException(String message) {
        super(message);
    }

    public SysErrorException(ResultCodeInfo error, Object... args) {
        super(String.format(error.getDesc(), args));
        this.resultCodeInfo = error;
        this.code = error.getCode();
    }

    public SysErrorException(Validation validation) {
        super(validation.getErrorMsg());
        this.code = validation.getErrorCode();
        this.resultCodeInfo = new ResultCodeInfo(validation.getErrorCode(), "", validation.getErrorMsg());
    }

    public int getCode() {
        return this.code;
    }

    public ResultCodeInfo getResultCodeInfo() {
        return this.resultCodeInfo;
    }
}
