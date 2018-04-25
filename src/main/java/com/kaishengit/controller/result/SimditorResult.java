package com.kaishengit.controller.result;

public class SimditorResult {
    private Boolean success;

    private String msg;

    private String file_path;

    public static final String IMG_ADDRESS = "https://retail.huataizhubao.com/";

    public static final Boolean STATE_SUCCESS = true;
    public static final Boolean STATE_ERROR = false;

    public static final String MSG_SUCCESS = "上传图片成功";
    public static final String MSG_ERROR = "上传图片失败";

    public static SimditorResult success(String filePath) {
        SimditorResult simditorResult = new SimditorResult();
        simditorResult.setSuccess(STATE_SUCCESS);
        simditorResult.setMsg(MSG_SUCCESS);
        simditorResult.setFile_path(IMG_ADDRESS + filePath);
        return simditorResult;
    }

    public static SimditorResult error() {
        SimditorResult simditorResult = new SimditorResult();
        simditorResult.setSuccess(STATE_ERROR);
        simditorResult.setMsg(MSG_ERROR);
        simditorResult.setFile_path(null);
        return simditorResult;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
