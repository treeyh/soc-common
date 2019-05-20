package com.treeyh.common.exception;

import com.treeyh.common.model.result.ResultCodeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author TreeYH
 * @version 1.0
 * @description 描述
 * @create 2019-05-17 19:11
 */
public class Validation {

    private List<ErrorMessage> errorList = new ArrayList<>();

    public static Validation newValidation(){
        return new Validation();
    }

    public Validation() {

    }

    public Validation(ResultCodeInfo error, Object... msgs) {
        errorList.add(new ErrorMessage(error, msgs));
    }

    public Validation addError(ResultCodeInfo error, Object... msgs) {
        errorList.add(new ErrorMessage(error, msgs));
        return this;
    }

    public Validation addError(Boolean isAddToErrorList, ResultCodeInfo error, Object... msgs) {
        if (isAddToErrorList) {
            errorList.add(new ErrorMessage(error, msgs));
        }
        return this;
    }

    public Validation addError(ResultCodeInfo error) {
        errorList.add(new ErrorMessage(error));
        return this;
    }

    public Validation addError(Validation v) {
        for (ErrorMessage em : v.errorList) {
            errorList.add(em);
        }
        return this;
    }

    public boolean isValid() {
        return errorList.size() == 0;
    }

    public void isValidThrowException() {
        if (!this.isValid()) {
            throw new SysErrorException(this);
        }
    }

    public String getErrorMsg() {
        if (null == errorList || 0 >= errorList.size()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (ErrorMessage error : errorList) {
            if( null == error.getMsgs() || error.getMsgs().length <= 0) {
                builder.append(error.getError().getDesc() + ",");
            }else{
                builder.append(String.format(error.getError().getDesc(), error.getMsgs()) + ",");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public Integer getErrorCode() {
        if (null == errorList || 0 >= errorList.size()) {
            return -1;
        }

        return errorList.get(0).getError().getCode();
    }

    public static boolean isInteger(String str) {
        return str != null && Pattern.matches("^\\d+$", str);
    }

    public static boolean isNumber(String str) {
        return str != null && Pattern.matches("^\\d+(.\\d+)?$", str);
    }

}
