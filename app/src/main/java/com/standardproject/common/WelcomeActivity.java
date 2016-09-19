package com.standardproject.common;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import com.standardproject.MainActivity;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.ugiant.util.AbSharedUtil;
/**
 * 欢迎页
 * @author cgd
 * 2016-2-25
 */
public class WelcomeActivity extends Activity{
	private long m_dwSplashTime=500;//(一般2000)
	private boolean m_bPaused=false;
	private boolean m_bSplashActive=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*//去title
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_welcome);
		Thread splashTimer=new Thread()
		{
			public void run(){
				try{
					//wait loop
					long ms=0;
					while(m_bSplashActive && ms<m_dwSplashTime){
						sleep(100);

						if(!m_bPaused)
							ms+=100;
					}

					boolean flag=AbSharedUtil.getBoolean(getApplicationContext(), "flag", true);
					//	flag="true";
					if(flag){
						//第一次使用进入新手指导页
						startActivity(new Intent(getApplicationContext(),GuideActivity.class));
					}else{
						//非第一次直接进入主页面
						startActivity(new Intent(getApplicationContext(),MainActivity.class));
					}

				}
				catch(Exception ex){
					Log.e("Splash",ex.getMessage());
				}
				finally{
					finish();
				}
			}
		};
		splashTimer.start();
	}
	@Override
	protected void onPause() {
		super.onPause();
		m_bPaused=true;

	}

	@Override
	protected void onResume() {
		super.onResume();
		m_bPaused=false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch(keyCode){
			case KeyEvent.KEYCODE_MENU:
				m_bSplashActive=false;
				break;
			case KeyEvent.KEYCODE_BACK:
			/*两种退出方法*/
			/* System.exit(0);*/
			/* android.os.Process.killProcess(android.os.Process.myPid());*/
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			default:
				break;
		}
		return true;
	}

}