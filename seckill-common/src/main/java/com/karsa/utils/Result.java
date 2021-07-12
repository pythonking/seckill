package com.karsa.utils;

import com.karsa.constants.GlobalResponseCode;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 返回code
     */
    private int code;
    /**
     * 返回描述
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public Result() {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
    }


    public Result(Integer code) {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
        this.code = code;
    }


    public Result(T data) {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
        this.data = data;
    }

    public Result(T data, String msg) {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
        this.data = data;
        this.msg = msg;
    }

    public Result(T data, int code, String msg) {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(Throwable e) {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
        this.msg = e.getMessage();
        this.code = GlobalResponseCode.FAIL;
    }

    public Result(Throwable e, int code) {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
        this.msg = e.getMessage();
        this.code = code;
    }

    public static <T> Result<T> succeed(String msg) {
        return succeedWith((T) null, GlobalResponseCode.SUCCESS, msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return succeedWith(model, GlobalResponseCode.SUCCESS, msg);
    }

    public static <T> Result<T> succeedWith(T datas, Integer code, String msg) {
        return new Result(datas, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return failedWith((T) null, GlobalResponseCode.FAIL, msg);
    }

    public static <T> Result<T> failed(T model, String msg) {
        return failedWith(model, GlobalResponseCode.FAIL, msg);
    }

    public static <T> Result<T> failedWith(T datas, Integer code, String msg) {
        return new Result(datas, code, msg);
    }


    public int getCode() {
        return this.code;
    }

    public Result<T> setCode(final int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public Result<T> setMsg(final String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(final T data) {
        this.data = data;
        return this;
    }
}
