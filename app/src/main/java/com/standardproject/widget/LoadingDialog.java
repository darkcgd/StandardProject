package com.standardproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.standardproject.R;

/**
 * 通用的弹出框
 *
 * @author cgd
 */
public class LoadingDialog {

	/**
	 * 得到自定义的Dialog
	 *
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		/*// 首先得到整个View
		View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, null);
		// 获取整个布局
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
		// 页面中的Img
		ImageView img = (ImageView) view.findViewById(R.id.img);
		// 页面中显示文本
		TextView tipText = (TextView) view.findViewById(R.id.tipTextView);

		// 加载动画，动画用户使img图片不停的旋转
		Animation animation = AnimationUtils.loadAnimation(context,R.anim.dialog_load_animation);
		// 显示动画
		img.startAnimation(animation);
		// 显示文本
		tipText.setText(msg);

		// 创建自定义样式的Dialog
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		// 设置返回键true有效，false 为无效，
		//loadingDialog.setCancelable(true);
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

		return loadingDialog;*/

		// 首先得到整个View
		View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, null);
		// 获取整个布局
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		tv_msg.setText(msg);
		// 创建自定义样式的Dialog
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		// 设置返回键true有效，false 为无效，
		//loadingDialog.setCancelable(true);
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

		return loadingDialog;
	}



}
