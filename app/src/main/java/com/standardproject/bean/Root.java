package com.standardproject.bean;

/**
 *
 *  通用实体类, (只接收简单类型)
 * @author cgd
 *  2016-05-06上午11:58:49
 */
public class Root {

	private boolean success;
	private String msg;

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}


}
