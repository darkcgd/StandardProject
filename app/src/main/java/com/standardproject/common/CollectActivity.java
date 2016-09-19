package com.standardproject.common;

import android.os.Bundle;
import android.provider.DocumentsContract.Root;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.util.Config;
import com.standardproject.util.MD5String;
import com.ugiant.AbActivity;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbSharedUtil;
import com.ugiant.util.AbToastUtil;
/**
 * CollectActivity
 * @author cgd
 * 2016-2-25
 */
public class CollectActivity extends AbActivity implements OnClickListener{
	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private int collectStatus;//收藏状态
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect);
		initViews();
		initData();
		setListeners();
	}

	@Override
	public void initViews() {
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		tv_title_right.setPadding(0,0,40,0);
		tv_title_right.setBackgroundResource(R.drawable.attention_no);

	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.tv_title_right: 	 //收藏,取消收藏
				if(collectStatus==0){
					tv_title_right.setBackgroundResource(R.drawable.attention);
					collectStatus=1;
					AbToastUtil.showToast(this, "收藏成功");
				}else{
					tv_title_right.setBackgroundResource(R.drawable.attention_no);
					collectStatus=1;
					AbToastUtil.showToast(this, "取消收藏成功");
				}
				//collectShop();
				break;

			default:
				break;
		}
	}

	/*private void collectShop() {
		String url = Config.getApi("/service/user/logined/addShopAttention");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();
		params.put("from_id", id);
		params.put("user_id", AbSharedUtil.getInt(TheApp.instance, "user_id"));
		// MD5加密后的签名
		String sign = MD5String.SignUser(AbSharedUtil.getInt(TheApp.instance, "user_id"), AbSharedUtil.getString(TheApp.instance, "token"));
		params.put("sign", sign);

		if(status==0){//（1-已关注，0-未关注）
			params.put("status", 1);
		}else{
			params.put("status", 2);
		}
		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				//Gson解析数据
				Gson gson=new Gson();
				Root rootMsg = gson.fromJson(content, Root.class);
				boolean success = rootMsg.getSuccess();
				if(!success){//判断是否返回成功
					ToastUtil.showUserTip(ShopDetailActivity.this, rootMsg);
				}else{
					if(status==0){//（1-已关注，0-未关注）
						ToastUtil.showToast(TheApp.instance, "关注成功");
						bt_title_right.setBackgroundResource(R.drawable.serve_serve);
						status=1;
					}else{
						ToastUtil.showToast(TheApp.instance, "取消关注成功");
						bt_title_right.setBackgroundResource(R.drawable.cosmetologist_like);
						status=0;
					}
				}

			};
			// 开始执行前
			@Override
			public void onStart() {
				//显示进度框
			}

			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				ToastUtil.showToast(TheApp.instance, error.getMessage());
			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() { 
			};

		});
	}*/
}
