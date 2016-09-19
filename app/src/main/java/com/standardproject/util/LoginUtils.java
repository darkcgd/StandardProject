package com.standardproject.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.Root;
import com.standardproject.widget.MyLinearLayout;
import com.ugiant.util.AbSharedUtil;
import com.ugiant.util.AbToastUtil;


public class LoginUtils {
	/**
	 * 判断是否被登陆或者帐号过期,跳转到登陆界面
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            错误信息
	 */

	public static int showUserTip(Context context, String msg) {
		if (msg != null) {
			if ("用户签名错误".equals(msg)||"用户信息错误".equals(msg)) {
				AbSharedUtil.putBoolean(context, "isLogin", false);
				AbSharedUtil.putInt(context, "totalNoReadCount", 0);//把未读条数置为0
				showDialog(context);
				return 2;//返回2  表示是签名错误  其他错误返回1
			} else {
				AbToastUtil.showToast(context, msg);
				return 1;//返回2  表示是签名错误  其他错误返回1
			}
		} else {
			//AbToastUtil.showToast(context, "请求出错");
			showDialog(context);
			return 1;//返回2  表示是签名错误  其他错误返回1
		}
	}
	private static Dialog dialog=null;//
	private static void showDialog(final Context context) {

		if (dialog==null) {
			dialog = new Dialog(context, R.style.inPutDialog);
			View view = View.inflate(context,R.layout.dialog_over_movedata,null);
			ImageView img = (ImageView) view.findViewById(R.id.img);//图片
			img.setImageResource(R.drawable.ic_warning_amber_56dp);
			TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);//重新登陆
			TextView tv_more = (TextView) view.findViewById(R.id.tv_more);//修改密码
			TextView tv_content = (TextView) view.findViewById(R.id.tv_content);//提示内容
			tv_cancel.setText("重新登陆");
			tv_more.setText("修改密码");
			tv_more.setTextColor(TheApp.instance.getResources().getColor(R.color.text_color_red));
			tv_content.setText("账号已在其他地方登陆");
			tv_cancel.setOnClickListener(new OnClickListener() {//重新登陆

				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					dialog=null;
				}
			});
			tv_more.setOnClickListener(new OnClickListener() {//修改密码

				@Override
				public void onClick(View arg0) {

					dialog.dismiss();
					dialog=null;
				}
			});
			dialog.setContentView(view, new LayoutParams((int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9
					android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//
			dialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface arg0) {
					dialog=null;
				}
			});
			// 设置点击外围解散
			dialog.setCanceledOnTouchOutside(true);
			// 设置显示宽高
			dialog.show();
		}else{
			dialog.show();
		}



	}
	public static int showUserTipNew(Context context, Root rootError,MyLinearLayout ll_status) {

		String msg = rootError.getMsg();
		if (msg != null) {
			if ("用户签名错误".equals(msg)||"用户信息错误".equals(msg)) {
				AbSharedUtil.putBoolean(context, "isLogin", false);
				AbSharedUtil.putInt(context, "totalNoReadCount", 0);//把未读条数置为0
				showDialog(context);
				return 2;//返回2  表示是签名错误  其他错误返回1
			} else {

				AbToastUtil.showToast(context, rootError.getMsg());
				return 1;//返回2  表示是签名错误  其他错误返回1
			}
		} else {
			AbToastUtil.showToast(context, "请求出错");
			return 1;//返回2  表示是签名错误  其他错误返回1
		}

	}
}
