package com.standardproject;

import android.app.Application;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.crashreport.CrashReport;
import com.ugiant.http.AbHttpUtil;
import com.ugiant.image.AbImageLoader;

import cn.jpush.android.api.JPushInterface;

import java.util.Map;

public class TheApp  extends Application{
	// 用于存放倒计时时间
	public static Map<String, Long> map;


	public static int screenWidth;//屏幕宽度
	public static int screenHeight; //屏幕高度

	public static AbHttpUtil mAbHttpUtil = null;
	public static TheApp instance;
	public static AbImageLoader mAbImageLoader;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		initHttpUtil();//初始化Http工具类
		getScreen();//获取屏幕宽高值
		initImageLoader();//初始化ImageLodaer
		initCrashException();//开启全局异常捕捉

		/**极光推送*/
		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);     		// 初始化 JPush


		SDKInitializer.initialize(this);
	}

	private void initCrashException() {
		//自己捕捉异常上传到自己服务器
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

		//使用第三方:腾讯的Bugly
		CrashReport.initCrashReport(getApplicationContext(), "900040729", false);
	}


	private void getScreen() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		if(screenWidth==0||screenHeight==0){
			screenWidth=720;
			screenHeight=1080;
		}
	}

	private void initHttpUtil() {
		mAbHttpUtil = AbHttpUtil.getInstance(this);
		mAbHttpUtil.setTimeout(10000);
	}

	private void initImageLoader() {
		mAbImageLoader=AbImageLoader.getInstance(this);
		mAbImageLoader.setErrorImage(R.drawable.ic_launcher);
		mAbImageLoader.setEmptyImage(R.drawable.ic_launcher);
	}



}
