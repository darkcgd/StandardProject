package com.standardproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;

public class Activity_Category extends AbActivity {


	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_category);

		initViews();
		initData();
		setListeners();

	}


	@Override
	public void initViews() {
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_left.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		tv_title_content.setText("更多");
	}


	@Override
	public void initData() {

	}


	@Override
	public void setListeners() {

	}


}
