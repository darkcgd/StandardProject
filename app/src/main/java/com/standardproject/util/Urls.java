package com.standardproject.util;



/**
 * 请求地址类
 * @author cgd
 *
 */
public class Urls {
	/**
	 * 注册
	 */
	public static final String REGISTER = Config.getApi("/api/member/register");
	/**
	 * 获取验证码
	 */
	public static final String GET_CODE = Config.getApi("/common/sms_code/send");
	/**
	 * 注册
	 */
	public static final String LOGIN = Config.getApi("/api/member/login");
	/**
	 * 广告列表
	 */
	public static final String AD_LIST = Config.getApi("/common/getAdList");
}
