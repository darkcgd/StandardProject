package com.standardproject.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.standardproject.R;
import com.standardproject.bean.RootCategory;
import com.ugiant.AbActivity;
import com.ugiant.util.AbAdapter;
import com.ugiant.util.AbToastUtil;
import com.ugiant.util.AbViewHolder;
/**
 * 下拉筛选
 * @author cgd
 * 2016-2-25
 */
public class DropDownActivity extends AbActivity implements OnClickListener {
	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private View view1;
	private ToggleButton tb1;
	private ToggleButton tb2;
	private ToggleButton tb3;

	private List<RootCategory> listProductCategory;//存放商品分类
	private List<RootCategory> listAreaCategory;  //存放区域分类
	private List<RootCategory> listConditionCategory;  //存放条件分类
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drop_down);
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

		view1=findViewById(R.id.view1);
		tb1=(ToggleButton) findViewById(R.id.tb1);
		tb2=(ToggleButton) findViewById(R.id.tb2);
		tb3=(ToggleButton) findViewById(R.id.tb3);
	}

	@Override
	public void initData() {
		listProductCategory=new ArrayList<RootCategory>();
		listAreaCategory=new ArrayList<RootCategory>();
		listConditionCategory=new ArrayList<RootCategory>();
		RootCategory category1=new RootCategory(1, "水果");
		RootCategory category2=new RootCategory(2, "小吃");
		RootCategory category3=new RootCategory(3, "鸡腿");
		listProductCategory.add(category1);
		listProductCategory.add(category2);
		listProductCategory.add(category3);

		RootCategory category44=new RootCategory(44, "全城");
		RootCategory category4=new RootCategory(4, "荔湾区");
		RootCategory category5=new RootCategory(5, "天河区");
		RootCategory category6=new RootCategory(6, "白云区");
		listAreaCategory.add(category44);
		listAreaCategory.add(category4);
		listAreaCategory.add(category5);
		listAreaCategory.add(category6);

		RootCategory category7=new RootCategory(7, "综合排序");
		RootCategory category8=new RootCategory(8, "最新推荐");
		RootCategory category9=new RootCategory(9, "热销");
		listConditionCategory.add(category7);
		listConditionCategory.add(category8);
		listConditionCategory.add(category9);

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);

		tb1.setOnClickListener(this);
		tb2.setOnClickListener(this);
		tb3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.tb1:
				createPopwindow(1,listProductCategory);
				break;
			case R.id.tb2:
				createPopwindow(2,listAreaCategory);
				break;
			case R.id.tb3:
				createPopwindow(3,listConditionCategory);
				break;

			default:
				break;
		}
	}

	private PopupWindow mPopupWindow;
	protected void createPopwindow(final int flag,List<RootCategory> list) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentview=inflater.inflate(R.layout.view_category, null);
		contentview.setFocusable(true);
		contentview.setFocusableInTouchMode(true);
		mPopupWindow = new PopupWindow(this);
		mPopupWindow.setContentView(contentview);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(-00000));
		mPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
		mPopupWindow.setHeight(LayoutParams.MATCH_PARENT);
		mPopupWindow.setAnimationStyle(R.style.categoryAnim);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(false);

		contentview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mPopupWindow.dismiss();
				return true;
			}
		});

		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if(flag==1){
					tb1.setChecked(false);
				}else if(flag==2){
					tb2.setChecked(false);
				}else if(flag==3){
					tb3.setChecked(false);
				}
				mPopupWindow=null;
			}
		});

		ListView lv = (ListView) contentview.findViewById(R.id.lv);
		AbAdapter<RootCategory> adapter=new AbAdapter<RootCategory>(DropDownActivity.this, list, R.layout.item_category) {

			@Override
			public void convert(AbViewHolder viewHolder, final RootCategory item,int position) {
				TextView tv_category = viewHolder.getView(R.id.tv_category);
				final String name = item.getName();
				if(name!=null){
					tv_category.setText(name);
				}else{
					tv_category.setText("");
				}
				tv_category.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mPopupWindow.dismiss();
						String tbName;
						if(name==null){
							tbName="";
						}else{
							tbName=name;
						}

						int id = item.getId();
						if(flag==1){
							tb1.setText(tbName);
						}else if(flag==2){
							tb2.setText(tbName);
						}else if(flag==3){
							tb3.setText(tbName);
						}
						AbToastUtil.showToast(DropDownActivity.this, "id:"+id+"  "+"name"+tbName);
					}
				});
			}
		};
		lv.setAdapter(adapter);
		mPopupWindow.showAsDropDown(view1);
	}

}
