package com.karsa.enums;

public enum ErrorCode {

    SUCCESS("0000", "成功"),
    PASSWORD_ERROR("0001", "用户名或密码错误"),
    NOT_LOGIN_ERROR("0001", "请重新登录"),
    DATA_PROCESSING_ERROR("0002", "数据处理异常"),
    PASSWORD_OR_ID_NOTNULL("0003", "机构ID或密码不能为空"),
    SYSTEM_BUSY("0004", "系统繁忙，请稍后重试"),
    FILE_NOT_EXIST("0005", "文件不存在"),


    PHONE_DUPLICATE("1063", "手机号重复"),

    UN_PARAM("1100", "必填参数为空或无效"),
    FAIL("1101", "内部系统异常"),

    CHARGERRECORD_NOT_EXIST("1403", "充电信息不存在"),
    CHARGERRECORD_ALREADY_STOP("1404", "充电信息已关闭"),


    TIMED_TASK_ERROR("2000", "定时任务异常");

    private final String code;
    private final String message;


    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
