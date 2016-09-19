package com.standardproject.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.standardproject.MainActivity;
import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbSharedUtil;
import com.ugiant.widget.sliding.AbSlidingPlayView;
import com.ugiant.widget.sliding.AbSlidingPlayView.AbOnChangeListener;
/**
 * GuideActivity
 * @author cgd
 * 2016-2-25
 */
public class GuideActivity extends AbActivity implements OnClickListener{
	private AbSlidingPlayView mSlidingPlayView = null;
	private TextView tv_guide;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initViews();
		initData();
		setListeners();

	}
	@Override
	public void initViews() {
		tv_guide = (TextView)findViewById(R.id.tv_guide);
		mSlidingPlayView = (AbSlidingPlayView)findViewById(R.id.mAbSlidingPlayView);
		mSlidingPlayView.setNavPageResources(R.drawable.play_display,R.drawable.play_hide);
		mSlidingPlayView.setNavHorizontalGravity(Gravity.CENTER_HORIZONTAL);

	}
	@Override
	public void initData() {
		for (int i = 0; i < 3; i++) {
			ImageView iv_play = new ImageView(GuideActivity.this);
			iv_play.setScaleType(ScaleType.FIT_XY);
			iv_play.setBackgroundResource(R.drawable.ic_logo);
			mSlidingPlayView.addView(iv_play);
		}
	}
	@Override
	public void setListeners() {
		tv_guide.setOnClickListener(this);

		mSlidingPlayView.setOnPageChangeListener(new AbOnChangeListener() {

			@Override
			public void onChange(int position) {
				if(position==2){
					tv_guide.setVisibility(View.VISIBLE);
				}else{
					tv_guide.setVisibility(View.GONE);
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_guide:
				AbSharedUtil.putBoolean(GuideActivity.this, "flag", false);
				startActivity(new Intent(GuideActivity.this,MainActivity.class));
				finish();
				break;

			default:
				break;
		}
	}

}
