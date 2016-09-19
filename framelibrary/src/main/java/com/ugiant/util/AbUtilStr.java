package com.ugiant.util;

public class AbUtilStr {
	/**
	 * 防止出现null导致程序奔溃
	 * 
	 * @param str
	 *            原有的字符串
	 * @return
	 */
	public static String setString(String str) {
		if (null == str || str.equals("null")||str.equals("")) {
			str = "暂无";
		}
		return str;
	};

	/**
	 * 防止出现null导致程序奔溃
	 * 
	 * @param str
	 *            原有的字符串
	 * @param replaceStr
	 *            替换字符串成replaceStr
	 * @return
	 */
	public static String setString(String str, String replaceStr) {
		if (null == str || str.equals("null")) {
			str = replaceStr;
		}
		return str;
	};
}
