package com.standardproject.common;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbSharedUtil;
/**
 *
 *
 *  分享界面
 * @author Administrator
 *
 */
public class ShareActivity extends AbActivity implements OnClickListener {


	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private TextView tv_share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);

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
		tv_share = (TextView) findViewById(R.id.tv_share);

	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		tv_share.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.tv_share:
				AbSharedUtil.showShare(ShareActivity.this, "分享标题", "http://sharesdk.cn", "我是分享文本", "http://www.dzhome01.com/resources/index/images/logo.png",
						"http://sharesdk.cn", "我是测试评论文本", "QQ空间", "http://sharesdk.cn");
				break;

			default:
				break;
		}
	}

}
