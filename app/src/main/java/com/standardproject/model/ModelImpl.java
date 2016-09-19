package com.standardproject.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.standardproject.TheApp;
import com.standardproject.presenter.BasePresenter;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;

public class ModelImpl implements IModel {

	/**
	 * 请求接口的公共方法
	 * @param type  请求类型(详见Type类)
	 * @param url   请求地址
	 * @param map   map封装的是参数
	 * @param presenter  presenter,用于回调数据结果
	 */
	public void requestMethod(final int type,String url,Map<String, String> map,final BasePresenter presenter) {
		AbRequestParams params = new AbRequestParams();
		if(map!=null&&map.size()!=0){
			try {
				Set<Entry<String, String>> entrySet = map.entrySet();
				Iterator<Entry<String, String>> iterator = entrySet.iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String>  mapEntry = (Map.Entry<String, String>) iterator.next();
					params.put(mapEntry.getKey(), mapEntry.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				presenter.handlerSucceed(type,content);
			};
			// 开始执行前
			@Override
			public void onStart() {
				//显示进度框
			}

			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				presenter.showNoNetWork(type,content);
			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {

			};

		});
	}
}
