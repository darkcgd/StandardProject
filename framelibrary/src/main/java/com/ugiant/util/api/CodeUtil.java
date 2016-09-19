package com.ugiant.util.api;

import android.content.Context;
import android.widget.EditText;

import com.ugiant.http.AbRequestParams;
import com.ugiant.util.AbToastUtil;

/**
 * 验证码模块原型
 * @author cgd
 * 2016-2-22
 */
public class CodeUtil {

	/**
	 * 获取验证码
	 * @param context 上下文
	 * @param et_phone  手机号码(输入框)
	 * @return
	 */
	public static AbRequestParams getCode(Context context,EditText et_phone) {
		String phone;

		if(et_phone!=null){
			phone = et_phone.getText().toString().trim();
		}else{
			AbToastUtil.showToast(context, "et_phone尚未初始化");
			return null;
		}
		/*if (phone.length()!=11) {
			ToastUtil.showToast(this, "输入正确手机号码");
			bt_get.setTextAfter("秒后重新获取").setTextBefore("").setLenght(0);
			return;
		}*/
		// 绑定参数
		AbRequestParams params = new AbRequestParams(); 
		params.put("phone_no", phone);
		return params;
	}
}
