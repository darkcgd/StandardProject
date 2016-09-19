package com.standardproject;

import com.standardproject.R;
import com.standardproject.common.NewLayoutActivity;
import com.standardproject.common.PublicDemoActivity;
import com.ugiant.AbActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Activity_Main extends AbActivity implements OnClickListener {

	private RelativeLayout rl_demo;
	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__main);
		initViews();
		setListeners();
	}
	@Override
	public void initViews() {
		rl_demo = (RelativeLayout) findViewById(R.id.rl_demo);
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_left.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		tv_title_content.setText("主页");

	}


	@Override
	public void initData() {

	}


	@Override
	public void setListeners() {
		rl_demo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_demo:


				startActivity(new Intent(this,PublicDemoActivity.class));
			//	startActivity(new Intent(this,NewLayoutActivity.class));
				break;

			default:
				break;
		}

	}
}
