package com.standardproject.bean;

public class RootSuggestion {


    /**
     * msg : 用户信息错误
     * success : false
     */

    private String msg;
    private boolean success;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}
