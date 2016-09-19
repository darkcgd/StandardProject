/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ugiant.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ugiant.R;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbToastUtil.java 
 * 描述：Toast工具类.
 *
 * @author cgd
 * @date 2016-01-11
 */

public class AbSnackBarUtil {
	public static final   int Info = 1;
	public static final  int Confirm = 2;
	public static final  int Warning = 3;
	public static final  int Alert = 4;


	public static  int red = 0xfff44336;
	public static  int green = 0xff4caf50;
	public static  int blue = 0xff2195f3;
	public static  int orange = 0xffffc107;
	public static  int black = 0xff000000;
	public static  int white = 0xffffffff;

    /**
     * 描述：SnackBar提示,开发一般用这个
     * @param msg  提示文本
     */
	public static void showSnackBar(View view, String msg,int msgTextColor){
		Snackbar snackbar =Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
		((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(msgTextColor);
		snackbar.show();
	}



	/**
	 * 短显示Snackbar，自定义颜色
	 * @param view
	 * @param message
	 * @param messageColor
	 * @param backgroundColor
	 * @return
	 */
	public static Snackbar ShortSnackbar(View view, String message, int messageColor, int backgroundColor){
		Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
		setSnackbarColor(snackbar,messageColor,backgroundColor);
		return snackbar;
	}

	/**
	 * 长显示Snackbar，自定义颜色
	 * @param view
	 * @param message
	 * @param messageColor
	 * @param backgroundColor
	 * @return
	 */
	public static Snackbar LongSnackbar(View view, String message, int messageColor, int backgroundColor){
		Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
		setSnackbarColor(snackbar,messageColor,backgroundColor);
		return snackbar;
	}

	/**
	 * 自定义时长显示Snackbar，自定义颜色
	 * @param view
	 * @param message
	 * @param messageColor
	 * @param backgroundColor
	 * @return
	 */
	public static Snackbar IndefiniteSnackbar(View view, String message,int duration,int messageColor, int backgroundColor){
		Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
		setSnackbarColor(snackbar,messageColor,backgroundColor);
		return snackbar;
	}

	/**
	 * 短显示Snackbar，可选预设类型
	 * @param view
	 * @param message
	 * @param type
	 * @return
	 */
	public static Snackbar ShortSnackbar(View view, String message, int type){
		Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
		switchType(snackbar,type);
		return snackbar;
	}

	/**
	 * 长显示Snackbar，可选预设类型
	 * @param view
	 * @param message
	 * @param type
	 * @return
	 */
	public static Snackbar LongSnackbar(View view, String message,int type){
		Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
		switchType(snackbar,type);
		return snackbar;
	}

	/**
	 * 自定义时常显示Snackbar，可选预设类型
	 * @param view
	 * @param message
	 * @param type
	 * @return
	 */
	public static Snackbar IndefiniteSnackbar(View view, String message,int duration,int type){
		Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
		switchType(snackbar,type);
		return snackbar;
	}

	//选择预设类型
	private static void switchType(Snackbar snackbar,int type){
		switch (type){
			case Info:
				setSnackbarColor(snackbar,blue);
				break;
			case Confirm:
				setSnackbarColor(snackbar,green);
				break;
			case Warning:
				setSnackbarColor(snackbar,orange);
				break;
			case Alert:
				setSnackbarColor(snackbar, Color.YELLOW,red);
				break;
		}
	}

	/**
	 * 设置Snackbar背景颜色
	 * @param snackbar
	 * @param backgroundColor
	 */
	public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
		View view = snackbar.getView();
		if(view!=null){
			view.setBackgroundColor(backgroundColor);
		}
	}

	/**
	 * 设置Snackbar文字和背景颜色
	 * @param snackbar
	 * @param messageColor
	 * @param backgroundColor
	 */
	public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
		View view = snackbar.getView();
		if(view!=null){
			view.setBackgroundColor(backgroundColor);
			((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
		}
	}

	/**
	 * 向Snackbar中添加view
	 * @param snackbar
	 * @param layoutId
	 * @param index 新加布局在Snackbar中的位置
	 */
	public static void SnackbarAddView( Snackbar snackbar,int layoutId,int index) {
		View snackbarview = snackbar.getView();
		Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

		View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		p.gravity= Gravity.CENTER_VERTICAL;

		snackbarLayout.addView(add_view,index,p);
	}

    

}
