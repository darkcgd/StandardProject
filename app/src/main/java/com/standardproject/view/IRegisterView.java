package com.standardproject.view;

public interface IRegisterView extends IBaseView{
	/**
	 * 获取EditText上的昵称
	 */
	String getNickName();
	/**
	 * 获取EditText上的手机号码
	 */
	String getPhone();
	/**
	 * 获取EditText上的验证码
	 */
	String getCode();
	/**
	 * 获取EditText上的密码
	 */
	String getPwd();
}
