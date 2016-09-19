/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.standardproject.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

import com.google.gson.Gson;
import com.standardproject.MainActivity;
import com.standardproject.TheApp;
import com.standardproject.util.Config;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbSharedUtil;
import com.ugiant.util.AbToastUtil;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler{

	String userName,passWord,userType,outShopId,mobilePhone,name;
	String openid;
	/**
	 * 处理微信发出的向第三方应用请求app message
	 * <p>
	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 * 做点其他的事情，包括根本不打开任何页面
	 */
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
		startActivity(iLaunchMyself);
	}

	/**
	 * 处理微信向第三方应用发起的消息
	 * <p>
	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
	 * 回调。
	 * <p>
	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 */
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null && msg.mediaObject != null
				&& (msg.mediaObject instanceof WXAppExtendObject)) {
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}




















	/** The Constant TAG. */
	private static final String TAG = "WXEntryActivity";
	private Context context = WXEntryActivity.this;
	public  IWXAPI api;

	Handler handler;

	private void handleIntent(Intent paramIntent) {
		api.handleIntent(paramIntent, this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, Config.APP_ID,  true);
		api.registerApp(Config.APP_ID);
		handleIntent(getIntent());
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}
	@Override
	public void onReq(BaseReq arg0) {
		Toast.makeText(context, "onReq成功", Toast.LENGTH_LONG).show();
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
					Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
					break;
				}
				String code = ((SendAuth.Resp) resp).code;
				System.out.println("微信确认登录返回的code：" + code);
				//Toast.makeText(context, "恭喜,微信授权登录成功!!!返回的code：" + code, Toast.LENGTH_LONG).show();

				//通过code获取token,openid;
				getToken(code);

				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				Toast.makeText(context, "拒绝授权", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Toast.makeText(context, "取消授权", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
		}
		finish();
	}

	private void getToken(String code) {
		//String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Constants.APP_ID+"&secret="+Constants.APPSECRET+"&code="+code+"&grant_type=authorization_code";
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();
		params.put("appid", Config.APP_ID);
		params.put("secret", Config.APPSECRET);
		params.put("code", code);
		params.put("grant_type", "authorization_code");
		TheApp.mAbHttpUtil.get(url,params, new AbStringHttpResponseListener() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {

				//Gson解析数据
				Gson gson=new Gson();
				RootWeiXinToken root = gson.fromJson(content, RootWeiXinToken.class);


				String access_token = root.getAccess_token();
				String expires_in = root.getExpires_in();
				String refresh_token = root.getRefresh_token();
				openid = root.getOpenid();
				String scope = root.getScope();
				String unionid = root.getUnionid();
				getUserInfo(access_token,openid);
			};
			// 开始执行前
			@Override
			public void onStart() {
				//显示进度框
			}

			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				AbToastUtil.showToast(TheApp.instance, error.getMessage());
			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {
			};

		});
	}

	protected void getUserInfo(String access_token, String openid) {
		String url = "https://api.weixin.qq.com/sns/userinfo";//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();
		params.put("access_token", access_token);
		params.put("openid", openid);
		TheApp.mAbHttpUtil.get(url,params, new AbStringHttpResponseListener() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {

				//Gson解析数据
				Gson gson=new Gson();
				RootWeiXinUserInfo root = gson.fromJson(content, RootWeiXinUserInfo.class);
				String nickname = root.getNickname();
				int sex = root.getSex();

				String city = root.getCity();
				String headimgurl = root.getHeadimgurl();
				String unionid = root.getUnionid();

				login(sex,nickname,unionid);
			};
			// 开始执行前
			@Override
			public void onStart() {
				//显示进度框
			}

			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				AbToastUtil.showToast(TheApp.instance, error.getMessage());
			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {
			};

		});

	}

	// 用户名密码登录
	public void login(int sex, String nickname, final String unionid) {
		String url = Config.getApi("/service/user/loginWeiXin");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();

		params.put("unionid", unionid);
		params.put("sex", sex);
		params.put("nickname", nickname);

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				//{"user_name":"oB8Vkvyb0BQIbkmCpn_uYpOCbJ9k","binded_phone":0,
				//"token":"45e2f04f384d4a858389bb142ad1ba36","user_id":45,"success":true}
				//Gson解析数据
				Gson gson=new Gson();
				RootUserWeiXInLoginInfo root = gson.fromJson(content, RootUserWeiXInLoginInfo.class);
				boolean success = root.isSuccess();

				if(!success){//判断是否返回成功
					AbToastUtil.showToast(TheApp.instance, "登录失败!");
					return;
				}else {
					AbSharedUtil.putBoolean(WXEntryActivity.this, "isOtherLogin", false);//登陆后设置为false,表示你重新登陆
					AbToastUtil.showToast(TheApp.instance, "登录成功!");
					// 保存个人信息
					AbSharedUtil.putString(WXEntryActivity.this, "user_name", root.getUser_name());  //  手机号
					AbSharedUtil.putString(WXEntryActivity.this, "token", root.getToken());
					AbSharedUtil.putInt(WXEntryActivity.this, "user_id", root.getUser_id());
					AbSharedUtil.putInt(WXEntryActivity.this, "binded_phone", root.getBinded_phone());//(1-已绑定 0-未绑定)
					AbSharedUtil.putString(WXEntryActivity.this, "login_type", "weixin");//登陆的类型,分为普通用户和微信,用于后面的判断(如修改手机)
					AbSharedUtil.putString(WXEntryActivity.this, "unionid", unionid);
					startActivity(new Intent(WXEntryActivity.this,MainActivity.class));
					finish();
				}

			};
			// 开始执行前
			@Override
			public void onStart() {
				//显示进度框
			}

			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				AbToastUtil.showToast(TheApp.instance, error.getMessage());
			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {
			};

		});



	}




}
