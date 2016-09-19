package com.standardproject;





import com.standardproject.R;

import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends TabActivity {



	private static TabHost tabHost;
	private static ImageView imageView_main;   // 主页
	private static ImageView imageView_around;    //  周围
	private static ImageView imageView_myinfo; 	 	// 个人中心

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);


		initView();
		// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
		JPushInterface.init(getApplicationContext());
	}

	private void initView() {
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("a").setIndicator("A").setContent(new Intent(this, Activity_Main.class)));
		tabHost.addTab(tabHost.newTabSpec("b").setIndicator("B").setContent(new Intent(this, Activity_Category.class)));
		tabHost.addTab(tabHost.newTabSpec("c").setIndicator("C").setContent(new Intent(this, Activity_My.class)));

		imageView_main=(ImageView) findViewById(R.id.radio_main);
		imageView_around=(ImageView) findViewById(R.id.radio_around);
		imageView_myinfo=(ImageView) findViewById(R.id.radio_myinfo);


		CustomPushNotificationBuilder builder =
				new CustomPushNotificationBuilder(this,R.layout.customer_notitfication_layout,R.id.icon, R.id.title, R.id.text);
		builder.layoutIconDrawable = R.drawable.ic_warning_amber_56dp;
		builder.developerArg0 = "developerArg2";
		JPushInterface.setPushNotificationBuilder(0, builder);


	}

	public void onNavClick(View v){
		toNormalNav();
		switch (v.getId()) {
			case R.id.radio_main:
				//	imageView_main.setImageResource(R.drawable.nav_a_click3);
				//		imageView_main.setBackgroundResource(R.drawable.guide_home_on);
				imageView_main.setImageResource(R.drawable.guide_home_on);
				tabHost.setCurrentTabByTag("a");
				break;
			case R.id.radio_around:
				//		toClickNav(imageView_around, R.drawable.nav_c_click);
				toClickNav(imageView_around, R.drawable.guide_discover_on);
				tabHost.setCurrentTabByTag("b");
				break;
			/*case R.id.radio_shoppcar:
			toClickNav(imageView_shoppcar, R.drawable.nav_d_click);
			tabHost.setCurrentTabByTag("d");
			break;*/
			case R.id.radio_myinfo:
				// 不登录允许进入个人中心界面， 在个人中心点击后再判断
		/*	if (TextUtils.isEmpty(TheApp.PF.getLoginUid())) {
				IntentUtil.redirect(this, LoginActivity.class, false, null);
				return;
			}
			else
			{
			}
			*/
				//		toClickNav(imageView_myinfo, R.drawable.nav_e_click);
				toClickNav(imageView_myinfo, R.drawable.guide_account_on);
				tabHost.setCurrentTabByTag("c");
				break;

			default:
				break;
		}
	}



	public void goToCurrentTab(int currentitem){
		toNormalNav();
		switch (currentitem) {
			case 1://首页
				//		toClickNav(imageView_main, R.drawable.nav_a_click3);
				toClickNav(imageView_main, R.drawable.guide_home_on);
				tabHost.setCurrentTabByTag("a");
				break;
			case 2:
				//		toClickNav(imageView_around, R.drawable.nav_c_click);
				toClickNav(imageView_around, R.drawable.guide_discover_on);
				tabHost.setCurrentTabByTag("b");
				break;
			case 3:
				//		toClickNav(imageView_myinfo, R.drawable.nav_e_click);
				toClickNav(imageView_myinfo, R.drawable.guide_account_on);
				tabHost.setCurrentTabByTag("c");
				break;
			default:
				break;
		}
	}

	private static void toNormalNav(){

		imageView_main.setImageResource(R.drawable.guide_home_nm);
		imageView_around.setImageResource(R.drawable.guide_discover_nm);
		imageView_myinfo.setImageResource(R.drawable.guide_account_nm);
	}

	private static void toClickNav(ImageView view,int drawable){
		//	view.setBackgroundResource(drawable);
		view.setImageResource(drawable);
	}



}
