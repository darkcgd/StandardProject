package com.standardproject.view;

/**
 * view 接口父类 ,所有v接口都要继承这个父接口
 * @author cgd
 *
 */
public interface IBaseView {
	/**
	 * 处理接口返回的错误
	 * @param msg
	 */
	void showError(int type,String msg);
	/**
	 * 显示加载信息
	 */
	void showLoding(int type);
	/**
	 * 加载信息消失  显示内容视图
	 */
	void showData(int type);
	/**
	 * 数据为空 显示空视图
	 */
	void showEmpty(int type);
	/**
	 * 服务器连接失败视图
	 */
	void showNoNetWork(int type,String msg);
}
