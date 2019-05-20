package com.treeyh.common.model.result;

/**
 * @author TreeYH
 * @version 1.0
 * @description 返回
 * 描述
 * @create 2019-05-17 19:09
 */
public class ResultCode {

    public static ResultCodeInfo SUCCESS = new ResultCodeInfo(0, "SUCCESS", "OK");
    public static ResultCodeInfo TOKEN_ERROR = new ResultCodeInfo(401, "TOKEN_ERROR", "token错误");
    public static ResultCodeInfo FORBIDDEN_ACCESS =  new ResultCodeInfo(403, "FORBIDDEN_ACCESS", "禁止访问");
    public static ResultCodeInfo PATH_NOT_FOUND = new ResultCodeInfo(404, "PATH_NOT_FOUND", "请求地址不存在");
    public static ResultCodeInfo PARAM_ERROR = new ResultCodeInfo(501, "PARAM_ERROR", "请求参数错误");
    public static ResultCodeInfo INTERNAL_SERVER_ERROR = new ResultCodeInfo(500, "INTERNAL_SERVER_ERROR", "服务器异常");
    public static ResultCodeInfo FAILURE =  new ResultCodeInfo(997, "FAILURE", "业务失败");
    public static ResultCodeInfo SYS_ERROR = new ResultCodeInfo(998, "SYS_ERROR", "系统异常");
    public static ResultCodeInfo UNKNOWN_ERROR = new ResultCodeInfo(999, "UNKNOWN_ERROR", "未知错误");

    public ResultCode() {
    }
}
