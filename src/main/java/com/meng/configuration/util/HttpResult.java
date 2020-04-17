package com.meng.configuration.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

public class HttpResult implements Serializable {
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_FAIL = -1;
    private static final  int NO_CHANGE = 402;

    /**
     * 编码
     */
    private int code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 数据
     */
    private Map data;
    /**
     * 版本
     */
    private Integer version;

    public HttpResult(int code, String msg, Map data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public HttpResult(int code, String msg, Map data, Integer version) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.version = version;
    }

    public static HttpResult SUCCESS() {
        return new HttpResult(CODE_SUCCESS, "成功", null);
    }

    public static HttpResult SUCCESS(String msg) {
        return new HttpResult(CODE_SUCCESS, msg, null);
    }

    public static HttpResult SUCCESS(Map data) {
        return new HttpResult(CODE_SUCCESS, "成功", data);
    }

    public static HttpResult SUCCESS(Map data, int version) {
        return new HttpResult(CODE_SUCCESS, "成功", data, version);
    }

    public static HttpResult SUCCESS(String msg, Map data) {
        return new HttpResult(CODE_SUCCESS, msg, data);
    }

    public static HttpResult ERROR(String msg) {
        return new HttpResult(CODE_FAIL, msg, null);
    }

    public static HttpResult ERROR(Map data) {
        return new HttpResult(CODE_FAIL, "失败", data);
    }

    public static HttpResult ERROR(int code, String msg, Map data) {
        return new HttpResult(code, msg, data);
    }

    public static HttpResult NOCHANGE(){
        return new HttpResult(NO_CHANGE, "没有更新", null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
