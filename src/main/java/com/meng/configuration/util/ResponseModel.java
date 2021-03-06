package com.meng.configuration.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/17 15:24
 */
@Data
@AllArgsConstructor
public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = -7535931660306785536L;
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_FAIL = -1;

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
    private T T;

    public T getData(){
        return T;
    }
    public static ResponseModel SUCCESS() {
        return new ResponseModel(CODE_SUCCESS, "成功", null);
    }
    public static ResponseModel SUCCESS(String msg){
        return new ResponseModel<>(CODE_SUCCESS, msg,null);
    }
    public static <T> ResponseModel<T> SUCCESS(T data) {
        return new ResponseModel<>(CODE_SUCCESS, "成功", data);
    }

    public static <T> ResponseModel<T> SUCCESS(String msg, T data) {
        return new ResponseModel<>(CODE_SUCCESS, msg, data);
    }

    public static ResponseModel ERROR(String msg) {
        return new ResponseModel(CODE_FAIL, msg, null);
    }

    public static <T> ResponseModel<T> ERROR(T data) {
        return new ResponseModel<>(CODE_FAIL, "失败", data);
    }

    public static <T> ResponseModel<T> ERROR(int code, String msg, T data) {
        return new ResponseModel<>(code, msg, data);
    }

    public boolean isSuccess() {
        return this.code == CODE_SUCCESS ? true : false;
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResponseModel<T> unauthorized(T data) {
        return new ResponseModel<T>(401, "暂未登录或token已经过期", data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResponseModel<T> forbidden(T data) {
        return new ResponseModel<T>(403, "没有相关权限", data);
    }
}
