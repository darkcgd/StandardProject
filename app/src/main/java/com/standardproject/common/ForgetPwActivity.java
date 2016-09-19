package com.standardproject.common;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.Root;
import com.standardproject.bean.RootRegister;
import com.standardproject.util.PostUtil;
import com.standardproject.util.Urls;
import com.standardproject.widget.TimeButton;
import com.ugiant.AbActivity;
import com.ugiant.http.AbRequestParams;
import com.ugiant.util.AbStrUtil;
import com.ugiant.util.AbToastUtil;

public class ForgetPwActivity extends AbActivity implements OnClickListener {


	private Button bt_title_left;
	private TextView tv_title_name;

	private EditText et_nickname;
	private EditText et_phone;
	private EditText et_code;
	private EditText et_pwd;
	private EditText et_pwd2;
	private Button bt_reg;
	private TimeButton bt_get;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pw);

		initViews();
		initData();
		setListeners();
	}

	@Override
	public void initViews() {

		bt_title_left = (Button) findViewById(R.id.bt_title_left);
		tv_title_name = (TextView) findViewById(R.id.tv_title_name);
		tv_title_name.setText("忘记密码");
		bt_get = (TimeButton) findViewById(R.id.bt_get);

		et_phone = (EditText) findViewById(R.id.et_phone);
		et_code = (EditText) findViewById(R.id.et_code);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		bt_reg = (Button) findViewById(R.id.bt_reg);

	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		bt_title_left.setOnClickListener(this);
		bt_reg.setOnClickListener(this);
		bt_get.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_title_left:
				finish();
				break;
			case R.id.bt_get: 	 // 获取验证码
				getCode();
				break;
			case R.id.bt_reg: 	 //完成修改
				//和 getCode();方法相同，暂无接口，不在此展示
				doReset();
				finish();
				break;

			default:
				break;
		}
	}
	//修改密码
	private void doReset() {
		String phone_no=et_phone.getText().toString();
		if(phone_no==null|| TextUtils.isEmpty(phone_no)){
			AbToastUtil.showToast(TheApp.instance, "请输入手机号码!");
			return ;
		}
		if(!AbStrUtil.isMobileNO(phone_no)){
			AbToastUtil.showToast(TheApp.instance, "请输入正确的手机号码!");
			return ;
		}
		String code=et_code.getText().toString();
		if(code==null|| TextUtils.isEmpty(code)){
			AbToastUtil.showToast(TheApp.instance, "请输入验证码!");
			return ;
		}
		AbToastUtil.showToast(TheApp.instance,"修改完成");
		//一下为调用接口
	}

	String phone_no;
	//获取验证码
	public void getCode(){
		phone_no=et_phone.getText().toString();
		if(phone_no==null|| TextUtils.isEmpty(phone_no)){
			AbToastUtil.showToast(TheApp.instance, "请输入手机号码!");
			return ;
		}
		if(!AbStrUtil.isMobileNO(phone_no)){
			AbToastUtil.showToast(TheApp.instance, "请输入正确的手机号码!");
			return ;
		}
		AbRequestParams params=new AbRequestParams();
		params.put("phone_no", phone_no);
		PostUtil.doPost(Urls.GET_CODE,params,bt_get, new PostUtil.DoPostCallBack() {
			@Override
			public void success(int statusCode, String content) {
				//Gson解析数据
				Gson gson=new Gson();
				RootRegister entity = gson.fromJson(content, RootRegister.class);
				boolean success = entity.isSuccess();
				if (!success) {
					String msg = gson.fromJson(content, Root.class).getMsg();
					AbToastUtil.showToast(TheApp.instance, msg);
				}else {
					AbToastUtil.showToast(TheApp.instance, "成功下发至"+phone_no+",请注意查收!");
				}
			}

			@Override
			public void failure(int statusCode, String content, Throwable error) {
				AbToastUtil.showToast(TheApp.instance, error.toString());
			}
		});
	}
}
