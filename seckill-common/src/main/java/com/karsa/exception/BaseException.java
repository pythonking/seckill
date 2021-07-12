package com.karsa.exception;

import com.karsa.enums.ErrorCode;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseException extends RuntimeException {
    private final ErrorCode error;

    private final HashMap<String, Object> data =new HashMap<>();

    public BaseException(ErrorCode error, Map<String, Object> data) {
        super(error.getMessage());
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public BaseException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public BaseException(ErrorCode error, String message) {
        super(message);
        this.error = error;
    }


    protected BaseException(ErrorCode error, Map<String, Object> data, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public ErrorCode getError() {
        return error;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
