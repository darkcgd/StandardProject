package com.standardproject.common;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbToastUtil;

/**
 *
 *
 *   没数据提醒界面
 * @author Administrator
 *
 */
public class NoDataActivity extends AbActivity implements OnClickListener {

	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;


	private Button bt_show;
	private View map_stub;
	private Button bt_show02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_data);
		initViews();
		initData();
		setListeners();
	}

	@Override
	public void initViews() {
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
//		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		tv_title_content.setText("数据为空界面");

		bt_show  = (Button) findViewById(R.id.bt_show);
		map_stub = findViewById(R.id.map_stub);

		map_stub.setVisibility(View.VISIBLE);



		bt_show02 = (Button) findViewById(R.id.bt_show02);
		bt_show02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AbToastUtil.showToast(NoDataActivity.this, "正在重新加载!");
				map_stub.setVisibility(View.GONE);
			}
		});



	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		bt_show.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;

			case R.id.tv_title_right:


				break;



			default:
				break;
		}
	}

}
