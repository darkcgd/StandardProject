package com.standardproject.common;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.RootAboutUs;
import com.standardproject.util.Config;
import com.ugiant.AbActivity;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbToastUtil;

/**
 *
 *  关于我们
 * @author Administrator
 *
 */
public class AboutActivity extends AbActivity implements OnClickListener {

	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private TextView tv_title;
	private ImageView iv_logo;
	private ImageView iv_qrcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

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
		tv_title_content.setText("关于我们");

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);
	}

	@Override
	public void initData() {
		getData();
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

			default:
				break;
		}
	}

	private void getData() {
		String url = Config.getApi("/common/getAboutUs");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {
			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				//Gson解析数据
				Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}
				RootAboutUs entity = gson.fromJson(content, RootAboutUs.class);
				boolean success = entity.isSuccess();
				if (success) {
					showData(entity);
				}else {
					AbToastUtil.showToast(AboutActivity.this, "获取失败!");
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
				AbToastUtil.showToast(TheApp.instance, error.getMessage());

			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {
			};

		});
	}

	/// 显示关于我们的详情
	private void showData(RootAboutUs entity) {
		tv_title.setText(entity.getDescription());
		TheApp.mAbImageLoader.display(iv_logo, entity.getLogo());
		TheApp.mAbImageLoader.display(iv_qrcode, entity.getQrcode());
	}

}
