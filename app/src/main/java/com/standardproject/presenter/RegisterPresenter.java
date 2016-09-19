package com.standardproject.presenter;

import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.standardproject.TheApp;
import com.standardproject.bean.Root;
import com.standardproject.bean.RootRegister;
import com.standardproject.common.RegisterActivity;
import com.standardproject.model.IModel;
import com.standardproject.model.ModelImpl;
import com.standardproject.util.Type;
import com.standardproject.util.Urls;
import com.standardproject.view.IRegisterView;
import com.ugiant.util.AbStrUtil;
import com.ugiant.util.AbToastUtil;

public class RegisterPresenter implements BasePresenter{
	private IModel model;
	private IRegisterView resultView;
	private String phone_no;
	private RegisterActivity activity;

	public RegisterPresenter(IRegisterView resultView) {
		this.resultView=resultView;
		activity=(RegisterActivity) resultView;
		model=new ModelImpl();
	}

	public void getCode(){
		phone_no = resultView.getPhone();
		if(phone_no==null||TextUtils.isEmpty(phone_no)){
			AbToastUtil.showToast(TheApp.instance, "请输入手机号码!");
			return ;
		}
		if(!AbStrUtil.isMobileNO(phone_no)){
			AbToastUtil.showToast(TheApp.instance, "请输入正确的手机号码!");
			return ;
		}

		resultView.showLoding(Type.ONE);
		Map<String, String> map=new HashMap<String, String>();
		map.put("phone_no", phone_no);
		model.requestMethod(Type.ONE,Urls.GET_CODE, map, this);
	}

	public void codeResult(int type, String content){
		//Gson解析数据
		Gson gson=new Gson();
		RootRegister entity = gson.fromJson(content, RootRegister.class);
		boolean success = entity.isSuccess();
		if (!success) {
			String msg = gson.fromJson(content, Root.class).getMsg();
			resultView.showError(type,msg);
		}else {
			AbToastUtil.showToast(TheApp.instance, "成功下发至"+phone_no+",请注意查收!");
		}
	}

	public void doRegister(int reg_type){
		Map<String, String> map=new HashMap<String, String>();
		String nickName = resultView.getNickName();
		String code = resultView.getCode();
		//String phone = resultView.getPhone();
		String pwd = resultView.getPwd();

		if(nickName==null||TextUtils.isEmpty(nickName)){
			AbToastUtil.showToast(TheApp.instance, "请输入昵称");
			return;
		}
		if(code==null||TextUtils.isEmpty(code)){
			AbToastUtil.showToast(TheApp.instance, "请输入验证码");
			return;
		}
		/*if(phone==null){
			AbToastUtil.showToast(TheApp.instance, "请输入手机号码");
			return;
		}*/
		if(pwd==null||TextUtils.isEmpty(pwd)){
			AbToastUtil.showToast(TheApp.instance, "请输入密码");
			return;
		}

		resultView.showLoding(Type.TWO);
		map.put("login_name", nickName);
		map.put("password", pwd);
		map.put("reg_type", String.valueOf(reg_type));//(注册类型，1-手机，2-email，3-微信,4-QQ)
		map.put("code", code);
		model.requestMethod(Type.TWO,Urls.REGISTER, map, this);
	}

	public void registerResult(int type, String content){
		//Gson解析数据
		Gson gson=new Gson();
		RootRegister entity = gson.fromJson(content, RootRegister.class);
		boolean success = entity.isSuccess();
		if (!success) {
			String msg = gson.fromJson(content, Root.class).getMsg();
			resultView.showError(type,msg);
		}else {
			AbToastUtil.showToast(TheApp.instance, "注册成功!");
			activity.finish();
		}
	}



	@Override
	public void handlerSucceed(int type, String content) {
		resultView.showData(type);
		if(type==Type.ONE){
			codeResult(type,content);
		}else if(type==Type.TWO){
			registerResult(type, content);
		}
	}

	@Override
	public void showNoNetWork(int type, String msg) {
		resultView.showData(type);
		resultView.showNoNetWork(type, msg);
	}

}
