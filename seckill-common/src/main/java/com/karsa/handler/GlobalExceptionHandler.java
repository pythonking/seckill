package com.karsa.handler;


import com.karsa.enums.ErrorCode;
import com.karsa.exception.BusinessException;
import com.karsa.utils.Result;
import com.karsa.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    private Result processException(MethodArgumentNotValidException e) {
        return buildResponse(ErrorCode.UN_PARAM.getCode(), e.getBindingResult().getFieldError().getDefaultMessage(), e);
    }

    @ExceptionHandler({BusinessException.class})
    private Result processException(BusinessException e) {
        return buildResponse(e.getError(), e);
    }


    @ExceptionHandler(Exception.class)
    private Result processException(Exception e) {
        return buildResponse(ErrorCode.FAIL, e);
    }


    private Result buildResponse(ErrorCode errorCode, Exception e) {
        log.info("异常通知：{}", e.getMessage());
        log.info("====================================================================>");
        return ResultUtils.error(errorCode);
    }

    private Result buildResponse(String code, String msg, Exception e) {
        log.info("异常通知：{}", e.getMessage());
        log.info("====================================================================>");
        return ResultUtils.basic(code, msg);
    }
}
