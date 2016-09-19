package com.standardproject.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
/**
 * 忘记密码,修改密码
 * @author cgd
 * 2016-2-25
 */
public class UpdatePwdActivity extends AbActivity implements OnClickListener{
	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private TextView tv_forget_pwd;//忘记密码
	private TextView tv_update_pwd;//修改密码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pwd);
		initViews();
		initData();
		setListeners();
	}

	@Override
	public void initViews() {
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);

		tv_forget_pwd=(TextView) findViewById(R.id.tv_forget_pwd);
		tv_update_pwd=(TextView) findViewById(R.id.tv_update_pwd);
	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		tv_forget_pwd.setOnClickListener(this);
		tv_update_pwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.tv_forget_pwd: 	 // 忘记密码
				startActivity(new Intent(this,ForgetPwdActivity.class));
				break;
			case R.id.tv_update_pwd: 	 // 修改密码
				startActivity(new Intent(this,UpdatePwdActivity.class));
				break;

			default:
				break;
		}
	}

}
