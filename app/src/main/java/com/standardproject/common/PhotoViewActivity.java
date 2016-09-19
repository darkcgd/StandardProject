package com.standardproject.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.util.Config;
import com.standardproject.widget.PhotoView;
import com.ugiant.AbActivity;
/**
 * 多图展示
 * @author cgd
 * 2016-2-25
 */
public class PhotoViewActivity extends AbActivity implements OnClickListener,OnPageChangeListener {
	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private ImageView iv_logo;
	private List<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view);
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
		iv_logo = (ImageView) findViewById(R.id.iv_logo);
	}

	@Override
	public void initData() {
		list=new ArrayList<String>();
		list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1784234501,600865947&fm=58");
		list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1934436338,1435084825&fm=58&s=93B654844C1108CE09167D8B0300309A");
		list.add("https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=3014168813,3622929424&fm=96&s=4518E83A5CAE750940D955DB010080B0");
		list.add("https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=3645252337,3856643642&fm=96&s=7882E81A570E7CEB54C5D0DA020010B2");
	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		iv_logo.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.iv_logo:
				showPhoneView();  //
				break;

			default:
				break;
		}
	}

	Dialog dialog;
	private TextView tv_show_num;
	// 显示点击放大的图片
	private void showPhoneView() {
		View view = getLayoutInflater().inflate(R.layout.item_show_phoneview,null);
		dialog = new Dialog(this,R.style.ChoiceWaiterDialog);
		dialog.setContentView(view, new LayoutParams((int) (TheApp.screenWidth),// 设置为屏幕宽度的0.9
				(int) (TheApp.screenHeight * 0.5)));//设置为屏幕高度的0.8
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		ViewPager mPager = (ViewPager) view.findViewById(R.id.pager);
		tv_show_num = (TextView) view.findViewById(R.id.tv_show_num);
		mPager.setOnPageChangeListener(this);
		tv_show_num.setText("1/"+list.size());
		mPager.setAdapter(new PagerAdapter() {
			//boolean show = true;
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				PhotoView view = new PhotoView(PhotoViewActivity.this);
				view.setScaleType(ScaleType.FIT_XY);
				view.enable();
				String main_pic = list.get(position);
				TheApp.mAbImageLoader.display(view, main_pic);
				container.addView(view);
				return view;
			}
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}
			public int getCount() {
				return list.size();
			}
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}
		});

	}
	/***
	 * 以下三个方法为了显示当前的张数
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}
	@Override
	public void onPageScrolled(int positionStart, float arg1, int positionEnd) {

	}
	@Override
	public void onPageSelected(int position) {
		position+=1;
		int size=list.size();
		tv_show_num.setText(position+"/"+size);
	}

}
