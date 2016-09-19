package com.standardproject.model;

import java.util.Map;

import com.standardproject.presenter.BasePresenter;

public interface IModel {

	/**
	 * 请求接口的公共方法
	 * @param type  请求类型(详见Type类)
	 * @param url   请求地址
	 * @param map   map封装的是参数
	 * @param presenter  presenter,用于回调数据结果
	 */
	void requestMethod(int type,String url,Map<String, String> map,final BasePresenter presenter);
}
