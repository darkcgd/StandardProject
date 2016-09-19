package com.standardproject.wxapi;

public class RootUserWeiXInLoginInfo {
	private boolean success;
	private int user_id;
	private String token;
	private String user_name;
	private int binded_phone; //(1-已绑定 0-未绑定)
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getBinded_phone() {
		return binded_phone;
	}
	public void setBinded_phone(int binded_phone) {
		this.binded_phone = binded_phone;
	}


}
