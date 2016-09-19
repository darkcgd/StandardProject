package com.standardproject.presenter;
/**
 * 所有presenter都实现这个接口
 * @author cgd
 * 2016-5-9
 */
public interface BasePresenter {
	void handlerSucceed(int type, String content);
	void showNoNetWork(int type, String msg);
}
