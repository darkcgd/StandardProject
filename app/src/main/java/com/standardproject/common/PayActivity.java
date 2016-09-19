package com.standardproject.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.standardproject.MainActivity;
import com.standardproject.R;
import com.standardproject.util.Config;
import com.standardproject.bean.EventMsg;
import com.standardproject.util.api.payutil.PayUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.ugiant.AbActivity;
import com.ugiant.util.AbSnackBarUtil;
import com.ugiant.util.AbToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/***
 * 	使用说明
 *  1.微信支付把包名.wxapi 包名复制到新项目的主目录下
 *  2.在config中配置微信支付宝等参数
 *  3.实现PayUtil.IPayUtil接口
 *  4.创建对象PayUtil payUtil=new PayUtil(this);
 *  5.设置监听payUtil.setPayListener(this);
 *  6.payUtil.weixinpay(...)和payUtil.zhifubaoPay(...)启动支付
 *  7.zhifubaoPay的结果回调:::重写handlerPaySucceed(),handlerPayFail(),handlerPayCancel(),handlerPayConfirming()方法处理支付结果
 *	  weixinpay的结果回调:::使用EventBus,接收结果,处理支付结果
 *
 *
 *
 * 支付界面(选择支付方式)
 * @author Administrator
 *
 */
public class PayActivity extends AbActivity implements OnClickListener,PayUtil.IPayUtil {
	private RelativeLayout re_weixin;
	private RelativeLayout re_zhifubao;
	private RelativeLayout re_yinlian;

	private TextView tv_title_left;
	private TextView tv_title_right;

	private TextView tv_title_content;
	private CoordinatorLayout coordinator;

	private PayUtil payUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		initViews();
		setListeners();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	/**
	 * 与发布者在同一个线程
	 * @param msg 事件
	 */
	@Subscribe
	public void onEvent(EventMsg msg){
		String mMsg = msg.getMsg();
		if(mMsg!=null&& Config.WEIXIN_PAY.equals(mMsg)){
			int errCode = msg.getPosition();
			if (errCode == -2) { // -2表示用户取消支付
				AbToastUtil.showToast(PayActivity.this, "用户取消支付!");
			} else if (errCode == 0) {
				AbToastUtil.showToast(PayActivity.this, "支付成功!");
				// 跳转到我的订单列表
				Intent intent = new Intent(PayActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	@Override
	public void initViews() {
		payUtil=new PayUtil(this);
		payUtil.setPayListener(this);
		EventBus.getDefault().register(this);

		re_weixin = (RelativeLayout) findViewById(R.id.re_weixin);
		re_zhifubao = (RelativeLayout) findViewById(R.id.re_zhifubao);
		re_yinlian = (RelativeLayout) findViewById(R.id.re_yinlian);

		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		coordinator = (CoordinatorLayout) findViewById(R.id.coordinator);

	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		re_weixin.setOnClickListener(this);
		re_zhifubao.setOnClickListener(this);
		re_yinlian.setOnClickListener(this);
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.re_weixin:   // 微信
				payUtil.weixinpay("商品描述", "88888888", "0.01",null); // 微信支付
				break;
			case R.id.re_zhifubao:      /// 支付宝
				payUtil.zhifubaoPay(PayActivity.this, "商品名称", "商品描述", "88888888", "0.01",null);
				break;
			case R.id.re_yinlian:         // 银联
				break;

			default:
				break;
		}
	}





	@Override
	public void handlerPaySucceed() {
		AbToastUtil.showToast(PayActivity.this, "支付成功");
		// 跳转到我的订单列表
		Intent intent = new Intent(PayActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void handlerPayFail() {
		AbToastUtil.showToast(PayActivity.this, "支付失败");
	}

	@Override
	public void handlerPayCancel() {
		/*Snackbar snackbar =Snackbar.make(coordinator, "您取消了支付", Snackbar.LENGTH_LONG);
		((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.white));
		snackbar.show();*/

		AbSnackBarUtil.ShortSnackbar(coordinator,"您取消了支付",AbSnackBarUtil.white,AbSnackBarUtil.black).show();
		/*AbSnackBarUtil.ShortSnackbar(coordinator,"您取消了支付",AbSnackBarUtil.white,AbSnackBarUtil.black)
				.setActionTextColor(AbSnackBarUtil.green).setAction("取消", new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		}).show();*/

		//AbToastUtil.showToast(PayActivity.this, "您取消了支付");
	}

	@Override
	public void handlerPayConfirming() {
		AbToastUtil.showToast(PayActivity.this, "支付结果确认中");
	}


}
