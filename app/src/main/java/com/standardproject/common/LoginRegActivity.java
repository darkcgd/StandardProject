package com.standardproject.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
/**
 *
 * 登录注册界面
 * @author Administrator
 *
 */
public class LoginRegActivity extends AbActivity implements OnClickListener {

	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private TextView tv_login;
	private TextView tv_reg;
	private TextView tv_forget_pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_reg);

		initViews();
		setListeners();
		initData();
	}

	@Override
	public void initViews() {
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);

		tv_login = (TextView) findViewById(R.id.tv_login);
		tv_reg = (TextView) findViewById(R.id.tv_reg);
	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		tv_reg.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.tv_login:    // 登录
				startActivity(new Intent(this,LoginActivity.class));
				break;
			case R.id.tv_reg:     // 注册
				startActivity(new Intent(this,RegisterActivity.class));
				break;
			default:
				break;
		}
	}

}
