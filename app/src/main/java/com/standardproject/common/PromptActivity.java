package com.standardproject.common;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbDialogUtil;

/***
 *   提醒方式界面
 *
 * @author Administrator
 *
 */
public class PromptActivity extends AbActivity implements OnClickListener {

	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;
	private TextView tv01,tv02,tv03;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prompt);
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
		tv_title_content.setText("提示界面");

		tv01 = (TextView) findViewById(R.id.tv01);
		tv02 = (TextView) findViewById(R.id.tv02);
		tv03 = (TextView) findViewById(R.id.tv03);

	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		tv01.setOnClickListener(this);
		tv02.setOnClickListener(this);
		tv03.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;

			// 进度框
			case R.id.tv01:
				AbDialogUtil.showProgressDialog(PromptActivity.this, 0,
						"查询中...");
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						AbDialogUtil.removeDialog(PromptActivity.this);

					}
				}, 3000);

				break;

			case R.id.tv02:
				break;
			case R.id.tv03:
				break;

			default:
				break;
		}
	}
}
