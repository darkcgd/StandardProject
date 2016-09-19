package com.ugiant.util.api;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.ugiant.http.AbRequestParams;
import com.ugiant.util.AbDES3Utils;
import com.ugiant.util.AbToastUtil;

/**
 * 登录功能模块原型
 * @author cgd
 * 2016-2-22
 */
public class LoginUtil {

	/**
	 * 登录
	 * @param context 上下文
	 * @param reg_type 注册类型，1-手机，2-email，3-微信,4-QQ
	 * @param et_user 用户名(输入框)
	 * @param et_pwd 密码(输入框)
	 * @return
	 */
	public static AbRequestParams doLogin(Context context,int reg_type,EditText et_user,EditText et_pwd) {
		String nickName;
		String pwd;

		if(et_user!=null){
			nickName = et_user.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "et_user尚未初始化");
			return null;
		}
		if(et_pwd!=null){
			pwd = et_pwd.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "et_pwd尚未初始化");
			return null;
		}
		
		if (TextUtils.isEmpty(nickName)) {
			AbToastUtil.showToast(context, "请输入您的账号!");
			return null;
		}else if (TextUtils.isEmpty(pwd)) {
			AbToastUtil.showToast(context, "请输入您的密码!");
			return null;
		}
		try {
			pwd = AbDES3Utils.encode(pwd);   // DES3加密密码
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 绑定参数
		AbRequestParams params = new AbRequestParams(); 
		params.put("username", nickName);
		params.put("password", pwd);
		params.put("reg_type", reg_type);
		return params;
	}
}
