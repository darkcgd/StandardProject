package com.ugiant.util.api;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.ugiant.http.AbRequestParams;
import com.ugiant.util.AbDES3Utils;
import com.ugiant.util.AbToastUtil;

/**
 * 注册功能模块原型
 * @author cgd
 * 2016-2-22
 */
public class RegisterUtil {

	/**
	 * 普通注册
	 * @param context 上下文
	 * @param url  请求的地址
	 * @param reg_type  注册类型，1-手机，2-email，3-微信,4-QQ
	 * @param et_nick  昵称(输入框)
	 * @param et_phone 手机号码(输入框)
	 * @param ec_code  验证码(输入框)
	 * @param et_pwd   密码(输入框)
	 * @param et_pwd2   再次确认密码(输入框)
	 * @return
	 */
	public static AbRequestParams doReg(Context context,int reg_type,EditText et_nick,EditText et_phone,EditText ec_code,EditText et_pwd,EditText et_pwd2) {
		String nickName;
		String phone;
		String code;
		String pwd;
		String pwd2;

		if(et_nick!=null){
			nickName = et_nick.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "et_nick尚未初始化");
			return null;
		}
		if(et_phone!=null){
			 phone = et_phone.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "et_phone尚未初始化");
			return null;
		}
		if(ec_code!=null){
			 code = ec_code.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "ec_code尚未初始化");
			return null;
		}
		if(et_pwd!=null){
			 pwd = et_pwd.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "et_pwd尚未初始化");
			return null;
		}
		if(et_pwd2!=null){
			 pwd2 = et_pwd2.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "et_pwd2尚未初始化");
			return null;
		}

		if (!pwd.equals(pwd2)) {
			AbToastUtil.showToast(context, "请出入一致密码");
			return null;
		}

		if (TextUtils.isEmpty(nickName)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(code)||TextUtils.isEmpty(pwd)) {
			AbToastUtil.showToast(context, "输填写完整信息!");
			return null;
		}

		try {
			pwd = AbDES3Utils.encode(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String url = Config.getApi("/api/member/register");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams(); 
		params.put("login_name", phone);
		params.put("password", pwd);
		params.put("reg_type", reg_type);  //(注册类型，1-手机，2-email，3-微信,4-QQ)
		params.put("code", code);
		return params;
	}
}
