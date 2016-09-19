package com.ugiant.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Package com.cgd.framework.common
 * @Description: 通用ViewHolder
 * @author: cgd
 * @date: 2015年10月18日 上午11:13:30
 */
public class AbViewHolder {

	/** @Fields mView :类似Map 键只能为Integer 效率高于Map */
	private SparseArray<View> mViews;
	/** @Fields mConvertView : 布局ConvertView */
	private View mConvertView;

	/**
	 * 初始化BaseViewHolder Inflater布局Layout 并setTag给mConvertView
	 * @param context 上下文
	 * @param parent  
	 * @param layoutId  资源layout文件
	 */
	private AbViewHolder(Context context, ViewGroup parent, int layoutId) {
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(AbViewHolder.this);
	}

	/**
	 * 获取资源layout文件获取CommonViewHolder
	 * @param context 上下文
	 * @param convertView 
	 * @param parent 
	 * @param layoutId 资源layout文件
	 * @return
	 */
	public static AbViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
		if (convertView == null) {
			return new AbViewHolder(context, parent, layoutId);
		}
		return (AbViewHolder) convertView.getTag();
	}
	
	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件Id获取对应的控件,如果没有则加入mViews
	 * @param viewId 资源Id
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public AbViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public AbViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public AbViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}
}
