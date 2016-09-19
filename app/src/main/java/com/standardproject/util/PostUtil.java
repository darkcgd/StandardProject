package com.standardproject.util;

import android.view.View;

import com.standardproject.TheApp;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbToastUtil;

/**
 * Created by yangpeixian on 2016/7/22.
 */
public class PostUtil {

    public static void doPost(String url, AbRequestParams params,final  View view,final DoPostCallBack callBack){
        if (view!=null){
            view.setClickable(false);
        }
        if (url==null){
            AbToastUtil.showToast(TheApp.instance,"url为空");
            if (view!=null){
                view.setClickable(true);
            }
            return;
        }else if(params==null){
            AbToastUtil.showToast(TheApp.instance,"params为空");
            if (view!=null){
                view.setClickable(true);
            }
            return;
        }else if(callBack==null){
            AbToastUtil.showToast(TheApp.instance,"callBack为空");
            if (view!=null){
                view.setClickable(true);
            }
            return;
        }
        TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {

            // 获取数据成功会调用这里
            @Override
            public void onSuccess(int statusCode, String content) {
                if (view!=null){
                    view.setClickable(true);
                }
                if (callBack!=null){
                    callBack.success(statusCode,content);
                }else{
                    throw new NullPointerException("callBack回调接口为空");
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
                if (view!=null){
                    view.setClickable(true);
                }
                if (callBack!=null){
                    callBack.failure(statusCode,content,error);
                }else{
                    throw new NullPointerException("callBack回调接口为空");
                }
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {

            };
        });
    }

    public interface DoPostCallBack{
        void success(int statusCode, String content);
        void failure(int statusCode, String content,Throwable error);
    }
}
