package com.standardproject.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by chijiaduo on 2016/7/28.
 */
public class ThirdLoginUtil implements Handler.Callback{
    private Context mContext;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;
    private Handler handler;
    public static String openID;
    public static String userImageUrl ;
    private String token, usergender, icon, name ;
    private long expire;
    public final static String QQ_LOGIN = QQ.NAME;
    public final static String WECHAT_LOGIN = Wechat.NAME;
    private Platform mPlatform;
    private HashMap<String, Object> userInfoMap;
    private PlatformDb platformDb;




    public ThirdLoginUtil(Context context) {
        this.mContext = context.getApplicationContext();
        ShareSDK.initSDK(mContext);
        handler = new Handler(Looper.getMainLooper(), this);



    }


    public void login(final String thirdPlatform) {
        mPlatform = ShareSDK.getPlatform(thirdPlatform);

        //采用ShareSDK里的自动授权
        if(mPlatform.isAuthValid()){
           mPlatform.removeAccount(true);
            return;
        }
        mPlatform.SSOSetting(false);
        mPlatform.setPlatformActionListener(new PlatformActionListener() {
            //hashmap的值会根据action的变化而变化，所以应该处理好hashmap的取值位置
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
                //打开登录窗口时获取到的用户数据
                if (action == Platform.ACTION_USER_INFOR) {
//                      userInfoMap = hashMap;
//                      platformDb = platform.getDb();
//                    Log.d("ThirdLoginUtil" , hashMap.toString());
//                    String map = hashMap.toString();
//                    PlatformDb platDB = platform.getDb();//获取数平台数据DB
//                    //通过DB获取各种数据
//                    token = platDB.getToken();
//                    expire = platDB.getExpiresTime();
//                    usergender = platDB.getUserGender();
//                    icon = platDB.getUserIcon();
//                    openID = platDB.getUserId();
//                    name = platDB.getUserName();
//                    userImageUrl = hashMap.get("figureurl_2").toString();
                    Message msg = new Message();
                    msg.what = MSG_AUTH_COMPLETE;
                    msg.arg2 = action;
                    msg.obj =  new Object[] {platform.getName(), hashMap , platform.getDb()}; //当前平台和用户信息
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onError(Platform platform, int action, Throwable throwable) {
                Message msg = new Message();
                msg.what = MSG_AUTH_ERROR;
                msg.arg2 = action;
                msg.obj =  new Object[] {platform.getName(), throwable}; //当前平台和用户信息
                handler.sendMessage(msg);



            }

            @Override
            public void onCancel(Platform platform, int action) {

                Toast.makeText(mContext, "取消授权", Toast.LENGTH_SHORT).show();

            }
        });
        mPlatform.showUser(null);

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_AUTH_COMPLETE:
                Object[] objs = (Object[]) msg.obj;
                String plat = (String) objs[0];
                @SuppressWarnings("unchecked")
                HashMap<String, Object> userInfo = (HashMap<String, Object>) objs[1];
                PlatformDb platformDb = (PlatformDb) objs[2];
                //传入当前平台和用户信息
                onThridLoginListener.getUserInfo(plat ,platformDb , userInfo  );
                break;
            case MSG_AUTH_ERROR:
                Object[] objs1 = (Object[]) msg.obj;
                String plat1 = (String) objs1[0];
                @SuppressWarnings("unchecked")
                Throwable throwable = (Throwable) objs1[1];
                String t = throwable.toString();
                if(t.equals("cn.sharesdk.wechat.utils.WechatClientNotExistException")){
                    Toast.makeText(mContext , "请先安装微信" , Toast.LENGTH_SHORT).show();

                }
        }
        return false;
    }





    private OnThridLoginListener onThridLoginListener;
    public void setOnThridLoginListener(OnThridLoginListener onThridLoginListener){
        this.onThridLoginListener = onThridLoginListener;
    }



    public interface OnThridLoginListener{

        /**
         * 获取第三方返回的openid
         * @param
         */
        public void getUserInfo(String platform, PlatformDb platformDb, HashMap<String, Object> userInfo);




    }
}
