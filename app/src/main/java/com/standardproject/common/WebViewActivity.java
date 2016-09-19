package com.standardproject.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbDateUtil;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AbActivity implements View.OnClickListener {
    private TextView tv_title_left;
    private TextView tv_title_right;
    private TextView tv_title_content;

    private WebView wv;
    private ProgressBar bar;
    private final static int LOAD_URL = 1;
    private Handler handler;
    private String h5url="http://www.baidu.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initViews();
        initData();
        setListeners();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == LOAD_URL) {
                    String url = (String) msg.obj;
                    wv.loadUrl(url);
                }
            }
        };
    }


    @Override
    public void initViews() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setVisibility(View.INVISIBLE);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_content.setText("原生态和h5交互工具库抽离");

        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        wv = (WebView) findViewById(R.id.wv);

        animationSet = new AnimationSet(true);
        translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                1f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(800);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        animationSet.addAnimation(translateAnimation);

    }

    private AnimationSet animationSet;
    private TranslateAnimation translateAnimation;

    public void initData() {
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        //设置 缓存模式
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是定位 出不来的主要原因
        webSettings.setDomStorageEnabled(true);

        /**
         * 这个很重要,JSInterface()这个很重要,所有与js交互的方法都写在这里面,QiCha是JSInterface()的一个实例,例如:在js中通过QiCha.back()
         * 可以调用JSInterface()中的back();从而达到在js中调用android的方法
         */
        wv.addJavascriptInterface(new JSInterface(), "H5Helper");

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                //tel://13557738484
                if (url != null && url.contains("tel://")) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url.replace("//", "").trim()));
                    if (ActivityCompat.checkSelfPermission(WebViewActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return true;
                    }
                    startActivity(intent);
                    return true;
                }

                view.loadUrl(url);
                return true;
            }


            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
				/* This call inject JavaScript into the page which just finished loading. */
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) {
                //用javascript隐藏系统定义的404页面信息
                String data = "Page NO FOUND！";
                view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
            }

        });

        wv.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(title!=null){
                    tv_title_content.setText(title);
                }
            }


            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            //配置权限（同样在WebChromeClient中实现）
            public void onGeolocationPermissionsShowPrompt(String origin,GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        });
            if (h5url != null) {
                wv.loadUrl(h5url);
            }
        }

    @Override
    public void setListeners() {
        tv_title_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_left:
                // wv.loadUrl("javascript:appHelper.getForwardUrl();");
                finish();
                break;

            default:
                break;
        }
    }
    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
        }
        return false;
    }

    /**
     * 与js交互的类,
     *
     * @author cgd
     *         2015-11-16
     */
    class JSInterface {
        //因为安全问题，在Android4.2中(如果应用的android:targetSdkVersion数值为17+)JS只能访问带有 @JavascriptInterface注解的Java函数。
        //因此如果你的开发版本比较高，需要在被调用的函数前加上@JavascriptInterface注解。
        @JavascriptInterface
        public void setForwardUrl(String forwardUrl) {  //点击html中的  WisdomTravel.backToMain()返回首页图标

        }
    }




}
