package com.standardproject.common;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.standardproject.R;
import com.standardproject.presenter.RegisterPresenter;
import com.standardproject.util.LoginUtils;
import com.standardproject.util.Type;
import com.standardproject.view.IRegisterView;
import com.standardproject.widget.LoadingDialog;
import com.standardproject.widget.TimeButton;
import com.ugiant.AbActivity;
import com.ugiant.util.AbToastUtil;

public class RegisterActivity extends AbActivity implements OnClickListener , IRegisterView{


	private Button bt_title_left;
	private TextView tv_title_name;

	private EditText et_nickname;
	private EditText et_phone;
	private EditText et_code;
	private EditText et_pwd;
	private EditText et_pwd2;
	private Button bt_reg;
	private TimeButton bt_get;

	private RegisterPresenter presenter;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initViews();
		initData();
		setListeners();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(presenter!=null){
			presenter=null;
		}
	}

	@Override
	public void initViews() {
		presenter=new RegisterPresenter(this);

		bt_title_left = (Button) findViewById(R.id.bt_title_left);
		tv_title_name = (TextView) findViewById(R.id.tv_title_name);
		tv_title_name.setText("注册");
		bt_get = (TimeButton) findViewById(R.id.bt_get);

		et_nickname = (EditText) findViewById(R.id.et_nickname);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_code = (EditText) findViewById(R.id.et_code);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		et_pwd2 = (EditText) findViewById(R.id.et_pwd2);
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
				presenter.getCode();
				break;
			case R.id.bt_reg: 	 // 普通注册
				presenter.doRegister(1);//(注册类型，1-手机，2-email，3-微信,4-QQ)
				break;

			default:
				break;
		}
	}

	@Override
	public String getNickName() {
		return et_nickname.getText().toString();
	}

	@Override
	public String getPhone() {
		return et_phone.getText().toString();
	}

	@Override
	public String getCode() {
		return et_code.getText().toString();
	}

	@Override
	public String getPwd() {
		return et_pwd.getText().toString();
	}

	@Override
	public void showError(int type, String msg) {
		LoginUtils.showUserTip(RegisterActivity.this, msg);
	}

	@Override
	public void showLoding(int type) {
		if(type==Type.ONE){
			progressDialog = LoadingDialog.createLoadingDialog(this, "正在下发验证码...");
			progressDialog.show();
		}else if (type==Type.TWO) {
			progressDialog = LoadingDialog.createLoadingDialog(this, "注册中...");
			progressDialog.show();
		}
	}

	@Override
	public void showData(int type) {
		if(progressDialog!=null){
			progressDialog.dismiss();
			progressDialog=null;
		}
	}

	@Override
	public void showEmpty(int type) {

	}

	@Override
	public void showNoNetWork(int type, String msg) {
		AbToastUtil.showToast(RegisterActivity.this, msg);
	}



}
