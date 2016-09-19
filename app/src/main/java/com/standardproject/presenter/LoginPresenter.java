package com.standardproject.presenter;

import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.standardproject.MainActivity;
import com.standardproject.TheApp;
import com.standardproject.bean.Root;
import com.standardproject.bean.RootLogin;
import com.standardproject.common.LoginActivity;
import com.standardproject.model.IModel;
import com.standardproject.model.ModelImpl;
import com.standardproject.util.Type;
import com.standardproject.util.Urls;
import com.standardproject.view.ILoginView;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.ugiant.util.AbSharedUtil;
import com.ugiant.util.AbToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter implements BasePresenter{
	private IModel model;
	private ILoginView resultView;
	private LoginActivity activity;

	public LoginPresenter(ILoginView resultView) {
		this.resultView=resultView;
		activity=(LoginActivity) resultView;
		model=new ModelImpl();
	}


	public void doLogin(int reg_type){
		Map<String, String> map=new HashMap<String, String>();
		String username = resultView.getUsername();
		String pwd = resultView.getPwd();

		if(username==null||TextUtils.isEmpty(username)){
			AbToastUtil.showToast(TheApp.instance, "请输入用户名");
			return;
		}
		if(pwd==null||TextUtils.isEmpty(pwd)){
			AbToastUtil.showToast(TheApp.instance, "请输入密码");
			return;
		}
		resultView.showLoding(Type.ONE);
		map.put("username", username);
		map.put("password", pwd);
		map.put("reg_type", String.valueOf(reg_type));//(注册类型，1-手机，2-email，3-微信,4-QQ)
		model.requestMethod(Type.ONE,Urls.LOGIN, map, this);
	}

	public  final String QQ_APP_ID="222222";
	public  Tencent mTencent;
	private UserInfo mInfo;//用户信息实体类
	private String openId;  // QQ 登录唯一Id
	private String imgUrl;   // QQ登录头像
	private String nickname;    // QQ 昵称
	private String gender;		// QQ 性别
	public void doThirdLogin(int reg_type){
		if(reg_type==4){
			mTencent = Tencent.createInstance(QQ_APP_ID, activity);
			//如果session无效，就开始登录
			if (!mTencent.isSessionValid()) {
				//所有的SDK接口调用，都会传入一个回调，用以接收SDK返回的调用结果......
				//开始qq授权登录
				mTencent.login(activity, "all", loginListener);
			} else {
				mTencent.logout(activity);
				updateUserInfo();
			}
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
						loginThird(2,imageUrl);//第三方帐号类型(1-微信，2-QQ)


					} catch (JSONException e1) {

						e1.printStackTrace();
					}


				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(activity, mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		}
	}

	protected void loginThird(int type, String imageUrl) {
		Map<String, String> map=new HashMap<String, String>();
		resultView.showLoding(Type.ONE);
		map.put("openId", openId);
		map.put("type", String.valueOf(type));
		model.requestMethod(Type.ONE,Urls.LOGIN, map, this);
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
				Toast.makeText(TheApp.instance, "返回为空,登录失败", Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				Toast.makeText(TheApp.instance, "返回为空,登录失败", Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(TheApp.instance, "授权成功!", Toast.LENGTH_SHORT).show();
			doComplete((JSONObject)response);

		}

		protected void doComplete(JSONObject values){

		}

		@Override
		public void onError(UiError e) {
			Toast.makeText(TheApp.instance, "出错了:" + e.errorDetail, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(TheApp.instance, "您取消了授权!", Toast.LENGTH_SHORT).show();
		}
	}


	public void onActivityResultData(int requestCode, int resultCode,Intent data) {
		mTencent.onActivityResultData(requestCode,resultCode,data,loginListener);
		if(requestCode == 10100) {
			if(resultCode == 10101) {
				mTencent.handleLoginData(data, loginListener);
			}
		}
	}


	public void loginResult(int type, String content){
		//Gson解析数据
		Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}
		RootLogin entity = gson.fromJson(content, RootLogin.class);

		boolean success = entity.isSuccess();

		if(!success){//判断是否返回成功
			String msg = gson.fromJson(content, Root.class).getMsg();
			resultView.showError(type,msg);
		}else {
			AbToastUtil.showToast(TheApp.instance, "登录成功!");
			// 保存个人信息
			AbSharedUtil.putString(TheApp.instance, "user_name", entity.getUser_name());  //  手机号
			AbSharedUtil.putString(TheApp.instance, "token", entity.getToken());
			AbSharedUtil.putString(TheApp.instance, "head_img", entity.getHead_img());
			AbSharedUtil.putInt(TheApp.instance, "user_id", entity.getUser_id());
			activity.startActivity(new Intent(TheApp.instance,MainActivity.class));
			activity.finish();
		}
	}



	@Override
	public void handlerSucceed(int type, String content) {
		resultView.showData(type);
		if(type==Type.ONE){
			loginResult(type,content);
		}else if(type==Type.TWO){
		}
	}

	@Override
	public void showNoNetWork(int type, String msg) {
		resultView.showData(type);
		resultView.showNoNetWork(type, msg);
	}

}
