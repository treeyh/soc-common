package com.treeyh.common.exception;

import com.treeyh.common.model.result.ResultCodeInfo;

/**
 * @author TreeYH
 * @version 1.0
 * @description 错误信息
 * @create 2019-05-17 11:48
 */
public class ErrorMessage {

    private ResultCodeInfo error;
    private Object[] msgs;

    public ErrorMessage(ResultCodeInfo errorCode) {
        this.error = errorCode;
    }

    public ErrorMessage(ResultCodeInfo errorCode, Object... msgs) {
        this.error = errorCode;

        this.msgs = msgs;
    }

    public ResultCodeInfo getError() {
        return error;
    }

    public Object[] getMsgs() {
        return msgs;
    }

    @Override
    public String toString() {
        return String.format(error.getDesc(), msgs);
    }
}
