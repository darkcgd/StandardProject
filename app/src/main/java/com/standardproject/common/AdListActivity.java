package com.standardproject.common;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.standardproject.R;
import com.standardproject.presenter.AdListPresenter;
import com.standardproject.util.LoginUtils;
import com.standardproject.view.IAdListView;
import com.ugiant.AbActivity;
import com.ugiant.util.AbToastUtil;
import com.ugiant.widget.sliding.AbSlidingPlayView;

/**
 *
 *  广告轮播界面
 * @author Administrator
 *
 */
public class AdListActivity extends AbActivity implements OnClickListener,IAdListView{

	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private AbSlidingPlayView mSlidingPlayView = null;
	private AdListPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advert_list);

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
		presenter=new AdListPresenter(this);
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		tv_title_content.setText("广告轮播");


		mSlidingPlayView = (AbSlidingPlayView)findViewById(R.id.mAbSlidingPlayView);
		mSlidingPlayView.setNavPageResources(R.drawable.play_display,R.drawable.play_hide);
		mSlidingPlayView.setNavHorizontalGravity(Gravity.CENTER_HORIZONTAL);
		mSlidingPlayView.startPlay();
	}

	@Override
	public void initData() {
		presenter.getBananList();
	}

	@Override
	public void finish() {
		mSlidingPlayView.stopPlay();
		super.finish();
	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);

		mSlidingPlayView.setOnItemClickListener(new AbSlidingPlayView.AbOnItemClickListener() {

			@Override
			public void onClick(int position) {
				presenter.onItemClickListener(position);
			}
		});

		mSlidingPlayView.setOnPageChangeListener(new AbSlidingPlayView.AbOnChangeListener() {

			@Override
			public void onChange(int position) {
				presenter.onPageChangeListener(position);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;

			default:
				break;
		}
	}

	@Override
	public void showError(int type, String msg) {
		LoginUtils.showUserTip(AdListActivity.this, msg);
	}

	@Override
	public void showLoding(int type) {

	}

	@Override
	public void showData(int type) {

	}

	@Override
	public void showEmpty(int type) {

	}

	@Override
	public void showNoNetWork(int type, String msg) {
		AbToastUtil.showToast(AdListActivity.this, msg);
	}

	@Override
	public void addView(ImageView iv_play) {
		mSlidingPlayView.addView(iv_play);
	}

}
