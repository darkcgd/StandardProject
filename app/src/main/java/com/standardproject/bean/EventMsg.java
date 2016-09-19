package com.standardproject.bean;

/**
 * EventBus传达类
 * Created by chenguangda on 2016/6/30.
 */
public class EventMsg {
    private String msg;
    private int id;
    private int position;

    public EventMsg(String msg) {
        super();
        this.msg = msg;
    }


    public EventMsg(String msg, int position) {
        super();
        this.msg = msg;
        this.position = position;
    }

    public EventMsg(String msg, int id, int position) {
        super();
        this.msg = msg;
        this.id = id;
        this.position = position;
    }


    public String getMsg() {
        return msg;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }


}
