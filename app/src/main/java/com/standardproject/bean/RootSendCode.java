package com.standardproject.bean;

public class RootSendCode {


    /**
     * data : null
     * code : null
     * msg : 发送成功
     * success : true
     */

    private String data;
    private String code;
    private String msg;
    private boolean success;

    public void setData(String data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}
