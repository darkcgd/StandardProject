package com.standardproject.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.standardproject.util.Config;
import com.standardproject.bean.EventMsg;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Config.APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		/*Intent intent = new Intent();
		intent.setAction(Constants.WEIXIN_ACTION);
		intent.putExtra("errCode", resp.errCode);
		this.sendBroadcast(intent);*/

		EventBus.getDefault().post(new EventMsg(Config.WEIXIN_PAY,resp.errCode));
		finish();
	}


}