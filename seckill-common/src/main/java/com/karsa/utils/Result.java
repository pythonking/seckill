package com.karsa.utils;

import com.karsa.constants.GlobalResponseCode;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            value = "返回code",
            example = "200000"
    )
    private int code;
    @ApiModelProperty(
            value = "返回描述",
            example = "success"
    )
    private String msg;
    @ApiModelProperty(
            value = "返回数据",
            example = "data"
    )
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

    public Result(int code) {
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

    public static <T> Result.ResultBuilder<T> builder() {
        return new Result.ResultBuilder();
    }

    public String toString() {
        return "Result(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public Result(final int code, final String msg, final T data) {
        this.code = GlobalResponseCode.SUCCESS;
        this.msg = "success";
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public static class ResultBuilder<T> {
        private int code;
        private String msg;
        private T data;

        ResultBuilder() {
        }

        public Result.ResultBuilder<T> code(final int code) {
            this.code = code;
            return this;
        }

        public Result.ResultBuilder<T> msg(final String msg) {
            this.msg = msg;
            return this;
        }

        public Result.ResultBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        public Result<T> build() {
            return new Result(this.code, this.msg, this.data);
        }

        public String toString() {
            return "Result.ResultBuilder(code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ")";
        }
    }
}
