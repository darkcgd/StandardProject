package com.ugiant;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

import com.autolayout.layout.AutoFrameLayout;
import com.autolayout.layout.AutoLinearLayout;
import com.autolayout.layout.AutoRelativeLayout;

// TODO: Auto-generated Javadoc
/**
 * 名称：AbActivity.java 
 * 描述：继承这个Activity使你的Activity拥有程序框架
 *
 * @author cgd
 * @date 2016-01-11
 */

public abstract class AbActivity extends FragmentActivity {
	private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
	private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
	private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs)
	{
		View view = null;
		if (name.equals(LAYOUT_FRAMELAYOUT))
		{
			view = new AutoFrameLayout(context, attrs);
		}

		if (name.equals(LAYOUT_LINEARLAYOUT))
		{
			view = new AutoLinearLayout(context, attrs);
		}

		if (name.equals(LAYOUT_RELATIVELAYOUT))
		{
			view = new AutoRelativeLayout(context, attrs);
		}

		if (view != null) return view;

		return super.onCreateView(name, context, attrs);
	}


	/**
	 * 初始化控件
	 * 一般用于:控件id的初始化 
	 * 上一个界面传过来的值 
	 * AbAdapter的初始化
	 */
	public abstract void initViews();
	/**
	 * 初始化数据
	 * 一般用于:请求数据
	 * 数据的交互
	 */
	public abstract void initData();
	/**
	 * 设置监听器
	 * 一般用于:
	 * 控件的事件监听
	 */
	public abstract void setListeners();

}
