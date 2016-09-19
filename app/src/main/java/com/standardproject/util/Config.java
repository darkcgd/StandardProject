package com.standardproject.util;

public class Config {

	//测试环境
	//private static final String API = "http://58.210.52.234:16100";
	private static final String API = "http://ugiant.f3322.net:57305";
	//请求地址(测试环境)
	public static String getApi(String method) {
		return API + method;
	}

	//捕捉异常上传服务器地址
	private static final String APICRASH = "http://115.38.29.141/";
	public static String getCrashApi(String method) {
		return APICRASH + method;
	}


    //<<----EventBus标识---->>
	/**
	 * 微信支付
	 */
	public final static String WEIXIN_PAY = "WEIXIN_PAY";




	// 支付宝旧信息
	/*
	//商户PID
		public static final String PARTNER = "2088911569821514";
		//商户收款账号
		public static final String SELLER = "3125734407@qq.com";
		//商户私钥，pkcs8格式
		public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALhWOw8ESzAMhpYzlGkGIF9hjodvKAdd38sC/j4lr7MGdISoJtWVuSSajBvRzG1GDtCKh3BbpGrsbbYNwagw/2yIzwZHGbWm9yPe8/Gjycdpv3LvjmJ+M6BiL3wtUVFe92lYbbqH8NZOyxcFf0iu3ArjB22XM/r16ACPCGR/I4kJAgMBAAECgYBSyKcNP2hk07s9iTyFTfML4swXlpvrcwUmLtnvHw2g4K1DRiDqmqri/WUeuUGNcYEFtrDMjNtyCWvb7qGh3x9V4iJltXSNfVO2UyXX4bCJVpMrOCW/0it5MmEekDJIfFLcv3CSToNnQssONY5IuxPvNPcCs8k5O0hxMkVQHsZPgQJBAPWrLbMVnmQO3EOCiZ1/KjjeAOxG9hATkYUvysJX25dWUaTjlN2RFgQrojKg+YWc2hjCkIjf2M746tllA4ROyT0CQQDAFsP7iZjhVwagI3IeS1pl3VME3vph61qFGAoOfWK50o69X9Mljs8yvEFlTcxC88+kkeVhudJeli/ZfKtUZkO9AkEAqZeEMs7HyxcfI1OoIJu60Ash4/qQv0Ri/uihNkxkgTLLpeKdz97qrfJZRE4wMva28MTP9k1hZHAd3/mF1f51mQJBAIzU5BFaW3Ty6g+kjTl6w6clcoWCuY3rEO29pzniYeydXDupstO5dV3cOhH8zQJVYOpmcQeKRptEf4Zz6eUyKP0CQBuXq3pxeMJoFC2J+iyJph1Ts0+/hAPLz4ziXDp85lWzldFPYKHf4EZ8aGheSMvKZBpx8n8u315WFM+/Qd+bLVA=";
		//支付宝公钥
		public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		private static final int SDK_PAY_FLAG = 1;
		private static final int SDK_CHECK_FLAG = 2;
	 */
	// 新支付宝账号信息
	//商户PID
	public static final String PARTNER = "2088911763192773";
	//商户收款账号
	public static final String SELLER = "shan4966@163.com";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAM/mMFHRiQy1EoEKKSi0lUzH54tCziiyxcH+bbERYQjd/sZzuezSEk/xqEFBYSAtI9USPOSutS1pzq7b/ujHDD8xaGhn0p3nYyRNK9hYJUo1HsTwmDGWNRq99iEIvqtBVRN6DaG2UQAbRxn+xM+hOJbjQxX80HjW2gETDuOuyetnAgMBAAECgYEAmLywuyuJ2IAfPBZO7szc8y/aeIF4HIZaq49U30W5WF5gE0RqUAZ11a3gwzBZPt5q+NsRSaDhEqAtbGHMARBbea2cw6R/Iq0PWwiooeQAjrm+ZcsyK+6UzZh7sERWA03DljnpN1E4NmF4yj0y7eB9ctlX7/maFJopgGujrBHT2wECQQD6hnthg5BELMZxVToiYoAtV9Icr+IZrcsL2mekog3O6bTTQ4Iv2073sncauv3KnVD5Xroo4F1l8FIgemQKjBUHAkEA1HE+HKV9ZgkWSuvvnp6DXdzxHiy2VVQDNcUpEWOsKllQPssNadX5JIVTNg/X5xe22hNEs58LRrpI+9+jnl8+oQJBAMUzBH2utOmlIRNm1drpFCIdBcwE+oHiFnkfEwQ5d4sO1YKSmjCXfaJmPXCyDkHl8S5JzVmNupcaQ1tDW+odeBMCQH5Zg7mB65z+FpYk0PUkDbmLqJRrv13sg6Q9SPmgsErXp9RQ6/9gVbeU3DKxJZW6EvSMtTWniAtey/2Ruzx8JUECQQCfM/q+gyxFpWreP0mpA8wIJVjxc7cJ6fAAzQcSzvE652yMW+/eA9W333wIx35cSZYkxGvD02tOc9O8sOs2kTbc";
	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;




	//appid
	//请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
	///微单 微信支付账号
	public static final String APP_ID = "wx26bd4c9dbcc8d0c3";
	public static final String APPSECRET = "9f8bfb30a5029766ec53d70bf9252e12";
	//微单 微信商户号
	public static final String MCH_ID = "1273628401";
	// 幸会 微信API密钥，在商户平台设置
	public static final  String API_KEY="a8e8b9468dc84a0cb7bea0099fccb1cf";
	/**
	 * 微信的支付回调地址
	 */
	public static final  String WEIXIN_NOTIFY_URL="/service/user/notifyFromWeiXin";
	/**
	 * 支付宝的支付回调地址
	 */
	public static final  String ZHIFUBAO_NOTIFY_URL="/service/third/alipay/notify_url";

	public static final String QQ_APP_ID = "222222";

}
