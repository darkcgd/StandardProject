package com.standardproject.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.Root;
import com.standardproject.bean.RootAdvertList;
import com.standardproject.common.AdListActivity;
import com.standardproject.model.IModel;
import com.standardproject.model.ModelImpl;
import com.standardproject.util.Type;
import com.standardproject.util.Urls;
import com.standardproject.view.IAdListView;
import com.ugiant.util.AbToastUtil;

public class AdListPresenter implements BasePresenter{
	private IModel model;
	private IAdListView resultView;
	private AdListActivity activity;
	private List<com.standardproject.bean.RootAdvertList.ListEntity> listBannar;

	public AdListPresenter(IAdListView resultView) {
		this.resultView=resultView;
		activity=(AdListActivity) resultView;
		model=new ModelImpl();
	}

	public void getBananList(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("code", "Home");
		model.requestMethod(Type.ONE,Urls.AD_LIST, map, this);
	}

	public void bananListResult(int type, String content){
		//Gson解析数据
		Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}
		RootAdvertList entity = gson.fromJson(content, RootAdvertList.class);
		boolean success = entity.isSuccess();
		if (!success) {
			String msg = gson.fromJson(content, Root.class).getMsg();
			resultView.showError(type,msg);
		}else {
			listBannar = entity.getList();
			int size=listBannar.size();
			if(size!=0){
				for (int i = 0; i < size; i++) {
					ImageView iv_play = new ImageView(activity);
					iv_play.setScaleType(ScaleType.FIT_XY);
					String image_url = listBannar.get(i).getImage_url();
					TheApp.mAbImageLoader.display(iv_play, image_url);
					resultView.addView(iv_play);
					//mSlidingPlayView.addView(iv_play);
				}
			}else{//如果无广告数据,设置一张默认图片
				ImageView iv_play = new ImageView(activity);
				iv_play.setScaleType(ScaleType.FIT_XY);
				iv_play.setBackgroundResource(R.drawable.ic_logo);
				resultView.addView(iv_play);
				//mSlidingPlayView.addView(iv_play);
			}

		}
	}


	@Override
	public void handlerSucceed(int type, String content) {
		resultView.showData(type);
		if(type==Type.ONE){
			bananListResult(type,content);
		}else if(type==Type.TWO){
		}
	}

	@Override
	public void showNoNetWork(int type, String msg) {
		resultView.showData(type);
		resultView.showNoNetWork(type, msg);
	}

	public void onItemClickListener(int position) {
		AbToastUtil.showToast(activity,"点击"+position);
	}

	public void onPageChangeListener(int position) {
		AbToastUtil.showToast(activity,"改变"+position);
	}

}
