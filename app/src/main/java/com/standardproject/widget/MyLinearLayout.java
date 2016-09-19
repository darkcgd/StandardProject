package com.standardproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.standardproject.R;

/**
 * 类描述： 一个方便在多种状态切换的LinearLayout 创建人: cgd 创建时间: 2016/4/09 10:20.
 */
public class MyLinearLayout extends LinearLayout {
	public static final int STATUS_CONTENT = 0x00;
	public static final int STATUS_LOADING = 0x01;
	public static final int STATUS_EMPTY = 0x02;
	public static final int STATUS_ERROR = 0x03;
	public static final int STATUS_NO_NETWORK = 0x04;

	private View emptyView;
	private View errorView;
	private View loadingView;
	private View noNetworkView;

	private String text;
	private int imageId;

	private int viewStatus;

	private LayoutInflater inflater;
	private final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT);

	public MyLinearLayout(Context context) {
		this(context, null);
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		inflater = LayoutInflater.from(getContext());
	}

	/** 获取当前状态 */
	public int getViewStatus() {
		return viewStatus;
	}

	/**
	 * 设置显示的文本(适用于所有状态)
	 *
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 设置显示的图标(适用于所有状态)
	 *
	 * @param imageId
	 */
	public void setImage(int imageId) {
		this.imageId = imageId;
	}

	private TextView tv;
	private ImageView iv;

	/** 显示空视图 */
	public View showEmpty(int resId) {
		viewStatus = STATUS_EMPTY;
		//		if(null == emptyView){
		//			emptyView = inflater.inflate(resId, null);
		//			iv = (ImageView) emptyView.findViewById(R.id.iv);
		//			tv = (TextView) emptyView.findViewById(R.id.tv);
		//			if(text!=null){
		//				tv.setText(text);
		//			}else{
		//				tv.setText("暂无数据");
		//			}
		//			if(imageId!=0){
		//				iv.setImageResource(imageId);
		//			}else{
		//				iv.setImageResource(R.drawable.jpg_empty_message_150dp);
		//			}
		//			addView(emptyView, 0, layoutParams);
		//		}else{
		//			if(tv!=null){
		//				if(text!=null){
		//					tv.setText(text);
		//				}else{
		//					tv.setText("暂无数据");
		//				}
		//			}
		//			if(iv!=null){
		//				if(imageId!=0){
		//					iv.setImageResource(imageId);
		//				}else{
		//					iv.setImageResource(R.drawable.jpg_empty_message_150dp);
		//				}
		//			}
		//		}

		if(null == emptyView){
			emptyView = inflater.inflate(resId, null);
			iv = (ImageView) emptyView.findViewById(R.id.iv);
			tv = (TextView) emptyView.findViewById(R.id.tv);
			if(text!=null){
				tv.setText(text);
			}else{
				tv.setText("暂无数据");
			}
			if(imageId!=0){
				iv.setImageResource(imageId);
			}else{
				iv.setImageResource(R.drawable.jpg_empty_message_150dp);
			}
			addView(emptyView, 0, layoutParams);
		}else{
			removeView(emptyView);


			emptyView = inflater.inflate(resId, null);
			iv = (ImageView) emptyView.findViewById(R.id.iv);
			tv = (TextView) emptyView.findViewById(R.id.tv);
			if(text!=null){
				tv.setText(text);
			}else{
				tv.setText("暂无数据");
			}
			if(imageId!=0){
				iv.setImageResource(imageId);
			}else{
				iv.setImageResource(R.drawable.jpg_empty_message_150dp);
			}
			addView(emptyView, 0, layoutParams);
		}

		showViewByStatus(viewStatus);
		return emptyView;
	}

	/** 显示错误视图 */
	public View showError(int resId) {
		viewStatus = STATUS_ERROR;
//		if (null == errorView) {
//			errorView = inflater.inflate(resId, null);
//			iv = (ImageView) errorView.findViewById(R.id.iv);
//			tv = (TextView) errorView.findViewById(R.id.tv);
//			if (text != null) {
//				tv.setText(text);
//			} else {
//				tv.setText("请求出错");
//			}
//			if (imageId != 0) {
//				iv.setImageResource(imageId);
//			} else {
//				iv.setImageResource(R.drawable.jpg_error_red_150dp);
//			}
//			addView(errorView, 0, layoutParams);
//		} else {
//			if (tv != null) {
//				if (text != null) {
//					tv.setText(text);
//				} else {
//					tv.setText("请求出错");
//				}
//			}
//			if (iv != null) {
//				if (imageId != 0) {
//					iv.setImageResource(imageId);
//				} else {
//					iv.setImageResource(R.drawable.jpg_error_red_150dp);
//				}
//			}
//		}
		if (null == errorView) {
			errorView = inflater.inflate(resId, null);
			iv = (ImageView) errorView.findViewById(R.id.iv);
			tv = (TextView) errorView.findViewById(R.id.tv);
			if (text != null) {
				tv.setText(text);
			} else {
				tv.setText("请求出错");
			}
			if (imageId != 0) {
				iv.setImageResource(imageId);
			} else {
				iv.setImageResource(R.drawable.jpg_error_red_150dp);
			}
			addView(errorView, 0, layoutParams);
		} else {
			removeView(errorView);

			errorView = inflater.inflate(resId, null);
			iv = (ImageView) errorView.findViewById(R.id.iv);
			tv = (TextView) errorView.findViewById(R.id.tv);
			if (text != null) {
				tv.setText(text);
			} else {
				tv.setText("请求出错");
			}
			if (imageId != 0) {
				iv.setImageResource(imageId);
			} else {
				iv.setImageResource(R.drawable.jpg_error_red_150dp);
			}
			addView(errorView, 0, layoutParams);
		}
		showViewByStatus(viewStatus);
		return errorView;
	}

	/** 显示加载中视图 */
	public View showLoading(int resId) {
		viewStatus = STATUS_LOADING;
		if (null == loadingView) {
			loadingView = inflater.inflate(resId, null);
			tv = (TextView) loadingView.findViewById(R.id.tv);
			if (text != null) {
				tv.setText(text);
			} else {
				tv.setText("正在加载中,请稍候..");
			}
			addView(loadingView, 0, layoutParams);
		} else {
			if (tv != null) {
				if (text != null) {
					tv.setText(text);
				} else {
					tv.setText("正在加载中,请稍候..");
				}
			}
		}
		showViewByStatus(viewStatus);
		return loadingView;
	}

	/** 显示无网络视图 */
	public View showNoNetwork(int resId) {
		viewStatus = STATUS_NO_NETWORK;
		if (null == noNetworkView) {
			noNetworkView = inflater.inflate(resId, null);
			iv = (ImageView) noNetworkView.findViewById(R.id.iv);
			tv = (TextView) noNetworkView.findViewById(R.id.tv);
			if (text != null) {
				tv.setText(text);
			} else {
				tv.setText("请求出错");
			}
			if (imageId != 0) {
				iv.setImageResource(imageId);
			} else {
				iv.setImageResource(R.drawable.ic_no_network);
			}
			addView(noNetworkView, 0, layoutParams);
		} else {
			if (tv != null) {
				if (text != null) {
					tv.setText(text);
				} else {
					tv.setText("请求出错");
				}
			}
			if (iv != null) {
				if (imageId != 0) {
					iv.setImageResource(imageId);
				} else {
					iv.setImageResource(R.drawable.ic_no_network);
				}
			}
		}
		showViewByStatus(viewStatus);
		return noNetworkView;
	}

	private void showViewByStatus(int viewStatus) {
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).setVisibility(View.GONE);
		}
		if (null != loadingView) {
			loadingView.setVisibility(viewStatus == STATUS_LOADING ? View.VISIBLE: View.GONE);
		}
		if (null != emptyView) {
			emptyView.setVisibility(viewStatus == STATUS_EMPTY ? View.VISIBLE: View.GONE);
		}
		if (null != errorView) {
			errorView.setVisibility(viewStatus == STATUS_ERROR ? View.VISIBLE: View.GONE);
		}
		if (null != noNetworkView) {
			noNetworkView.setVisibility(viewStatus == STATUS_NO_NETWORK ? View.VISIBLE: View.GONE);
		}

		text = null;
		imageId = 0;
	}

	/** 显示内容视图,如果调用showLoading等,则一定要调用该方法 */
	public final void showData() {
		viewStatus = STATUS_CONTENT;
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).setVisibility(View.VISIBLE);
		}
		if (null != loadingView) {
			loadingView.setVisibility(View.GONE);
		}
		if (null != emptyView) {
			emptyView.setVisibility(View.GONE);
		}
		if (null != errorView) {
			errorView.setVisibility(View.GONE);
		}
		if (null != noNetworkView) {
			noNetworkView.setVisibility(View.GONE);
		}
	}

}
