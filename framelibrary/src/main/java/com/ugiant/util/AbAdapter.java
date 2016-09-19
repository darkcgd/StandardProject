package com.ugiant.util;

import java.util.List;

import com.autolayout.utils.AutoUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mDatas;
	protected int layoutId;

	/**
	 * CommonAdapter构造方法
	 * 
	 * @param context
	 *            上下文
	 * @param mDatas
	 *            数据源
	 * @param mItemLayoutId
	 *            item布局文件
	 */
	public AbAdapter(Context context, List<T> mDatas, int layoutId) {
		this.mContext = context;
		this.mDatas = mDatas;
		this.layoutId = layoutId;
	}

	public void updateView(List<T> mDatas) {
		this.mDatas = mDatas;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AbViewHolder viewHolder = AbViewHolder.get(mContext, convertView,
				parent, layoutId);
		convert(viewHolder, getItem(position), position);

		View mConvertView = viewHolder.getConvertView();
		AutoUtils.autoSize(mConvertView);
		return mConvertView;
	}

	/**
	 * 用于填充数据等逻辑,在使用该Adapter时调用
	 * 
	 * @param viewHolder
	 *            通过这个可以根据资源Id找到控件 例如: TextView tv =
	 *            viewHolder.getView(R.id.id_tv_title); ImageView iv=
	 *            viewHolder.getView(R.id.iv);
	 * @param item
	 *            数据源 T是泛型 ,一般是List或者数组
	 * @param position
	 *            数据源所在的位置
	 */
	public abstract void convert(AbViewHolder viewHolder, T item, int position);

}
