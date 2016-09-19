package com.standardproject.bean;

public class RootLogin {


    /**
     * user_name : null
     * token : f017d82494ea4ac28ad49e21954100c2
     * head_img : 
     * user_id : 1
     * success : true
     */

    private String user_name;
    private String token;
    private String head_img;
    private String msg;
    private int user_id;
    private boolean success;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getToken() {
        return token;
    }

    public String getHead_img() {
        return head_img;
    }

    public int getUser_id() {
        return user_id;
    }

    public boolean isSuccess() {
        return success;
    }


}
