package com.karsa.exception;




import com.karsa.enums.ErrorCode;

import java.util.Map;

public class BusinessException extends BaseException {

    public BusinessException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode, data);
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
