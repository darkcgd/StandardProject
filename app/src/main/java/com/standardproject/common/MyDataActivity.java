package com.standardproject.common;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.R.id;
import com.standardproject.R.layout;
import com.ugiant.AbActivity;
import com.ugiant.util.AbSharedUtil;

public class MyDataActivity extends AbActivity implements OnClickListener {


	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;
	private Button bt_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_data);

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
		tv_title_content.setText("我的");

		bt_exit = (Button) findViewById(R.id.bt_exit);
	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.bt_exit:
				AbSharedUtil.putInt(TheApp.instance, "user_id", -999);
				AbSharedUtil.putString(TheApp.instance, "user_name", "");
				AbSharedUtil.putString(TheApp.instance, "token", "");
				AbSharedUtil.putString(TheApp.instance, "head_img", "");
				finish();
				break;

			default:
				break;
		}
	}

}
