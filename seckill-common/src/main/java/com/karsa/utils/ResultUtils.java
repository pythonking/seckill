package com.karsa.utils;


import com.karsa.enums.ErrorCode;

public class ResultUtils {
    public static Result success(Object data) {
        return new Result(data);
    }

    public static Result success() {
        return new Result("");
    }


    public static Result error(ErrorCode errorCode) {
        return new Result(errorCode);
    }
    public static Result basic(String code,String msg) {
        return new Result(code, msg);
    }
}
