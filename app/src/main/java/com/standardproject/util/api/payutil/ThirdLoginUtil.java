package com.standardproject.util.api.payutil;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.standardproject.util.Config;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by chijiaduo on 2016/7/20.
 * 第三方登录
 */
public class ThirdLoginUtil {

    private Context context;
    private String QQ_APP_ID;
    private Tencent mTencent;
    String openId;  // QQ 登录唯一Id
    String imgUrl;   // QQ登录头像
    String nickname;    // QQ 昵称
    String gender;		// QQ 性别

    private IWXAPI api;

    public ThirdLoginUtil(Context context){
        this.context = context;



    }

    /**
     * 微信登录
     */
    public void WeChatLogin(){
        api = WXAPIFactory.createWXAPI(context, Config.APP_ID,  true);
        api.registerApp(Config.APP_ID);
        if(api.isWXAppInstalled()){
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "carjob_wx_login";
            api.sendReq(req);
        }else {
            Toast.makeText(context , "请先安装微信" , Toast.LENGTH_SHORT).show();
        }

    }

    ///////////////
    //////////////  qq登录
    //////////////
    public void QQLogin(){
        QQ_APP_ID = Config.QQ_APP_ID;
        mTencent = Tencent.createInstance(QQ_APP_ID, context);
        //如果session无效，就开始登录
        if (!mTencent.isSessionValid()) {
            //所有的SDK接口调用，都会传入一个回调，用以接收SDK返回的调用结果......
            //开始qq授权登录
            mTencent.login((Activity)context, "all", loginListener);
        } else {
            mTencent.logout(context);
            //updateUserInfo();
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
           // updateUserInfo();
        }
    };

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
                Toast.makeText(context, "返回为空,登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(context, "返回为空,登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(context, "授权成功!", Toast.LENGTH_SHORT).show();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values){

        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(context, "出错了:" + e.errorDetail, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(context, "您取消了授权!", Toast.LENGTH_SHORT).show();
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

   public interface IThirdLogin{
       /**
        * 登录成功
        */
       void handlerLoginSucceed();

       /**
        * 登录失败
        */
       void handlerLoginFail();

       /**
        * 取消登录
        */
       void handlerLoginCancel();

       /**
        * 登录中
        */
       void handlerLoginConfirming();
   }
}
