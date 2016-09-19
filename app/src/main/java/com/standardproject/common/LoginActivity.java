package com.standardproject.common;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.standardproject.R;
import com.standardproject.presenter.LoginPresenter;
import com.standardproject.util.Config;
import com.standardproject.util.LoginUtils;
import com.standardproject.util.api.payutil.ThirdLoginUtil;
import com.standardproject.view.ILoginView;
import com.standardproject.widget.LoadingDialog;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.ugiant.AbActivity;
import com.ugiant.util.AbToastUtil;
import com.ugiant.widget.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AbActivity implements OnClickListener,ILoginView {


	private Button bt_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;
	private ClearEditText et_phone_number;
	private ClearEditText et_password;
	private TextView tv_login;
	private TextView tv_weixin;  // 微信登录
	private TextView tv_qq;  // QQ登录
	private TextView tv_regist;    // 手机注册
	private TextView tv_forget;    // 忘记密码

	String openId;  // QQ 登录唯一Id
	String imgUrl;   // QQ登录头像
	String nickname;    // QQ 昵称
	String gender;		// QQ 性别

	private LoginPresenter presenter;
	private Dialog progressDialog;

	private ThirdLoginUtil thirdLoginUtil;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initViews();
		initData();
		setListeners();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(presenter!=null){
			presenter=null;
		}
	}

	@Override
	public void initViews() {
		presenter=new LoginPresenter(this);
		bt_title_left = (Button) findViewById(R.id.bt_title_left);

		et_phone_number = (ClearEditText) findViewById(R.id.et_phone_number);
		et_password = (ClearEditText) findViewById(R.id.et_password);
		tv_login = (TextView) findViewById(R.id.tv_login);
		tv_weixin = (TextView) findViewById(R.id.tv_weixin);
		tv_qq = (TextView) findViewById(R.id.tv_qq);
		tv_regist = (TextView) findViewById(R.id.tv_regist);
		tv_forget = (TextView) findViewById(R.id.tv_forget);

	}

	@Override
	public void initData() {
		thirdLoginUtil = new ThirdLoginUtil(this);

	}

	@Override
	public void setListeners() {
		bt_title_left.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		tv_weixin.setOnClickListener(this);
		tv_qq.setOnClickListener(this);
		tv_regist.setOnClickListener(this);
		tv_forget.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_title_left:
				finish();
				break;
			case R.id.tv_login:   // 普通登录
				presenter.doLogin(1);
				break;
			case R.id.tv_weixin:    // 微信登录
				//weixinLogin();
				thirdLoginUtil.WeChatLogin();
				break;
			case R.id.tv_qq:    // QQ登录
				QQLogin();
				break;
			case R.id.tv_regist:    //  手机注册
				startActivity(new Intent(this,RegisterActivity.class));
				break;
			case R.id.tv_forget:
				startActivity(new Intent(this,ForgetPwActivity.class));
				break;
			default:
				break;
		}

	}

	/**
	 * 微信登陆
	 */
	private IWXAPI api;
	private void weixinLogin() {
		api = WXAPIFactory.createWXAPI(this, Config.APP_ID,  true);
		api.registerApp(Config.APP_ID);

		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "carjob_wx_login";
		api.sendReq(req);
	}


	/**
	 * QQ登录
	 */
	public static final String QQ_APP_ID="222222";
	public static Tencent mTencent;
	private UserInfo mInfo;//用户信息实体类

	private void QQLogin() {

		mTencent = Tencent.createInstance(QQ_APP_ID, LoginActivity.this);
		//如果session无效，就开始登录
		if (!mTencent.isSessionValid()) {
			//所有的SDK接口调用，都会传入一个回调，用以接收SDK返回的调用结果......
			//开始qq授权登录
			mTencent.login(this, "all", loginListener);
		} else {
			mTencent.logout(this);
			updateUserInfo();
		}

	}

	/**
	 * 获得token和openid
	 * @param jsonObject
	 */
	public  void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString("access_token");
			String expires = jsonObject.getString("expires_in");

			openId = jsonObject.getString("openid");    //  QQ  唯一标识


			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}

		} catch(Exception e) {
		}
	}
	/**
	 * 授权登录的回调  ,BaseUiListener实现IUiListener,为了代码好看,抽取doComplete()进行逻辑判断
	 */
	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			//获得token和openid
			initOpenidAndToken(values);
			//更新用户信息(昵称和头像)
			updateUserInfo();
		}
	};

	/**
	 * 更新用户信息(昵称和头像)
	 */
	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					/**
					 * {"is_yellow_year_vip":"0",
					 * "ret":0,
					 * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/222222\/E5F3F2E20C3BA9FDD6088B4705BCD837\/40",
					 * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/222222\/E5F3F2E20C3BA9FDD6088B4705BCD837\/100",
					 * "nickname":"Dark",
					 * "yellow_vip_level":"0",
					 * "is_lost":0,"msg":"",
					 * "city":"茂名",
					 * "figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/222222\/E5F3F2E20C3BA9FDD6088B4705BCD837\/50",
					 * "vip":"0",
					 * "level":"0",
					 * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/222222\/E5F3F2E20C3BA9FDD6088B4705BCD837\/100",
					 * "province":"广东",
					 * "is_yellow_vip":"0",
					 * "gender":"男",
					 * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/222222\/E5F3F2E20C3BA9FDD6088B4705BCD837\/30"}
					 */

					try {
						JSONObject json = (JSONObject)response;

						// 获取用户信息
						nickname = json.getString("nickname");
						gender = json.getString("gender");
						String imageUrl = json.getString("figureurl_2");
						//修改性别和头像

						//第三方帐号登录(QQ)
						//			loginThird(2,imageUrl);//第三方帐号类型(1-微信，2-QQ)


					} catch (JSONException e1) {

						e1.printStackTrace();
					}


				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		} else {

		}
	}

	private Handler handler;

	/**
	 * 应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码：
	 */
	@SuppressWarnings("static-access")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		presenter.onActivityResultData(requestCode,resultCode,data);

	}



	/**
	 * 调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
	 * @author cgd
	 *
	 * 2015-8-22
	 */
	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				Toast.makeText(LoginActivity.this, "返回为空,登录失败", Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				Toast.makeText(LoginActivity.this, "返回为空,登录失败", Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(LoginActivity.this, "授权成功!", Toast.LENGTH_SHORT).show();
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values){

		}

		@Override
		public void onError(UiError e) {
			Toast.makeText(LoginActivity.this, "出错了:" + e.errorDetail, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(LoginActivity.this, "您取消了授权!", Toast.LENGTH_SHORT).show();
		}
	}



	@Override
	public void showError(int type, String msg) {
		LoginUtils.showUserTip(LoginActivity.this, msg);
	}

	@Override
	public void showLoding(int type) {
		progressDialog = LoadingDialog.createLoadingDialog(this, "登录中...");
		progressDialog.show();
	}

	@Override
	public void showData(int type) {
		if(progressDialog!=null){
			progressDialog.dismiss();
			progressDialog=null;
		}
	}

	@Override
	public void showEmpty(int type) {

	}

	@Override
	public void showNoNetWork(int type, String msg) {
		AbToastUtil.showToast(this, msg);
	}

	@Override
	public String getUsername() {
		return et_phone_number.getText().toString();
	}

	@Override
	public String getPwd() {
		return et_password.getText().toString();
	}




}
